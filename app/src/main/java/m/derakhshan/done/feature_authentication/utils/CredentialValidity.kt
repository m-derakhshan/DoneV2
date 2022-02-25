package m.derakhshan.done.feature_authentication.utils

import android.util.Patterns


@Throws
fun credentialValidityChecker(email: String, password: String, nameAndFamily: String? = null) {
    when {
        email.isBlank() -> {
            throw InvalidCredentialException("Email address can't left blank.")
        }
        !Patterns.EMAIL_ADDRESS.matcher(email)
            .matches() -> {
            throw InvalidCredentialException("Email address format is not correct.")
        }
        password.isBlank() -> {
            throw InvalidCredentialException("Password can't left blank.")
        }
        password.length < 5 -> {
            throw InvalidCredentialException("Password should have at least 5 characters.")
        }
        nameAndFamily != null -> {
            if (nameAndFamily.isBlank())
                throw InvalidCredentialException("Name and family can't left blank.")
        }
    }
}

class InvalidCredentialException(message: String) : Exception(message)