package com.example.budgetbuddy.auth

import com.example.budgetbuddy.extensions.isValidPassword
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class PasswordValidatorUnitTests {

    @Test
    fun password_isValid() {
        val passwordInput = "heslo123"
        assertEquals(true, passwordInput.isValidPassword(),
        )
    }

    @Test
    fun password_isNotValid_1() {
        val passwordInput = "heslo"
        assertEquals(false, passwordInput.isValidPassword(),
        )
    }

    @Test
    fun password_isNotValid_2() {
        val passwordInput = "heslo1"
        assertEquals(true, passwordInput.isValidPassword().not(),
        )
    }

    @Test
    fun password_isNotValid_3() {
        val passwordInput = "123456"
        assertNotEquals(true, passwordInput.isValidPassword(),
        )
    }

    @Test
    fun `when password input is not incorrect we expect not valid`() {
        val passwordInput = "a"
        assertNotEquals(true, passwordInput.isValidPassword(),
        )
    }

}
