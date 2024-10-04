package com.example.loginpage.ui.component

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.data.uievents.SignUpPageUIEvent
import com.example.data.uistate.EmailVerifyUIState
import com.example.data.uistate.SignUpPageUIState
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.data.viewmodel.VerifyEmailViewModel
import com.example.loginpage.MainActivity
import com.example.navigation.Routes
import com.google.firebase.auth.FirebaseAuth

private val TAG = VerifyEmailViewModel::class.simpleName
var changePasswordEmail: String = ""
@Composable
fun NormalTextComponent(value: String){
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp),
        text = value,
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal
        ),
        color = Color.Black, //if (isSystemInDarkTheme()) Color.White else Color.Black,
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeadingTextComponent(value: String = "None"){
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
        color = Color.Black, // if (isSystemInDarkTheme()) Color.White else Color.Black,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextFieldComponent(labelValue: String, painterResource: Painter,
                         onTextChanged:(String) -> Unit,
                         errorStatus: Boolean = false,
                         emailViewModel: VerifyEmailViewModel = viewModel(),
                         action: String = "None"){
    val textValue = rememberSaveable{ mutableStateOf("")}

    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = {Text(text = labelValue)},
        value = textValue.value,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(20.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next),
        onValueChange = {
            textValue.value = it
            when(action){
                "ForgotPassword" ->{
                    emailViewModel.emailAddress = textValue.value
                    changePasswordEmail = textValue.value
                    Log.d(TAG, "ForgotPassword email to Send OTP Code: ${emailViewModel.emailAddress}")
                }
                "VerifyAndGotoHomeScreen" -> {
                    emailViewModel.sentOTPCode = textValue.value
                    Log.d(TAG, "Sent OTP Code: ${emailViewModel.sentOTPCode}")
                }
            }
            onTextChanged(it)},
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "" )
        },
        isError =! errorStatus
    )
}

@Composable
fun MyPasswordFieldComponent(labelValue: String, painterResource: Painter,
                             onTextChanged: (String) -> Unit,
                             errorStatus: Boolean = false){
    val passwordValue = rememberSaveable{ mutableStateOf("")}
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,
            /*imeAction = ImeAction.Next*/),
        onValueChange = {
            passwordValue.value = it
            onTextChanged(it)},
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
        },
        isError =! errorStatus
    )
}

@Composable
fun MyConfirmPasswordFieldComponent(labelValue: String, painterResource: Painter,
                                    onTextChanged: (String) -> Unit,
                                    errorStatus: Boolean = false){
    val passwordValue = rememberSaveable{ mutableStateOf("")}
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done),
        onValueChange = {
            passwordValue.value = it
            onTextChanged(it)},
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
        },
        isError =! errorStatus
    )
}

@Composable
fun CheckBoxComponent(value: String, navController: NavHostController,
                      onCheckBoxChecked: (Boolean) -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkState = remember { mutableStateOf(false)}
        Checkbox(
            checked = checkState.value,
            onCheckedChange = {checkState.value  = it
            onCheckBoxChecked.invoke(it)})

        ClickableTextComponent(value = value, navController = navController)
    }
}

@Composable
fun ClickableTextComponent(value: String = "", navController: NavHostController){
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
            navController.navigate(Routes.TermsAndConditionsScreen.route)
        }
    })
}

