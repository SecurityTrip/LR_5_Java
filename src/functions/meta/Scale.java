package functions.meta;

import functions.Function;

public class Scale implements Function {
    private Function fun;
    private double X;
    private double y;

    public Scale(Function fun, double X, double y) {
        this.fun = fun;
        this.X = X;
        this.y = y;
    }

    @Override
    public double getLeftDomainBorder() {
        return fun.getLeftDomainBorder() * X;
    }

    @Override
    public double getRightDomainBorder() {
        return fun.getRightDomainBorder() * X;
    }

    @Override
    public double getFunctionValue(double x) {
        return fun.getFunctionValue(x * X) * y;
    }
}