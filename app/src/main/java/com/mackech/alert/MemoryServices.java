package com.mackech.alert;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Cesar on 12/20/16.
 */

public class MemoryServices {

    private static final String USER_PREFS = "USER";
    private static final String EMERGENCY_CONTACTS = "EMERGENCY_CONTACTS";
    private static final String LAT_LNG_PREF = "LAT_LNG";
    private static final String ALERT_STATE = "ALERT_STATE";

    public static void setEmergencyContacts(Context context, Set<String> emergencyContacts) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE).edit();
        editor.putStringSet(EMERGENCY_CONTACTS, emergencyContacts).apply();
    }

    public static Set<String> getEmergencyContacts(Context context) {
        return context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE).getStringSet(EMERGENCY_CONTACTS, new HashSet<String>(){{
            add("3338415110");
            add("3317928713");
        }});
    }

    public static void setAlertButtonState(Context context, boolean state) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE).edit();
        editor.putBoolean(ALERT_STATE, state).apply();
    }

    public static boolean getAlertButtonState(Context context) {
        return context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE).getBoolean(ALERT_STATE,true);
    }

    public static void setLocation(Context context, Location location) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE).edit();
        editor.putString(LAT_LNG_PREF, String.format("%f,%f", location.getLatitude(), location.getLongitude())).apply();
    }

    public static Location getLocation(Context context) {

        String latLng = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE).getString(LAT_LNG_PREF, null);
        if (latLng == null) return null;

        String[] locationData = latLng.split(",");
        Location location = new Location("");
        location.setLatitude(Double.parseDouble(locationData[0]));
        location.setLongitude(Double.parseDouble(locationData[1]));

        return location;
    }

}
