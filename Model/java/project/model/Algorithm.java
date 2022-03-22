package project.model;

public abstract class Algorithm {
    double precision;
    int maxIterationNum;
    double a;
    double b;
    String function;
    Assumption assumption = new Assumption();
    MathFunction mathFunction = new MathFunction();

    public MyPair<Double, Integer> calculateWithPrecision() {
        return new MyPair<>(0.0, 0);
    }

    public double calculateWithIter() {
        return 0;
    }

    public boolean isDifferentSignsValid() {
        double fa, fb;
        fa = f(a);
        fb = f(b);
        return assumption.isDifferentSignsValid(fa, fb);
    }

    public boolean isOneRootValid() {
        return assumption.isOneRootValid(a, b, function);
    }

    double f(double x) {
        return switch (function) {
            case "polynomial" -> mathFunction.polynomial(x);
            case "trigonometric" -> mathFunction.trigonometric(x);
            case "exponential" -> mathFunction.exponential(x);
            case "mixed" -> mathFunction.mixed(x);
            default -> 0;
        };
    }

    double fDiff(double x) {
        return switch (function) {
            case "polynomial" -> mathFunction.diffPolynomial(x);
            case "trigonometric" -> mathFunction.diffTrigonometric(x);
            case "exponential" -> mathFunction.diffExponential(x);
            case "mixed" -> mathFunction.diffMixed(x);
            default -> 0;
        };
    }
}
