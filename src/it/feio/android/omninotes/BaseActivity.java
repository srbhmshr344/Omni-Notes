package it.feio.android.omninotes;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;
import it.feio.android.omninotes.R;
import it.feio.android.omninotes.utils.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class BaseActivity extends Activity  {
	
	private final boolean TEST = false;

	protected Tracker tracker;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		/* 
		 * In esecuzioni di test viene abilitato lo StrictMode per il debug delle
		 * operazioni onerose sul thread principale e viene inibito l'invio di dati a GA
		*/
		if (TEST) {
			StrictMode.enableDefaults();
			GoogleAnalytics.getInstance(this).setDryRun(true);
		}
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void onStart() {
		super.onStart();
		// Google Analytics
		EasyTracker.getInstance(this).activityStart(this);
		tracker = GoogleAnalytics.getInstance(this).getTracker("UA-45502770-1");
	}
	

	@Override
	public void onStop() {
		super.onStop();
		// Google Analytics
		EasyTracker.getInstance(this).activityStop(this);
	}

	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settings:
				Intent settingsIntent = new Intent(this, SettingsActivity.class);
	            startActivity(settingsIntent);
				break;
		}
		return super.onMenuItemSelected(featureId, item);
	}	
	
	protected boolean navigationArchived() {
		return PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.PREF_NAVIGATION, "").equals(getResources().getStringArray(R.array.navigation_list)[1]);		
	}
	
	
}
