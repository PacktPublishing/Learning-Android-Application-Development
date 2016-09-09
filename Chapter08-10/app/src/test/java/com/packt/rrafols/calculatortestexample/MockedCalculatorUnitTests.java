package com.packt.rrafols.calculatortestexample;


import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockedCalculatorUnitTests {
    private static final String NaN = "NaN";

    private CalculatorLogic logic;

    @Mock
    private Context appContext;

    @Before
    public void initTest() {
        when(appContext.getString(R.string.not_a_number)).thenReturn(NaN);

        logic = new CalculatorLogic(appContext);
    }

    @Test
    public void useAppContext() throws Exception {
        String nan = appContext.getString(R.string.not_a_number);
        assertThat(nan, is(logic.add(null, null)));
    }
}


