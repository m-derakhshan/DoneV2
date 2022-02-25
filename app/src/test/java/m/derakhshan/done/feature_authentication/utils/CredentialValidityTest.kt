package m.derakhshan.done.feature_authentication.utils

import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase.fail
import org.junit.Test


class CredentialValidityTest {

    @Test
    fun emptyEmail_raiseEmptyEmailException() {
        try {
            credentialValidityChecker("", "12345", null)
            fail("Empty email should raise exception.")
        } catch (e: Exception) {
            assertThat(e.message).isEqualTo("Email address can't left blank.")
        }
    }

    @Test
    fun wrongFormatEmail_raiseWrongEmailException() {
        val wrongFormatEmailList = listOf(
            "mohammad", "mohammad@", "mohammad@derakhshan",
            "mohammad@derakhshan.", "mohammad@.", "mohammad.derakhshan@com"
        )
        for (email in wrongFormatEmailList)
            try {
                credentialValidityChecker(email, "12345", null)
                fail("Wrong format email should raise exception.")
            } catch (e: Exception) {
                assertThat(e.message).isEqualTo("Email address format is not correct.")
            }
    }

    @Test
    fun emptyPassword_raiseEmptyPasswordException() {
        try {
            credentialValidityChecker("mohammad@derakhshan.com", "12345", null)
            fail("Empty password should raise exception.")
        } catch (e: Exception) {
            assertThat(e.message).isEqualTo("Password can't left blank.")
        }
    }

    @Test
    fun weakPassword_raiseWeakPasswordException() {
        try {
            credentialValidityChecker(
                "mohammad@derakhshan.com", "1234", null
            )
            fail("weak password should raise exception.")
        } catch (e: Exception) {
            assertThat(e.message).isEqualTo("Password should have at least 5 characters.")
        }
    }

    @Test
    fun emptyNameAndFamily_raiseEmptyNameAndFamilyException() {
        try {
            credentialValidityChecker("mohammad@derakhshan.com", "12345", "")
            fail("Empty name and family should raise exception.")
        } catch (e: Exception) {
            assertThat(e.message).isEqualTo("Name and family can't left blank.")
        }
    }

}