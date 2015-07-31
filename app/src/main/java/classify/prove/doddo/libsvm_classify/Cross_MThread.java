package classify.prove.doddo.libsvm_classify;


import libsvm.svm;
import libsvm.svm_parameter;
import libsvm.svm_problem;

/**
 * Created by doddo on 7/31/15.
 */
public class Cross_MThread implements Runnable {

    private int threadNum;
    private int threadCount;
    private svm_parameter svmParameter;
    private svm_problem svmProblem;

    public Cross_MThread(int threadNum, int threadCount, svm_problem svmProblem)
    {
        this.threadNum = threadNum;
        this.threadCount = threadCount;
        this.svmProblem = svmProblem;
        svmParameter = new DefaultSVMParameter(LoadFeatureFile.f).svmParameter;
    }

    public void run()
    {
        for(int cParameters = threadNum; cParameters < CrossValidation.cLength; cParameters += threadCount)
        {
            svmParameter.C = Math.pow(2, CrossValidation.log_C_coef[cParameters]);
            for(int gParameters = 0; gParameters < CrossValidation.gLength; gParameters ++)
            {
                svmParameter.gamma = Math.pow(2, CrossValidation.log_Gamma_coef[gParameters]);
                libsvm.svm.svm_cross_validation(svmProblem, svmParameter, 5, CrossValidation.results[CrossValidation.gLength * cParameters + gParameters]);
                //svm.svm_cross_validation(svmProblem, svmParameter, 5, CrossValidation.results[CrossValidation.gLength * cParameters + gParameters]);

                CrossValidation.parameters[CrossValidation.gLength * cParameters + gParameters] =
                        "log2c=" + CrossValidation.log_C_coef[cParameters] + ", log2g=" + CrossValidation.log_Gamma_coef[gParameters] + ", ";
            }
        }
    }
}
