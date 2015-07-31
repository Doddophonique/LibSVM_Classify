package classify.prove.doddo.libsvm_classify;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_parameter;
import libsvm.svm_problem;

/**
 * Created by doddo on 7/31/15.
 */
public abstract class CrossValidation {

    public static double[][]   results;
    public static double[]     percentages;
    public static int[] correct;
    // TODO the user must not enter invalid values for those six variables
    public static int cStart, cEnd, cStep;
    public static int gStart, gEnd, gStep;
    public static String filename;

    public static int cLength;
    public static int gLength;
    public static int[] log_C_coef;
    public static int[] log_Gamma_coef;
    public static String[] parameters;
    static FileOutputStream fileOutputStream;
    static BufferedOutputStream bufferedOutputStream;

    static DefaultSVMParameter defaultSVMParameter;
    static svm_parameter svmParameter;
    static svm_model svmModel;

    public static void cross_validate(svm_problem svmProblem) {

        defaultSVMParameter = new DefaultSVMParameter(LoadFeatureFile.f);
        svmParameter = defaultSVMParameter.svmParameter;
        svmModel = svm.svm_train(svmProblem, svmParameter);

        // Number of step = [(End - Start) / Step] + 1
        // Valid only if the Step is adequate for starting at Start and arriving exactly at End
        // Calculated when the user selects c and g
        cLength = ((cEnd - cStart) / cStep) +1;
        gLength = ((gEnd - gStart) / gStep) +1;

        log_C_coef = new int[cLength];
        log_Gamma_coef = new int[gLength];

        results = new double[cLength * gLength][LoadFeatureFile.l];
        correct = new int[LoadFeatureFile.l];
        percentages = new double[results.length];

        initCoefficientsArrays();

        // Array that stores each line of the file to be written
        parameters = new String[log_C_coef.length * log_Gamma_coef.length];

        int numCores = 8;

        try {
            Thread[] threads = new Thread[numCores];
            for (int C = 0; C < numCores; C++) {
                threads[C] = new Thread(new Cross_MThread(C, numCores, svmProblem));
                threads[C].start();
            }
            for (int C = 0; C < numCores; C++)
                threads[C].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
/*
        // Iterate the loop for every log2C and log2Gamma that the user wants to try
        for(int i = 0; i < cLength; i++)
        {
            // Changing the parameter affects svmModel too
            svmParameter.C = Math.pow(2, log_C_coef[i]);
            for (int j = 0; j < gLength; j++)
            {
                svmParameter.gamma = Math.pow(2, log_Gamma_coef[j]);
                // Do the cross validation and store the results
                // TODO: replace 5 with the user selected number of folds
                svm.svm_cross_validation(svmProblem, svmParameter, 2, results[gLength * i + j]);
                // Store the parameters in the correct order of cross validation
                parameters[gLength * i + j] =
                        "log2c=" + log_C_coef[i] + ", log2g=" + log_Gamma_coef[j] + ", ";
            }
        }
*/
        calculatePercentages(svmProblem);
        writeResultsToFile();
    }

    private static void initCoefficientsArrays()
    {
        if(cLength == gLength)
        {
            for(int i = 0; i < cLength; i++)
            {
                log_C_coef[i]       = cStart + (cStep * i);
                log_Gamma_coef[i]   = gStart + (gStep * i);
            }
        }
        else if(cLength > gLength)
        {
            int i;
            for(i = 0; i < gLength; i++)
            {
                log_C_coef[i]       = cStart + (cStep * i);
                log_Gamma_coef[i]   = gStart + (gStep * i);
            }
            for(; i < cLength; i++)
                log_C_coef[i]       = cStart + (cStep * i);
        }
        else
        {
            int i;
            for(i = 0; i < cLength; i++)
            {
                log_C_coef[i]       = cStart + (cStep * i);
                log_Gamma_coef[i]   = gStart + (gStep * i);
            }
            for(; i < gLength; i++)
                log_Gamma_coef[i]   = gStart + (gStep * i);
        }
    }

    private static void calculatePercentages(svm_problem svmProblem)
    {
        // For every combination of C and G
        for(int i = 0; i < cLength * gLength; i++)
        {
            // For every result obtained
            for(int j = 0; j < LoadFeatureFile.l; j++)
            {
                if(results[i][j] == svmProblem.y[j])
                    correct[i]++;
            }
            percentages[i] = (double) correct[i] / LoadFeatureFile.l;
            parameters[i] += "rate = " + (percentages[i] * 100);
        }
    }

    private static void writeResultsToFile()
    {
        filename += ".coeff";

        try {
            fileOutputStream = new FileOutputStream(filename);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            for (String line : parameters) {
                bufferedOutputStream.write((line + "\n").getBytes());
            }

            bufferedOutputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
