package com.packt.rrafols.calculatortestexample;

import android.content.Context;

class CalculatorLogic {

    public String NOT_A_NUMBER;

    public CalculatorLogic(Context context) {
        NOT_A_NUMBER = context.getString(R.string.not_a_number);
    }

    public CalculatorLogic() {
        NOT_A_NUMBER = "NaN";
    }


    public String add(String xs, String ys) {
        if(xs == null || ys == null || xs.length() == 0 || ys.length() == 0)
            return NOT_A_NUMBER;

        try {
            long x = Long.parseLong(xs);
            long y = Long.parseLong(ys);

            long result = add(x, y);
            return String.valueOf(result);
        } catch(NumberFormatException e) {
            return NOT_A_NUMBER;
        } catch (ArithmeticException e) {
            return NOT_A_NUMBER;
        }
    }

    public String multiply(String xs, String ys) {
        if(xs == null || ys == null || xs.length() == 0 || ys.length() == 0)
            return NOT_A_NUMBER;

        try {
            long x = Long.parseLong(xs);
            long y = Long.parseLong(ys);

            long result = multiply(x, y);
            return String.valueOf(result);
        } catch (NumberFormatException e) {
            return NOT_A_NUMBER;
        } catch (ArithmeticException e) {
            return NOT_A_NUMBER;
        }
    }

    public long add(long x, long y) throws ArithmeticException {
        long result = x + y;

        if(x > 0 && y > 0 && result < 0) throw
                new ArithmeticException("Numbers out of bounds: " + x + ", " + y);

        if(x < 0 && y < 0 && result > 0) throw
                new ArithmeticException("Numbers out of bounds: " + x + ", " + y);

        return result;
    }

    public long multiply(long x, long y) {
        long result = x * y;

        if(x > 0 && y > 0 && result < 0) throw
                new ArithmeticException("Numbers out of bounds: " + x + ", " + y);

        if(x < 0 && y < 0 && result > 0) throw
                new ArithmeticException("Numbers out of bounds: " + x + ", " + y);

        return result;
    }
}


