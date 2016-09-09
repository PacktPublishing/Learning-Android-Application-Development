package com.packt.rrafols.calculatortestexample;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CalculatorUnitTests {
    private CalculatorLogic logic;

    @BeforeClass
    public static void initClass() {
        // Nothing for the moment being
    }

    @Before
    public void initTest() {
        logic = new CalculatorLogic();
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4L, logic.add(2L, 2L));
        assertEquals(2L, logic.add(0L, 2L));
        assertEquals(0L, logic.add(0L, 0));
    }

    @Test(expected = ArithmeticException.class)
    public void addition_isCorrectEdgeCases() throws Exception {
        logic.add(Long.MAX_VALUE,  1L);
    }

    @Test
    public void multiplication_isCorrect() throws Exception {
        assertEquals(8L, logic.multiply(2L, 4L));
        assertEquals(0L, logic.multiply(0L, 250L));
        assertEquals(125L, logic.multiply(25L, 5L));
    }

    @Test
    public void stringAddition_isCorrectForPositiveNumbers() throws Exception {
        assertThat("30", is(logic.add("25", "5")));
        assertThat("5", is(logic.add("0", "5")));
    }

    @Test
    public void stringAddition_isCorrectForNegativeNumbers() throws Exception {
        assertThat("20", is(logic.add("25", "-5")));
        assertThat("-5", is(logic.add("20", "-25")));
    }

    @Test
    public void stringAddition_isCorrectForInvalidNumbers() throws Exception {
        assertThat(StaticCalculatorLogic.NOT_A_NUMBER, is(logic.add(null, null)));
        assertThat(StaticCalculatorLogic.NOT_A_NUMBER, is(logic.add("25", null)));
        assertThat(StaticCalculatorLogic.NOT_A_NUMBER, is(logic.add("", "")));
        assertThat(StaticCalculatorLogic.NOT_A_NUMBER, is(logic.add("Invalid Number", "dummy")));
    }

}