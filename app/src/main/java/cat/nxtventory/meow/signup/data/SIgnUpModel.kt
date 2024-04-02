package cat.nxtventory.meow.signup.data

import android.accounts.NetworkErrorException
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.navigator.Navigator
import cat.nxtventory.meow.firebase.FirebaseManager
import cat.nxtventory.meow.firebase.isEmailValid
import cat.nxtventory.meow.firebase.isPasswordStrong
import cat.nxtventory.meow.signin.ui.SignInScreen
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class SignUpModel : ViewModel() {

    var username = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val isPasswordVisible = mutableStateOf(false)
    val emailError = mutableStateOf(false)
    val passwordError = mutableStateOf(false)
    val signUpProgress = mutableStateOf(false)

    fun SignUpButtonClick(
        context: Context,
        navigator: Navigator?,
    ) {
        signUpProgress.value = true
        emailError.value = !isEmailValid(email.value)
        passwordError.value =
            !isPasswordStrong(password.value)
        if (!emailError.value && !passwordError.value) {
            FirebaseManager.signUp(
                email.value,
                password.value
            ) { user, exception ->
                signUpProgress.value = false
                if (user != null) {
                    Toast.makeText(
                        context,
                        "Account Created",
                        Toast.LENGTH_SHORT
                    ).show()
                    navigator?.push(SignInScreen())
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
            signUpProgress.value = false
        }
    }

    fun SignInTextButtonClick(navigator: Navigator?) {
        navigator?.pop()
        navigator?.push(SignInScreen())
    }
}