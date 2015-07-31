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

    public svm_parameter svmParameter = new svm_parameter();
    public static double c, g;

    private static boolean initialized = false;

    public void train(svm_problem svmProblem)
    {


    }

    public TrainSVM()
    {
        // Default parameters
        svmParameter.svm_type = 0;
        svmParameter.kernel_type = 2;
        svmParameter.degree = 3;
        svmParameter.gamma = 1/LoadFeatureFile.f;
        svmParameter.coef0 = 0;

        // For training only
        svmParameter.cache_size = 100;
        svmParameter.eps = 0.001;
        svmParameter.C = 1;
        svmParameter.nu = 0.5;
        svmParameter.nr_weight = 0;
        svmParameter.weight_label = null;
        svmParameter.weight = null;
        svmParameter.p = 0.1;
        svmParameter.shrinking = 1;
        svmParameter.probability = 0;
    }
}
