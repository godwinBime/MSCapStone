package com.example.loginpage.ui.component

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.data.uievents.SignUpPageUIEvent
import com.example.data.uistate.EmailVerifyUIState
import com.example.data.viewmodel.GoogleSignInViewModel
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.ProfileViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.data.viewmodel.TimerViewModel
import com.example.data.viewmodel.VerifyEmailViewModel
import com.example.loginpage.R
import com.example.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import com.example.data.uistate.UserProfilePictureData
import kotlinx.coroutines.delay

private val TAG = VerifyEmailViewModel::class.simpleName
var changePasswordEmail: String = ""

@Composable
fun LoadingScreenComponent(
    googleSignInViewModel: GoogleSignInViewModel = hiltViewModel(),
    signUpPageViewModel: SignUpPageViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel(),
    verifyEmailViewModel: VerifyEmailViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel()
){
    val googleSignInState = googleSignInViewModel.googleState.value
    if (signUpPageViewModel.signInSignUpInProgress.value ||
        profileViewModel.updateProfileInProgress.value ||
        homeViewModel.checkActiveSessionInProgress.value ||
        verifyEmailViewModel.authenticationInProgress.value ||
        googleSignInState.loading){
        CircularProgressIndicator(
            color = Color.DarkGray,
            strokeWidth = 10.dp,
            modifier = Modifier
                .wrapContentSize(align = Alignment.Center)
                .size(100.dp)
        )
    }
}

