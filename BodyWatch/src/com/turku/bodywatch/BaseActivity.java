package turku.com.bodywatch;

import android.app.Activity;
import android.text.Editable;

public class BaseActivity extends Activity {

	protected long GetLongValueOrDefault(Editable editable) {
		try {
			return Long.parseLong(editable.toString());
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
