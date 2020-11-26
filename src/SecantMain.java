import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class SecantMain {
    private static SecantMethods methods = new SecantMethods();
    private static Scanner coefficients = new Scanner(System.in);
    private static Scanner initialConds = new Scanner(System.in);
    private static Scanner stoppingCrit = new Scanner(System.in);
    private static Vector<Double> coeffVec = new Vector<Double>();          //[C,B,A]
    private static ArrayList<Double> initCond = new ArrayList<Double>();    //[xi-1,xi]
    private static ArrayList<Double> stopCrit = new ArrayList<Double>();    //[i,e,fxr]

    public static void main(String[] args) {
        //initial conditions
        double iterNo=1,xNext,xPrevNext,fxPrev,fxCurr,fxNext,e;
        xNext=xPrevNext=fxPrev=fxCurr=fxNext=e=0;

        //set function assumptions
        setCoeff();
        setInitCond();
        setStopCrit();

        //set initial iteration row (Row #1)
        double[] iterData = {iterNo,initCond.get(0),initCond.get(1),xNext,xPrevNext,fxPrev,fxCurr,fxNext,e};

        //perform algo and display table
        System.out.printf("\n%-8s%-12s%-12s%-12s%-12s%-12s%-12s%-12s\n","iter #","xi-1","xi","xi+1","f(xi-1)","f(xi)","f(xi+1)","e %");
        runSecantAlgo(coeffVec,stopCrit,iterData);

        coefficients.close();
        initialConds.close();
        stoppingCrit.close();
    }

    //user input
    public static void setCoeff(){
        //define function
        System.out.println("Secant Method: Find your equation's root!");
        System.out.print("Equation's coefficients ([C B A] Format): ");
        coeffVec = methods.stringToDblVector(coefficients.nextLine());
        return;
    }

    public static void setInitCond() {
        //set initial conditions
        System.out.println("\nInitial Conditions:");
        System.out.print("xi-1 = ");
        initCond.add(initialConds.nextDouble());

        System.out.print("xi = ");
        initCond.add(initialConds.nextDouble());

        System.out.println(initCond);
        return;
    }

    public static void setStopCrit(){
        //set stopping criteria
        System.out.println("\nStopping Criterion:");
        System.out.print("# of Iterations = ");
        stopCrit.add(stoppingCrit.nextDouble());

        System.out.print("|Error| = ");
        stopCrit.add(stoppingCrit.nextDouble());

        System.out.print("|f(xi+1)| = ");
        stopCrit.add(stoppingCrit.nextDouble());

        System.out.println(stopCrit);
        return;
    }

    //everything with an "i" at the end indicates the value/s for the next (succeeding) iteration 
    //0=iteration,1=xi-1,2=xi,3=xi+1(NEW),4=xi+1(OLD),5=fxPrev,6=fxCurr,7=fxNext,8=e (iterData index references)
    public static void runSecantAlgo(Vector<Double> coefficients, ArrayList<Double> stopCrit, double[] iterData) {
        double approxRoot,xNewPrev,xNewCurr;
        approxRoot=xNewPrev=xNewCurr=0;

        iterData[5] = methods.createFunction(coefficients,coefficients.size(),iterData[1]); //f(xPrev)
        iterData[6] = methods.createFunction(coefficients,coefficients.size(),iterData[2]); //f(xCurr)
        iterData[3] = methods.getXnext(iterData[1],iterData[2],iterData[5],iterData[6]);   //xi+1 (NEW xi+1)
        iterData[7] = methods.createFunction(coefficients,coefficients.size(),iterData[3]); //f(xNext)
        iterData[8] = methods.getE(iterData[3],iterData[2]); //e
        iterData[4] = iterData[2];  //xi+1 (OLD xi+1)

        methods.printIterationData(iterData);   //print iteration row
        
        xNewPrev = iterData[2];
        xNewCurr = iterData[3];
        
        iterData[0]++;  //increase iteration counter

        //stopping criterion:
        // iteration <= stopping Iteration && error <= stopping Error% && f(xi+1) <= stopping f(xi+1)
        if(!(iterData[0]>stopCrit.get(0)) && !(iterData[8]<=stopCrit.get(1)) && !(Math.abs(iterData[7])<=stopCrit.get(2))){
            //update iteration row (Row #i)
            double[] iterDatai = {iterData[0],xNewPrev,xNewCurr,iterData[3],iterData[4],iterData[5],iterData[6],iterData[7],iterData[8]};

            runSecantAlgo(coefficients,stopCrit,iterDatai);
        } else {
            System.out.println("\nA stopping criteria has been met--stopped iterating.");
            DecimalFormat df = new DecimalFormat("0.00");
            if(xNewCurr > 0) df.setRoundingMode(RoundingMode.FLOOR);
            else df.setRoundingMode(RoundingMode.CEILING);
            approxRoot = Double.parseDouble(df.format(xNewCurr));   //rootAsymp
            System.out.println("\nThe equation's root is approximately " + approxRoot);
        }  
        return;
    }
}
