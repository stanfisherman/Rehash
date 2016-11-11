package ateam.rehashprot2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import static android.app.Notification.PRIORITY_HIGH;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Stanton on 26/10/2016.
 */

public class Receiver extends BroadcastReceiver {
    private Notification.Builder builder;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;

    @Override
    public void onReceive(Context context, Intent intent) {
        String module = intent.getStringExtra("Module");
        //sets the action to go to the landing page for when the user clicks the notification
        context.sendBroadcast(new Intent("LandingPage"));
        pref = context.getSharedPreferences("settings", MODE_PRIVATE);
        prefEditor = pref.edit();
        int id = intent.getIntExtra("Id", 0);
        builder = new Notification.Builder(context);
        //sets the title of the notification
        builder.setContentTitle(module);

        //sets the message of the notification based on which module was called
        switch (module) {
            case "Medicine":
                builder.setContentText("Please take your medicine");
                break;
            case "Exercise":
                if (LandingPage.getCurrentWeatherCode() >= 26 && LandingPage.getCurrentWeatherCode() <= 34) {
                    builder.setContentText("Please do your exercise for today, the current forecast is: " + LandingPage.getCurrentWeather());
                }
                else {
                    builder.setContentText("Please do your exercise another time, the current forecast is: " + LandingPage.getCurrentWeather());
                }
                break;
            default:
                builder.setContentText(module);
                break;
        }
        //sets up the notification
        builder.setSmallIcon(R.drawable.ic_menu_alarm);
        builder.setPriority(PRIORITY_HIGH);
        builder.setVibrate(new long[]{1000, 0, 0, 0, 0});
        Intent action = new Intent(context, LandingPage.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        prefEditor.putBoolean(module + "Toggle", false);
        prefEditor.apply();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());
    }
}

