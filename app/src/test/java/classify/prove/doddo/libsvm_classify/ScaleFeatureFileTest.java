package classify.prove.doddo.libsvm_classify;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by doddo on 8/1/15.
 */
public class ScaleFeatureFileTest {

    static String filename;

    @Before
    public void setUp() throws Exception {
        filename = "/home/doddo/Tests/training_set_4_feature_files";
    }

    @Test
    public void testScale() throws Exception {
        ScaleFeatureFile.Scale(filename, -1, 1);
    }
}