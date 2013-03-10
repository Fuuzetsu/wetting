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

import com.google.gson.Gson;

import java.util.*;

public class AddActivity extends Activity {

	private final String TAG = "AddActivity";
	private final String KEY = "DIARY";
	private DrinkDiary diary;
	private List<String> drinks = new ArrayList<String>();

	public void saveDrink(View view) {
		return;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

		/* Initialise diary, populate list */
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String json = prefs.getString(KEY, "");
		if (KEY.length() == 0) {
			this.diary = new DrinkDiary();
		}
		else {
			Gson gson = new Gson();
			this.diary = gson.fromJson(json, DrinkDiary.class);
		}

		DrinkDiary aoe = new DrinkDiary();
		Gson g = new Gson();
		String j = g.toJson(aoe);
		Log.d(TAG, "printing json");
		Log.d(TAG, j);

		Boolean b = this.diary == null;
		Log.d(TAG, "Is diary null?: " + b.toString());

		// for (Map.Entry<Date, String> entry : this.diary.getActivities().entrySet()) {
		// 	String drink = entry.getValue();
		// 	drinks.add(drink);
		// }
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
