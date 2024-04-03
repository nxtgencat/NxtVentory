package cat.nxtventory.meow.firebase


import android.accounts.NetworkErrorException
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser


object UserDataManager {
    fun saveUserId(context: Context, userId: String) {
        val sharedPref = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("user_id", userId)
            apply()
        }
    }

    fun isLoggedIn(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val userId = sharedPref.getString("user_id", null)
        return userId != null
    }

    fun signOut(context: Context, onSignOut: () -> Unit) {
        val sharedPref = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            remove("user_id")
            apply()
        }
        onSignOut() // Call the callback after removing user ID
    }
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
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Sign in with email and password
    fun signIn(email: String, password: String, onComplete: (FirebaseUser?, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
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


    // Sign up with email and password
    fun signUp(email: String, password: String, onComplete: (FirebaseUser?, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
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
        auth.sendPasswordResetEmail(email)
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