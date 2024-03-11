package cat.nxtventory.meow.forgotpassword

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.nxtventory.R
import cat.nxtventory.meow.firebase.FirebaseManager.sendPasswordResetEmail
import cat.nxtventory.meow.firebase.isEmailValid
import cat.nxtventory.ui.theme.OneUISans

@Composable
fun ForgotPasswordScreen(navControllerX: NavController) {
    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(125.dp),
            painter = painterResource(id = R.drawable.nxtventory),
            contentDescription = "welcome_logo"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Forgot Password",
            fontSize = 32.sp,
            fontFamily = OneUISans,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            value = email,
            label = { Text("Email") },
            isError = emailError,
            supportingText = { if (emailError) Text(text = "Invalid Email") },
            onValueChange = {
                email = it
                emailError = false
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon"
                )
            }

        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
            onClick = {
                emailError = !isEmailValid(email)
                if (!emailError) {
                    sendPasswordResetEmail(email,
                        onSuccess = {
                            // Password reset email sent successfully
                            Toast.makeText(context, "Password reset email sent", Toast.LENGTH_SHORT)
                                .show()
                        },
                        onFailure = { errorMessage ->
                            // Failed to send password reset email
                            Toast.makeText(
                                context,
                                "Failed to send reset email: $errorMessage",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            },
            enabled = email.isNotEmpty()
        ) {
            Text(text = "Reset Password")
        }
        TextButton(onClick = { navControllerX.popBackStack() }) {
            Text(text = "Go back")
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen(navControllerX = rememberNavController())
}