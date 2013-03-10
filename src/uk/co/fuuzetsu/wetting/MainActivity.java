package uk.co.fuuzetsu.wetting;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;


public class MainActivity extends Activity
{

    public static final String EXTRA_MESSAGE = "MAIN_MSG";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void showDayButtonMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra(MainActivity.EXTRA_MESSAGE, "Activated Show Day button");
        startActivity(intent);
    }

    public void showRemoveScreen(View view) {
        startActivity(new Intent(this, RemoveActivity.class));
    }

    public void changeToEntry(View view) {
        startActivity(new Intent(this, EntryActivity.class));
    }

}
