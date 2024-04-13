package cat.nxtventory.meow.signin.ui

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cat.nxtventory.meow.forgotpassword.ui.ForgotPasswordScreen
import cat.nxtventory.meow.signin.data.SignInModel
import cat.nxtventory.ui.theme.NxtVentoryTheme

class SignInScreen : Screen {
    @Composable
    override fun Content() {
        SignInScreenUI()
    }
}

@Composable
private fun SignInScreenUI() {

    val viewModel: SignInModel = viewModel()

    val navigator = LocalNavigator.current
    val context = LocalContext.current


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 25.dp)
                .weight(3f),
            verticalArrangement = Arrangement.Center
        ) {
            TopBarUI()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .weight(7f)
                .background(color = MaterialTheme.colorScheme.surfaceContainer),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Column(
                modifier = Modifier
                    .weight(6f)
            ) {
                EmailTextField(viewModel)
                PasswordTextField(viewModel)
                Box(modifier = Modifier.align(Alignment.End)) {
                    ForgotPassTextButton { navigator?.push(ForgotPasswordScreen()) }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                SignInButton(viewModel, context, navigator)
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Don't have an account yet?",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                SignUpTextButton(viewModel, navigator)
                Spacer(modifier = Modifier.height(50.dp))
            }
            if (viewModel.showResendDialog.value) {
                ResendAlertDialog()
            }
        }
    }

}

@Composable
fun ForgotPassTextButton(
    onClick: () -> Unit
) {
    TextButton(
        onClick = { onClick() }
    ) {
        Text(
            text = "Forgot password?",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun TopBarUI() {
    Text(
        text = "Your Inventory Insights Await",
        style = MaterialTheme.typography.displayMedium,
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = "Access your smart inventory.",
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun EmailTextField(
    viewModel: SignInModel
) {
    OutlinedTextField(
        modifier = Modifier
            .height(90.dp)
            .width(300.dp),
        shape = RoundedCornerShape(50.dp),
        singleLine = true,
        textStyle = MaterialTheme.typography.titleSmall,
        value = viewModel.username.value,
        label = {
            Text(
                text = "Username",
                style = MaterialTheme.typography.titleSmall
            )
        },
        isError = viewModel.usernameError.value,
        supportingText = { if (viewModel.usernameError.value) Text(text = "Invalid username") },
        onValueChange = {
            viewModel.username.value = it
            viewModel.usernameError.value = false
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = if (viewModel.isPasswordVisible.value) "Hide password" else "Show password"
            )
        }
    )
}

@Composable
private fun PasswordTextField(
    viewModel: SignInModel
) {
    OutlinedTextField(
        modifier = Modifier
            .height(90.dp)
            .width(300.dp),
        shape = RoundedCornerShape(50.dp),
        singleLine = true,
        textStyle = MaterialTheme.typography.titleSmall,
        value = viewModel.password.value,
        label = {
            Text(
                text = "Password",
                style = MaterialTheme.typography.titleSmall
            )
        },
        isError = viewModel.passwordError.value,
        onValueChange = {
            viewModel.password.value = it
            viewModel.passwordErrorReset()
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        supportingText = { if (viewModel.passwordError.value) Text(text = "Invalid password") },
        visualTransformation = if (viewModel.isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = {
                    viewModel.togglePassVisibility()
                }
            ) {
                Icon(
                    imageVector = if (viewModel.isPasswordVisible.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (viewModel.isPasswordVisible.value) "Hide password" else "Show password"
                )
            }
        }
    )
}

@Composable
private fun SignInButton(
    viewModel: SignInModel,
    context: Context,
    navigator: Navigator?
) {
    Button(
        modifier = Modifier
            .width(250.dp)
            .height(60.dp),
        enabled = viewModel.isSignInButtonEnabled(),
        onClick = { viewModel.signInButtonClick(context, navigator) }
    ) {
        if (viewModel.signInProgress.value) {
            CircularProgressIndicator() // Show CircularProgressIndicator when sign-in is in progress
        } else {
            Text(
                text = "Sign in",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun SignUpTextButton(
    viewModel: SignInModel,
    navigator: Navigator?
) {

    TextButton(
        onClick = { viewModel.signUpTextButtonClick(navigator) }
    ) {
        Text(
            text = "Create account",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun ResendAlertDialog() {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(text = "Email Not Verified")
        },
        text = {
            Text(
                text = "Your email is not verified. Would you like to send a verification email?",
                style = MaterialTheme.typography.labelLarge
            )
        },
        confirmButton = {
            Button(
                onClick = { }
            ) {
                Text(text = "Send Email")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = { }
            ) {
                Text(text = "Dismiss")
            }
        }
    )
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true)
@Composable
private fun UniveralPreview() {
    NxtVentoryTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SignInScreenUI()
        }
    }
}