package classify.prove.doddo.libsvm_classify;

import org.junit.Test;

import static org.junit.Assert.*;

public class MergeFeatureFileTest {

    static String[] paths;
    @Test
    public void testMergeSameNameFiles() throws Exception {

        paths = new String[] {"/home/doddo/Tests/[2015-07-09-14_07_13]Davide.ff",
                "/home/doddo/Tests/[2015-07-09-14_10_18]Davide.ff"};

        MergeFeatureFile.MergeSameNameFiles(paths);
    }
}