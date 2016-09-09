package com.packt.rrafols.calculatortestexample;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentationTest {

    private CalculatorLogic logic;
    private Context appContext;

    @Before
    public void initTest() {
        appContext = InstrumentationRegistry.getTargetContext();
        logic = new CalculatorLogic(appContext);
    }

    @Test
    public void useAppContext() throws Exception {
        String nan = appContext.getResources().getString(R.string.not_a_number);
        assertThat(nan, is(logic.add(null, null)));
    }
}


