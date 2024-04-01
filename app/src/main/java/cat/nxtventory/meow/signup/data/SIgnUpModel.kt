package cat.nxtventory.meow.signup.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SignUpModel : ViewModel() {

    var username = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val isPasswordVisible = mutableStateOf(false)
    val emailError = mutableStateOf(false)
    val passwordError = mutableStateOf(false)
    val SignUpProgress = mutableStateOf(false)
}