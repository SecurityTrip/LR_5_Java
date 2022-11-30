package functions;

import java.io.Serializable;
import java.util.Objects;

public class FunctionPoint implements Serializable {
    double x;
    double y;

    public FunctionPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public FunctionPoint(FunctionPoint point) {
        this.x = point.x;
        this.y = point.y;
    }

    FunctionPoint() {
        this.x = 0;
        this.y = 0;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public String toString(){
        return "(" + this.x + ";" + this.y + ")";
    }

    @Override
    public boolean equals(Object o){
        return o instanceof FunctionPoint && this.getX() == ((FunctionPoint) o).getX() && this.getY() == ((FunctionPoint) o).getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}