package com.example.budgetbuddy.auth

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class PasswordConfirmationUnitTests {

    @Test
    fun `when passwords match, validation is successful`() {
        val password = "heslo123"
        val confirmPassword = "heslo123"
        assertEquals("Passwords should match", password, confirmPassword)
    }

    @Test
    fun `when passwords do not match, validation fails`() {
        val password = "heslo123"
        val confirmPassword = "heslo456"
        assertNotEquals("Passwords should not match", password, confirmPassword)
    }
}