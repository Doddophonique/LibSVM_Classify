package classify.prove.doddo.libsvm_classify;

import org.junit.Test;

import static org.junit.Assert.*;

public class MergeFeatureFileTest {

    static String[] paths;
    static String[] labeledPaths, names;
    @Test
    public void testMergeSameNameFiles() throws Exception {

        paths = new String[] {"/home/doddo/Tests/[2015-07-09-14_07_13]Davide.ff",
                "/home/doddo/Tests/[2015-07-09-14_10_18]Davide.ff",
                "/home/doddo/Tests/[2015-07-09-14_04_08]emacann.ff",
                "/home/doddo/Tests/[2015-07-09-14_08_19]emacann.ff"};

        labeledPaths = new String[] {"/home/doddo/Tests/Andrea.txt",
                "/home/doddo/Tests/Davide.txt",
                "/home/doddo/Tests/Emanuele.txt"};

        names = new String[]{"Andrea", "Davide", "Emanuele"};

        //MergeFeatureFile.MergeSameNameFiles(paths);
        MergeFeatureFile.JoinLabeledFiles(labeledPaths, names);
    }
}