package project.model;

public class BisectionAlgorithm extends Algorithm {

    public BisectionAlgorithm(double precision, int iterationNum, double intervalStart,
                              double intervalEnd, String function) {
        this.precision = precision;
        this.maxIterationNum = iterationNum;
        this.a = intervalStart;
        this.b = intervalEnd;
        this.function = function;
    }


    // jeśli warunkiem stopu jest precyzja
    public MyPair<Double, Integer> calculateWithPrecision() {
        double fa, fx;
        double x0 = -1;
        fa = f(a);
        int iterationCounter = 0;
        boolean shouldContinue = true;
        while (shouldContinue) {
            iterationCounter++;
            x0 = (a + b) / 2;

            if (Math.abs(a - x0) < precision) {
                shouldContinue = false;
            } else {
                fx = f(x0);
                if (fa * fx < 0) {
                    b = x0;
                } else {
                    a = x0;
                }
            }
        }
        return new MyPair<>(x0, iterationCounter);
    }

    // jeśli warunkiem stopu jest liczba iteracji
    public double calculateWithIter() {
        double fa, fx;
        double x0 = -1;
        fa = f(a);

        for (int i = 0; i < maxIterationNum; i++) {
            x0 = (a + b) / 2;
            fx = f(x0);

            if (fa * fx < 0) {
                b = x0;
            } else {
                a = x0;
            }
        }
        return x0;
    }

}
