package project.model;

public class MathFunction {

    public double polynomial(double x) {
        return Math.pow(x, 3) - 6 * Math.pow(x, 2) + 4 * x + 8;
    }

    public double diffPolynomial(double x) { return 3 * Math.pow(x, 2) -12*x +4; }

    public double secondDiffPolynomial(double x) {
        return 6 * x - 12;
    }

    public double trigonometric(double x) {
        return 2 * Math.tan(x) - Math.sin(x) * Math.cos(2 * x);
    }

    public double diffTrigonometric(double x) {
        return 2 / Math.pow(Math.cos(x), 2) - Math.cos(x) * Math.cos(2 * x) +
                2 * Math.sin(x) * Math.sin(2 * x);
    }

    public double secondDiffTrigonometric(double x) {
        return (4 * Math.sin(x) / Math.pow(Math.cos(x), 3)) + 5 * Math.cos(2 * x) * Math.sin(x) + 4 * Math.cos(x) * Math.sin(2 * x);
    }

    public double exponential(double x) {
        return Math.pow(2, x) - 8;
    }

    public double diffExponential(double x) {
        return Math.log(2) * Math.pow(2, x);
    }

    public double secondDiffExponential(double x) {
        return Math.pow(Math.log(2), 2) * Math.pow(2, x);
    }

    public double mixed(double x) {
        return Math.sin(6 * Math.pow(x, 4) + 3 * x) + Math.pow(2, Math.cos(x) + Math.pow(x, 4))-2;
    }

    public double diffMixed(double x) {
//        return Math.cos(6 * Math.pow(x, 4) + 3 * x) * (24 * Math.pow(x, 3) + 3) -
//                Math.log(2) * Math.pow(2, Math.cos(x)) * Math.sin(x) +
//                Math.log(2) * Math.pow(2, 2 * x + 1);

        return (24*Math.pow(x,3)+8) * Math.cos(6*Math.pow(x,4) + 3*x)
                + Math.log(2) * (4*Math.pow(x,3) - Math.sin(x))
                * Math.pow(2, Math.pow(x, 4) + Math.cos(x));
    }

    public double secondDiffMixed(double x) {
        return -1 * Math.pow((24 * Math.pow(x, 3) + 3), 2)
                * Math.sin(6 * Math.pow(x, 4) + 3 * x)
                + Math.pow(Math.log(x), 2) * Math.pow((4 * Math.pow(x, 3) - Math.sin(x)), 2)
                * Math.pow(2, Math.pow(x, 4) + Math.cos(x))
                + 72 * Math.pow(x, 2) * Math.cos(6 * Math.pow(x, 4) + 3 * x)
                + Math.log(2) * Math.pow(2, Math.pow(x, 4) + Math.cos(x))
                * (12 * Math.pow(x, 2) - Math.cos(x));
    }
}
