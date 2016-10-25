package ateam.rehashprot2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import dalvik.system.DexClassLoader;


/**
 * Created by hooth_l8hejea on 6/29/2016.
 */

public class ListViewFragment extends Fragment {
    private ArrayList<String> medWords = new ArrayList<String>();
    private ArrayList<String> actWords = new ArrayList<String>();
    private String[] defMeds = {"antibiotic", "cure", "drug", "medicine", "pill", "prescription", "remedy", "balm", "salve", "tablet", "serum", "lotion", "ointment", "sedative", "serum", "medication", "meds"};
    private String[] defAct = {"run", "walk", "exercise", "stroll", "steps"};
    private String[] states;

    private ArrayList<String> statesArray = new ArrayList();

    //Module ID determines the order of appearance of modules
    private boolean isMedModOn = false;
    private boolean isActModOn = false;

    private ArrayList<String> modules = new ArrayList();
    private ArrayList<String> description = new ArrayList();
    private ArrayList<Integer> icon_id = new ArrayList();

    private boolean isState = false;
    private Class<Object> defaultSMStatemachine;
    private Class<Object> iDefaultSMStatemachine;
    private Class<Object> iStatemachine;
    private Object defaultInstance;
    private Object iDefaultInstance;
    private Object iStatemachineInstance;
    private Boolean status = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.rehash_fragment_layout, parent, false);

    }

    // This event is triggered soon after onCreateView()
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Set up handling of statechart download and set up
        new SetUpStatechart().execute();

        CustomAdapter adapter = new CustomAdapter(this.getActivity(), R.layout.module_row, modules, description, icon_id);
        final ListView lpListView = (ListView) view.findViewById(R.id.list_categories);
        lpListView.setAdapter(adapter);
        lpListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DetailsFragment df = new DetailsFragment();
                int pos = lpListView.getPositionForView(view);
                Bundle args = new Bundle();
                // args.putInt("Position", pos);
                args.putString("Title", modules.get(pos));
                args.putString("Description", description.get(pos));
                args.putInt("Icon", icon_id.get(pos));
                df.setArguments(args);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame_layout, df);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    /*
        Set up keywords to be searched (This is a method just in case you want to add your own)
     */
    public void setupKeywords() {
        for (int x = 0; x < defMeds.length; x++)
            medWords.add(defMeds[x]);
        for (int y = 0; y < defAct.length; y++)
            actWords.add(defAct[y]);
    }

    /*
    * Grab the states and put them into arrays in order of state appearance
    * */
    public void createStateStringArray() {

        for (int x = 0; x < states.length - 1; x++) {
            String stateToEdit = "";
            StringReader sr = new StringReader(states[x]);
            try {
                // read stateToEdit
                for (int i = 0; i < states[x].length(); i++) {
                    char c = (char) sr.read();
                    if (i > 11)
                        if (c == '_')
                            stateToEdit += " ";
                        else
                            stateToEdit += c;
                }
                // close the stream
                sr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            statesArray.add(x, stateToEdit);
        }
    }

    /*
        read States is the method responsible on grabbing the states and running the algorithm to check the keywords
        and call the appropriate module to handle them
     */
    public void readStates(int x) {
        String state = "";
        while (x < states.length) {
            state = statesArray.get(x);
            String[] words = state.split("\\s+");

            for (int i = 0; i < words.length; i++) {
                for (String temp : medWords) {
                    if (words[i].equals(temp) && !isMedModOn)
                        callMedicineModule();
                }
                for (String temp : actWords) {
                    if (words[i].equals(temp) && !isActModOn)
                        callExerciseModule();
                }
            }
            x++;
        }
    }

    //MODULES HERE
    //Medicine Module
    public void callMedicineModule() {
        modules.add("Test if it works");
        description.add("Don't forget to take your medicine");
        icon_id.add(R.drawable.pill);
        isMedModOn = true;
    }

    public void callExerciseModule() {
        modules.add("Exercise at this time");
        description.add("Go for a walk at this time");
        icon_id.add(R.drawable.exercise);
        isActModOn = true;
    }

    public Boolean getStatechart() {
        //gets firebase instance and sets up the firebase urls for download
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://test-d93ae.appspot.com");
        StorageReference testRef = storageRef.child("statechart.apk");

        //create a temporary file object to hold the downloaded apk
        final File localFile = new File(getActivity().getApplicationInfo().dataDir + "/statechart.apk");

        //if file doesn't exist create the file
        try {
            if (localFile.createNewFile()) {
                //starts the download
                testRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        status = true;
                        Log.i("getStatechart()", "File download started");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        localFile.delete();
                        status = false;
                        Log.e("getStatechart()", "File download failed");
                    }
                });
                return status;
            }
            else {
                status = true;
                Log.i("getStatechart()", "File already exists");
                return status;
            }
        } catch (IOException e) {
            Log.e("getStatechart()", "IOException file download failed");
            e.printStackTrace();
        }
        return status;
    }

    public void instanciateStatechart() {
        //path of the statechart
        final File localFile = new File(getActivity().getApplicationInfo().dataDir + "/statechart.apk");

        //initialise the class loader, for dynamic class loading
        DexClassLoader cl = new DexClassLoader(localFile.toString(), getActivity().getApplicationInfo().dataDir, null, getActivity().getClass().getClassLoader());
        if (localFile.exists()) {
            //loads the statechart classes
            try {
                defaultSMStatemachine = (Class<Object>) cl.loadClass("ateam.statechart.DefaultSMStatemachine");
            } catch (ClassNotFoundException e) {
                Log.e("InstanciateStatechart()", "Class Not Found");
                e.printStackTrace();
            }
        }
        //gets the enum class of the statemachine
        Class[] statesInnerClass = defaultSMStatemachine.getClasses();
        for (Class s: statesInnerClass) {
            //gets the enum constants in the enum inner class of statemachine
            Object[] constants = s.getEnumConstants();
            //creates a new array of constants[] length to convert to String[]
            states = new String[constants.length];
            //store constant[] values as string in states[]
            for (int i = 0; i < constants.length - 1; ++i) {
                states[i] =  constants[i].toString();
                Log.e("InstanciateStatechart()", "" + i);
                Log.e("InstanciateStatechart()", states[i]);
            }
        }
    }

    class SetUpStatechart extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            getStatechart();
            //waits for download to complete before proceeding
            while (status != true) {
            }
            Log.e("download", "doInBackground complete");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //instanciates statechart to get states enum and sets up the state array
            instanciateStatechart();
            // Setup
            setupKeywords();
            // Setup any handles to view objects here
            createStateStringArray();
            // Read the states and look for the keywords and decide which modules are to be accessed
            readStates(states.length);
            Log.e("download", "onPostExecute complete");
        }

    }

}