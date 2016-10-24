package ateam.rehashprot2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


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

        ic.setImageResource(detIcon);
        tit.setText(detTitle);
        des.setText(detDesc);

    }
}
