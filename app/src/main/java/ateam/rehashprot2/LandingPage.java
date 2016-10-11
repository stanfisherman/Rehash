package ateam.rehashprot2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
<<<<<<< HEAD
import android.view.MenuItem;
=======
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import dalvik.system.DexClassLoader;
>>>>>>> origin/master

public class LandingPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Array of strings...
    String[] mobileArray = {"Placeholder1","Placeholder2","Placeholder3","Placeholder4","Placeholder5","Placeholder6","Placeholder7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        getStatechart();
        instanciateStatechart();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        ArrayAdapter list_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mobileArray);
//        ListView lpListView = (ListView) findViewById(R.id.list_view);
//        lpListView.setAdapter(list_adapter);

        //*************FRAGMENT MANAGEMENT******************
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment_frame_layout, new CounterFragment());
        //ft.add(R.id.fragment_frame_layout, new CounterFragment());
        // Complete the changes added above
        ft.commit();
    }

    //To add items to the list view
    public void addListItems()
    {
        //mobileArray
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
            // Handle the home action
        } else if (id == R.id.nav_calendar) {
            Intent intent = new Intent(LandingPage.this, CalendarPage.class);
            startActivity(intent);
        } else if (id == R.id.nav_weather) {
            Intent intent = new Intent(LandingPage.this, WeatherActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_alarm) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void getStatechart() {
        //gets firebase instance and sets up the firebase urls for download
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://test-d93ae.appspot.com");
        StorageReference testRef = storageRef.child("statechart.apk");

        //create a temporary file object to hold the downloaded apk
        final File localFile = new File(getFilesDir().toString() + "/statechart.apk");

        //if file doesn't exist create the file
        if (!localFile.exists()) {
            try {
                localFile.createNewFile();
            } catch (IOException e) {
                Log.e("LandingPage", "File couldn't be created");
            }

            //starts the download
            testRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.i("LandingPage", "File downloaded");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("LandingPage", "File download failed");
                }
            });
        }

    }

    public void instanciateStatechart() {
        final File localFile = new File(getFilesDir().toString() + "/statechart.apk");

        //if file doesn't exist create the file
        if (!localFile.exists()) {
            //initialise the class loader, for dynamic class loading
            DexClassLoader cl = new DexClassLoader(localFile.getAbsolutePath().toString(), getFilesDir().getAbsolutePath().toString(), null, getClassLoader());

            //loads the statechart classes
            Class<Object> defaultSMStatemachine = null;
            Class<Object> iDefaultSMStatemachine = null;
            Class<Object> iStatemachine = null;
            try {
                defaultSMStatemachine = (Class<Object>) cl.loadClass("ateam.defaultSMStatemachine");
                iDefaultSMStatemachine = (Class<Object>) cl.loadClass("ateam.iDefaultSMStatemchine");
                iStatemachine = (Class<Object>) cl.loadClass("ateam.iStatemchine");
            } catch (ClassNotFoundException e) {
                Log.e("LandingPage", "statechart classes weren't loaded");
                e.printStackTrace();
            }

            //attempts to instanciate the statechart objects
            Object defaultInstance = null;
            Object iDefaultInstance = null;
            Object iStatemachineInstance = null;
            try {
                defaultInstance = defaultSMStatemachine.newInstance();
                iDefaultInstance = iDefaultSMStatemachine.newInstance();
                iStatemachineInstance = iStatemachine.newInstance();
            } catch (InstantiationException e) {
                Log.e("LandingPage", "Couldn't instantiate statechart objects");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                Log.e("LandingPage", "Illegal access, statechart objects weren't created");
                e.printStackTrace();
            }

            //access fields and methods
//            Field field = null;
//            try {
//                field = defaultSMStatemachine.getDeclaredField("store");
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            }
//            field.setAccessible(true);
//            String value = "replace";
//            try {
//                value = (String) field.get(instance);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
        }
    }
}
