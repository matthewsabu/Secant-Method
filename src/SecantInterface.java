import java.util.*;

public interface SecantInterface {
    public Vector<Double> stringToDblVector(String coefficients);
    public void printIterationData(double[] iterData);
    public double createFunction(Vector<Double> coefficients, int vectorSize, double value);
    public double getXnext(double xPrev, double xCurr, double fxPrev, double fxCurr);
    public double getE(double xNext, double xCurr);
}
