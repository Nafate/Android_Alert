package com.mackech.alert.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.LocationResult;
import com.mackech.alert.MemoryServices;

/**
 * Created by Cesar on 12/21/16.
 */

public class LocationUpdateReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        if(LocationResult.hasResult(intent)){
            Location location = LocationResult.extractResult(intent).getLastLocation();
            MemoryServices.setLocation(context,location);
        }

    }
}
