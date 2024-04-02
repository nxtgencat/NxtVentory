package cat.nxtventory.meow.signin.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import cat.nxtventory.meow.firebase.FirebaseManager
import cat.nxtventory.meow.firebase.UserDataManager
import cat.nxtventory.meow.firebase.isEmailValid
import cat.nxtventory.meow.firebase.isPasswordStrong
import cat.nxtventory.meow.navigation.data.Screen

class SignInModel : ViewModel() {

    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val isPasswordVisible = mutableStateOf(false)
    val emailError = mutableStateOf(false)
    val passwordError = mutableStateOf(false)
    val signInProgress = mutableStateOf(false)

    fun SignInButtonClick(
        navControllerX: NavController,
        context: Context,
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
                    navControllerX.navigate(Screen.NxtVentory.route) {
                        navControllerX.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                inclusive = true
                            }
                        }
                    }
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

    fun SignUpButtonClick(navControllerX: NavController) {
        navControllerX.navigate(Screen.SignUp.route) {
            navControllerX.graph.startDestinationRoute?.let { startDestinationRoute ->
                popUpTo(startDestinationRoute) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}