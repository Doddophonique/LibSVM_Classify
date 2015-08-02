package classify.prove.doddo.libsvm_classify;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;

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
        String tempName;
        String allNames = "";
        String[] names;
        String[] sameNamePath;

        // Find names
        for (String filepath: params) {
            // TODO make a function that works also with files not created by the application
            tempName = filepath.substring(filepath.lastIndexOf(']'), filepath.lastIndexOf('.'));

            if(!allNames.contains(tempName)) {
                allNames += tempName + ",";
            }
        }

        names = allNames.split(",");
        sameNamePath = new String[names.length];

        int i = 0;
        // For each name
        for(String name: names)
        {
            // For each feature file
            for(String filepath: params)
            {
                if(filepath.contains(name))
                {
                    // Prepare the array of files that have to be joined
                    sameNamePath[i] += filepath + ",";
                }
            }
            i++;
        }

        JoinFiles(sameNamePath);

    }

    private static String[] JoinFiles(String... paths)
    {
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;



        return null;
    }
    /**
     * After labeling, call this function to combine together the labeled feature files
     * @param params paths to labeled feature files
     */
    public static void JoinLabeledFiles(String... params)
    {

    }

}
