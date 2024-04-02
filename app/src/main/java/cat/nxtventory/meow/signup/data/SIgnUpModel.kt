package cat.nxtventory.meow.signup.data

import android.accounts.NetworkErrorException
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import cat.nxtventory.meow.firebase.FirebaseManager
import cat.nxtventory.meow.firebase.isEmailValid
import cat.nxtventory.meow.firebase.isPasswordStrong
import cat.nxtventory.meow.navigation.data.Screen
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class SignUpModel : ViewModel() {

    var username = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val isPasswordVisible = mutableStateOf(false)
    val emailError = mutableStateOf(false)
    val passwordError = mutableStateOf(false)
    val SignUpProgress = mutableStateOf(false)

    fun SignUpButtonClick(
        navControllerX: NavController,
        context: Context,
    ) {
        SignUpProgress.value = true
        emailError.value = !isEmailValid(email.value)
        passwordError.value =
            !isPasswordStrong(password.value)
        if (!emailError.value && !passwordError.value) {
            FirebaseManager.signUp(
                email.value,
                password.value
            ) { user, exception ->
                SignUpProgress.value = false
                if (user != null) {
                    Toast.makeText(
                        context,
                        "Account Created",
                        Toast.LENGTH_SHORT
                    ).show()
                    navControllerX.navigate(Screen.SignIn.route) {
                        navControllerX.graph.startDestinationRoute?.let { startDestinationRoute ->
                            popUpTo(startDestinationRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                } else {
                    exception?.let {
                        when (it) {
                            is FirebaseAuthUserCollisionException -> {
                                Toast.makeText(
                                    context,
                                    "Account already exists!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is FirebaseAuthWeakPasswordException -> {
                                Toast.makeText(
                                    context,
                                    "Password is too weak!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is NetworkErrorException -> {
                                Toast.makeText(
                                    context,
                                    "Network error. Please try again!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {
                                Toast.makeText(
                                    context,
                                    "Something went wrong!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                }
            }
        } else {
            SignUpProgress.value = false
        }
    }

    fun SignInButtonClick(navControllerX: NavController) {
        navControllerX.navigate(Screen.SignIn.route) {
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