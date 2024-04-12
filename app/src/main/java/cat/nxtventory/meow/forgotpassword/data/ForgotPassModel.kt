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


    fun emailErrorReset() {
        emailError.value = false
    }

    private fun toggleResetOFF() {
        resetProgress.value = false
    }

    private fun toggleResetON() {
        resetProgress.value = true
    }

    fun isResetButtonEnabled(): Boolean {
        return !resetProgress.value && email.value.isNotEmpty()
    }

    private fun clearEmailField() {
        email.value = ""
    }

    fun resetPasswordButtonClick(
        context: Context
    ) {
        toggleResetON()
        if (isEmailValid(email.value)) {
            validateResetPass(context)
        } else {
            toggleResetOFF()
        }
    }

    private fun validateResetPass(
        context: Context
    ) {
        FirebaseManager.resetPassword(
            email.value
        ) { isSuccess, errorMessage ->
            toggleResetOFF()
            if (isSuccess) {
                Toast.makeText(
                    context,
                    "Password reset email sent",
                    Toast.LENGTH_SHORT
                ).show()
                clearEmailField()
            } else {
                Toast.makeText(
                    context,
                    errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}