@Composable
fun NormalTextComponent(value: String, action: String = "None"){
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp),
        text = value,
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = if (action == "dpUpdate") FontWeight.W100 else FontWeight.Medium,
            fontStyle = FontStyle.Normal
        ),
        color = if (action == "DeleteProfile") Color.Red else Color.Black, //if (isSystemInDarkTheme()) Color.White else Color.Black,
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
                         updateProfileViewModel: ProfileViewModel = viewModel(),
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
                "ChangePasswordVerifyEmail" -> {
                    emailViewModel.sentOTPCode = textValue.value
                }
                "UpdatePhoneNumber" -> {
                    Log.d(TAG, "Updated PhoneNumber: ${textValue.value}")
                    updateProfileViewModel.updatedPhoneNumber = textValue.value
                }
                "UpdateFirstName" -> {
                    Log.d(TAG, "Updated First name: ${textValue.value}")
                    updateProfileViewModel.updatedFirstName = textValue.value
                }
                "UpdateLastName" -> {
                    Log.d(TAG, "Updated Last name: ${textValue.value}")
                    updateProfileViewModel.updatedLastName = textValue.value
                }
                "DeleteProfile" -> {
                    emailViewModel.sentOTPCode = textValue.value
                    Log.d(TAG, "Sent OTP Code to validate DeleteProfile action: ${emailViewModel.sentOTPCode}")
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
                             errorStatus: Boolean = false,
                             updateProfileViewModel: ProfileViewModel = viewModel()){
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
            updateProfileViewModel.oldPassword = passwordValue.value
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
                                    errorStatus: Boolean = false,
                                    updateProfileViewModel: ProfileViewModel = viewModel()){
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
            updateProfileViewModel.newPassword = passwordValue.value
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
fun GeneralClickableTextComponent(value: String, navController: NavHostController,
                                  rank: Int = 100,
                                  verifyEmailViewModel: VerifyEmailViewModel = viewModel(),
                                  timerViewModel: TimerViewModel = viewModel(),
                                  type: String = "None"){
    val context = LocalContext.current
    var isClickableEnabled by remember { mutableStateOf(false) }
    val auth = FirebaseAuth.getInstance()

    Box(modifier = Modifier
        .background(Color.Transparent)) {
        ClickableText(
            text = buildAnnotatedString {
                if (isClickableEnabled){
                    withStyle(style = MaterialTheme.typography.body1.toSpanStyle()){
                        append(text = value)
                    }
                }else{
                    withStyle(style = MaterialTheme.typography.body1.toSpanStyle()){
                        append(text = value)
                    }
                }
            },
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
                          if (timerViewModel.isFinished.value) {
                              getToast(context = context, "Resending OTP code")
                              val email = auth.currentUser?.email?.let { otpEmailTask ->
                                      EmailVerifyUIState(otpEmailTask)
                                  }
                              Log.d(TAG, "Resending OTP to: ${email?.to}")
                              if (email != null) {
                                  timerViewModel.isFinished.value = false
                                  timerViewModel.isRunning.value = false
                                  timerViewModel.timeLeft.value = 60L
                                  verifyEmailViewModel.sendOTPToEmail(
                                      email = email,
                                      navController = navController,
                                      type = type
                                  )
                              }
                          } else {
                              Log.d(TAG, "Timer triggered")
                              timerViewModel.startTimer(timerDuration = 1000)
                              Log.d(TAG, "Time left: ${timerViewModel.timeLeft.value}")
                          }
                      }
                      5 -> {
                          navController.navigate(Routes.ChangePasswordVerifyEmail.route)
                      }
                      6 -> {
                          navController.navigate(Routes.Home.route)
                      }
                      7 -> {
                          navController.navigate(Routes.UserProfilePicture.route)
                      }
                      8 -> {
//                          Log.d(TAG, "Navigating to ChooseVerificationMethod")
                          navController.navigate(Routes.ChangePasswordVerifyEmail.route)
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
                    value: String,
                    homeViewModel: HomeViewModel = viewModel(),
                    rank: Int = 100,
                    onButtonClicked: () -> Unit, isEnable: Boolean = false,
                    verifyEmailViewModel: VerifyEmailViewModel = viewModel(),
                    updateProfileViewModel: ProfileViewModel = viewModel(),
                    signUpPageViewModel: SignUpPageViewModel = viewModel(),
                    originalPage: String = "None", userType: String = ""){
    val email = EmailVerifyUIState(verifyEmailViewModel.emailAddress)
    val context = LocalContext.current.applicationContext
    val googleContext = LocalContext.current

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
            }
            3 -> {
                onButtonClicked.invoke()
                Log.d(TAG, "From $originalPage in ButtonComponent")
                navController.navigate(Routes.NewPassword.route)
            }
            4 -> {
                Log.d(TAG, "About to update user profile...")
                onButtonClicked.invoke()
                Log.d(TAG, "From $originalPage in ButtonComponent")
                updateProfileViewModel.updateUserProfile(navController = navController)
            }
            5 -> {
                onButtonClicked.invoke()
                Log.d(TAG, "From $originalPage in ButtonComponent")
                Log.d(TAG, "Inside VerifyAndGotoHomeScreen statement---")
                verifyEmailViewModel.verifySentOTPCode(
                    navController = navController, destination = "VerifyAndGotoHomeScreen")
            }
            6 -> {
                onButtonClicked.invoke()
                Log.d(TAG, "From $originalPage in ButtonComponent")
                navController.navigate(Routes.UpdateProfile.route)
            }
            7 -> {
                onButtonClicked.invoke()
                Log.d(TAG, "From $originalPage in ButtonComponent")
                updateProfileViewModel.changeUserPassword(navController = navController)
            }
            8 ->{
                onButtonClicked.invoke()
                Log.d(TAG, "From $originalPage in ButtonComponent")
                Log.d(TAG, "Inside ChangePasswordVerifyEmail statement---" )
                verifyEmailViewModel.verifySentOTPCode(
                    navController = navController, destination = "ChangePasswordVerifyEmail")
            }
            9 -> {
                onButtonClicked.invoke()
                Log.d(TAG, "From $originalPage in ButtonComponent")
                if (userType == "password") {
                    updateProfileViewModel.deleteCurrentProfile(
                        navController = navController,
                        signUpPageViewModel = signUpPageViewModel,
                        providerId = userType
                    )
                }else if (userType == "google.com"){
                    updateProfileViewModel.deleteGoogleCredentials(
                        navController = navController, signUpPageViewModel = signUpPageViewModel,
                        context = googleContext, homeViewModel = homeViewModel,
                        providerId = userType)
                }
                getToast(context, action = "Round Delete Button clicked!")
            }
            10 -> {
                onButtonClicked.invoke()
                Log.d(TAG, "From $originalPage in ButtonComponent")
                navController.navigate(Routes.Login.route)
            }
            11 -> {
                Log.d(TAG, "From $originalPage in ButtonComponent")
                Log.d(TAG, "Inside DeleteProfileVerifyEmail statement---" )
                verifyEmailViewModel.verifySentOTPCode(
                    navController = navController, destination = "DeleteProfile")
            }
        }
    },
        modifier = Modifier
            .fillMaxSize(0.9f)
            .heightIn(),
            contentPadding = PaddingValues(),
        enabled = isEnable,
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .heightIn(70.dp)
            .background(
                brush = if(originalPage == "DeleteProfile.kt") Brush.horizontalGradient(listOf(Color.Gray, Color.Red, Color.Gray))
                else Brush.horizontalGradient(listOf(Color.Gray, Color.Black, Color.Gray)),
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
              signUpPageViewModel: SignUpPageViewModel = viewModel(),
              isEnable: Boolean = false,
              originalPage: String = "None", userType: String = ""){
    Card(modifier = Modifier
        .height(90.dp)
        .fillMaxHeight(.9f),
        elevation = 0.dp
    ){
        ButtonComponent(navController = navController,
            value = value,
            rank = rank,
            onButtonClicked = {
                signUpPageViewModel.onSignUpEvent(
                    SignUpPageUIEvent.RegisterButtonClickedAfterFirebaseAuth,
                    navController = navController
                )
            },
            isEnable = isEnable,
            originalPage = originalPage,
            userType = userType)
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ChooseMFAButton(name: String, navController: NavHostController,
                    buttonType: String = "None", onButtonClicked: () -> Unit,
                    verifyEmailViewModel: VerifyEmailViewModel = viewModel()){

    val context = LocalContext.current.applicationContext
    val email = EmailVerifyUIState()
    val auth = FirebaseAuth.getInstance()
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
                        Log.d(TAG, "Going to Send MFA code sent to ${auth.currentUser?.email}")
                        verifyEmailViewModel.sendOTPToEmail(email, type = "MFAVerifyEmail",
                            navController = navController)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .height(80.dp)
                .width(160.dp)
                .align(Alignment.CenterEnd)
        ) {
            Box(modifier = Modifier
                .heightIn(50.dp)
                .width(140.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(Color.Gray, Color.Black, Color.Gray)),
                    shape = RoundedCornerShape(95.dp)
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
fun DividerTextComponent(type: String = "None"){
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

        when(type){
            "Login" -> {
                Text(modifier = Modifier
                    .padding(8.dp),
                    text = " or ",
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            thickness = 1.dp,
            color = Color.Gray
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
fun RadioButtonSpace(value: String){
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
        SwitchToggleButtonComponent()
    }
}

@Composable
fun SwitchToggleButtonComponent(){
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
                           verifyEmailViewModel: VerifyEmailViewModel = viewModel(),
                           signUpPageViewModel: SignUpPageViewModel = viewModel()){
    val context = LocalContext.current.applicationContext
    val auth = FirebaseAuth.getInstance()
    val providerId = signUpPageViewModel.checkUserProvider(user = auth.currentUser)
    when(providerId) {
        "password" -> {
            HomeScreenDrawerHeader(
                value = signUpPageViewModel.fullNames.substringBefore(" "),
                user = auth.currentUser, provider = "password", context = context,
                navController = navController)
        }
        "google.com" -> {
            HomeScreenDrawerHeader(
                value = auth.currentUser?.displayName?.substringBefore(" "),
                user = auth.currentUser,
                provider = "google.com",
                context = context, navController = navController)
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
                "Change Password" -> {
                    if (providerId == "google.com") {
                        getToast(context = context, "Use Your Google Account for this action.")
                    }else if (providerId == "password"){
                        val email = auth.currentUser?.email?.let { userEmail ->
                            EmailVerifyUIState(
                                userEmail
                            )
                        }
                        Log.d(TAG, "Call to Change Password From ChangePassword DrawerContentComponent")
                        if (email != null) {
                            verifyEmailViewModel.sendOTPToEmail(
                                email = email,
                                navController = navController,
                                type = "ChangePasswordVerifyEmail")
                            if (verifyEmailViewModel.isOTPSent) {
                                Log.d(TAG, "OTPSent...Navigating to verify OTP and Change Password...")
                                navController.navigate(
                                    Routes.MFAVerifyEmail.route)
                            }
                            navController.popBackStack()
                        }else {
                            getToast(context = context, "No email provided.")
                        }
                    }else {
                        getToast(context = context, "Error: Auth is neither Google or email/password")
                    }
                }
                "Setting" -> {
                    Log.d("Setting ", "Inside onNavigationItemClicked Settings = ${it.itemId}, ${it.title}")
                    navController.navigate(Routes.Settings.route)
                }
                "Logout" -> {
                    Log.d("Logout", "Inside onNavigationItemClicked Logout = ${it.itemId}, ${it.title}")
                    homeViewModel.logOut(navController = navController,
                        signUpPageViewModel = signUpPageViewModel,
                        context = context)
                }
            }
        }
    )
}

@Composable
fun GoogleAccountProfilePictureComponent(user: FirebaseUser?, size: Dp = 120.dp){
    Image(
        painter = rememberAsyncImagePainter(
            model = user?.photoUrl,
        ),
        contentDescription = "Profile Picture",
        modifier = Modifier
            .clip(CircleShape)
//            .padding(20.dp)
            .size(size = size),
        contentScale = ContentScale.Crop
    )
}
/*
fun ChangeProfilePictureIcon(iconSize: Dp = 45.dp, onClick:() -> Unit){
//    Spacer(modifier = Modifier.height(20.dp))
    Box(modifier = Modifier
//        .padding(6.dp)
        .size(55.dp)
//        .align(Alignment.BottomEnd)
        .background(Color.Gray, shape = CircleShape)){
        IconButton(modifier = Modifier
            .align(Alignment.Center),
            onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.baseline_add_a_photo_24),
                contentDescription = "Change profile picture",
                modifier = Modifier
//                    .padding(10.dp)
                    .clickable (onClick = onClick)
                    .align(Alignment.Center)
                    .size(size = iconSize),
                tint = Color.LightGray
            )
        }
    }
}
*/

@Composable
fun DisplayProfilePicture(uri: Uri?, navController: NavHostController,
                          imageSize: Dp, pageSource: String, onClick: () -> Unit){
    Image(
//                painter = painter,
        painter = rememberAsyncImagePainter(uri),
        contentDescription = "Profile Picture",
        modifier = if (pageSource == "HomeScreenDrawerHeader"){
            Modifier
//                    .clickable { showDialog = pageSource == "HomeScreenDrawerHeader" }
                .clickable { navController.navigate(Routes.UserProfilePicture.route)}
                .border(width = 2.dp, color = Color.Gray,
                    shape = RoundedCornerShape(size = 90.dp))
                .size(size = imageSize)
                .fillMaxSize()
        }else{
            Modifier
                .clickable(onClick = onClick)
                .border(width = 2.dp, color = Color.Gray,
                    shape = RoundedCornerShape(size = 299.dp))
                .size(size = imageSize)
                .fillMaxSize()
        },
        contentScale = ContentScale.Crop)
}

@Composable
fun DoesPictureExist(imagePath: String, profileViewModel: ProfileViewModel = viewModel()){
    profileViewModel.isPictureExistInDatabase(imagePath = imagePath,
        onSuccess = {
            profileViewModel.profilePictureExist.value = true
        },
        onFailure = {
            profileViewModel.profilePictureExist.value = false
        })
}

@Composable
fun downloadImage(imagePath: String,
                  uri: Uri?,
                  context: Context,
                  isCallValid: Boolean = false,
                  profileViewModel: ProfileViewModel = viewModel()): Uri?{
    val TAG1 = ProfileViewModel::class.simpleName
    var downloadedImageUri by rememberSaveable() {mutableStateOf<Uri?>(null)}
    val profilePictureUri by profileViewModel.profilePictureUri.observeAsState()

    DoesPictureExist(imagePath = imagePath, profileViewModel = profileViewModel)
    LaunchedEffect(Unit) {
        if (profilePictureUri?.userProfilePictureDataImageUri == null &&
            uri == null) {
            Log.d(TAG1, "Inside downloadImage() if() uri's all null...\n\n")
            profileViewModel.downloadProfilePicture(imagePath = imagePath,
                isCallValid = isCallValid,
                onSuccess = { uri ->
                    downloadedImageUri = uri
                    Toast.makeText(
                        context,
                        "Image Downloaded successfully....",
                        Toast.LENGTH_LONG
                    ).show()
                },
                onFailure = {
                    Toast.makeText(
                        context,
                        "Image Download Failed...",
                        Toast.LENGTH_LONG
                    ).show()
                })
        } else {
            downloadedImageUri = uri?:profilePictureUri?.userProfilePictureDataImageUri
            Log.d(
                TAG1,
                "Inside downloadImage() else()...\n" +
                        "Does profilePictureExist in uri : ${uri == null}\n" +
                        "Does profilePictureExist in profilePictureUri?.userProfilePictureDataImageUri : " +
                        "${profilePictureUri?.userProfilePictureDataImageUri == null}"
            )
        }
    }
    return downloadedImageUri
}

@Composable
fun UploadPicture(imageUri: Uri?, isCallValid: Boolean, navController: NavHostController,
                  profileViewModel: ProfileViewModel = viewModel(),
                  onSuccess: () -> Unit, onFailure: () -> Unit){
    val context = LocalContext.current
    LaunchedEffect(imageUri) {
        if (imageUri != null) {
            profileViewModel.uploadProfilePicture(uri = imageUri,
                isCallValid = isCallValid, navController = navController,
                onSuccess = {
                    onSuccess()
                    Toast.makeText(
                        context,
                        "Image Uploaded successfully...${profileViewModel.uploadProgress.value}%",
                        Toast.LENGTH_LONG
                    ).show()
                },
                onFailure = {
                    onFailure()
                    Toast.makeText(
                        context,
                        "Image Upload Failed...",
                        Toast.LENGTH_LONG
                    ).show()
                })

        }else{
            Toast.makeText(
                context,
                "Image Uploaded Impossible without Uri...",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

@Composable
private fun TraditionalAccountProfilePictureComponent(imageUri: Uri?,
                                                      pageSource: String = "None",
                                                      navController: NavHostController,
                                                      imageSize: Dp = 90.dp,
                                                      size: Dp = 12.dp,
                                                      boxSize: Dp = 120.dp,
                                                      isImageClicked: Boolean = false,
                                                      profileViewModel: ProfileViewModel = viewModel(),
                                                      onClick: () -> Unit){
    val TAG1 = ProfileViewModel::class.simpleName
    var finalImageUri by rememberSaveable() { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val defaultProfileImageUri: Uri? =
        Uri.parse("android.resource://com.example.loginpage/drawable/default_profile_picture")
    val user = FirebaseAuth.getInstance().uid
    val imagePath = "/ProfilePictures/$user"
    val profilePictureUri by profileViewModel.profilePictureUri.observeAsState()

    Log.d(TAG1,
        "Is profilePictureUri empty-->: ${profilePictureUri?.userProfilePictureDataImageUri == null}"
    )
    LaunchedEffect(Unit) {
        finalImageUri = (profilePictureUri?.userProfilePictureDataImageUri?: imageUri)
    }

    if (finalImageUri == null){
        Log.d(TAG1, "\n\nfinalImageUri is null...\n\n")
        finalImageUri = downloadImage(imagePath = imagePath,
            uri = imageUri,
            context = context, isCallValid = isImageClicked)
    }

    Box(modifier = Modifier
        .size(
            size =
        if (pageSource == "HomeScreenDrawerHeader"){
                size
            }else{
                boxSize
            }
        )
        .clip(CircleShape),
        contentAlignment = Alignment.Center){
        (finalImageUri?: defaultProfileImageUri)?.let { uri ->
            DisplayProfilePicture(
                uri = uri, navController = navController,
                imageSize = imageSize, pageSource = pageSource
            ) {
                onClick.invoke()
            }
        }
    }
    if (imageUri != null && isImageClicked){
        Log.d(TAG1, "Update dp initiated...image clicked")
        UploadPicture(imageUri = imageUri, isCallValid = true,
            navController = navController, profileViewModel = profileViewModel,
            onSuccess = {}, onFailure = {})
    }
}

@Composable
fun PhotoPickerComponent(navController: NavHostController,
                         isImageClicked: Boolean = false,
                         profileViewModel: ProfileViewModel = viewModel(),
                         pageSource: String =" None",
                         imageSize: Dp = 90.dp,
                         size: Dp = 120.dp, boxSize: Dp = 120.dp){
    var selectedImageUri by rememberSaveable() {
        mutableStateOf<Uri?>(null)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            selectedImageUri = it
        }
    )

    TraditionalAccountProfilePictureComponent(
        imageUri = selectedImageUri,
        pageSource = pageSource,
        navController = navController,
        isImageClicked = pageSource == "UserProfilePicture",
        imageSize = imageSize,
        size = size, boxSize = boxSize) {
        photoPickerLauncher.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }
    if (pageSource == "UserProfilePicture"){
        ProfilePictureButtonComponent(navController = navController){
            photoPickerLauncher.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }
    }
}

@Composable
fun ProfilePictureButtonComponent(navController: NavHostController,
                                  profileViewModel: ProfileViewModel = viewModel(),
                                  onClick: () -> Unit){
    val context = LocalContext.current
    val user = FirebaseAuth.getInstance().uid
    val imagePath = "/ProfilePictures/$user"

    Spacer(modifier = Modifier.height(180.dp))
    Row(
        modifier = Modifier
//            .background(Color.Gray)
            .padding(start = 12.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ProfileButtonComponent(action = "Change Picture",
            btnName = "Change",
            buttonIconPainterResource = painterResource(id = R.drawable.baseline_edit_24),
            navController = navController){
            onClick.invoke()
        }
        Spacer(modifier = Modifier.width(40.dp))
        ProfileButtonComponent(action = "Remove Picture",
            btnName = "Remove",
            buttonIconPainterResource = painterResource(id = R.drawable.baseline_delete_24),
            navController = navController){
            profileViewModel.deleteProfilePicture(imagePath = imagePath,
                navController = navController)
            getToast(context = context, "Delete Profile Picture")
        }
    }
    Spacer(modifier = Modifier
        .height(120.dp))
}

@Composable
fun PopUpMessageComposable(isShowDialogClicked: Boolean, action: String = "None",
                           message: String = "None",
                           profileViewModel: ProfileViewModel = viewModel(),
                           navController: NavHostController){
    var showDialog by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        showDialog = isShowDialogClicked
        if (showDialog){
            AlertDialog(
                onDismissRequest = {},
                title = { Text(text = action)},
                text = { Text(text = message)},
                confirmButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent),
                        onClick = {
                            showDialog = false
//                            profileViewModel.isProfilePictureSuccessfullyChanged.value = false
                            navController.navigate(Routes.UserProfile.route)
                        }
                    ) {
                        PopUpButtonComponent()
                    }
                }
            )
        }
    }
}

@Composable
fun ProfileButtonComponent(action: String = "None", btnName: String = "None",
                           buttonIconPainterResource: Painter,
                           navController: NavHostController, onClick: () -> Unit){
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Gray
        ),
        modifier = Modifier
            .padding(8.dp),
        onClick = {
            onClick.invoke()
        }
    ) {
        Image(
            modifier = Modifier
                .size(24.dp),
            painter = buttonIconPainterResource,
            contentDescription = action
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = btnName,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun PopUpButtonComponent(description: String = "Dismiss"){
    Box(modifier = Modifier
        .heightIn(45.dp)
        .width(120.dp)
        .background(
            brush = Brush.horizontalGradient(listOf(Color.Gray, Color.Black, Color.Gray)),
            shape = RoundedCornerShape(95.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = description,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}