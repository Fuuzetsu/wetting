package uk.co.fuuzetsu.wetting;

import android.app.*;
import android.content.*;
import android.util.*;
import android.os.*;
import android.preference.*;
import android.text.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AdapterView.*;


import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;

public class EntryActivity extends Activity {
	private final String KEY = "DIARY";
	private final String TAG = "EntryActivity";
	private DrinkDiary diary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);

		this.diary = loadDiary();

		TextView tv = (TextView) findViewById(R.id.dateLabel);


		Calendar c = Calendar.getInstance();
		SimpleDateFormat dfDate_day= new SimpleDateFormat("dd MMMM yyyy");
		tv.setText(dfDate_day.format(c.getTime()));

		List<String> entries = populateList(this.diary);

		ListView lv = (ListView) findViewById(R.id.entryList);
		ArrayAdapter adptr = new ArrayAdapter(this, android.R.layout.simple_list_item_1, entries);
		lv.setAdapter(adptr);

    }

	public List<String> populateList(DrinkDiary d) {
		SimpleDateFormat df= new SimpleDateFormat("dd/mm/yyyy");
		List<String> l = new ArrayList<String>();

		for (Map.Entry<Long, Either<Drink, Toilet>> entry : d.getActivities().entrySet()) {
			Long dt = entry.getKey();
			String date = df.format(new Date(dt));
			Either<Drink, Toilet> v = entry.getValue();
			if (v.isLeft())
				l.add(date + " | " + v.getLeft().getName());
			else
				l.add(date + " | Toilet");
		}
		return l;
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
