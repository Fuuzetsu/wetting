package uk.co.fuuzetsu.wetting;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;


public class AddActivity extends Activity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

		/* Disable the button on start up */
		Button save = (Button) findViewById(R.id.buttonSaveDrink);
		save.setEnabled(false);

		EditText drinkInput = (EditText) findViewById(R.id.newDrinkTextbox);
		Spinner drinkSpinner = (Spinner) findViewById(R.id.oldDrinkSpinner);

		drinkInput.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (drinkInput.getText().toString().length() == 0) {
						if (drinkSpinner.getSelectedItem().toString().length() == 0) {
							drinkInput.setEnabled(true);
							drinkSpinner.setEnabled(true);
							save.setEnabled(false);
						}
						else {
							drinkSpinner.setEnabled(false);
							save.setEnabled(true);
						}

					}
					else {
						drinkSpinner.setEnabled(false);
						save.setEnabled(true);
					}
				}

			});


    }
}
