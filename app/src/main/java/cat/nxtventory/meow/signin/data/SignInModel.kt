package cat.nxtventory.meow.signin.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SignInModel : ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val isPasswordVisible = mutableStateOf(false)
    val emailError = mutableStateOf(false)
    val passwordError = mutableStateOf(false)
    val SignInProgress = mutableStateOf(false)
}