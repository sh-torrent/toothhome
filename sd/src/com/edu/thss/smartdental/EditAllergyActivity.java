package com.edu.thss.smartdental;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EditAllergyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_allergy);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_allergy, menu);
		return true;
	}

}