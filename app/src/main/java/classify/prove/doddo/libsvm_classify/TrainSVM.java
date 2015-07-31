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
public abstract class TrainSVM {

    static DefaultSVMParameter defaultSVMParameter;
    static svm_parameter svmParameter;
    static svm_model svmModel;

    public static String filename;
    public static double C, G;

    public static void train(svm_problem svmProblem)
    {
        defaultSVMParameter = new DefaultSVMParameter(LoadFeatureFile.f);
        svmParameter = defaultSVMParameter.svmParameter;
        filename += ".model";

        // Change the parameters with the desired C and Gamma
        svmParameter.C = 0.125;
        svmParameter.gamma = 0.5;
        // Train the SVM
        svmModel = svm.svm_train(svmProblem, svmParameter);

        try {
            svm.svm_save_model(filename, svmModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
