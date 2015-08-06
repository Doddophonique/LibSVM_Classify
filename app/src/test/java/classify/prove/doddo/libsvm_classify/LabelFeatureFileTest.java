package classify.prove.doddo.libsvm_classify;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LabelFeatureFileTest {
    static String[] paths;
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testLabel() throws Exception {
        paths = new String[] {
                "/home/doddo/Tests/[2015-07-09-14_04_44]Andrea.ff",
                "/home/doddo/Tests/[2015-07-09-14_12_36]Andrea.ff"};

        /*
        paths = new String[] {"/home/doddo/Tests/[2015-07-09-14_07_13]Davide.ff",
                "/home/doddo/Tests/[2015-07-09-14_10_18]Davide.ff",
                "/home/doddo/Tests/[2015-07-09-14_04_08]Emanuele.ff",
                "/home/doddo/Tests/[2015-07-09-14_08_19]Emanuele.ff",
                "/home/doddo/Tests/[2015-07-09-14_04_44]Andrea.ff",
                "/home/doddo/Tests/[2015-07-09-14_12_36]Andrea.ff"};
         */

        LabelFeatureFile.label(paths);
    }
}