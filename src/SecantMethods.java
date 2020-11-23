import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class SecantMethods implements SecantInterface{

    @Override
    public Vector<Double> stringToDblVector(String coefficients) {
        String[] abc = coefficients.split(" "); //whitespaces can be denoted by \\s+

        Vector<Double> coeffVec = new Vector<Double>();

        for(String strCoeff : abc){
            double intCoeff = Double.parseDouble(strCoeff);
            coeffVec.addElement(intCoeff);
        }

        System.out.println("Inputted Vector: " + coeffVec);
        return coeffVec;
    }

    @Override
    public void printIterationData(double[] iterData) {
        int index=0;
        for(double column : iterData){
            if(index != 4) {
                if(index == 0) System.out.printf("%-8.0f",column);
                else System.out.printf("%-12s",column);
            }
            index++;
        }
        System.out.println();
        return;
    }

    @Override
    public double createFunction(Vector<Double> coefficients, int vectorSize, double value) {
        double sum = 0;
        for(int x=vectorSize-1;x>=0;x--){
            double term = coefficients.get(x)*Math.pow(value,x);
            sum = sum + term;
        }
        DecimalFormat df = new DecimalFormat("0.00000");
        if(sum > 0) df.setRoundingMode(RoundingMode.FLOOR);
        else df.setRoundingMode(RoundingMode.CEILING);
        double truncatedSum = Double.parseDouble(df.format(sum));
        return truncatedSum;
    }

    @Override
    public double getXnext(double xPrev, double xCurr, double fxPrev, double fxCurr) {
        double xNext;
        double numerator = fxCurr*(xCurr-xPrev);
        double denominator = (fxCurr-fxPrev);
        if(numerator == 0 || denominator == 0) xNext = 0;
        else xNext = xCurr - (numerator/denominator);
        DecimalFormat df = new DecimalFormat("0.00000");
        if(xNext > 0) df.setRoundingMode(RoundingMode.FLOOR);
        else df.setRoundingMode(RoundingMode.CEILING);
        double truncatedXnext = Double.parseDouble(df.format(xNext));
        return truncatedXnext;
    }

    @Override
    public double getE(double xNext, double xCurr) {
        double e = Math.abs(((xNext - xCurr)/xNext) * 100);
        DecimalFormat df = new DecimalFormat("0.00000");
        df.setRoundingMode(RoundingMode.FLOOR);
        double truncatedE = Double.parseDouble(df.format(e));
        return truncatedE;
    }
    
}
