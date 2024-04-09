package cat.nxtventory.meow.signup.data

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.navigator.Navigator
import cat.nxtventory.meow.firebase.FirebaseManager
import cat.nxtventory.meow.firebase.isEmailValid
import cat.nxtventory.meow.firebase.isPasswordStrong
import cat.nxtventory.meow.signin.ui.SignInScreen
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SignUpModel : ViewModel() {
    val username = mutableStateOf("")
    val usernameAvailableMessage = mutableStateOf("")
    val isusernameAvailable = mutableStateOf(false)
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val isPasswordVisible = mutableStateOf(false)
    val usernameError = mutableStateOf(false)
    val emailError = mutableStateOf(false)
    val passwordError = mutableStateOf(false)
    val signUpProgress = mutableStateOf(false)

    fun signUpButtonClick(context: Context, navigator: Navigator?) {
        signUpProgress.value = true
        validateSignUp(context, navigator)
    }

    private fun validateSignUp(context: Context, navigator: Navigator?) {
        emailError.value = !isEmailValid(email.value)
        passwordError.value = !isPasswordStrong(password.value)
        if (isusernameAvailable.value && !emailError.value && !passwordError.value) {
            performSignUp(context, navigator)
        } else {
            signUpProgress.value = false
        }
    }

    private fun performSignUp(context: Context, navigator: Navigator?) {
        FirebaseManager.signUp(email.value, password.value) { user, errorMessage ->
            signUpProgress.value = false
            if (user != null) {
                saveUserData(user)
                sendEmailVerification(context, user)
                navigator?.replaceAll(SignInScreen())
            } else {
                displayErrorMessage(context, errorMessage)
            }
        }
    }

    private fun saveUserData(user: FirebaseUser) {
        val userId = user.uid
        val userData = hashMapOf(
            "username" to username.value,
            "email" to email.value
        )
        Firebase.firestore.collection("users").document(userId).set(userData)
    }

    private fun sendEmailVerification(context: Context, user: FirebaseUser) {
        FirebaseManager.sendEmailVerification(user) { isSuccess, emailErrorMessage ->
            if (isSuccess) {
                Toast.makeText(
                    context,
                    "Account Created. Email verification sent.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    emailErrorMessage ?: "Failed to send email verification.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun displayErrorMessage(context: Context, errorMessage: String?) {
        Toast.makeText(
            context,
            errorMessage ?: "Unknown error occurred.",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun signInTextButtonClick(navigator: Navigator?) {
        navigator?.pop()
        navigator?.push(SignInScreen())
    }
}
