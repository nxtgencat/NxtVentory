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
import androidx.compose.material.icons.filled.Email
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.nxtventory.meow.navigation.data.Screen
import cat.nxtventory.meow.signin.data.SignInModel
import cat.nxtventory.ui.theme.myTypography

@Composable
fun SignInScreen(navControllerX: NavController) {
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
                                onClick = { navControllerX.navigate(Screen.ForgotPassword.route) }
                            ) {
                                Text(
                                    text = "Forgot Password?",
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

                        LoginButton(viewModel, navControllerX, context)

                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = "Don't have an account yet?",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        SignUpTextButton(viewModel, navControllerX)

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
fun EmailTextField(viewModel: SignInModel) {
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
            imeAction = ImeAction.Next
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = if (viewModel.isPasswordVisible.value) "Hide password" else "Show password"
            )
        }
    )
}

@Composable
fun PasswordTextField(viewModel: SignInModel) {
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
        supportingText = { if (viewModel.passwordError.value) Text(text = "Invalid Password") },
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
fun LoginButton(viewModel: SignInModel, navControllerX: NavController, context: Context) {
    Button(
        modifier = Modifier
            .width(250.dp)
            .height(60.dp),
        enabled = !viewModel.signInProgress.value && viewModel.email.value.isNotEmpty() && viewModel.password.value.isNotEmpty(),
        onClick = { viewModel.SignInButtonClick(navControllerX, context) }
    ) {
        if (viewModel.signInProgress.value) {
            CircularProgressIndicator() // Show CircularProgressIndicator when sign-in is in progress
        } else {
            Text(
                text = "Login",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun SignUpTextButton(viewModel: SignInModel, navControllerX: NavController) {

    TextButton(
        onClick = { viewModel.SignUpButtonClick(navControllerX) }
    ) {
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignInPreview() {
    SignInScreen(navControllerX = rememberNavController())
}