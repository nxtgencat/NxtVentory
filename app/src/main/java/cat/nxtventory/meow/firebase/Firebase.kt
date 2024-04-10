package cat.nxtventory.meow.firebase


import android.accounts.NetworkErrorException
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


object UserDataManager {
    fun saveUserInfo(context: Context, userID: String) {
        val sharedPref = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("userID", userID)
            apply()
        }
    }


    fun isLoggedIn(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        val userID = sharedPref.getString("userID", null)
        return userID != null
    }

    fun signOut(context: Context, onSignOut: () -> Unit) {
        val sharedPref = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            remove("userID")
            apply()
        }
        onSignOut() // Call the callback after removing user ID
    }

    suspend fun getUserDetails(context: Context): Map<String, Any>? {
        val sharedPref = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        val userID = sharedPref.getString("userID", null)
        val db = FirebaseFirestore.getInstance()
        val userDoc = userID?.let { db.collection("users").document(it).get().await() }
        return userDoc?.data
    }
}

fun isPasswordValid(password: String): Boolean {
    val passwordRegex = Regex("^.{6,}\$")
    return password.matches(passwordRegex)
}

fun isUsernameValid(username: String): Boolean {
    val usernameRegex = Regex("^[a-zA-Z0-9_.-]{3,20}$")
    return username.matches(usernameRegex)
}

fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+\$")
    return email.matches(emailRegex)
}

fun isPasswordStrong(password: String): Boolean {
    val passwordRegex =
        Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+\$).{8,}\$")
    return password.matches(passwordRegex)
}

object FirebaseManager {

    fun fetchEmailForUsername(username: String, onComplete: (String?, String?) -> Unit) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .whereEqualTo("username", username)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val querySnapshot = task.result
                    if (querySnapshot != null && !querySnapshot.isEmpty) {
                        val email = querySnapshot.documents.first().getString("email")
                        onComplete(email, null) // Email found
                    } else {
                        onComplete(null, "Username not found")
                    }
                } else {
                    onComplete(null, "Error fetching email: ${task.exception?.message}")
                }
            }
    }



    // Sign in with email and password
    fun signIn(email: String, password: String, onComplete: (FirebaseUser?, String?) -> Unit) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null && user.isEmailVerified) {
                        onComplete(user, null)
                    } else {
                        onComplete(null, "Email is not verified.")
                    }
                } else {
                    val errorMessage = getSignInErrorMessage(task.exception)
                    onComplete(null, errorMessage)
                }
            }
    }

    private fun getSignInErrorMessage(exception: Exception?): String {
        return when (exception) {
            is FirebaseAuthInvalidUserException -> {
                if (exception.errorCode == "ERROR_USER_DISABLED") {
                    "Your account has been disabled."
                } else {
                    "Invalid user credentials!"
                }
            }

            is FirebaseAuthInvalidCredentialsException -> "Invalid email or password!"
            is NetworkErrorException -> "Network error. Please try again!"
            else -> "Something went wrong!"
        }
    }


    fun checkUsernameAvailability(username: String, onComplete: (Boolean, String?) -> Unit) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .whereEqualTo("username", username)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val querySnapshot = task.result
                    if (querySnapshot != null && !querySnapshot.isEmpty) {
                        onComplete(false, "Username already taken")
                    } else {
                        onComplete(true, "Username is available")
                    }
                } else {
                    onComplete(false, "Error checking username availability: ${task.exception?.message}")
                }
            }
    }


    // Sign up with email and password
    fun signUp(email: String, password: String, onComplete: (FirebaseUser?, String?) -> Unit) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    onComplete(user, null)
                } else {
                    val errorMessage = getSignUpErrorMessage(task.exception)
                    onComplete(null, errorMessage)
                }
            }
    }


    private fun getSignUpErrorMessage(exception: Exception?): String {
        return when (exception) {
            is FirebaseAuthUserCollisionException -> "Account already exists!"
            is FirebaseAuthWeakPasswordException -> "Password is too weak!"
            is NetworkErrorException -> "Network error. Please try again!"
            else -> "Something went wrong!"
        }
    }


    // Function to send password reset email
    fun resetPassword(email: String, onComplete: (Boolean, String?) -> Unit) {
        FirebaseAuth.getInstance()
            .sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    val errorMessage = getResetPasswordErrorMessage(task.exception)
                    onComplete(false, errorMessage)
                }
            }
    }

    private fun getResetPasswordErrorMessage(exception: Exception?): String {
        return when (exception) {
            is FirebaseAuthInvalidUserException -> "No user found with this email!"
            is NetworkErrorException -> "Network error. Please try again!"
            else -> "Something went wrong!"
        }
    }

    fun sendEmailVerification(user: FirebaseUser?, onComplete: (Boolean, String?) -> Unit) {
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    val errorMessage = getEmailVerificationErrorMessage(task.exception)
                    onComplete(false, errorMessage)
                }
            }
    }

    private fun getEmailVerificationErrorMessage(exception: Exception?): String {
        return when (exception) {
            is FirebaseAuthInvalidUserException -> "No user found with this email!"
            is NetworkErrorException -> "Network error. Please try again!"
            else -> "Failed to send email verification. Please try again later."
        }
    }

}