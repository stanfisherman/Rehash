package ateam.rehashprot2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by hooth_l8hejea on 9/29/2016.
 */

public class CounterFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.counter_fragment, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        TextView textView = (TextView) view.findViewById(R.id.textView1);
        textView.setText("I have been changed from the default value");
        Button button = (Button) view.findViewById(R.id.button1);
        button.setText("Button is now different");
    }
}
