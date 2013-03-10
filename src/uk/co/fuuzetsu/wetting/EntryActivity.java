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

        diary = loadDiary();
        Calendar c = Calendar.getInstance();
        Date time = new Date(c.getTimeInMillis());
        changeDay(time);
    }

    public void onButtonPlusClick(View v) {
        startActivity(new Intent(this, AddActivity.class));
    }

    public void changeDay(Date time) {

        TextView tv = (TextView) findViewById(R.id.dateLabel);

        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        Log.v(TAG, "Changing day to " + df.format(time));

        tv.setText(df.format(time));

        List<String> entries = populateList(this.diary, time);

        ListView lv = (ListView) findViewById(R.id.entryList);
        ArrayAdapter adptr = new ArrayAdapter(this, android.R.layout.simple_list_item_1, entries);
        lv.setAdapter(adptr);
    }

    public List<String> populateList(DrinkDiary d, Date time) {
        SimpleDateFormat df = new SimpleDateFormat("HH:MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<String> l = new ArrayList<String>();

        for (Map.Entry<Long, Either<Drink, Toilet>> entry : d.getActivities().entrySet()) {
            Long dt = entry.getKey();
            String date = df.format(new Date(dt));
            String dateDay = dayFormat.format(new Date(dt));
            Log.v(TAG, "date: " + date);
            Log.v(TAG, "dateDay: " + dateDay);

            if (dayFormat.format(time).equals(dateDay)) {
                Either<Drink, Toilet> v = entry.getValue();
                if (v.isLeft())
                    l.add(date + " | " + v.getLeft().getName());
                else
                    l.add(date + " | Toilet");
            }
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
