Multi-factor authentication is the backbone of this mobile application.
Users are encouraged to practice securing their mobile devices by following authentication best practices. Using Google authentication as a login alternative can be beneficial to both developers and users.

========================= ATTENTION===================================
BEFORE THIS APP RUNS, COMPLETE THE FOLLOWING CONFIGURATIONS
- Create a firebase account and connect the app there,
- Download the "google-services.json" and follow the instructions on where the place it in Android Studio.


=====================NEXT==========================
GOTO "LoginPage/app/src/main/java/com/example/data/local/entities/Constant.kt"
object Constant {
    const val SERVERCLIENT = "Web SDK configuration -> Web client ID"
}
web client ID, Needed in google sign in component file
 - You can get this from your Firebase sign-in method tab under Google as your provider
  labelled "Web SDK configuration -> Web client ID"


==================================================
NOTE:
If you login using your Google credentials, you cannot only make changes to your profile 
from your main Google account.

Below are explanations on how core functionalities of this app work.
APP LAUNCH:
When the app starts, it checks if there is an existing session:
- If there is one, it checks the authentication provider, if it's "google.com, it navigates
  immediately to the home screen. If the provider is "password", it navigates immediately
  to the verification page and a 5 digit pin is sent to the user's email and they provide
  the code and if it is correct, they get authenticated and navigated to the home screen.

- If there is no active session, the user then choose to login with email and password and follow
  MFA procedure or login using their Google credentials or create a new account.

- PASSWORD RESET WHEN PASSWORD IS FORGOTTEN
When the user clicks the "forgot password" button, the user is asked to provide the email address 
they used in creating an account with, if there is an account associated with this email, 
a password reset link is sent to them which they can click on an a password reset page will 
then open. Then the user will be prompted to enter a new password, once it is registered, 
the user can then use it as the new password.
- PASSWORD RESET WHEN LOGGED-IN
When a user is authenticated, if the user wants to change the password, 
the user gets verified one more time, then a password reset option within 
the app is provided which then lets the user change the password.

- OTP Code Expiry
Whenever a OTP is sent to the user's email, 
the users has 60 seconds to input it before ir expires.
The user is provided with a OTP resend code button each time the code expires.

- OTP Code Resend
The current OTP code has to expire before a new one is sent.
The user gets a counter on the screen telling them how much time is left
before a new OTP is resent if they click the resend button.

- NOTE
When the user clicks the back button continuously and arrives the login page, 
the user automatically gets logged out of the app. There the backstack gets emptied
in case there are still some screens available in the navigation stack.

- Authentication Start timer
  The timer starts as soon as the user starts typing into the input fields.
  If the user leaves the authentication process incomplete the timer will
  keep counting until the user comes and complete it. It can take days, weeks
  months, years, the timer will keep track as long as the user does not uninstall
  and reinstall the app.
  - the timer gets reset once the user gets to the home screen. 
  - The timer also gets reset when the app runs and checks if the timer is set  to true
    if it is set to true while the user is in the login screen, it gets reset again.

- HANDLING STATES
  - Sharepreferences is being used to handle the states, such as if a user is logged-in,
    logged-out, returning active user, if the authentication process is complete.
    These state handlers work work with Viewmodels to make sure data persists across screens
    and mainly when a reconfiguration occurs through out the life cycle of the application.


References:
Google icons created by Freepik - Flaticon:
- https://www.flaticon.com/search?word=google
