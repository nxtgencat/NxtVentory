package cat.nxtventory.meow.signin.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.core.stack.popUntil
import cafe.adriel.voyager.navigator.Navigator
import cat.nxtventory.NxtVentory
import cat.nxtventory.meow.firebase.FirebaseManager
import cat.nxtventory.meow.firebase.UserDataManager
import cat.nxtventory.meow.firebase.isEmailValid
import cat.nxtventory.meow.firebase.isPasswordStrong
import cat.nxtventory.meow.signup.ui.SignUpScreen
import cat.nxtventory.meow.welcome.WelcomeScreen

class SignInModel : ViewModel() {

    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val isPasswordVisible = mutableStateOf(false)
    val emailError = mutableStateOf(false)
    val passwordError = mutableStateOf(false)
    val signInProgress = mutableStateOf(false)

    fun SignInButtonClick(
        context: Context,
        navigator: Navigator,
    ) {
        signInProgress.value = true
        emailError.value = !isEmailValid(email.value)
        passwordError.value = !isPasswordStrong(password.value)
        if (!emailError.value && !passwordError.value) {
            FirebaseManager.signIn(
                email.value,
                password.value
            ) { user, errorMessage ->
                // Reset the sign-in progress when the sign-in process is complete
                signInProgress.value = false
                if (user != null) {
                    navigator.popAll()
                    navigator.push(NxtVentory())
                    UserDataManager.saveUserId(context, user.uid)
                    Log.d("MyApp", "Saved User ID: ${user.uid}")
                } else {
                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            signInProgress.value = false
        }
    }

    fun SignUpTextButtonClick(navigator: Navigator) {
        navigator.pop()
        navigator.push(SignUpScreen())
    }
}