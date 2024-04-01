package cat.nxtventory.meow.firebase


import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
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
                val errorMessage = when {
                    task.exception != null -> {
                        when (task.exception) {
                            is FirebaseAuthInvalidCredentialsException -> "Invalid password"
                            else -> "Sign-in failed"
                        }
                    }

                    task.isSuccessful && auth.currentUser == null -> "User not found"
                    else -> null
                }
                if (errorMessage != null) {
                    onComplete(null, errorMessage)
                } else {
                    val user = auth.currentUser
                    onComplete(user, null)
                }
            }
    }

    // Sign up with email and password
    fun signUp(email: String, password: String, onComplete: (FirebaseUser?, Exception?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onComplete(user, null)
                } else {
                    onComplete(null, task.exception)
                }
            }
    }

    // Function to send password reset email
    fun sendPasswordResetEmail(email: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Unknown error occurred")
                }
            }
    }

}