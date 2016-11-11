package ateam.rehashprot2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Warrick
 */
public class ViewEventActivity extends AppCompatActivity {

    private TextView titleTV;
    private TextView startDateTV;
    private TextView endDateTV;
    private TextView startTimeTV;
    private TextView endTimeTV;
    private TextView descriptionTV;

    private String jsonText;
    private JSONObject jsonObject;

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        titleTV = (TextView) findViewById(R.id.titleTV);
        startDateTV = (TextView) findViewById(R.id.start_dateTV);
        startTimeTV = (TextView) findViewById(R.id.start_timeTV);
        endDateTV = (TextView) findViewById(R.id.end_dateTV);
        endTimeTV = (TextView) findViewById(R.id.end_timeTV);
        descriptionTV = (TextView) findViewById(R.id.descriptionTV);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        jsonObject = new JSONObject();

        DatabaseReference ref = database.getReference(mAuth.getCurrentUser().getUid()).child("Schedule").child(id);
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        ProgressDialog pd = new ProgressDialog(ViewEventActivity.this);
                        pd.setMessage("Updating Data");
                        pd.show();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            switch (postSnapshot.getKey()) {
                                case "title":
                                    titleTV.setText(postSnapshot.getValue().toString());
                                    try {
                                        jsonObject.put("title",postSnapshot.getValue().toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case "startDate":
                                    startDateTV.setText(postSnapshot.getValue().toString());
                                    try {
                                        jsonObject.put("startDate",postSnapshot.getValue().toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case "startTime":
                                    startTimeTV.setText(postSnapshot.getValue().toString());
                                    try {
                                        jsonObject.put("startTime",postSnapshot.getValue().toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case "endDate":
                                    endDateTV.setText(postSnapshot.getValue().toString());
                                    try {
                                        jsonObject.put("endDate",postSnapshot.getValue().toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case "endTime":
                                    endTimeTV.setText(postSnapshot.getValue().toString());
                                    try {
                                        jsonObject.put("endTime",postSnapshot.getValue().toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case "description":
                                    descriptionTV.setText(postSnapshot.getValue().toString());
                                    try {
                                        jsonObject.put("description",postSnapshot.getValue().toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                        }
                        jsonText = jsonObject.toString();
                        pd.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing_page, menu);
        return true;
    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
