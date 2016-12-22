package com.mackech.alert.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

public class SMSAlertService extends IntentService {

    private static final String ACTION_SEND_SMS_ALERT = "com.mackech.alert.services.action.SEND_ALERT_SMS";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.mackech.alert.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.mackech.alert.services.extra.PARAM2";

    public SMSAlertService() {
        super("SMSAlertService");
    }

    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SMSAlertService.class);
//        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SMSAlertService.class);
//        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SEND_SMS_ALERT.equals(action)) {
                sendAlertSMS();
            }
        }
    }


    private void sendAlertSMS() {

    }


}
