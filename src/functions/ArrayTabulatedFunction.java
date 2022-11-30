package functions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable, Cloneable {
    private FunctionPoint[] ValuesArray;
    private int AvalableNumberOfPoints;

    public void print() {
        for(int i = 0; i < AvalableNumberOfPoints; i++) {
            System.out.println("(" + ValuesArray[i].getX() + ";" + ValuesArray[i].getY() + ")");
        }
        System.out.println();
    }

    public ArrayTabulatedFunction(FunctionPoint[] massPoints) throws IllegalArgumentException {
        if (massPoints.length < 2 || checkAbscissa(massPoints)) {
            throw new IllegalArgumentException();
        } else {
            ValuesArray = new FunctionPoint[massPoints.length];
            for (int i = 0; i < massPoints.length; ++i) {
                ValuesArray[i] = massPoints[i];
                ++AvalableNumberOfPoints;
            }
        }
    }

    public static boolean checkAbscissa(FunctionPoint[] massPoints) {
        for(int i = 0; i < massPoints.length - 1; ++i) {
            if(massPoints[i].getX() >= massPoints[i + 1].getX()){
                return true;
            }
        }

        return false;
    }

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if (leftX >= rightX || pointsCount < 2) {
            throw new IllegalArgumentException();
        } else {
            ValuesArray = new FunctionPoint[pointsCount];
            double interval = (rightX - leftX) / (pointsCount - 1);
            for (int i = 0; i < pointsCount; ++i) {
                ValuesArray[i] = new FunctionPoint(leftX + interval * i, 0.0);
            }
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {
        if (leftX >= rightX || values.length < 2) {
            throw new IllegalArgumentException();
        } else {
            ValuesArray = new FunctionPoint[values.length];
            double interval = (rightX - leftX) / (values.length - 1);
            for (int i = 0; i < values.length; ++i) {
                ValuesArray[i] = new FunctionPoint(leftX + interval * i, values[i]);
                AvalableNumberOfPoints += 1;
            }
        }
    }

    public double getLeftDomainBorder() {
        return getPointX(0);
    }

    public double getRightDomainBorder() {
        return getPointX(getAvalableNumberOfPoints() - 1);
    }

    public int getAvalableNumberOfPoints() {
        return AvalableNumberOfPoints;
    }

    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder())
            return Double.NaN;

        for (int i = 0; i < getAvalableNumberOfPoints(); ++i) {
            if (ValuesArray[i].getX() == x) {
                return ValuesArray[i].getY();
            }
        }
        if (getLeftDomainBorder() <= x && getRightDomainBorder() >= x) {
            double leftY = getPointY(0);
            double rightY = getPointY(getAvalableNumberOfPoints() - 1);
            double k = (rightY - leftY) / (getRightDomainBorder() - getLeftDomainBorder()) ;
            double b = rightY - k * getRightDomainBorder();
            return k * x + b;
        }

        return Double.NaN;
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        if (correctIndex(index)) {
            return ValuesArray[index];
        } else {
            throw new FunctionPointIndexOutOfBoundsException();
        }
    }

    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (!correctIndex(index)) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (getLeftDomainBorder() > point.getX() || getRightDomainBorder() < point.getX()) {
            throw new InappropriateFunctionPointException();
        }

        ValuesArray[index] = point;
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
        if (correctIndex(index)) {
            return getPoint(index).getX();
        } else {
            throw new FunctionPointIndexOutOfBoundsException();
        }
    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (!correctIndex(index)) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if(getLeftDomainBorder() > x || getRightDomainBorder() < x) {
            throw new InappropriateFunctionPointException();
        }

        getPoint(index).x = x;
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException {
        if (correctIndex(index)) {
            return getPoint(index).getY();
        } else {
            throw new FunctionPointIndexOutOfBoundsException();
        }

    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException {
        if (correctIndex(index)) {
            getPoint(index).y = y;
        } else {
            throw new FunctionPointIndexOutOfBoundsException();
        }
    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (!correctIndex(index)) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (getAvalableNumberOfPoints() < 3) {
            throw new InappropriateFunctionPointException();
        }

        AvalableNumberOfPoints = getAvalableNumberOfPoints() - 1;
        System.arraycopy(ValuesArray, index + 1, ValuesArray, index, getAvalableNumberOfPoints() - index);
    }

    public void  addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        for (int i = 0; i < getAvalableNumberOfPoints(); ++i) {
            if(getPointX(i) == point.getX()) {
                throw new InappropriateFunctionPointException();
            }
        }

        for (int i = 0; i < getAvalableNumberOfPoints(); ++i) {
            if (point.getX() >= getPointX(i) && point.getX() <= getPointX(i + 1)) {
                if (getAvalableNumberOfPoints() == ValuesArray.length) {
                    FunctionPoint[] old = new FunctionPoint[ValuesArray.length];
                    System.arraycopy(ValuesArray, 0, old, 0, ValuesArray.length);

                    ValuesArray = new FunctionPoint[ValuesArray.length + 1];
                    System.arraycopy(old, 0, ValuesArray, 0, old.length);
                }
                System.arraycopy(ValuesArray, i + 1, ValuesArray, i + 2, getAvalableNumberOfPoints() - i - 1);
                setPoint(i + 1, point);
                AvalableNumberOfPoints = getAvalableNumberOfPoints() + 1;

                return;
            }
        }
    }

    public boolean correctIndex(int index) {
        return index >= 0 && index < getAvalableNumberOfPoints();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.AvalableNumberOfPoints; ++i) {
            builder.append(i).append(": ").append(ValuesArray[i].toString()).append('\n');
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
       if(this.hashCode() == o.hashCode()) return true;
       else return false;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getAvalableNumberOfPoints());
        for (int i = 0; i < getAvalableNumberOfPoints(); ++i) {
            result += Objects.hashCode(ValuesArray[i]);
        }
        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


}