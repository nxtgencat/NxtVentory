package cat.nxtventory.meow.signin.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.navigator.Navigator
import cat.nxtventory.meow.firebase.FirebaseManager
import cat.nxtventory.meow.firebase.UserDataManager
import cat.nxtventory.meow.firebase.isPasswordValid
import cat.nxtventory.meow.firebase.isUsernamePassValid
import cat.nxtventory.meow.nxtventory.ui.NxtVentory
import cat.nxtventory.meow.signup.ui.SignUpScreen
import com.google.firebase.auth.FirebaseUser

class SignInModel : ViewModel() {

    val username = mutableStateOf("")
    val password = mutableStateOf("")
    val isPasswordVisible = mutableStateOf(false)
    val usernameError = mutableStateOf(false)
    val passwordError = mutableStateOf(false)
    val signInProgress = mutableStateOf(false)


    fun isSignInButtonEnabled(): Boolean {
        return !signInProgress.value && username.value.isNotEmpty() && password.value.isNotEmpty()
    }

    fun passwordErrorReset() {
        passwordError.value = false
    }

    fun togglePassVisibility() {
        isPasswordVisible.value = !isPasswordVisible.value
    }

    private fun reportPassError() {
        passwordError.value = !isPasswordValid(password.value)
    }

    private fun toggleSignInON() {
        signInProgress.value = true
    }

    private fun toggleSignInOFF() {
        signInProgress.value = false
    }

    fun signInButtonClick(
        context: Context,
        navigator: Navigator?
    ) {
        toggleSignInON()
        reportPassError()
        validateSignUp(context, navigator)
    }

    private fun validateSignUp(
        context: Context,
        navigator: Navigator?
    ) {
        if (isUsernamePassValid(username.value, password.value)) {
            performSignIn(context, navigator)
        } else {
            toggleSignInOFF()
        }
    }

    private fun performSignIn(
        context: Context,
        navigator: Navigator?
    ) {
        FirebaseManager.fetchEmailForUsername(username.value) { email, errorMessage ->
            if (email != null) {
                signInWithEmail(context, navigator, email)
            } else {
                displayErrorMessage(context, errorMessage ?: "Unknown error occurred.")
                signInProgress.value = false
            }
        }
    }

    private fun signInWithEmail(
        context: Context,
        navigator: Navigator?,
        email: String
    ) {

        FirebaseManager.signIn(
            email,
            password.value
        ) { user, errorMessage ->

            toggleSignInOFF()
            handleSignInResult(context, navigator, user, errorMessage)

        }
    }

    private fun handleSignInResult(
        context: Context,
        navigator: Navigator?,
        user: FirebaseUser?,
        errorMessage: String?,
    ) {
        if (user != null && user.isEmailVerified) {
            navigator?.replaceAll(NxtVentory())
            UserDataManager.saveUserInfo(context, user.uid)
            Log.d("MyApp", "Saved User ID: ${user.uid}")
        } else {
            if (user != null && !user.isEmailVerified) {

                displayEmailNotVerifiedMessage(context)
            } else {
                displayErrorMessage(context, errorMessage)
            }
        }
    }

    private fun displayEmailNotVerifiedMessage(
        context: Context
    ) {

        Toast.makeText(
            context,
            "Email is not verified. Please verify your email.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun displayErrorMessage(
        context: Context,
        errorMessage: String?
    ) {
        Toast.makeText(
            context,
            errorMessage ?: "Unknown error occurred.",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun signUpTextButtonClick(
        navigator: Navigator?
    ) {
        navigator?.pop()
        navigator?.push(SignUpScreen())
    }
}