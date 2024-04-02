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
    val ResetProgress = mutableStateOf(false)

    fun ResetPasswordButtonClick(context: Context) {
        ResetProgress.value = true
        emailError.value = !isEmailValid(email.value)
        if (!emailError.value) {
            FirebaseManager.sendPasswordResetEmail(
                email.value,
                onSuccess = {
                    // Password reset email sent successfully
                    Toast.makeText(
                        context,
                        "Password reset email sent",
                        Toast.LENGTH_SHORT
                    ).show()
                    ResetProgress.value = false
                },
                onFailure = { errorMessage ->
                    // Failed to send password reset email
                    Toast.makeText(
                        context,
                        "Failed to send reset email: $errorMessage",
                        Toast.LENGTH_SHORT
                    ).show()
                    ResetProgress.value = false
                }
            )

        }
    }
}