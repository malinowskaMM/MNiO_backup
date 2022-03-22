package project.model;

public class Assumption {

    MathFunction mathFunction = new MathFunction();

    public boolean isDifferentSignsValid(double fa, double fb) {
        return fa * fb < 0;
    }

    public boolean isOneRootValid(double a, double b, String function) {
        return countRoots(a, b, function) == 1;
    }

    public int countRoots(double a, double b, String function) {
        int resolution = 500;
        double step = (b - a) / resolution;
        int result = 0;
        double l = a;
        double p = a + step;
        for (int i = 0; i < resolution; i++) {
            double fa = 0;
            double fb = 0;
            l += step;
            p += step;
            switch (function) {
                case "polynomial" -> {
                    fa = mathFunction.polynomial(l);
                    fb = mathFunction.polynomial(p);
                }
                case "trigonometric" -> {
                    fa = mathFunction.trigonometric(l);
                    fb = mathFunction.trigonometric(p);
                }
                case "exponential" -> {
                    fa = mathFunction.exponential(l);
                    fb = mathFunction.exponential(p);
                }
                case "mixed" -> {
                    fa = mathFunction.mixed(l);
                    fb = mathFunction.mixed(p);
                }
            }
            if (fa * fb < 0) {
                result++;
            }
        }
        return result;
    }

}
