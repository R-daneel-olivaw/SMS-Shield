package sms.shieldpro;

import sms.shieldpro.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

 public class SmsPrefrenceActivity extends PreferenceActivity {
     @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);  

         addPreferencesFromResource(R.xml.preference);
     }
 }