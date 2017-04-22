package com.example.chintan.tamagotchigo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/*___________________________________________________________________
|
| Class: PrefsFragmentAbout
|__________________________________________________________________*/
public class PrefsFragmentAbout extends PreferenceFragment
{
	/*___________________________________________________________________
	|
	| Function: PrefsFragmentAbout (constructor)
	|__________________________________________________________________*/
    public PrefsFragmentAbout()
    { 
    }
    
	/*___________________________________________________________________
	|
	| Function: onCreate 
	|__________________________________________________________________*/
	@Override
    public void onCreate (Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);   	
    	// Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.prefs_fragment_about);
    }
	
	/*___________________________________________________________________
	|
	| Function: onResume 
	|__________________________________________________________________*/
    @Override
    public void onResume() 
    {
    	super.onResume();
  	}
}
