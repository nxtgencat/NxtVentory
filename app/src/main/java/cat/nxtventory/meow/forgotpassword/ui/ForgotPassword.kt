package cat.nxtventory.meow.forgotpassword.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.nxtventory.meow.firebase.FirebaseManager.sendPasswordResetEmail
import cat.nxtventory.meow.firebase.isEmailValid
import cat.nxtventory.meow.forgotpassword.data.ForgotPasswordModel
import cat.nxtventory.ui.theme.myTypography


@Composable
fun ForgotPasswordScreen(navControllerX: NavController) {
    val context = LocalContext.current
    val viewModel: ForgotPasswordModel = viewModel()
    MaterialTheme(
        typography = myTypography // Applying custom typography here
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Column(
                    modifier = Modifier
                        .padding(all = 25.dp)
                        .weight(3f),
                    verticalArrangement = Arrangement.Center
                ) {
                    TopBarUI()
                }
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                        .weight(7f)
                        .background(color = MaterialTheme.colorScheme.surfaceContainer),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(50.dp)
                    )
                    Column(
                        modifier = Modifier
                            .weight(6f)
                    ) {

                        EmailTextField(viewModel)

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(4f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {

                        ResetPasswordButton(viewModel, context)

                        Spacer(modifier = Modifier.height(30.dp))

                        GoBackTextButton(navControllerX)

                        Spacer(modifier = Modifier.height(50.dp))
                    }
                }
            }
        }

    }
}

@Composable
fun TopBarUI() {
    Text(
        text = "Forgot password",
        style = MaterialTheme.typography.displayMedium,
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = "Best way to manage your inventory",
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun EmailTextField(viewModel: ForgotPasswordModel) {
    OutlinedTextField(
        modifier = Modifier
            .height(90.dp)
            .width(300.dp),
        shape = RoundedCornerShape(50.dp),
        singleLine = true,
        textStyle = MaterialTheme.typography.titleSmall,
        value = viewModel.email.value,
        label = {
            Text(
                text = "Email",
                style = MaterialTheme.typography.titleSmall
            )
        },
        isError = viewModel.emailError.value,
        supportingText = { if (viewModel.emailError.value) Text(text = "Invalid Email") },
        onValueChange = {
            viewModel.email.value = it
            viewModel.emailError.value = false
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
}

@Composable
fun ResetPasswordButton(viewModel: ForgotPasswordModel, context: Context) {
    Button(
        modifier = Modifier
            .width(250.dp)
            .height(60.dp),
        enabled = !viewModel.ResetProgress.value && viewModel.email.value.isNotEmpty(),
        onClick = {
            viewModel.ResetProgress.value = true
            viewModel.emailError.value = !isEmailValid(viewModel.email.value)
            if (!viewModel.emailError.value) {
                sendPasswordResetEmail(
                    viewModel.email.value,
                    onSuccess = {
                        // Password reset email sent successfully
                        Toast.makeText(
                            context,
                            "Password reset email sent",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.ResetProgress.value = false
                    },
                    onFailure = { errorMessage ->
                        // Failed to send password reset email
                        Toast.makeText(
                            context,
                            "Failed to send reset email: $errorMessage",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.ResetProgress.value = false
                    }
                )

            }
        }
    ) {
        if (viewModel.ResetProgress.value) {
            CircularProgressIndicator()
        } else {
            Text(
                text = "Reset password",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun GoBackTextButton(navControllerX: NavController) {
    TextButton(
        onClick = { navControllerX.popBackStack() }
    ) {
        Text(
            text = "Go back",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen(navControllerX = rememberNavController())
}