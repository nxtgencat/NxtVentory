package cat.nxtventory.meow.firebase


import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser

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
        // Check if the email exists before attempting to sign in
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onComplete(user, null)
                } else {
                    val errorMessage = if (task.exception is FirebaseAuthInvalidUserException) {
                        "User not found"
                    } else {
                        "Invalid credentials"
                    }
                    onComplete(null, errorMessage)
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

    // Check if the user is already signed in
    fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }

    // Sign out the current user
    fun signOut() {
        auth.signOut()
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


fun saveSignInState(context: Context, isSignedIn: Boolean) {
    val sharedPref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putBoolean("isSignedIn", isSignedIn)
        apply()
    }
}

// Get user's sign-in state from local storage
fun isUserSignedIn(context: Context): Boolean {
    val sharedPref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
    return sharedPref.getBoolean("isSignedIn", false)
}


