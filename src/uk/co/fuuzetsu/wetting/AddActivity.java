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

import com.google.gson.Gson;

import java.util.*;
import java.text.*;

public class AddActivity extends Activity {
    private final String TAG = "AddActivity";
    private final String KEY = "DIARY";
    private DrinkDiary diary;
    private List<String> drinks = new ArrayList<String>();

    protected Thread th;

    @Override
    public void onResume() {
        super.onResume();

        th = null;
    }

    public void saveDrink(View view) {
        final Spinner drinkSpinner = (Spinner) findViewById(R.id.oldDrinkSpinner);
        final CheckBox fizzyCheck = (CheckBox) findViewById(R.id.fizzyCheckbox);
        final EditText drinkInput = (EditText) findViewById(R.id.newDrinkTextbox);

        String spin = drinkSpinner.getSelectedItem().toString();
        String drink = drinkInput.getText().toString();
        Boolean b = fizzyCheck.isChecked();
        Log.d(TAG, "spin: " + spin);
        Log.d(TAG, "drink: " + drink);
        Log.d(TAG, "fizzy " + b.toString());

        String dName = "".equals(drink) ? spin : drink;

        saveValue(new Drink(dName, b));
    }

    public void saveValue(Event ev) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final DrinkDiary diary = this.diary;

        Calendar c = Calendar.getInstance();
        Long time = c.getTimeInMillis();

        diary.getActivities().put(time, ev);

        final AddActivity that = this;

        Thread th = new Thread() {
                public void run() {
                    Log.d(TAG, "starting serlisation");

                    if (that.th != null) {
                        Log.d(TAG, "already writing; not writing again");
                        return;
                    }

                    that.th = this;

                    String j = (new Gson()).toJson(diary);
                    Log.d(TAG, j);

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(KEY, j);
                    editor.commit();

                    Log.d(TAG, "done editor commiting");
                    that.finish();

                    that.th = null;
                }
            };

        ((Button) findViewById(R.id.buttonSaveDrink)).setEnabled(false);
        ((Button) findViewById(R.id.buttonToilet)).setEnabled(false);

        th.start();
    }

    public void saveToilet(View view) {
        Log.d(TAG, "pressed Toilet");

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final DrinkDiary diary = this.diary;

        saveValue(new Toilet());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        /* Initialise diary, populate list */
        String json = PreferenceManager.getDefaultSharedPreferences(this)
                                       .getString(KEY, "");

        diary = "".equals(json)
            ? new DrinkDiary()
            : (new Gson()).fromJson(json, DrinkDiary.class);

        DrinkDiary aoe = new DrinkDiary();
        Gson g = new Gson();
        String j = g.toJson(aoe);

        drinks.add("test1");
        drinks.add("test2");

        for (Map.Entry<Long, Event> entry : this.diary.getActivities().entrySet()) {
            Event v = entry.getValue();

            try {
                String d = ((Drink) v).getName();
                if (!drinks.contains(d)) drinks.add(d);
            } catch (ClassCastException e) { }
        }


        final Spinner drinkSpinner = (Spinner) findViewById(R.id.oldDrinkSpinner);

        ArrayAdapter<String> adptr = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.drinks);
        drinkSpinner.setAdapter(adptr);
    }
}
