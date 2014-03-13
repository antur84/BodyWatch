package com.turku.bodywatch;

import java.text.DecimalFormat;
import java.text.ParseException;

import android.app.Activity;
import android.text.Editable;

public class BaseActivity extends Activity {

	private DecimalFormat _format = new DecimalFormat();

	protected double getDoubleValueOrDefault(Editable editable) {
		Number number = 0;
		int attempts = 0;
		boolean parseOk = false;
		while (attempts < 2 && !parseOk) {
			try {
				attempts++;
				_format.setMinimumFractionDigits(1);
				String toParse = getStringToParse(editable, attempts);
				number = _format.parse(toParse);
				parseOk = true;

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return number.doubleValue();
	}

	private String getStringToParse(Editable editable, int attemptNumber) {
		if (attemptNumber == 1) {
			return editable.toString().replace(".", ",");
		}

		return editable.toString().replace(",", ".");
	}
}
