package uk.co.fuuzetsu.wetting;

import android.app.*;
import android.content.*;
import android.util.*;
import android.os.*;
import android.text.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;


public class AddActivity extends Activity {

	private final String TAG = "AddActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

		/* Disable the appropriate items */
		final Button save = (Button) findViewById(R.id.buttonSaveDrink);
		final CheckBox fizzyCheck = (CheckBox) findViewById(R.id.fizzyCheckbox);
		save.setEnabled(false);

		final EditText drinkInput = (EditText) findViewById(R.id.newDrinkTextbox);
		final Spinner drinkSpinner = (Spinner) findViewById(R.id.oldDrinkSpinner);

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
