import android.app.ProgressDialog;
import android.os.AsyncTask;

import classify.prove.doddo.libsvm_classify.Cross_SThread;
import classify.prove.doddo.libsvm_classify.LoadFeatureFile;
import classify.prove.doddo.libsvm_classify.TrainSVM;
import libsvm.svm_problem;

/**
 * Created by doddo on 7/31/15.
 */
public class ModelFromFile extends AsyncTask <String, Void, Void> {

    static svm_problem svmProblem;

    protected void onPreExecute()
    {

    }

    @Override
    protected Void doInBackground(String... params) {

        /**
         * params[0] = name of the training file
         * params[1] = action [Cross, Train]
         */

        TrainSVM.filename = params[0];
        Cross_SThread.filename = params[0];

        try {
            svmProblem = LoadFeatureFile.load(params[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch(params[1])
        {
            case("Cross"):
                Cross_SThread.cross_validate(svmProblem);
                break;

            case("Train"):
                TrainSVM.train(svmProblem);
                break;
        }

        return null;
    }
}
