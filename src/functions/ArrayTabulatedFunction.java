package functions;

import java.io.Serializable;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable {
    private FunctionPoint[] mass;
    private int AvalablePointsCount;

    public void print() {
        for(int i = 0; i < AvalablePointsCount; i++) {
            System.out.println("(" + mass[i].getX() + ";" + mass[i].getY() + ")");
        }
        System.out.println();
    }

    public ArrayTabulatedFunction(FunctionPoint[] massPoints) throws IllegalArgumentException {
        if (massPoints.length < 2 || checkAbscissa(massPoints)) {
            throw new IllegalArgumentException();
        } else {
            mass = new FunctionPoint[massPoints.length];
            for (int i = 0; i < massPoints.length; ++i) {
                mass[i] = massPoints[i];
                ++AvalablePointsCount;
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
            mass = new FunctionPoint[pointsCount];
            double interval = (rightX - leftX) / (pointsCount - 1);
            for (int i = 0; i < pointsCount; ++i) {
                mass[i] = new FunctionPoint(leftX + interval * i, 0.0);
            }
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {
        if (leftX >= rightX || values.length < 2) {
            throw new IllegalArgumentException();
        } else {
            mass = new FunctionPoint[values.length];
            double interval = (rightX - leftX) / (values.length - 1);
            for (int i = 0; i < values.length; ++i) {
                mass[i] = new FunctionPoint(leftX + interval * i, values[i]);
                AvalablePointsCount += 1;
            }
        }
    }

    public double getLeftDomainBorder() {
        return getPointX(0);
    }

    public double getRightDomainBorder() {
        return getPointX(getAvalablePointsCount() - 1);
    }

    public int getAvalablePointsCount() {
        return AvalablePointsCount;
    }

    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder())
            return Double.NaN;

        for (int i = 0; i < getAvalablePointsCount(); ++i) {
            if (mass[i].getX() == x) {
                return mass[i].getY();
            }
        }
        if (getLeftDomainBorder() <= x && getRightDomainBorder() >= x) {
            double leftY = getPointY(0);
            double rightY = getPointY(getAvalablePointsCount() - 1);
            double k = (rightY - leftY) / (getRightDomainBorder() - getLeftDomainBorder()) ;
            double b = rightY - k * getRightDomainBorder();
            return k * x + b;
        }

        return Double.NaN;
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        if (correctIndex(index)) {
            return mass[index];
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

        mass[index] = point;
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
        if (getAvalablePointsCount() < 3) {
            throw new InappropriateFunctionPointException();
        }

        AvalablePointsCount = getAvalablePointsCount() - 1;
        System.arraycopy(mass, index + 1, mass, index, getAvalablePointsCount() - index);
    }

    public void  addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        for (int i = 0; i < getAvalablePointsCount(); ++i) {
            if(getPointX(i) == point.getX()) {
                throw new InappropriateFunctionPointException();
            }
        }

        for (int i = 0; i < getAvalablePointsCount(); ++i) {
            if (point.getX() >= getPointX(i) && point.getX() <= getPointX(i + 1)) {
                if (getAvalablePointsCount() == mass.length) {
                    FunctionPoint[] old = new FunctionPoint[mass.length];
                    System.arraycopy(mass, 0, old, 0, mass.length);

                    mass = new FunctionPoint[mass.length + 1];
                    System.arraycopy(old, 0, mass, 0, old.length);
                }
                System.arraycopy(mass, i + 1, mass, i + 2, getAvalablePointsCount() - i - 1);
                setPoint(i + 1, point);
                AvalablePointsCount = getAvalablePointsCount() + 1;

                return;
            }
        }
    }

    public boolean correctIndex(int index) {
        return index >= 0 && index < getAvalablePointsCount();
    }
}