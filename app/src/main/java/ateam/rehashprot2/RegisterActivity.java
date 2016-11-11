package ateam.rehashprot2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Warrick
 */
public class RegisterActivity extends AppCompatActivity {
    private ProgressDialog pd ;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        pd = new ProgressDialog(RegisterActivity.this);
        pd.setMessage("registering");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("user", "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d("user", "onAuthStateChanged:signed_out");
                }
            }
        };

        Button registerButton = (Button) findViewById(R.id.sign_up_register_button);

        final EditText usernameET = (EditText) findViewById(R.id.sign_up_page_username);
        final EditText passwordET = (EditText) findViewById(R.id.sign_up_page_password);
        final EditText displayNameET = (EditText) findViewById(R.id.sign_up_page_display_name);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                final String displayName = displayNameET.getText().toString();
                register(username, password,displayName);
            }
        });
    }

    private void register(final String username, String password, final String displayName) {
        pd.show();
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        System.out.println("createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Register failed.",
                                    Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        } else {
                            FirebaseDatabase fd = FirebaseDatabase.getInstance();
                            DatabaseReference dr = fd.getReference();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Map<String,String> map = new HashMap<String, String>();
                            map.put("email",username);
                            map.put("display",displayName);
                            dr.child(user.getUid()).setValue(map);
                            Intent intent = new Intent(RegisterActivity.this, LandingPage.class);
                            startActivity(intent);
                            pd.dismiss();
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
