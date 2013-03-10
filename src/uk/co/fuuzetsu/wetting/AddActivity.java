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

        String dName =  drinkInput.isEnabled() ? drink : spin;

        saveValue(new Either<Drink, Toilet>(new Drink(dName, b), true));
    }

    public void saveValue(Either<Drink, Toilet> edt) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final DrinkDiary diary = this.diary;

        Calendar c = Calendar.getInstance();
        Long time = c.getTimeInMillis();

        diary.getActivities().put(time, edt);

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

        Either<Drink, Toilet> t = new Either<Drink, Toilet>(new Toilet());

        saveValue(t);
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

        for (Map.Entry<Long, Either<Drink, Toilet>> entry : this.diary.getActivities().entrySet()) {
            Either<Drink, Toilet> v = entry.getValue();

            if (v.isLeft()) {
                String s = v.getLeft().getName();
                if (!drinks.contains(s))
                    drinks.add(s);
            }
        }


        final Spinner drinkSpinner = (Spinner) findViewById(R.id.oldDrinkSpinner);

        ArrayAdapter<String> adptr = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.drinks);
        drinkSpinner.setAdapter(adptr);

        /* Disable the appropriate items */
        final Button save = (Button) findViewById(R.id.buttonSaveDrink);
        final CheckBox fizzyCheck = (CheckBox) findViewById(R.id.fizzyCheckbox);
        final EditText drinkInput = (EditText) findViewById(R.id.newDrinkTextbox);


        Log.d(TAG, "In after last init");

        drinkSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView,
                                           View           selectedItemView,
                                           int            position,
                                           long           id) {
                    Boolean x = drinkSpinner == null;
                    String drinkSpinnerTV = drinkSpinner.getSelectedItem().toString();
                    Log.d(TAG, "before test");
                    if (drinkSpinnerTV == null || drinkSpinnerTV.length() == 0) {
                        drinkInput.setEnabled(true);
                        drinkSpinner.setEnabled(true);
                        fizzyCheck.setEnabled(false);
                    }
                    else {
                        drinkInput.setEnabled(false);
                        drinkSpinner.setEnabled(true);
                        save.setEnabled(true);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {}

            });

        drinkInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {

                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                public void onTextChanged(CharSequence s, int start, int before, int count){}
            });
    }
}
