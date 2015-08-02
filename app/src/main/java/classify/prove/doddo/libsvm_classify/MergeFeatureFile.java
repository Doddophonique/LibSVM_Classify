package classify.prove.doddo.libsvm_classify;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Abstract class that handles feature files before and after labeling: it combines them in one
 * single file ready to be labeled (before) or to be scaled and trained (after)
 */
public abstract class MergeFeatureFile {

    /**
     * Before labeling, call this function to combine together multiple, if any,
     * feature files of the same speaker
     * @param params paths to selected feature files
     */
    public static void MergeSameNameFiles(String... params)
    {
        String tempName;
        String allNames = "";
        String[] names;
        String[] sameNamePath;

        // Find names
        for (String filepath: params) {
            // TODO make a function that works also with files not created by the application
            tempName = filepath.substring(filepath.lastIndexOf(']') + 1, filepath.lastIndexOf('.'));

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
            sameNamePath[i] = "";
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

        try {
            MergeFiles(sameNamePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void MergeFiles(final String paths, final String filename) throws IOException {
        File[] files;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;


        String[] path = paths.split(",");

        files = new File[path.length];
        // Initialize a file for every path
        for (int i = 0; i < files.length; i++)
            files[i] = new File(path[i]);

        fileWriter = new FileWriter(filename);
        bufferedWriter = new BufferedWriter(fileWriter);

        for (File file : files) {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        }

        bufferedWriter.close();

    }

    public static void MergeFiles(String... groupOfPaths) throws IOException
    {
        File[]          files;
        FileWriter      fileWriter;
        BufferedWriter  bufferedWriter;

        // For each group of paths
        for(String paths: groupOfPaths)
        {
            String[] path = paths.split(",");

            files = new File[path.length];
            // Initialize a file for every path
            for(int i = 0; i < files.length; i++)
                files[i] = new File(path[i]);

            // Whatever is inside, brackets included
            String bracketsContent =
                        path[0].substring(path[0].lastIndexOf("["), path[0].lastIndexOf("]") + 1);
            // The name says it all
            String pathWithoutBrackets = path[0].replace(bracketsContent, "");
            // The name of the merged feature file is <speaker_name>.ff.unique
            String mergedFile = pathWithoutBrackets.replace(".ff", ".ff.unique");

            fileWriter =        new FileWriter(mergedFile);
            bufferedWriter =    new BufferedWriter(fileWriter);

            for(File file : files) {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }

            bufferedWriter.close();

        }
    }
    /**
     * After labeling, call this function to combine together the labeled feature files
     * @param params paths to labeled feature files
     * @param names ordered names provided by LabelFeatureFile
     */
    public static void JoinLabeledFiles(String[] params, String[] names)
    {
        String uniquePath = "";

        // For each name
        for(String name: names)
        {
            // For each feature file
            for(String filepath: params)
            {
                if(filepath.contains(name))
                {
                    // Prepare the array of files that have to be joined
                    uniquePath += filepath + ",";
                }
            }
        }

        try {
            MergeFiles(uniquePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
