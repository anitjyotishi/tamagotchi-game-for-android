package com.example.chintan.tamagotchigo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;


public class PrefsFragmentSettings extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
{
    public boolean b,c;
    public PrefsFragmentSettings () 
    { 
    }

	@Override
    public void onCreate (Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);   	
    	// Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.prefs_fregment_interface);
    }

    @Override
    public void onResume() 
    {
    	super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        Preference pref = getPreferenceScreen().findPreference("restart");
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick (Preference preference) {
                try {
                    AlertDialog.Builder d  = new AlertDialog.Builder(PrefsActivity.context);

                    d.setIcon(R.drawable.ic_launcher);
                    //AlertDialog.Builder builder = d.setView(R.layout.dialogs);
                    d.setView(R.layout.dialogs);
                    d.setPositiveButton(Html.fromHtml("<font color='#0099CC'>Yes</font>"),new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Assets.state = Assets.GameState.READY;
                            Assets.isPlaying=true;
                            Intent intent = new Intent(PrefsActivity.context, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    d.setNegativeButton("No",null);
                    d.setCancelable(true);
                    d.create().show();

                }
                catch (Exception e) {
                    System.out.println(e);
                }
                return true;
            }
        });

  	}


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("tts_sound")) {
            b = sharedPreferences.getBoolean("tts_sound",true);
            Assets.isTTS=b;
        }
    }
}
