package functions;

import java.io.*;

public class TabulatedFunctions {
    private TabulatedFunctions() {

    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()) {
            throw new IllegalArgumentException();
        }

        FunctionPoint[] values = new FunctionPoint[pointsCount];
        double length = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; ++i) {
            double x = leftX + length * i;
            values[i] = new FunctionPoint(x, function.getFunctionValue(x));
        }
        return new ArrayTabulatedFunction(values);
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            int pointsCount = function.getAvalableNumberOfPoints();
            dos.writeInt(pointsCount);
            for (int i = 0; i < pointsCount; ++i) {
                dos.writeDouble(function.getPointX(i));
                dos.writeDouble(function.getPointY(i));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) {
        try (DataInputStream dis = new DataInputStream(in)) {
            int pointsCount = dis.readInt();
            FunctionPoint[] points = new FunctionPoint[pointsCount];
            for (int i = 0; i < pointsCount; ++i) {
                points[i] = new FunctionPoint(dis.readDouble(), dis.readDouble());
            }
            return new ArrayTabulatedFunction(points);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) {
        try (BufferedWriter bw = new BufferedWriter(out)) {
            int pointsCount = function.getAvalableNumberOfPoints();
            bw.write(pointsCount + " ");
            for (int i = 0; i < pointsCount; i++) {
                bw.write(function.getPointX(i) + " " + function.getPointY(i) + " ");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println();
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) {
        try {
            StreamTokenizer st = new StreamTokenizer(in);
            st.nextToken();
            int pointsCount = (int) st.nval;
            FunctionPoint[] mass = new FunctionPoint[pointsCount];
            for (int i = 0; st.nextToken() != StreamTokenizer.TT_EOF; i++) {
                double x = st.nval;
                st.nextToken();
                double y = st.nval;
                mass[i] = new FunctionPoint(x, y);
            }
            return new ArrayTabulatedFunction(mass);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }
}
