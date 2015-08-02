package classify.prove.doddo.libsvm_classify;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *  Abstract class that labels feature files with keys provided by the user or
 *  obtained from the filename
 */

public abstract class LabelFeatureFile {

    static String[] names;
    /**
     * This function is called when the labelling is performed on .ff files created by the application
     * @param params Array containing paths of the files to be labeled
     */
    public static void label(String[] params) throws Exception
    {
        // TODO: replace this line with names selected
        names = new String[]{"Andrea", "Davide", "Emanuele"};

        String[] sameNamePath = groupFilesByName(params);
        String toBeMerged = "";
        String[][] path = new String[sameNamePath.length][];

        for(int i = 0; i < path.length; i++)
            path[i] = sameNamePath[i].split(",");

        for(int i = 0; i < sameNamePath.length; i++) {

            File[] files;
            FileWriter fileWriter;
            BufferedWriter bufferedWriter;

            //String[] path = sameNamePath[i].split(",");

            files = new File[path[i].length];
            // Initialize a file for every path
            for (int j = 0; j < files.length; j++)
                files[j] = new File(path[i][j]);

            // Whatever is inside, brackets included
            String bracketsContent =
                    path[i][0].substring(path[i][0].lastIndexOf("["),
                            path[i][0].lastIndexOf("]") + 1);
            // The name says it all
            String pathWithoutBrackets = path[i][0].replace(bracketsContent, "");
            // The name of the merged feature file is <speaker_name>.ff.unique
            String mergedFile = pathWithoutBrackets.replace(".ff", ".labeled");
            // Container for paths to labeled files, needed by MergeFiles
            toBeMerged += mergedFile + ",";

            fileWriter = new FileWriter(mergedFile);
            bufferedWriter = new BufferedWriter(fileWriter);

            for (File file : files) {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    bufferedWriter.write(i + " " + line);
                    bufferedWriter.newLine();
                }
            }

            bufferedWriter.close();

        }

        // Constructing the filename
        String mergedFileName = path[0][0].substring(0, path[0][0].lastIndexOf('/') + 1);
        mergedFileName += "[" + getCurrentDate() + "]";
        for(String name : names)
            mergedFileName += name;
        mergedFileName += ".unscaled";

        MergeFeatureFile.MergeFiles(toBeMerged, mergedFileName);
    }

    private static String getCurrentDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.ITALY);
        Date date = new Date();
        return(dateFormat.format(date));
    }


    private static String[] groupFilesByName(String[] paths)
    {

        String[] sameNamePath = new String[names.length];
        int i = 0;

        for(String name: names)
        {
            sameNamePath[i] = "";
            // For each feature file
            for(String filepath: paths)
            {
                if(filepath.contains(name))
                {
                    // Prepare the array of files that have to be joined
                    sameNamePath[i] += filepath + ",";
                }
            }
            i++;
        }

        return sameNamePath;
    }
}
