package classify.prove.doddo.libsvm_classify;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import libsvm.*;

public class svm_trainTest extends TestCase {
    static int l = 0, f = 0;
    // svm_problem needed by svm_train
    static svm_problem svmProblem = new svm_problem();
    static svm_parameter svmParameter = new svm_parameter();
    static double[][]   results;
    static double[]     percentages;
    static int[] log_C_coef;
    static int[] log_Gamma_coef;
    static int[] correct;

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testMain() throws Exception {

        File file = new File("/home/doddo/Desktop/libsvm-3.20/a0d0e0.scale");
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        String line = bufferedReader.readLine();

        String features = line.substring(0, line.lastIndexOf(':'));
        features = features.substring(features.lastIndexOf(' ') + 1);
        f = Integer.valueOf(features);

        for (; ; ) {
            if (line != null) {
                l++;
                line = bufferedReader.readLine();
            } else {
                break;
            }
        }

        fileInputStream.getChannel().position(0);
        bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));


        // number of rows of features
        svmProblem.l = l;
        // labels for each row
        svmProblem.y = new double[l];
        // svm_node for indexes and values
        svmProblem.x = new svm_node[l][f + 1];

        // Initializa svm_nodes in SvmProblem.x
        for (int i = 0; i < svmProblem.x.length; i++) {
            for (int j = 0; j < svmProblem.x[0].length; j++) {
                svmProblem.x[i][j] = new svm_node();
            }
        }

        // Initialize arrays with selected values of C and Gamma
        log_C_coef      = new int[] {-5, -2, 1, 4, 7, 10, 13};
        log_Gamma_coef  = new int[] {2, -1, -4, -7, -10, -13};
        results = new double[log_C_coef.length * log_Gamma_coef.length][l];
        percentages = new double[results.length];
        correct = new int[l];

        populateArray(bufferedReader);

        svmParameter.svm_type = 0;
        svmParameter.kernel_type = 2;
        svmParameter.degree = 3;
        svmParameter.gamma = 1/f;
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
        svmParameter.shrinking = 0;
        svmParameter.probability = 0;

/*
        //svm_model model = svm.svm_load_model("/home/doddo/Desktop/model.model");
        //svm_model svmModel = svm.svm_train(svmProblem, svmParameter);
        //svm.svm_save_model("/home/doddo/Desktop/model.model", svmModel);
        //svm.svm_cross_validation(svmProblem, svmParameter, 10, results);
        //svm.svm_cross_validation(svmProblem, model.param, 5, results);
        for(int i = 0; i < l; i++)
        {
            //results[i] = svm.svm_predict(model, svmProblem.x[i]);
            //results[i] = svm.svm_predict(svmModel, svmProblem.x[i]);
            //results[i] = svm.svm_predict_values(svmModel, svmProblem.x[i], b);
       }
        int positive = 0;
        for(int i = 0; i < l; i++)
        {
           if(results[i] == svmProblem.y[i])
            {
                positive++;
            }
        }
        double perc;
        perc = (double) positive / a.length;
        String[] commandScale = new String[] {"-l", "-1", "-u", "+1",
                "/home/doddo/Desktop/Feature_Training.txt"};
        svm_scale svmScale = new svm_scale();
        svm_scale.main(commandScale);
*/
        int M = log_C_coef.length;
        int N = log_Gamma_coef.length;
        svm_model svmModel = svm.svm_train(svmProblem, svmParameter);
        //svm.svm_save_model("/home/doddo/Desktop/model.model", svmModel);
        FileOutputStream fileOutputStream = new FileOutputStream("/home/doddo/Desktop/output.txt");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        String[] parameters = new String[log_C_coef.length * log_Gamma_coef.length];
        // Replace 3 with step
        // Iterate the loop for every log2C and log2Gamma that the user wants to try
        for(int i = 0; i < log_C_coef.length; i++)
        {
            svmParameter.C = Math.pow(2, log_C_coef[i]);
            for (int j = 0; j < log_Gamma_coef.length; j++)
            {
                svmParameter.gamma = Math.pow(2, log_Gamma_coef[j]);
                svm.svm_cross_validation(svmProblem, svmModel.param, 5, results[log_Gamma_coef.length * i + j]);
                parameters[log_Gamma_coef.length * i + j] =
                        "log2c=" + svmParameter.C + ", log2g=" + svmParameter.gamma + ", ";
            }
        }

        for(int i = 0; i < M * N; i++)
        {
            for(int j = 0; j < l; j++)
            {
                if(results[i][j] == svmProblem.y[j])
                    correct[i]++;
            }
            percentages[i] = (double) correct[i] / l;
            parameters[i] += "%=" + (percentages[i] * 100);
        }

        for(int i = 0; i < parameters.length; i++)
        {
            bufferedOutputStream.write((parameters[i] + "\n").getBytes());
        }

        bufferedOutputStream.close();

        Assert.assertTrue(true);
    }

    private void populateArray(BufferedReader bufferedReader) throws Exception {
        try {
            for (int C = 0; C < l; C++) {
                int i = 0;
                int pos = 0;
                String line = bufferedReader.readLine();

                if (line.charAt(0) == '+' || line.charAt(0) == '-') {
                    svmProblem.y[C] = (Double.valueOf(line.substring(0, 2)));
                } else {
                    svmProblem.y[C] = Double.valueOf(line.substring(0, 1));
                }

                int index = 1;
                for (i = 0; i < f; i++) {
                    pos = line.indexOf(':');
                    //if (pos == -1)
                    //    break;
                    line = line.substring(pos + 1);
                    svmProblem.x[C][i].index = i + 1;
                    svmProblem.x[C][i].value = Double.valueOf(line.substring(0, line.indexOf(' ')));
                    index++;
                }
                svmProblem.x[C][i].index = -1;
                svmProblem.x[C][i].value = .0;
            }
        }
        catch(Exception ew)
        {

        }
    }
}


