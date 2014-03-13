package com.turku.bodywatch;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.androidplot.ui.PositionMetrics;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.turku.bodywatch.model.BodyWatchRepository;
import com.turku.bodywatch.model.IBodyWatchRepository;
import com.turku.bodywatch.model.domainobjects.WeightEntry;
import com.turku.bodywatch.subactivity.CreateNewEntryActivity;
import com.turku.bodywatch.subactivity.ListAllEntriesInTableActivity;
import com.turku.bodywatch.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class HomeScreenActivity extends Activity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;

	private IBodyWatchRepository repo;

	@Override
	protected void onRestart() {
		super.onRestart();
		
		loadGraphData(contentView);
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home_screen);

		repo = new BodyWatchRepository(this);
		controlsView = findViewById(R.id.fullscreen_content_controls);
		contentView = findViewById(R.id.fullscreen_content);

		//getActionBar().hide();
		loadGraphData(contentView);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		findViewById(R.id.button_view_stats).setOnTouchListener(
				mDelayHideTouchListener);
		findViewById(R.id.button_new_entry).setOnTouchListener(
				mDelayHideTouchListener);
	}

	private void loadGraphData(View contentView) {
		// initialize our XYPlot reference:
		XYPlot plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);

		List<WeightEntry> entries = repo.getAllWeightEntries();
		
		if(entries.size() > 0){
		List<Double> weight = new ArrayList<Double>();
		List<Double> regDate = new ArrayList<Double>();
		for (int i = 0; i < entries.size(); i++) {
			weight.add(entries.get(i).getBodyWeight().getValue());
			regDate.add((double) entries.get(i).getRegistrationDate().getValue()
					.getTime());
		}

		// Turn the above arrays into XYSeries':
		XYSeries weightSeries = new SimpleXYSeries(regDate, weight, "Body weight"); 
																				
		// Create a formatter to use for drawing a series using
		// LineAndPointRenderer
		// and configure it from xml:
		LineAndPointFormatter weightSeriesFormat = new LineAndPointFormatter();
		weightSeriesFormat.setPointLabelFormatter(new PointLabelFormatter());
		weightSeriesFormat.configure(getApplicationContext(),
				R.xml.line_point_formatter_with_plf1);
		plot.setDomainStep(XYStepMode.SUBDIVIDE, regDate.size()); 
		plot.setDomainValueFormat(new Format() {
 
            private DateFormat dateFormat = SimpleDateFormat.getDateInstance();
 
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
 
                long timestamp = ((Number) obj).longValue();
                Date date = new Date(timestamp);
                return dateFormat.format(date, toAppendTo, pos);
            }
 
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
 
            }
        });		
		
		// add a new series' to the xyplot:
		plot.addSeries(weightSeries, weightSeriesFormat);

		plot.getTitleWidget().setVisible(true);
		// reduce the number of range labels
		plot.setTicksPerRangeLabel(3);
		plot.setMarkupEnabled(false); // todo set to false when "done" with graph design
		}else{
			plot.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}

	public void newEntryClick(View view) {
		Intent intent = new Intent(this, CreateNewEntryActivity.class);
		startActivity(intent);
	}
	
	public void listAllEntriesClick(View view) {
		Intent intent = new Intent(this, ListAllEntriesInTableActivity.class);
		startActivity(intent);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	private View contentView;

	private View controlsView;

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
}
