package classify.prove.doddo.libsvm_classify;

import classify.prove.doddo.libsvm_classify.libsvm.svm_scale;
import libsvm.*;

public abstract class ScaleFeatureFile {

    public void Scale(String filename) throws Exception {
        String[] pep = new String[]{"-l", "-1", "-u", "+1", filename};

        svm_scale svmScale = new svm_scale();

        svm_scale.main(pep);
    }

}
