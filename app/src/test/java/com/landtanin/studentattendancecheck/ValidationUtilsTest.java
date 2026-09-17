package com.landtanin.studentattendancecheck;

import com.landtanin.studentattendancecheck.util.ValidationUtils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests verifying input validation logic for credentials and student profiles.
 */
public class ValidationUtilsTest {

    @Test
    public void validEmail_returnsTrue() {
        assertTrue(ValidationUtils.isValidEmail("student@university.edu"));
        assertTrue(ValidationUtils.isValidEmail("john.doe@domain.com"));
        assertTrue(ValidationUtils.isValidEmail("atharva.naik@vit.ac.in"));
        assertTrue(ValidationUtils.isValidEmail("user123_test@my-domain.co.uk"));
    }

    @Test
    public void invalidEmail_returnsFalse() {
        assertFalse("Missing @ symbol", ValidationUtils.isValidEmail("plainaddress"));
        assertFalse("Missing username", ValidationUtils.isValidEmail("@domain.com"));
        assertFalse("Missing domain", ValidationUtils.isValidEmail("student@"));
        assertFalse("Missing TLD", ValidationUtils.isValidEmail("student@domain"));
        assertFalse("Space in email", ValidationUtils.isValidEmail("student @domain.com"));
        assertFalse("Trailing dot", ValidationUtils.isValidEmail("student@domain."));
    }

    @Test
    public void nullOrEmptyEmail_returnsFalse() {
        assertFalse("Null email must fail", ValidationUtils.isValidEmail(null));
        assertFalse("Empty string must fail", ValidationUtils.isValidEmail(""));
        assertFalse("Whitespace-only string must fail", ValidationUtils.isValidEmail("   "));
    }

    @Test
    public void passwordLength_validatesCorrectly() {
        assertTrue("Password length 4 should pass default", ValidationUtils.isValidPassword("pass"));
        assertTrue("Password length 8 should pass default", ValidationUtils.isValidPassword("password"));
        assertFalse("Password length 3 should fail default", ValidationUtils.isValidPassword("123"));
        assertFalse("Empty password should fail", ValidationUtils.isValidPassword(""));
        assertFalse("Null password should fail", ValidationUtils.isValidPassword(null));
    }

    @Test
    public void customMinPasswordLength_validatesCorrectly() {
        assertTrue(ValidationUtils.isValidPassword("secret123", 6));
        assertFalse(ValidationUtils.isValidPassword("12345", 6));
    }

    @Test
    public void studentId_validatesPositiveIntegers() {
        assertTrue(ValidationUtils.isValidStudentId(1));
        assertTrue(ValidationUtils.isValidStudentId(10542));
        assertFalse("Zero student ID is invalid", ValidationUtils.isValidStudentId(0));
        assertFalse("Negative student ID is invalid", ValidationUtils.isValidStudentId(-5));
    }

    @Test
    public void loginInput_validatesCombination() {
        assertTrue(ValidationUtils.isValidLoginInput("student@campus.edu", "securePass"));
        assertFalse(ValidationUtils.isValidLoginInput("invalid-email", "securePass"));
        assertFalse(ValidationUtils.isValidLoginInput("student@campus.edu", "12"));
        assertFalse(ValidationUtils.isValidLoginInput(null, null));
    }
}