@Composable
fun GeneralClickableTextComponent(value: String, navController: NavHostController, rank: Int = 100){
    val context = LocalContext.current
    Box(modifier = Modifier
        .background(Color.Transparent)) {
        ClickableText(
            text = AnnotatedString(value),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            onClick = {
                  when (rank){
                      0  -> {
                          getToast(context = context, "Nav to login")
                          navController.navigate(Routes.Login.route)
                      }
                      1 -> {
                          getToast(context = context, "Nav to SignUp")
                          navController.navigate(Routes.SignUp.route)
                      }
                      2 -> {
                          getToast(context = context, "Nav to Reset Password")
                          navController.navigate(Routes.ForgotPassword.route)
                      }
                      3 -> {
                          navController.navigate(Routes.Home.route)
                      }
                      4 -> {
                          getToast(context = context, "Nav to Update Profile")
//                          navController.navigate(Routes.UpdateProfile.route)
                      }
                      5 -> {
                          navController.navigate(Routes.ChangePasswordVerifyEmail.route)
                      }
                      6 -> {
                          navController.navigate(Routes.LoginAndSecurity.route)
                      }
                      7 -> {
                          navController.navigate(Routes.UpdateProfile.route)
                      }
                      8 -> {
//                          Log.d(TAG, "Navigating to ChooseVerificationMethod")
                          navController.navigate(Routes.Home.route)
                      }
                  }
            },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = Color.Blue
            )
        )
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun ButtonComponent(navController: NavHostController,
                    value: String, rank: Int = 100,
                    homeViewModel: HomeViewModel = viewModel(),
                    onButtonClicked: () -> Unit, isEnable: Boolean = false,
                    verifyEmailViewModel: VerifyEmailViewModel = viewModel(),
                    originalPage: String = "None"){
    val email = EmailVerifyUIState(verifyEmailViewModel.emailAddress)

    Button(onClick = {
        when(rank){
            0 -> {
                onButtonClicked.invoke()
                Log.d(TAG, "From $originalPage in ButtonComponent Going to choose verification method with email: ${verifyEmailViewModel.auth.currentUser?.email}")
            }
            1 -> {
                onButtonClicked.invoke()
                Log.d(TAG, "From $originalPage in ButtonComponent")
                verifyEmailViewModel.sendOTPToEmail(
                    email = email,
                    navController = navController,
                    type = "MFAVerifyEmail")
            }
            2 -> {
                onButtonClicked.invoke()
                Log.d(TAG, "From $originalPage in ButtonComponent")
                verifyEmailViewModel.passwordResetLink(email = verifyEmailViewModel.emailAddress)
                navController.navigate(Routes.ContinueToPasswordChange.route)
//                val auth = FirebaseAuth.getInstance()
//                Log.d(TAG, "From $originalPage in ButtonComponent")
//                verifyEmailViewModel.doesEmailExist(auth = auth,
//                    email = verifyEmailViewModel.emailAddress)
//                verifyEmailViewModel.sendOTPToEmail(
//                    email = email,
//                    navController = navController,
//                    type = "ChangePasswordVerifyEmail")
            }
            3 -> {
                onButtonClicked.invoke()
                Log.d(TAG, "From $originalPage in ButtonComponent")
                navController.navigate(Routes.NewPassword.route)
            }
            4 -> {
                onButtonClicked.invoke()
                Log.d(TAG, "From $originalPage in ButtonComponent")
                navController.navigate(Routes.UserProfile.route)
            }
            5 -> {
                onButtonClicked.invoke()
                Log.d(TAG, "From $originalPage in ButtonComponent")
                verifyEmailViewModel.verifySentOTPCode(
                    navController = navController, destination = "VerifyAndGotoHomeScreen")
            }
            6 -> {
                onButtonClicked.invoke()
                Log.d(TAG, "From $originalPage in ButtonComponent")
                navController.navigate(Routes.UpdateProfile.route)
            }
        }
    },
        modifier = Modifier
            .fillMaxSize()
            .heightIn(48.dp),
            contentPadding = PaddingValues(),
        enabled = isEnable,
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .heightIn(60.dp)
            .background(
                brush = Brush.horizontalGradient(listOf(Color.Gray, Color.Black, Color.Gray)),
                shape = RoundedCornerShape(50.dp)
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
fun SubButton(navController: NavHostController, value: String, rank: Int = 100,
              homeViewModel: HomeViewModel = viewModel(),
              signUpPageViewModel: SignUpPageViewModel = viewModel(),
              isEnable: Boolean = false,
              originalPage: String = "None"){
    Card(modifier = Modifier
        .height(90.dp)
        .fillMaxHeight(.9f),
        elevation = 0.dp
    ){
        ButtonComponent(navController = navController,
            value = value,
            rank = rank,
            homeViewModel = homeViewModel,
            onButtonClicked = {
                signUpPageViewModel.onSignUpEvent(
                    SignUpPageUIEvent.RegisterButtonClickedAfterFirebaseAuth,
                    navController = navController
                )
            },
            isEnable = isEnable,
            originalPage = originalPage)
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ChooseMFAButton(name: String, navController: NavHostController,
                    buttonType: String = "None", onButtonClicked: () -> Unit,
                    verifyEmailViewModel: VerifyEmailViewModel = viewModel()){

    val context = LocalContext.current.applicationContext
    val email = EmailVerifyUIState()

//    when(buttonType){
//        "MFAVerifyEmail" -> {
//            LaunchedEffect(key1 = Unit) {
//                val email = EmailVerifyUIState()
//                Log.d(TAG, "About to send MFAVerifyEmail to ${email.to}")
//                verifyEmailViewModel.sendOTPEmail(email, type = "MFAVerifyEmail",
//                    navController = navController)
//            }
//        }
//        "No Btn click Detected" -> {
//            Log.d(TAG, "Error: -> No Btn click Detected... Try again")
//        }
//    }

    Box {
        Button(
            onClick = {
                when(buttonType){
                    "MFAAuthenticatorApp" -> {
                        onButtonClicked.invoke()
//                        navController.navigate(Routes.AuthenticatorAppVerification.route)
                        getToast(context, action = "AuthenticatorAppVerification Coming soon!")
                    }
                    "MFASMSVerification" -> {
                        onButtonClicked.invoke()
//                        navController.navigate(Routes.SMSVerification.route)
                        getToast(context, action = "SMSVerification coming soon!")
                    }
                    "MFAVerifyEmail" -> {
                        onButtonClicked.invoke()
                        Log.d(TAG, "Going to Send MFA code sent to  ${verifyEmailViewModel.auth.currentUser?.email}")
                        verifyEmailViewModel.sendOTPToEmail(email, type = "MFAVerifyEmail",
                            navController = navController)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .height(90.dp)
                .align(Alignment.CenterEnd)
        ) {
            Box(modifier = Modifier
                .heightIn(60.dp)
                .width(128.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(Color.Gray, Color.Black, Color.Gray)),
                    shape = RoundedCornerShape(50.dp)
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun DesignMFASpace(navController: NavHostController,
                   value: String, buttonType: String = "No Btn click Detected", type: String = "None",
                   signUpPageViewModel: SignUpPageViewModel
){
    Card(modifier = Modifier
        .height(100.dp)
        .fillMaxWidth(),
        elevation = 10.dp,
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(38.dp),
            text = value,
            fontSize = 20.sp,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
            textAlign = TextAlign.Justify
        )
        ChooseMFAButton(name = buttonType,
            navController = navController, buttonType = type,
            onButtonClicked = {
                signUpPageViewModel.onSignUpEvent(
                    signUpEvent = SignUpPageUIEvent.RegisterButtonClickedAfterFirebaseAuth,
                    navController = navController
                )
            }
        )
    }
}

@Composable
fun DividerTextComponent(){
    Row(modifier = Modifier
        .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically) {

        HorizontalDivider(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            thickness = 1.dp,
            color = Color.Gray
        )

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
            thickness = 1.dp
        )
    }
}

@Composable
fun ClickableLoginOrLogOutText(navController: NavHostController,
                               initialText: String, loginText: String, rank: Int = 100){
    val annotatedString = buildAnnotatedString {
        append("$initialText ")
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
                    when(rank){
                        0 -> {
                            navController.navigate(Routes.Login.route)
                        }
                        1 -> {
                            navController.navigate(Routes.SignUp.route)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun RadioButtonSpace(value: String, mainActivity: MainActivity){
    Card(modifier = Modifier
        .height(100.dp)
        .fillMaxWidth(),
        elevation = 10.dp,
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(30.dp),
            text = value,
            fontSize = 20.sp,
            color = Color.Black,
            textAlign = TextAlign.Justify
        )
        SwitchToggleButtonComponent(mainActivity = mainActivity)
    }
}

@Composable
fun SwitchToggleButtonComponent(mainActivity: MainActivity){
    var isCheckedButton by rememberSaveable { mutableStateOf(
        false)}
    val scope = rememberCoroutineScope()

    Switch(
        modifier = Modifier
            .fillMaxSize(.4f)
            .padding(300.dp, 0.dp, 0.dp, 0.dp),
        checked = isCheckedButton,
        onCheckedChange = {
            isCheckedButton = it
        },
        thumbContent = if (isCheckedButton){
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null)
            }
        }else{
            null
        }
    )
}

@Composable
fun DrawerContentComponent(navController: NavHostController, homeViewModel: HomeViewModel,
                           headerTitle: String, defaultTitle: Int = 0){
    when(defaultTitle){
        0 -> {
            HomeScreenDrawerHeader(headerTitle)
        }
    }
    NavigationDrawerBody(navigationDrawerItems = homeViewModel.navigationItemList,
        onNavigationItemClicked = {
            when(it.title){
                "Home" -> {
                    Log.d("Home", "Inside onNavigationItemClicked Home = ${it.itemId}, ${it.title}")
                    navController.navigate(Routes.Home.route)
                }
                "Profile" -> {
                    Log.d("Profile ", "Inside onNavigationItemClicked Profile = ${it.itemId}, ${it.title}")
                    navController.navigate(Routes.UserProfile.route)
                }
                "Setting" -> {
                    Log.d("Setting ", "Inside onNavigationItemClicked Settings = ${it.itemId}, ${it.title}")
                    navController.navigate(Routes.Settings.route)
                }
                "Logout" -> {
                    Log.d("Logout", "Inside onNavigationItemClicked Logout = ${it.itemId}, ${it.title}")
                    homeViewModel.logOut(navController = navController)
                }
            }
        }
    )
}