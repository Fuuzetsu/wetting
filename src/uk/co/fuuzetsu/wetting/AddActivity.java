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

		Either<Drink, Toilet> t = edt;
		diary.getActivities().put(time, t);

		Thread th = new Thread() {
				public void run() {
					Gson g = new Gson();
					Log.d(TAG, "starting serlisation");
					String j = g.toJson(diary);
					Log.d(TAG, j);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString(KEY, j);
					editor.commit();

					Log.d(TAG, "done editor commiting");
				}
			};
		th.start();
	}

	public void saveToilet(View view) {
		Log.d(TAG, "pressed Toilet");

		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		final DrinkDiary diary = this.diary;

		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Long time = c.getTimeInMillis();
		Log.d(TAG, sdf.format(time));

		Either<Drink, Toilet> t = new Either<Drink, Toilet>(new Toilet());
		diary.getActivities().put(time, t);

		Thread th = new Thread() {
				public void run() {
					Gson g = new Gson();
					Log.d(TAG, "starting serlisation");
					String j = g.toJson(diary);
					Log.d(TAG, j);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString(KEY, j);
					editor.commit();

					Log.d(TAG, "done editor commiting");
				}
			};
		th.start();
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

		/* Initialise diary, populate list */
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String json = prefs.getString(KEY, "");
		if (json.length() == 0) {
			this.diary = new DrinkDiary();
		}
		else {
			Gson gson = new Gson();
			Log.d(TAG, "json des:\n" + json);
			this.diary = gson.fromJson(json, DrinkDiary.class);
		}

		DrinkDiary aoe = new DrinkDiary();
		Gson g = new Gson();
		String j = g.toJson(aoe);

		for (Map.Entry<Long, Either<Drink, Toilet>> entry : this.diary.getActivities().entrySet()) {
			Either<Drink, Toilet> v = entry.getValue();

			if (v.isLeft())
				drinks.add(v.getLeft().getName());
		}

		drinks.add("test1");
		drinks.add("test2");

		final Spinner drinkSpinner = (Spinner) findViewById(R.id.oldDrinkSpinner);

		ArrayAdapter adptr = new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.drinks);
		drinkSpinner.setAdapter(adptr);

		/* Disable the appropriate items */
		final Button save = (Button) findViewById(R.id.buttonSaveDrink);
		final CheckBox fizzyCheck = (CheckBox) findViewById(R.id.fizzyCheckbox);
		save.setEnabled(false);

		final EditText drinkInput = (EditText) findViewById(R.id.newDrinkTextbox);


		Log.d(TAG, "In after last init");

		drinkSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
					Boolean x = drinkSpinner == null;
					String drinkSpinnerTV = drinkSpinner.getSelectedItem().toString();
					Log.d(TAG, "before test");
					if (drinkSpinnerTV == null || drinkSpinnerTV.length() == 0) {
						drinkInput.setEnabled(true);
						drinkSpinner.setEnabled(true);
						save.setEnabled(false);
						fizzyCheck.setEnabled(false);
					}
					else {
						drinkInput.setEnabled(false);
						drinkSpinner.setEnabled(true);
						save.setEnabled(true);
						fizzyCheck.setEnabled(true);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parentView) {}

			});

		drinkInput.addTextChangedListener(new TextWatcher() {
				@Override
				public void afterTextChanged(Editable s) {
					if (drinkInput.length() == 0) {
						TextView drinkSpinnerTV = (TextView) drinkSpinner.getSelectedItem();
						if (drinkSpinnerTV == null || drinkSpinnerTV.toString().length() == 0) {
							drinkInput.setEnabled(true);
							drinkSpinner.setEnabled(true);
							save.setEnabled(false);
							fizzyCheck.setEnabled(false);
						}
						else {
							drinkSpinner.setEnabled(false);
							save.setEnabled(true);
							fizzyCheck.setEnabled(true);
						}
					}
					else {
						drinkSpinner.setEnabled(false);
						save.setEnabled(true);
					}
				}

				public void beforeTextChanged(CharSequence s, int start, int count, int after){}
				public void onTextChanged(CharSequence s, int start, int before, int count){}
			});


    }
}
