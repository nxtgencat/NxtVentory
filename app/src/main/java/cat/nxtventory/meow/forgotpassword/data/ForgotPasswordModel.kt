package cat.nxtventory.meow.forgotpassword.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ForgotPasswordModel : ViewModel() {
    val email = mutableStateOf("")
    val emailError = mutableStateOf(false)
    val ResetProgress = mutableStateOf(false)
}