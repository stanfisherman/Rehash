package ateam.rehashprot2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Warrick
 */
public class CalendarPage extends AppCompatActivity
        implements WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;
    private List<WeekViewEvent> events;
    private List<String> eventId;

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        setTitle("ReHash: Calendar");

        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CalendarPage.this, AddEventActivity.class);
                startActivity(i);
            }
        });

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());


        events = new ArrayList<>();
        eventId = new ArrayList<>();


        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        mWeekView.setShowNowLine(true);

        mWeekView.goToHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));

        DatabaseReference calendarRef = database.getReference(mAuth.getCurrentUser().getUid()).child("Schedule");

        calendarRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        // Get calendar value
                        pd = new ProgressDialog(CalendarPage.this);
                        pd.setMessage("updating calendar");
                        pd.show();
                        events.clear();
                        int i = 1;
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                            eventId.add(postSnapshot.getKey());

                            String title = postSnapshot.child("title").getValue().toString();

                            Calendar startCal = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
                            try {
                                startCal.setTime(sdf.parse(postSnapshot.child("startDate").getValue().toString() + " " + postSnapshot.child("startTime").getValue().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Calendar endCal = Calendar.getInstance();
                            try {
                                endCal.setTime(sdf.parse(postSnapshot.child("endDate").getValue().toString() + " " + postSnapshot.child("endTime").getValue().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            WeekViewEvent weekViewEvent = new WeekViewEvent(i, title, startCal, endCal);
                            events.add(weekViewEvent);
                            i++;
                        }
                        mWeekView.notifyDatasetChanged();
                        if (pd.isShowing()) {
                            pd.dismiss();
                            pd = null;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_today:
                mWeekView.goToToday();
                return true;
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);
                    Calendar c = Calendar.getInstance();
                    mWeekView.goToHour(c.get(Calendar.HOUR_OF_DAY));

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);
                    Calendar c = Calendar.getInstance();
                    mWeekView.goToHour(c.get(Calendar.HOUR_OF_DAY));

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 9, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 9, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);
                    Calendar c = Calendar.getInstance();
                    mWeekView.goToHour(c.get(Calendar.HOUR_OF_DAY));

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Intent intent = new Intent(CalendarPage.this, ViewEventActivity.class);
        intent.putExtra("id", eventId.get((int) event.getId() - 1));
        startActivity(intent);
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        ArrayList<WeekViewEvent> change = new ArrayList<>();
        for (WeekViewEvent ev : events) {
            if (checkDate(ev, newMonth, newYear)) {
                change.add(ev);
            }
        }
        return change;
    }

    private boolean checkDate(WeekViewEvent ev, int newMonth, int newYear) {
        return (ev.getStartTime().get(Calendar.MONTH) == newMonth - 1) && ev.getStartTime().get(Calendar.YEAR) == (newYear) &&
                (ev.getEndTime().get(Calendar.MONTH) == newMonth - 1) && ev.getEndTime().get(Calendar.YEAR) == (newYear);
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {

    }
}
