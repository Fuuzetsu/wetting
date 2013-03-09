package uk.co.fuuzetsu.wetting;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EntryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);

		TextView tv = (TextView) findViewById(R.id.dateLabel);

		SimpleDateFormat dfDate_day= new SimpleDateFormat("dd MMMM yyyy");

		Calendar c = Calendar.getInstance();

		tv.setText(dfDate_day.format(c.getTime()));

    }

}
