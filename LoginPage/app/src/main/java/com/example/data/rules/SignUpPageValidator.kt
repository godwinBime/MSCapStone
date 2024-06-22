package com.example.data.rules

object SignUpPageValidator {
    fun validateFirstName(firstName: String): SignUpValidationResult{
        return SignUpValidationResult(
            (!firstName.isNullOrEmpty() && firstName.length > 1)
        )
    }

    fun validateLastName(lastName: String): SignUpValidationResult{
        return SignUpValidationResult(
            (!lastName.isNullOrEmpty() && lastName.length > 1)
        )
    }

    fun validateEmail(email: String): SignUpValidationResult{
        return SignUpValidationResult(
            (!email.isNullOrEmpty() && email.length > 10)
        )
    }

    fun validatePhoneNumber(phoneNumber: String): SignUpValidationResult{
        return SignUpValidationResult(
            (!phoneNumber.isNullOrEmpty() && phoneNumber.length > 7)
        )
    }

    fun validatePassword(password: String): SignUpValidationResult{
        return SignUpValidationResult(
            (password.isNotEmpty() && password.length >= 7)
        )
    }

    fun validateConfirmPassword(confirmPassword: String, password: String): SignUpValidationResult{
        return SignUpValidationResult(
            (confirmPassword.isNotEmpty() && confirmPassword.length >= 7
//                    && confirmPassword == password
            )
        )
    }

    fun validateVerificationCode(verificationCode: String): SignUpValidationResult{
        return SignUpValidationResult(
            (!verificationCode.isNullOrEmpty() && verificationCode.length > 2)
        )
    }

    fun validatePrivacyPolicyAcceptanceState(privacyPolicyStatusValue: Boolean): SignUpValidationResult{
        return SignUpValidationResult(
            !privacyPolicyStatusValue
        )
    }
}

data class SignUpValidationResult(
    val signUpStatus: Boolean = false
)