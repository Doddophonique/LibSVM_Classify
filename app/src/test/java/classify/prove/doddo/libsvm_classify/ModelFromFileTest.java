package classify.prove.doddo.libsvm_classify;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by doddo on 7/31/15.
 */
public class ModelFromFileTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testDoInBackground() throws Exception {
        String[] params = {"/home/doddo/Desktop/libsvm-3.20/a0d0e0.scale", "Cross"};
        ModelFromFile modelFromFile = new ModelFromFile();
        modelFromFile.doInBackground(params);
    }
}