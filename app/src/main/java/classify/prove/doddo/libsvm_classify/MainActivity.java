package classify.prove.doddo.libsvm_classify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import libsvm.*;

public class MainActivity extends AppCompatActivity {

    public static int l = 0, f = 0;
    // svm_problem needed by svm_train
    public static svm_problem svmProblem = new svm_problem();
    public static svm_parameter svmParameter = new svm_parameter();
    public static double[][]   results;
    public static double[]     percentages;
    public static int[] log_C_coef;
    public static int[] log_Gamma_coef;
    public static int[] correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
