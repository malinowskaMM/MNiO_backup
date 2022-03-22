package project.model;

public class NewtonAlgorithm extends Algorithm {

    public NewtonAlgorithm(double precision, int maxIterationNum, double a, double b,
                           String function) {
        this.precision = precision;
        this.maxIterationNum = maxIterationNum;
        this.a = a;
        this.b = b;
        this.function = function;
    }

    public MyPair<Double, Integer> calculateWithPrecision() {
        double fa, fb;
        double x;
        int iterationCounter = 0;
        fa = f(a);
        fb = f(b);
        assumption.isDifferentSignsValid(fa, fb);
        assumption.countRoots(a, b, function);
        double x0 = (a + b) / 2;
        boolean shouldContinue = true;
        while (shouldContinue) {
            iterationCounter++;
            if (fDiff(x0) == 0) {
                throw new ArithmeticException();
            }

            x = x0 - (f(x0) / fDiff(x0));
            if (Math.abs(x - x0) < precision) {
                shouldContinue = false;
            } else {
                x0 = x;
            }
        }
        return new MyPair<Double, Integer>(x0, iterationCounter);
    }

    public double calculateWithIter() {
        double fa, fb;
        double x0 = (a + b) / 2;
        fa = f(a);
        fb = f(b);
        assumption.isDifferentSignsValid(fa, fb);
        assumption.countRoots(a, b, function);

        for (int i = 0; i < maxIterationNum; i++) {
            x0 = x0 - (f(x0) / fDiff(x0));
        }
        return x0;
    }
}
