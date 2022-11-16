package functions.meta;

import functions.Function;

public class Power implements Function {
    private Function basis;
    private double degree;

    public Power(Function basis, double degree) {
        this.basis = basis;
        this.degree = degree;
    }

    @Override
    public double getLeftDomainBorder() {
        return basis.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return basis.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.pow(basis.getFunctionValue(x), degree);
    }
}
