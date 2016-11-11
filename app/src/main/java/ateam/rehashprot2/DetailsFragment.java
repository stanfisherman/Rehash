package ateam.rehashprot2;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
    private ToggleButton but;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;
    private Button dateButton;
    private Button timeButton;
    private String currentDate;
    private String currentHour;


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.medicine_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String detTitle = getArguments().getString("Title");
        String detDesc = getArguments().getString("Description");
        int detIcon = getArguments().getInt("Icon");

        ImageView ic = (ImageView) view.findViewById(R.id.medDetailsImg);
        TextView tit = (TextView) view.findViewById(R.id.medDetailsTitle);
        TextView des = (TextView) view.findViewById(R.id.medDetailsDesc);

        //sets button for alarm and variables for shared pref
        but = (ToggleButton) view.findViewById(R.id.notifyButton);
        pref = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        prefEditor = pref.edit();

        //sets up date and time button so users can change the alarm time
        dateButton = (Button) getActivity().findViewById(R.id.module_start_date);
        timeButton = (Button) getActivity().findViewById(R.id.module_start_time);

        //sets up the current date and time for buttons if not stored in shared pref already
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.US);
        final SimpleDateFormat timeDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
        Calendar newCalendar = Calendar.getInstance();

        //sets the time for the date and time buttons, current if not set in shared prefs or uses the shared prefs stored time
        if (pref.getString("Date", "").equals("") && pref.getString("Time", "").equals("")) {
            currentDate = dateFormatter.format(newCalendar.getTime());
            currentHour = timeFormatter.format(newCalendar.getTime());
        }
        else {
            currentDate = pref.getString(getArguments().getString("Title") + "Date", "");
            currentHour = pref.getString(getArguments().getString("Title") + "Time", "");
        }

        //sets the time
        dateButton.setText(currentDate);
        timeButton.setText(currentHour);

        //sets the listeners for the button
        setOnClickDatePicker(dateButton);
        setOnClickTimePickerEditor(timeButton);

        //sets the toggle button value if stored in shared prefs
        if (pref.getBoolean(getArguments().getString("Title") + "Toggle", false) == true)
            but.setChecked(true);

        //sets alarm when toggled on, cancels alarm when toggled off
        but.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (but.isChecked() == true) {
                    Calendar time = Calendar.getInstance();

                   String set = dateButton.getText().toString() + " " + timeButton.getText().toString();
                    Log.e("set", set);
                    try {
                        time.setTimeInMillis(timeDate.parse(set).getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.e("set", time.toString());
                    ((LandingPage)getActivity()).setAlarm(time, getArguments().getInt("Id"), getArguments().getString("Title"));
                    Toast.makeText(getContext(), "Alarm set.", Toast.LENGTH_SHORT).show();
                }
                else {
                    ((LandingPage)getActivity()).cancelAlarm(getArguments().getInt("Id"));
                    Toast.makeText(getContext(), "Alarm turned off.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ic.setImageResource(detIcon);
        tit.setText(detTitle);
        des.setText(detDesc);
    }



    //the date dialog for the fragment allowing you to set the date for alarm
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        dateButton.setText(dateFormatter.format(newDate.getTime()));
                    }

                }, year, month, day);
                datePickerDialog.updateDate(year, month, day);
                datePickerDialog.show();

            }
        });
    }


    //the time dialog for the fragment allowing you to set the time for alarm
    public void setOnClickTimePickerEditor(final Button editTime) {
        String currentTime = editTime.getText().toString();
        String[] timeSplit = currentTime.split(":");
        final int hour = Integer.parseInt(timeSplit[0]);
        final int minute = Integer.parseInt(timeSplit[1]);
        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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

    @Override
    public void onDetach() {
        super.onDetach();
        //onces the fragment is closed these variables are updated and stored
        prefEditor.putString(getArguments().getString("Title") + "Date", this.dateButton.getText() + "");
        prefEditor.putString(getArguments().getString("Title") + "Time", this.timeButton.getText() + "");
        prefEditor.putBoolean(getArguments().getString("Title") + "Toggle", but.isChecked());
        prefEditor.apply();
    }
}
