package classify.prove.doddo.libsvm_classify.libsvm;

/**
 * Abstract class that handles feature files before and after labeling: it combines them in one
 * single file ready to be labeled (before) or to be scaled and trained (after)
 */
public abstract class JoinFeatureFile {

    /**
     * Before labeling, call this function to combine together multiple, if any,
     * feature files of the same speaker
     * @param params paths to selected feature files
     */
    public static void JoinSameNameFiles(String... params)
    {

    }

    /**
     * After labeling, call this function to combine together the labeled feature files
     * @param params paths to labeled feature files
     */
    public static void JoinLabeledFiles(String... params)
    {

    }

}
