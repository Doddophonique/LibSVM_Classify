package classify.prove.doddo.libsvm_classify;

import classify.prove.doddo.libsvm_classify.libsvm.svm_scale;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import libsvm.*;

/**
 * Created by doddo on 7/30/15.
 */
public class svm_trainTest extends TestCase {
    static int c_start, c_end, c_step;
    static int g_start, g_end, g_step;
    static int l = 0, f = 0;
    // svm_problem needed by svm_train
    static svm_problem svmProblem = new svm_problem();
    static svm_parameter svmParameter = new svm_parameter();
    static double[] results;

    public void setUp() throws Exception {
        super.setUp();
        c_start = -1;
        c_end = 1;
        c_step = 1;

        g_start = 0;
        g_end = 0;
        g_step = 0;
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

        double[] b = new double[l];
        double[] a = new double[l];
        results = new double[l];
        // number of rows of features
        svmProblem.l = l;
        // labels for each row
        svmProblem.y = new double[l];
        // svm_node for indexes and values
        svmProblem.x = new svm_node[l][f + 1];

        for (int i = 0; i < svmProblem.x.length; i++) {
            for (int j = 0; j < svmProblem.x[0].length; j++) {
                svmProblem.x[i][j] = new svm_node();
            }
        }

        populateArray(bufferedReader);

        svmParameter.svm_type = 0;
        svmParameter.kernel_type = 2;
        svmParameter.degree = 3;
        svmParameter.gamma = 0.5;
        svmParameter.coef0 = 0;

        // For training only
        svmParameter.cache_size = 100;
        svmParameter.eps = 0.001;
        svmParameter.C = 0.125;
        svmParameter.nu = 0.5;
        svmParameter.nr_weight = 0;
        svmParameter.weight_label = null;
        svmParameter.weight = null;
        svmParameter.p = 0.1;
        svmParameter.shrinking = 1;
        svmParameter.probability = 0;


        svm_model model = svm.svm_load_model("/home/doddo/Desktop/model.model");
        //svm_model svmModel = svm.svm_train(svmProblem, svmParameter);
        //svm.svm_save_model("/home/doddo/Desktop/model.model", svmModel);
        //svm.svm_cross_validation(svmProblem, svmParameter, 10, results);
        //svm.svm_cross_validation(svmProblem, model.param, 5, results);
        for(int i = 0; i < l; i++)
        {
            results[i] = svm.svm_predict(model, svmProblem.x[i]);
            //results[i] = svm.svm_predict(svmModel, svmProblem.x[i]);
            //results[i] = svm.svm_predict_values(svmModel, svmProblem.x[i], b);
            //a[i] = (results[i] * svmProblem.y[i]);
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
        String[] pep = new String[] {"-l", "-1", "-u", "+1",
                "/home/doddo/Desktop/Feature_Training.txt"};
        svm_scale svmScale = new svm_scale();
        svm_scale.main(pep);
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


