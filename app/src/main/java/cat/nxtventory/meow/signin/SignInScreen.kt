package cat.nxtventory.meow.signin

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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.nxtventory.R
import cat.nxtventory.meow.firebase.FirebaseManager.signIn
import cat.nxtventory.meow.firebase.isEmailValid
import cat.nxtventory.meow.firebase.isPasswordStrong
import cat.nxtventory.meow.navigation.data.Screen
import cat.nxtventory.ui.theme.OneUISans

@Composable
fun SignInScreen(navControllerX: NavController) {
    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.nxtventory),
            contentDescription = "welcome_logo"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Login",
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
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                )
            }

        )
        OutlinedTextField(
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            label = { Text(text = "Password") },
            value = password,
            isError = passwordError,
            onValueChange = {
                password = it
                passwordError = false
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            supportingText = { if (passwordError) Text(text = "Invalid Password") },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(
                    onClick = { isPasswordVisible = !isPasswordVisible }
                ) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                    )
                }
            }
        )
        TextButton(
            onClick = { navControllerX.navigate(Screen.ForgotPassword.route) }
        ) {
            Text(text = "Forgot Password?")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
            onClick = {
                emailError = !isEmailValid(email)
                passwordError = !isPasswordStrong(password)
                if (!emailError && !passwordError) {
                    signIn(email, password) { user, exception ->
                        if (user != null) {
                            navControllerX.navigate(Screen.NxtVentory.route) {
                                popUpTo(Screen.SignIn.route) { inclusive = true }
                            }

                        } else {
                            Toast.makeText(context, "Incorrect Credentials!", Toast.LENGTH_SHORT)
                                .show()
                            exception?.let {
                                // Handle exception
                            }
                        }
                    }

                }
            },
            enabled = email.isNotEmpty() && password.isNotEmpty()
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Don't have an account yet?",
            fontSize = 16.sp,
            fontFamily = OneUISans,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextButton(
            onClick = {
                navControllerX.navigate(Screen.SignUp.route) {
                    navControllerX.graph.startDestinationRoute?.let { startDestinationRoute ->
                        popUpTo(startDestinationRoute) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        ) {
            Text(text = "Create Account")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignInPreview() {
    SignInScreen(navControllerX = rememberNavController())
}