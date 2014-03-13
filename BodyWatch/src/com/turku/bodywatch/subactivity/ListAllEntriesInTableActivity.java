package com.turku.bodywatch.subactivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.turku.bodywatch.BaseActivity;
import com.turku.bodywatch.R;
import com.turku.bodywatch.model.BodyWatchRepository;
import com.turku.bodywatch.model.IBodyWatchRepository;
import com.turku.bodywatch.model.domainobjects.WeightEntry;

public class ListAllEntriesInTableActivity extends BaseActivity {

	private List<Integer> _selectedRows = new ArrayList<Integer>();
	
	@Inject
	private IBodyWatchRepository repo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry_list);
		final TableLayout table = (TableLayout) findViewById(R.id.gridEntries);

		repo = new BodyWatchRepository(this);
		List<WeightEntry> entries = repo.getAllWeightEntries();
		int size = entries.size();
		for (int i = 0; i < size; i++) {
			TableRow row = new TableRow(this);
			
			ColorAlternateRowDark(i, row);
			
			WeightEntry current = entries.get(i);
			
			row.setId(current.getId());
			int minHeightpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
			row.setMinimumHeight(minHeightpx);
			
			TextView actualData = CreateTextView(current.getRegistrationDate().toString());

			TextView col1 = CreateTextView(current.getBodyWeight().getValue()
					.toString());

			TextView col2 = CreateTextView(current.getBodyFat().getValue()
					.toString());

			TextView col3 = CreateTextView(current.getBodyWater().getValue()
					.toString());
			row.addView(actualData);

			TextView col4 = CreateTextView(current.getMuscleRatio().getValue()
					.toString());

			TextView col5 = CreateTextView(current.getBoneWeight().getValue()
					.toString());

			row.addView(col1);
			row.addView(col2);
			row.addView(col3);
			row.addView(col4);
			row.addView(col5);

			row.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int id = v.getId();
					if(_selectedRows.contains(id)){
						_selectedRows.remove(Integer.valueOf(id));
						ColorAlternateRowDark(table.indexOfChild(table.findViewById(id))-1, v);
					}else{
						_selectedRows.add(id);
						v.setBackgroundColor(getResources().getColor(R.color.black_overlay));
					}
				}
			});
			
			table.addView(row);
		}
		
		Button deleteButton = new Button(this);
		deleteButton.setText(getResources().getText(R.string.delete_selected));
		deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(v.getContext())
				.setTitle(getResources().getText(R.string.delete_selected))
				.setMessage(getResources().getText(R.string.delete_selected_explanation))
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

				    public void onClick(DialogInterface dialog, int whichButton) {
				    	repo.deleteWeightEntries(_selectedRows);				    				    					    	
				        Toast.makeText(ListAllEntriesInTableActivity.this, getResources().getText(R.string.delete_selected_success), Toast.LENGTH_SHORT).show();
				        for (int i = 0; i < _selectedRows.size(); i++) {
				        	table.removeView(table.findViewById(_selectedRows.get(i)));
						}	
				    }})
				 .setNegativeButton(android.R.string.no, null).show();				
			}
		});
		
		TableRow deleteRow = new TableRow(this);
		deleteRow.addView(deleteButton);
		table.addView(deleteRow);
	}

	private void ColorAlternateRowDark(int rowIndex, View view) {
		if(rowIndex % 2 == 0){
			view.setBackgroundColor(getResources().getColor(R.color.text_color_blue));
		}else{
			view.setBackgroundColor(getResources().getColor(R.color.text_color_blue_darker));
		}
	}

	private TextView CreateTextView(String text) {
		TextView actualData = new TextView(this);
		actualData.setTextAppearance(this, R.style.AllEntriesTableTextView);
		actualData.setText(text);
		return actualData;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_new_entry, menu);
		return true;
	}
}
