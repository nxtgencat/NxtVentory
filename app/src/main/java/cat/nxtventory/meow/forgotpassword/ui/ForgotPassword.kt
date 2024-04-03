package cat.nxtventory.meow.forgotpassword.ui

import android.content.Context
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cat.nxtventory.meow.forgotpassword.data.ForgotPasswordModel
import cat.nxtventory.ui.theme.myTypography

class ForgotPasswordScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        ForgotPasswordScreenUI(navigator)
    }
}

@Composable
fun ForgotPasswordScreenUI(navigator: Navigator?) {

    val context = LocalContext.current
    val viewModel: ForgotPasswordModel = viewModel()

    MaterialTheme(
        typography = myTypography
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

                        GoBackTextButton(navigator)

                        Spacer(modifier = Modifier.height(50.dp))
                    }
                }
            }
        }

    }
}

@Composable
private fun TopBarUI() {
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
private fun EmailTextField(
    viewModel: ForgotPasswordModel,
) {
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
private fun ResetPasswordButton(
    viewModel: ForgotPasswordModel,
    context: Context,
) {
    Button(
        modifier = Modifier
            .width(250.dp)
            .height(60.dp),
        enabled = !viewModel.resetProgress.value && viewModel.email.value.isNotEmpty(),
        onClick = { viewModel.resetPasswordButtonClick(context) }
    ) {
        if (viewModel.resetProgress.value) {
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
private fun GoBackTextButton(navigator: Navigator?) {
    TextButton(
        onClick = { navigator?.pop() }
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
    val navigator = LocalNavigator.current
    ForgotPasswordScreenUI(navigator)
}