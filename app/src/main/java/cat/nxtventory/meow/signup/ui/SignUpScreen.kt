package cat.nxtventory.meow.signup.ui

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
import androidx.compose.material.icons.filled.Person
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
import cat.nxtventory.meow.signup.data.SignUpModel
import cat.nxtventory.ui.theme.myTypography

class SignUpScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        SignUpScreenUI(navigator)
    }
}

@Composable
private fun SignUpScreenUI(navigator: Navigator?) {
    val context = LocalContext.current
    val viewModel: SignUpModel = viewModel()

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

                        UsernameTextField(viewModel)

                        EmailTextField(viewModel)

                        PasswordTextField(viewModel)

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(4f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {

                        SignUpButton(viewModel, context, navigator)

                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = "Already have an acoount?",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        LoginTextButton(viewModel, navigator)

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
        text = "Signup",
        style = MaterialTheme.typography.displayMedium,
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = "Best way to manage your inventory",
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun UsernameTextField(viewModel: SignUpModel) {
    OutlinedTextField(
        modifier = Modifier
            .height(90.dp)
            .width(300.dp),
        shape = RoundedCornerShape(50.dp),
        textStyle = MaterialTheme.typography.titleSmall,
        singleLine = true,
        label = {
            Text(
                text = "Username",
                style = MaterialTheme.typography.titleSmall
            )
        },
        value = viewModel.username.value,
        supportingText = { },
        onValueChange = {
            viewModel.username.value = it
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
private fun EmailTextField(viewModel: SignUpModel) {
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
private fun PasswordTextField(viewModel: SignUpModel) {
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
private fun SignUpButton(viewModel: SignUpModel, context: Context, navigator: Navigator?) {
    Button(
        modifier = Modifier
            .width(250.dp)
            .height(60.dp),
        enabled = !viewModel.signUpProgress.value && viewModel.email.value.isNotEmpty() && viewModel.password.value.isNotEmpty(),
        onClick = { viewModel.SignUpButtonClick(context, navigator) }
    ) {
        if (viewModel.signUpProgress.value) {
            CircularProgressIndicator()
        } else {
            Text(
                text = "Signup",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun LoginTextButton(
    viewModel: SignUpModel,
    navigator: Navigator?,
) {
    TextButton(
        onClick = { viewModel.SignInTextButtonClick(navigator) }
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    val navigator = LocalNavigator.current
    SignUpScreenUI(navigator)
}