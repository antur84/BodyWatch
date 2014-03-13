package turku.com.bodywatch.subactivity;

import javax.inject.Inject;

import turku.com.bodywatch.BaseActivity;
import turku.com.bodywatch.R;
import turku.com.bodywatch.model.BodyWatchRepository;
import turku.com.bodywatch.model.IBodyWatchRepository;
import turku.com.bodywatch.model.domainobjects.BodyFat;
import turku.com.bodywatch.model.domainobjects.BodyWater;
import turku.com.bodywatch.model.domainobjects.BodyWeight;
import turku.com.bodywatch.model.domainobjects.BoneWeight;
import turku.com.bodywatch.model.domainobjects.MuscleRatio;
import turku.com.bodywatch.model.domainobjects.WeightEntry;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class CreateNewEntryActivity extends BaseActivity {

	@Inject
	private IBodyWatchRepository repo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_entry);
		
		repo = new BodyWatchRepository(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_new_entry, menu);
		return true;
	}

	public void saveClick(View view) {
		BodyWeight bodyWeight = new BodyWeight(
				GetLongValueOrDefault(((EditText) findViewById(R.id.textbox_weight))
						.getText()));
		BodyFat bodyFat = new BodyFat(
				GetLongValueOrDefault(((EditText) findViewById(R.id.textbox_body_fat))
						.getText()));
		BodyWater bodyWater = new BodyWater(
				GetLongValueOrDefault(((EditText) findViewById(R.id.textbox_body_water))
						.getText()));
		MuscleRatio muscleRatio = new MuscleRatio(
				GetLongValueOrDefault(((EditText) findViewById(R.id.textbox_muscle_ratio))
						.getText()));
		BoneWeight boneWeight = new BoneWeight(
				GetLongValueOrDefault(((EditText) findViewById(R.id.textbox_bone))
						.getText()));

		WeightEntry weightEntry = new WeightEntry(bodyWeight, bodyFat,
				bodyWater, muscleRatio, boneWeight);

		repo.insertWeightEntry(weightEntry);
	}

}
