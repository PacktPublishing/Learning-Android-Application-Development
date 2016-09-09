package com.packt.rrafols.calculatortestexample;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4L, StaticCalculatorLogic.add(2L, 2L));
        assertEquals(2L, StaticCalculatorLogic.add(0L, 2L));
        assertEquals(0L, StaticCalculatorLogic.add(0L, 0));
    }

    @Test
    public void multiplication_isCorrect() throws Exception {
        assertEquals(8L, StaticCalculatorLogic.multiply(2L, 4L));
        assertEquals(0L, StaticCalculatorLogic.multiply(0L, 250L));
        assertEquals(125L, StaticCalculatorLogic.multiply(25L, 5L));
    }

    @Test
    public void stringAddition_isCorrectForPositiveNumbers() throws Exception {
        assertThat("30", is(StaticCalculatorLogic.add("25", "5")));
        assertThat("5", is(StaticCalculatorLogic.add("0", "5")));
    }

    @Test
    public void stringAddition_isCorrectForNegativeNumbers() throws Exception {
        assertThat("20", is(StaticCalculatorLogic.add("25", "-5")));
        assertThat("-5", is(StaticCalculatorLogic.add("20", "-25")));
    }

    @Test
    public void stringAddition_isCorrectForInvalidNumbers() throws Exception {
        assertThat(StaticCalculatorLogic.NOT_A_NUMBER, is(StaticCalculatorLogic.add(null, null)));
        assertThat(StaticCalculatorLogic.NOT_A_NUMBER, is(StaticCalculatorLogic.add("25", null)));
        assertThat(StaticCalculatorLogic.NOT_A_NUMBER, is(StaticCalculatorLogic.add("", "")));
        assertThat(StaticCalculatorLogic.NOT_A_NUMBER, is(StaticCalculatorLogic.add("Invalid Number", "dummy")));
    }

}