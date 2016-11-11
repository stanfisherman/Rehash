package ateam.rehashprot2;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Warrick
 */
public class AddEventActivity extends AppCompatActivity {
    private EditText titleET;
    private Button startDateButton;
    private Button startTimeButton;
    private Button endDateButton;
    private Button endTimeButton;
    private EditText descriptionText;
    private Button addEventButton;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private Date alarmDate;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        ref = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        titleET = (EditText) findViewById(R.id.add_event_title);
        startDateButton = (Button) findViewById(R.id.add_event_start_date);
        startTimeButton = (Button) findViewById(R.id.add_event_start_time);
        endDateButton = (Button) findViewById(R.id.add_event_end_date);
        endTimeButton = (Button) findViewById(R.id.add_event_end_time);
        descriptionText = (EditText) findViewById(R.id.add_event_description);
        addEventButton = (Button) findViewById(R.id.add_event_button);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
                String start = startDateButton.getText().toString() + " " + startTimeButton.getText().toString();
                String end = endDateButton.getText().toString() + " " + endTimeButton.getText().toString();

                Date startDate = Calendar.getInstance().getTime();
                Date endDate = Calendar.getInstance().getTime();
                try {
                    startDate = dateFormatter.parse(start);
                    endDate = dateFormatter.parse(end);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (startDate.before(endDate)) {
                    new AddEvent().execute();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddEventActivity.this);
                    builder.setMessage("start date is after end date").show();
                }
                alarmDate = startDate;
            }
        });

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.US);
        Calendar newCalendar = Calendar.getInstance();

        String currentDate = dateFormatter.format(newCalendar.getTime());
        String currentHour = timeFormatter.format(newCalendar.getTime());

        startDateButton.setText(currentDate);
        endDateButton.setText(currentDate);

        startTimeButton.setText(currentHour);
        endTimeButton.setText(currentHour);


        setOnClickDatePicker(startDateButton);
        setOnClickDatePicker(endDateButton);
        setOnClickTimePickerEditor(startTimeButton);
        setOnClickTimePickerEditor(endTimeButton);
    }

    public void setOnClickDatePicker(final Button editDate) {
        String currentDate = editDate.getText().toString();
        final String[] dateSplit = currentDate.split("/");
        final int year = Integer.parseInt(dateSplit[2]);
        final int month = Integer.parseInt(dateSplit[1]);
        final int day = Integer.parseInt(dateSplit[0]);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEventActivity.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        editDate.setText(dateFormatter.format(newDate.getTime()));
                    }

                }, year, month, day);
                datePickerDialog.updateDate(year, month, day);
                datePickerDialog.show();

            }
        });
    }


    public void setOnClickTimePickerEditor(final Button timeButton) {
        String currentTime = timeButton.getText().toString();
        String[] timeSplit = currentTime.split(":");
        final int hour = Integer.parseInt(timeSplit[0]);
        final int minute = Integer.parseInt(timeSplit[1]);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String selectedHourString = selectedHour + "";
                        if (selectedHourString.length() == 1) {
                            selectedHourString = "0" + selectedHourString;
                        }
                        String selectedMinuteString = selectedMinute + "";
                        if (selectedMinuteString.length() == 1) {
                            selectedMinuteString = "0" + selectedMinuteString;
                        }
                        timeButton.setText(selectedHourString + ":" + selectedMinuteString);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setAlarm(Calendar time, int id, String module) {
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Receiver.class);
        intent.putExtra("Module", module);
        intent.putExtra("Id", id);
        alarmIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), alarmIntent);
    }

    private class AddEvent extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pd;
        private String startDate;
        private String endDate;
        private String startTime;
        private String endTime;
        private String title;
        private String description;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(AddEventActivity.this);
            pd.setMessage("loading");
            pd.show();
            startDate = startDateButton.getText().toString();
            endDate = endDateButton.getText().toString();
            startTime = startTimeButton.getText().toString();
            endTime = endTimeButton.getText().toString();
            title = titleET.getText().toString();
            description = descriptionText.getText().toString();

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... params) {
            Calendar time = Calendar.getInstance();
            time.setTime(alarmDate);
            DatabaseReference fb = ref.child(mAuth.getCurrentUser().getUid()).child("Schedule").push();

            CustomDate cd = new CustomDate(startDate, endDate, startTime, endTime, title, description);
            fb.setValue(cd);
            setAlarm(time, (int) System.currentTimeMillis(), title);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pd.dismiss();
            super.onPostExecute(aVoid);
            pd = null;
            onBackPressed();
            finish();
        }
    }
}
