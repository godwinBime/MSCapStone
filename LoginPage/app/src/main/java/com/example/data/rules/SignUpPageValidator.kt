package com.example.data.rules

object SignUpPageValidator {
    fun validateFirstName(firstName: String): SignUpValidationResult{
        return SignUpValidationResult(
            (firstName.isNotEmpty() && firstName.length >= 3)
        )
    }

    fun validateLastName(lastName: String): SignUpValidationResult{
        return SignUpValidationResult(
            (lastName.isNotEmpty() && lastName.length >= 3)
        )
    }

    fun validateEmail(email: String): SignUpValidationResult{
        return SignUpValidationResult(
            (email.isNotEmpty())
        )
    }

    fun validatePhoneNumber(phoneNumber: String): SignUpValidationResult{
        return SignUpValidationResult(
            (phoneNumber.isNotEmpty() && phoneNumber.length >= 9)
        )
    }

    fun validatePassword(password: String): SignUpValidationResult{
        return SignUpValidationResult(
            (password.isNotEmpty() && password.length >= 4)
        )
    }

    fun validateConfirmPassword(confirmPassword: String): SignUpValidationResult{
        return SignUpValidationResult(
            (confirmPassword.isNotEmpty() && confirmPassword.length >= 4)
        )
    }

    fun validateVerificationCode(verificationCode: String): SignUpValidationResult{
        return SignUpValidationResult(
            (verificationCode.isNotEmpty() && verificationCode.length >= 4)
        )
    }
}

data class SignUpValidationResult(
    val signUpStatus: Boolean = false
)