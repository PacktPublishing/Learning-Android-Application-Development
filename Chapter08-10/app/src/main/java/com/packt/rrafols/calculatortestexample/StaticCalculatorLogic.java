package com.packt.rrafols.calculatortestexample;

class StaticCalculatorLogic {
    final static String NOT_A_NUMBER = "NaN";

    static String add(String xs, String ys) {
        if(xs == null || ys == null || xs.length() == 0 || ys.length() == 0)
            return NOT_A_NUMBER;

        try {
            long x = Long.parseLong(xs);
            long y = Long.parseLong(ys);

            long result = add(x, y);
            return String.valueOf(result);
        } catch(NumberFormatException e) {
            return NOT_A_NUMBER;
        }
    }

    static String multiply(String xs, String ys) {
        if(xs == null || ys == null || xs.length() == 0 || ys.length() == 0)
            return NOT_A_NUMBER;

        try {
            long x = Long.parseLong(xs);
            long y = Long.parseLong(ys);

            long result = multiply(x, y);
            return String.valueOf(result);
        } catch(NumberFormatException e) {
            return NOT_A_NUMBER;
        }
    }

    static long add(long x, long y) {
        return x + y;
    }

    static long multiply(long x, long y) {
        return x * y;
    }
}


