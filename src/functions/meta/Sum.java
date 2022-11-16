package functions.meta;
import functions.Function;

public class Sum implements Function {
    private Function fun1;
    private Function fun2;

    public Sum(Function fun1, Function fun2){
        this.fun1 = fun1;
        this.fun2 = fun2;
    }

    @Override
    public double getLeftDomainBorder() {
        return Math.min(fun1.getLeftDomainBorder(), fun2.getLeftDomainBorder());
    }

    @Override
    public double getRightDomainBorder() {
        return Math.min(fun1.getRightDomainBorder(), fun2.getRightDomainBorder());
    }

    @Override
    public double getFunctionValue(double x) {
        return fun1.getFunctionValue(x) + fun2.getFunctionValue(x);
    }
}
