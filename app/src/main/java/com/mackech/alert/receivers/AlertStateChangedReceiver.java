package com.mackech.alert.receivers;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.mackech.alert.MemoryServices;
import com.mackech.alert.R;
import com.mackech.alert.services.BluetoothLeService;

import java.util.Set;

public class AlertStateChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);

        boolean state = Integer.valueOf(data) > 0;
        boolean lastState = MemoryServices.getAlertButtonState(context);
        if(!lastState && state)
            sendSMSToEmergencyContacts(context);
        MemoryServices.setAlertButtonState(context,state);
    }

    private void sendSMSToEmergencyContacts(Context context) {

        Set<String> emergencyContacts = MemoryServices.getEmergencyContacts(context);
        Location location = MemoryServices.getLocation(context);
        if(location != null){
            String message = String.format(context.getString(R.string.location_message_holder),
                    location.getLatitude(),location.getLongitude(),location.getLatitude(),location.getLongitude());

            for(String phone : emergencyContacts){
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(phone, null, message, null, null);
            }
        }
    }
}
