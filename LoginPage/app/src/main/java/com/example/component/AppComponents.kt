package com.example.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.navigation.Routes
import com.example.screen.getToast

@Composable
fun NormalTextComponent(value: String){
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp),
        text = value,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeadingTextComponent(value: String){
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        text = value,
        style = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        color = Color.Black,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun MyTextFieldComponent(labelValue: String, painterResource: Painter){
    val textValue = remember{ mutableStateOf("")}
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "" )
        },
        label = {Text(text = labelValue)},
        value = textValue.value,
        shape = RoundedCornerShape(20.dp),
        keyboardOptions = KeyboardOptions.Default,
        onValueChange = {textValue.value = it})
}

@Composable
fun MyPasswordFieldComponent(labelValue: String, painterResource: Painter){
    val passwordValue = remember{ mutableStateOf("")}
    val showPassword = remember { mutableStateOf(false) }
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "" )
        },
        label = {Text(text = labelValue)},
        value = passwordValue.value,
        shape = RoundedCornerShape(20.dp),
        visualTransformation =
            if (showPassword.value){
                VisualTransformation.None
            } else{
                PasswordVisualTransformation()
            },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = {passwordValue.value = it},
        trailingIcon = {
            if (showPassword.value){
                IconButton(onClick = { showPassword.value = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "Hide Password",
                        tint = Color.Black
                    )
                }
            }else{
                IconButton(onClick = { showPassword.value = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "Show Password",
                        tint = Color.Black
                    )
                }
            }
        }
    )
}

@Composable
fun MyConfirmPasswordFieldComponent(labelValue: String, painterResource: Painter){
    val passwordValue = remember{ mutableStateOf("")}
    val confirmShowPassword = remember { mutableStateOf(false) }
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "" )
        },
        label = {Text(text = labelValue)},
        value = passwordValue.value,
        shape = RoundedCornerShape(20.dp),
        visualTransformation =
            if (confirmShowPassword.value){
                VisualTransformation.None
            } else{
                PasswordVisualTransformation()
            },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = {passwordValue.value = it},
        trailingIcon = {
            if (confirmShowPassword.value){
                IconButton(onClick = { confirmShowPassword.value = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "Hide Password",
                        tint = Color.Black
                    )
                }
            }else{
                IconButton(onClick = { confirmShowPassword.value = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "Show Password",
                        tint = Color.Black
                    )
                }
            }
        }
    )
}

@Composable
fun CheckBoxComponent(value: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkState = remember {
            mutableStateOf(false)
        }
        Checkbox(
            checked = checkState.value,
            onCheckedChange = {checkState.value != checkState.value })

        ClickableTextComponent(value = value)
    }
}

@Composable
fun ClickableTextComponent(value: String){
    val initialText = "By continuing, you accept our "
    val privacyPolicyText = "Privacy policy "
    val andText = "and "
    val termsAndConditionsText = "Terms of use."

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Color.Blue)){
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }

        append(andText)

        withStyle(style = SpanStyle(color = Color.Blue)){
            pushStringAnnotation(tag = termsAndConditionsText, annotation = termsAndConditionsText)
            append(termsAndConditionsText)
        }
    }
    ClickableText(text = annotatedString, onClick = {
        offset -> annotatedString.getStringAnnotations(offset, offset)
        .firstOrNull()?.also { span ->
            Log.d("ClickableTextComponent: ", "msg: {$span}")
        }
    })
}

@Composable
fun GeneralClickableTextComponent(value: String, navController: NavHostController, rank: Int){
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        ClickableText(
            text = AnnotatedString(value),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            onClick = {
//                getToast(context = context, "Navigate to Forgot Pass")
//                navController.navigate(Routes.ForgotPassword.route)
                if(rank == 0) {
                    getToast(context = context, "Nav to login")
                    navController.navigate(Routes.Login.route)
                } else if (rank == 1){
                    navController.navigate(Routes.SignUp.route)
                }else if (rank == 2){
                    getToast(context = context, "Nav to Forgot Pass")
                    navController.navigate(Routes.ForgotPassword.route)
                }
            },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = Color.Black
            )
        )
    }
}

@Composable
fun ButtonComponent(navController: NavHostController, value: String, rank: Int){
    Button(onClick = {
                     if (rank == 0){
                         navController.navigate(Routes.ChooseVerificationMethod.route)
                     }else if (rank == 1){
                         navController.navigate(Routes.Login.route)
                     }else if (rank == 2){
                         navController.navigate(Routes.ChangePasswordVerifyEmail.route)
                     }else if (rank == 3){
                         navController.navigate(Routes.NewPassword.route)
                     }else if (rank == 4){
                         navController.navigate(Routes.NewPassword.route)
                     }else if(rank == 5){
                         navController.navigate(Routes.Login.route)
                     }
    },
        modifier = Modifier
            .fillMaxSize()
            .heightIn(48.dp),
            contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .heightIn(48.dp)
            .background(
                brush = Brush.horizontalGradient(listOf(Color.Gray, Color.Black, Color.Gray)),
                shape = RoundedCornerShape(50.dp),

                ),
            contentAlignment = Alignment.Center
            ) {
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun SubButton(navController: NavHostController, value: String, rank: Int){
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(60.dp, 0.dp, 60.dp, 610.dp)){
        ButtonComponent(navController = navController, value = value, rank = rank)

    }
}

@Composable
fun DividerTextComponent(){
    Row(modifier = Modifier
        .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically) {
        Divider(modifier = Modifier
            .fillMaxSize()
            .weight(1f),
            color = Color.Gray,
            thickness = 1.dp)

        Text(modifier = Modifier
            .padding(8.dp),
            text = " or ",
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
        )

        Divider(modifier = Modifier
            .fillMaxSize()
            .weight(1f),
            color = Color.Gray,
            thickness = 1.dp)
    }
}

@Composable
fun ClickableLoginText(navController: NavHostController, initialText: String, loginText: String){
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Color.Blue)){
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }
    ClickableText(
        modifier = Modifier
            .heightIn(40.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        text = annotatedString, onClick = {offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also { span ->
                if(span.item == loginText){
                    navController.navigate(Routes.Login.route)
                }
            }
    })
}