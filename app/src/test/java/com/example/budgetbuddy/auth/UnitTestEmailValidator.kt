package com.example.budgetbuddy.auth

import com.example.budgetbuddy.extensions.isValidEmail
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class EmailValidatorUnitTests {

    @Test
    fun email_isValid() {
        val emailInput = "email@test.com"
        assertEquals(
            true, emailInput.isValidEmail(),
        )
    }

    @Test
    fun email_isNotValid_1() {
        val emailInput = "emailtest.com"
        assertEquals(
            false, emailInput.isValidEmail(),
        )
    }

    @Test
    fun email_isNotValid_2() {
        val emailInput = "email@testcom"
        assertEquals(
            true, emailInput.isValidEmail().not(),
        )
    }

    @Test
    fun email_isNotValid_3() {
        val emailInput = "emailtestcom"
        assertNotEquals(
            true, emailInput.isValidEmail(),
        )
    }

    @Test
    fun email_isNotValid_4() {
        val emailInput = "emailtestcom"
        assertNotEquals(
            false, emailInput.isValidEmail().not(),
        )
    }

    @Test
    fun email_isNotValid_5() {
        val emailInput = "email@@test.com"
        assertNotEquals(
            true, emailInput.isValidEmail(),
        )
    }

    @Test
    fun email_isNotValid_5_same_but_different() {
        val emailInput = "email@@test.com"
        assertEquals(
            false, emailInput.isValidEmail(),
        )
    }

    @Test
    fun email_isNotValid_evil() {
        val emailInput = "emailtestcom"
        assertNotEquals(
            false.not(), emailInput.isValidEmail(),
        )
    }

}
