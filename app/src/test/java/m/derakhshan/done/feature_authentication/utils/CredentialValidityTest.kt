package m.derakhshan.done.feature_authentication.utils


import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase.fail
import org.junit.Test


class CredentialValidityTest {

    @Test
    fun emptyEmail_raiseEmptyEmailException() {
        try {
            credentialValidityChecker(email = "", password = "12345")
            fail("Empty email should raise exception.")
        } catch (e: InvalidCredentialException) {
            assertThat(e.message).isEqualTo("Email address can't left blank.")
        }
    }

    @Test
    fun wrongFormatEmail_raiseWrongEmailException() {
        val wrongFormatEmailList = listOf(
            "test", "test@", "test@application",
            "test@application.", "test@.", "test.application@com"
        )
        for (email in wrongFormatEmailList)
            try {
                credentialValidityChecker(email = email, password = "12345")
                fail("Wrong format email should raise exception.")
            } catch (e: InvalidCredentialException) {
                assertThat(e.message).isEqualTo("Email address format is not correct.")
            }
    }

    @Test
    fun emptyPassword_raiseEmptyPasswordException() {
        try {
            credentialValidityChecker(email = "test@application.com", password = "")
            fail("Empty password should raise exception.")
        } catch (e: InvalidCredentialException) {
            assertThat(e.message).isEqualTo("Password can't left blank.")
        }
    }

    @Test
    fun weakPassword_raiseWeakPasswordException() {
        try {
            credentialValidityChecker(email = "test@application.com", password = "1234")
            fail("weak password should raise exception.")
        } catch (e: InvalidCredentialException) {
            assertThat(e.message).isEqualTo("Password should have at least 5 characters.")
        }
    }

    @Test
    fun emptyNameAndFamily_raiseEmptyNameAndFamilyException() {
        try {
            credentialValidityChecker(
                email = "test@application.com",
                password = "12345",
                nameAndFamily = ""
            )
            fail("Empty name and family should raise exception.")
        } catch (e: InvalidCredentialException) {
            assertThat(e.message).isEqualTo("Name and family can't left blank.")
        }
    }

}