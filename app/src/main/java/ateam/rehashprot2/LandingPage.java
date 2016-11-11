package ateam.rehashprot2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import ateam.rehashprot2.data.Channel;
import ateam.rehashprot2.data.Condition;
import ateam.rehashprot2.service.WeatherService;
import ateam.rehashprot2.service.WeatherServiceCallback;

public class LandingPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        WeatherServiceCallback {

    private int fragmentInt = 0;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private static Condition condition;
    private static WeatherService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        mAuth = FirebaseAuth.getInstance();
        service = new WeatherService(this);
        service.refreshWeather("Auckland, NZ");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        final TextView userNameTV = (TextView) header.findViewById(R.id.username_header);
        final TextView emailTV = (TextView) header.findViewById(R.id.email_header);

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());

        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        String displayName = dataSnapshot.child("display").getValue().toString();
                        String disPlayEmail = dataSnapshot.child("email").getValue().toString();
                        userNameTV.setText(displayName);
                        emailTV.setText(disPlayEmail);
                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        //*************FRAGMENT MANAGEMENT******************
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment_frame_layout, new ListViewFragment(), "ListView");
        // Complete the changes added above
        ft.commit();




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing_page, menu);
        return true;
    } */


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home_page) {
            Intent intent = new Intent(LandingPage.this, LandingPage.class);
            startActivity(intent);
        } else if (id == R.id.nav_calendar) {
            Intent intent = new Intent(LandingPage.this, CalendarPage.class);
            startActivity(intent);
        } else if (id == R.id.nav_weather) {
            Intent intent = new Intent(LandingPage.this, WeatherActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(LandingPage.this, LoginActivity.class);
            startActivity(intent);
            mAuth.signOut();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setAlarm(Calendar time, int id, String module) {
        WeatherService service = new WeatherService(this);
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Receiver.class);
        intent.putExtra("Module", module);
        intent.putExtra("Id", id);
        alarmIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), alarmIntent);
    }

    //cancels an alarm based on id
    public void cancelAlarm(int id) {
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    //gets the current weather code from yahoo
    public static int getCurrentWeatherCode() {
        return condition.getCode();
    }

    //gets the name of the weather code
    public static String getCurrentWeather() {
        return condition.getDescription();
    }

    //necessary service to get the weather
    @Override
    public void serviceSuccess(Channel channel) {
        condition = channel.getItem().getCondition();
    }

    @Override
    public void serviceFailure(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
