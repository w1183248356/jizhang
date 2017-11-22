package com.jizhang;


import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.Toast;


public class SettingsActivity extends AppCompatPreferenceActivity {

    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            if("pref_server_port".equalsIgnoreCase(preference.getKey())){
                try {
                    Integer port = Integer.parseInt(stringValue);
                    if(port < 0 || port > 65535){
                        Toast.makeText(JiZhangApplication.getInstance(), "端口号需在1~65535之间", Toast.LENGTH_SHORT)
                                .show();
                        return false;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            preference.setSummary(stringValue);
            preference.getSharedPreferences().edit().putString(preference.getKey(), stringValue).commit();
            //
            return true;
        }
    };


    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        addPreferencesFromResource(R.xml.pref_general);

        bindPreferenceSummaryToValue(findPreference("pref_server_ip"));
        bindPreferenceSummaryToValue(findPreference("pref_server_port"));
        bindPreferenceSummaryToValue(findPreference("pref_server_root"));
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
