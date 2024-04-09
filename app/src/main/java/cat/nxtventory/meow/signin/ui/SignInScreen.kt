package cat.nxtventory.meow.signin.ui

import android.content.Context
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
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import cat.nxtventory.ui.theme.myTypography

class SignInScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        SignInScreenUI(navigator)
    }
}

@Composable
private fun SignInScreenUI(navigator: Navigator?) {
    val context = LocalContext.current
    val viewModel: SignInModel = viewModel()


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

                        PasswordTextField(viewModel)

                        Box(
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            TextButton(
                                onClick = { navigator?.push(ForgotPasswordScreen()) }
                            ) {
                                Text(
                                    text = "Forgot password?",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(4f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {

                        LoginButton(viewModel, context, navigator)

                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = "Don't have an account yet?",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        SignUpTextButton(viewModel, navigator)

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
        text = "Login",
        style = MaterialTheme.typography.displayMedium,
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = "Best way to manage your inventory",
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun EmailTextField(viewModel: SignInModel) {
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
private fun PasswordTextField(viewModel: SignInModel) {
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
            viewModel.passwordError.value = false
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        supportingText = { if (viewModel.passwordError.value) Text(text = "Invalid password") },
        visualTransformation = if (viewModel.isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = {
                    viewModel.isPasswordVisible.value =
                        !viewModel.isPasswordVisible.value
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
private fun LoginButton(viewModel: SignInModel, context: Context, navigator: Navigator?) {
    Button(
        modifier = Modifier
            .width(250.dp)
            .height(60.dp),
        enabled = !viewModel.signInProgress.value && viewModel.username.value.isNotEmpty() && viewModel.password.value.isNotEmpty(),
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
private fun SignUpTextButton(viewModel: SignInModel, navigator: Navigator?) {

    TextButton(
        onClick = { viewModel.signUpTextButtonClick(navigator) }
    ) {
        Text(
            text = "Create account",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignInPreview() {
    val navigator = LocalNavigator.current
    SignInScreenUI(navigator)
}