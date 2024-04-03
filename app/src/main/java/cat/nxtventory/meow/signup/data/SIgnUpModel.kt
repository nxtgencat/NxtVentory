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

class SignUpModel : ViewModel() {

    var username = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val isPasswordVisible = mutableStateOf(false)
    val emailError = mutableStateOf(false)
    val passwordError = mutableStateOf(false)
    val signUpProgress = mutableStateOf(false)

    fun signUpButtonClick(
        context: Context,
        navigator: Navigator?,
    ) {
        signUpProgress.value = true
        emailError.value = !isEmailValid(email.value)
        passwordError.value = !isPasswordStrong(password.value)
        if (!emailError.value && !passwordError.value) {
            FirebaseManager.signUp(
                email.value,
                password.value
            ) { user, errorMessage ->
                // Reset the sign-up progress when the sign-up process is complete
                signUpProgress.value = false
                if (user != null) {
                    // If sign-up is successful, send email verification
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
                    navigator?.replaceAll(SignInScreen())
                } else {
                    // If sign-up fails, display error message
                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            signUpProgress.value = false
        }
    }


    fun signInTextButtonClick(navigator: Navigator?) {
        navigator?.pop()
        navigator?.push(SignInScreen())
    }
}