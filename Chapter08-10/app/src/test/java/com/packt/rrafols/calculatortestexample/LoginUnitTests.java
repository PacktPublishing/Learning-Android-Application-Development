package com.packt.rrafols.calculatortestexample;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class LoginUnitTests {
    @Test
    public void checkUsernameValidity_isCorrect() {
        assertEquals(true, LoginActivity.isUsernameValid("john@doe.net"));
        assertEquals(false, LoginActivity.isUsernameValid("john@doenet"));
        assertEquals(false, LoginActivity.isUsernameValid("johndoe.net"));
        assertEquals(false, LoginActivity.isUsernameValid("j@d.t"));
    }

    @Test
    public void checkPasswordValidity_isCorrect() {
        assertEquals(true, LoginActivity.isPasswordValid("letters110"));
        assertEquals(false, LoginActivity.isUsernameValid("onlytext"));
        assertEquals(false, LoginActivity.isUsernameValid("123456789"));
        assertEquals(false, LoginActivity.isUsernameValid("ab12"));
    }
}


