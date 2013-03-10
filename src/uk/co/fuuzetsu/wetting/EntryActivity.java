package uk.co.fuuzetsu.wetting;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.gson.Gson;

public class EntryActivity extends Activity {
	private final String KEY = "DIARY";
	private DrinkDiary diary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);

		this.diary = loadDiary();

		TextView tv = (TextView) findViewById(R.id.dateLabel);


		Calendar c = Calendar.getInstance();

		tv.setText(dfDate_day.format(c.getTime()));

    }


	public DrinkDiary loadDiary() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		DrinkDiary d;
		String json = prefs.getString(KEY, "");
		if (json.length() == 0) {
			d = new DrinkDiary();
		}
		else {
			Gson gson = new Gson();
			Log.d(TAG, "json des:\n" + json);
			d = gson.fromJson(json, DrinkDiary.class);
		}
		return d;
	}

}
