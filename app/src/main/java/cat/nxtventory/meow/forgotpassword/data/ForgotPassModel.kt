package cat.nxtventory.meow.forgotpassword.data

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.nxtventory.meow.firebase.FirebaseManager
import cat.nxtventory.meow.firebase.isEmailValid

class ForgotPasswordModel : ViewModel() {

    val email = mutableStateOf("")
    val emailError = mutableStateOf(false)
    val resetProgress = mutableStateOf(false)

    fun resetPasswordButtonClick(context: Context) {
        resetProgress.value = true
        emailError.value = !isEmailValid(email.value)
        if (!emailError.value) {
            FirebaseManager.resetPassword(
                email.value
            ) { isSuccess, errorMessage ->
                resetProgress.value = false
                if (isSuccess) {
                    // Password reset email sent successfully
                    Toast.makeText(
                        context,
                        "Password reset email sent",
                        Toast.LENGTH_SHORT
                    ).show()
                    email.value = ""
                } else {
                    // Failed to send password reset email
                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            resetProgress.value = false
        }
    }

}