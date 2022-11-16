package functions;
import functions.meta.*;

public class Functions {
    private Functions(){

    }

    public static Function shift(Function fun, double X, double y) {
        return new Shift(fun, X, y);
    }

    public static Function scale(Function fun, double X, double y) {
        return new Scale(fun, X, y);
    }

    public static Function power(Function basis, double degree) {
        return new Power(basis, degree);
    }

    public static Function sum(Function fun1, Function fun2) {
        return new Sum(fun1, fun2);
    }

    public static Function mult(Function fun1, Function fun2) {
        return new Mult(fun1, fun2);
    }

    public static Function composition(Function fun1, Function fun2) {
        return new Composition(fun1, fun2);
    }
}
