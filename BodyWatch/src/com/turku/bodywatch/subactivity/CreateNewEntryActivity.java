package com.turku.bodywatch.subactivity;

import java.util.Date;

import javax.inject.Inject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.turku.bodywatch.BaseActivity;
import com.turku.bodywatch.HomeScreenActivity;
import com.turku.bodywatch.R;
import com.turku.bodywatch.model.BodyWatchRepository;
import com.turku.bodywatch.model.IBodyWatchRepository;
import com.turku.bodywatch.model.domainobjects.BodyFat;
import com.turku.bodywatch.model.domainobjects.BodyWater;
import com.turku.bodywatch.model.domainobjects.BodyWeight;
import com.turku.bodywatch.model.domainobjects.BoneWeight;
import com.turku.bodywatch.model.domainobjects.MuscleRatio;
import com.turku.bodywatch.model.domainobjects.RegistrationDate;
import com.turku.bodywatch.model.domainobjects.WeightEntry;

public class CreateNewEntryActivity extends BaseActivity {

	@Inject
	private IBodyWatchRepository repo;
	private EditText bodyWeightEditText;
	private EditText bodyFatEditText;
	private EditText bodyWaterEditText;
	private EditText muscleRatioEditText;
	private EditText boneWeightEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_entry);
		repo = new BodyWatchRepository(this);
		
		bodyWeightEditText = ((EditText) findViewById(R.id.textbox_weight));
		bodyFatEditText = ((EditText) findViewById(R.id.textbox_body_fat));
		bodyWaterEditText = ((EditText) findViewById(R.id.textbox_body_water));
		muscleRatioEditText = ((EditText) findViewById(R.id.textbox_muscle_ratio));
		boneWeightEditText = ((EditText) findViewById(R.id.textbox_bone));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_new_entry, menu);
		return true;
	}

	public void saveClick(View view) {

		BodyWeight bodyWeight = null;
		boolean isValid = true;
		try {
			bodyWeight = new BodyWeight(
					getDoubleValueOrDefault(bodyWeightEditText.getText()));
		} catch (IllegalArgumentException e) {
			bodyWeightEditText
					.setError(getString(R.string.error_requiredField));
			isValid = false;
		}

		if (isValid) {
			BodyFat bodyFat = new BodyFat(
					getDoubleValueOrDefault(bodyFatEditText.getText()));
			BodyWater bodyWater = new BodyWater(
					getDoubleValueOrDefault(bodyWaterEditText.getText()));
			MuscleRatio muscleRatio = new MuscleRatio(
					getDoubleValueOrDefault(muscleRatioEditText.getText()));
			BoneWeight boneWeight = new BoneWeight(
					getDoubleValueOrDefault(boneWeightEditText.getText()));

			RegistrationDate date = new RegistrationDate(new Date());
			WeightEntry weightEntry = new WeightEntry(bodyWeight, bodyFat,
					bodyWater, muscleRatio, boneWeight, date);

			repo.insertWeightEntry(weightEntry);
			
			Intent backToHome = new Intent(this, HomeScreenActivity.class);
			startActivity(backToHome);
		}
	}

}
