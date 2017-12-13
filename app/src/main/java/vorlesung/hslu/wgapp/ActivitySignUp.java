package vorlesung.hslu.wgapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ActivitySignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText inputName;
    EditText inputEmail;
    EditText inputPassword;
    static Person person;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    public Wohngemeinschaft wg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_createaccount);

        //Setting up Database connection
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("wg");

        //Views and onClickListener!
        Button signUpEmail = (Button) this.findViewById(R.id.signup_btn_signup);
        inputName = (EditText) this.findViewById(R.id.signup_input_name);
        inputEmail = (EditText) this.findViewById(R.id.signup_input_email);
        inputPassword = (EditText) this.findViewById(R.id.signup_input_password);

        signUpEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                signUp(name, email, password);
            }
        });


        //Nav to Activity Login
        TextView login = (TextView) this.findViewById(R.id.signup_link_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(ActivitySignUp.this, ActivityLogin.class);
                finish();
                startActivity(login);
            }
        });


        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    //protected for Testing purpose
    protected void signUp(final String name, final String email, final String password) {
        if (!validateForm(name, email, password)) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            person = new Person(inputName.getText().toString(), user.getEmail());
                            person.setId(mAuth.getCurrentUser().getUid().toString());
                            screen_enter_wg();

                        } else {
                            Toast.makeText(ActivitySignUp.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm(String name, String email, String password) {
        boolean valid = true;

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(ActivitySignUp.this, "Please check the name", Toast.LENGTH_LONG).show();
            valid = false;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(ActivitySignUp.this, "Please check the email adress", Toast.LENGTH_LONG).show();
            valid = false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(ActivitySignUp.this, "Please check the password", Toast.LENGTH_LONG).show();
            valid = false;
        }
        return valid;
    }

    private void screen_enter_wg() {
        setContentView(R.layout.activity_signup_enter_wg);

        Button enter_wg = (Button) this.findViewById(R.id.signup_btn_wg_enter);
        final EditText inputCode = (EditText) this.findViewById(R.id.signup_input_wgcode);
        enter_wg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String code = inputCode.getText().toString();

                //lesen von daten
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> snapshot = dataSnapshot.getChildren();

                        for (DataSnapshot singlesnap : snapshot) {
                            if (singlesnap.getKey().equals(code)) {

                                wg = singlesnap.getValue(Wohngemeinschaft.class);
                                wg.addMitbewohner(person);
                                mAuth = FirebaseAuth.getInstance();
                                Map<String, Object> postValues = person.toMap();
                                Map<String, Object> childUpdates = new HashMap<>();
                                childUpdates.put("/mitbewohner/" + mAuth.getCurrentUser().getUid() + "/", postValues);
                                mDatabase.child(code).updateChildren(childUpdates);
                                Wohngemeinschaft.setInstance(wg);
                                start_next_activity();
                                return;
                            }
                        }
                        if (wg == null) {
                            Toast.makeText(ActivitySignUp.this, "Das ist keine Butze.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });

        TextView new_wg = (TextView) this.findViewById(R.id.signup_link_new_wg);
        new_wg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screen_new_wg();
            }
        });
    }

    private void screen_new_wg() {
        setContentView(R.layout.activity_signup_create_wg);

        Button create_wg = (Button) this.findViewById(R.id.signup_btn_new_wg);
        final EditText inputWGname = (EditText) this.findViewById(R.id.signup_wg_input_wgname);

        create_wg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputWGname.getText().toString();
               // Wohngemeinschaft wg = Wohngemeinschaft.getInstance();
              //  wg.setName(name);
                //NEUE WG IN FIREBASE SPEICHERN
                mDatabase.child(name);
                //person hinzufügen und ebenfalls speichern
               // wg.addMitbewohner(person);
                Map<String, Object> postValues = person.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/mitbewohner/" + mAuth.getCurrentUser().getUid() + "/", postValues);
                mDatabase.child(inputWGname.getText().toString()).updateChildren(childUpdates);
                mDatabase.child(name).child("name").setValue(name);
                Wohngemeinschaft.getInstance();

                start_next_activity();
            }
        });

        // CODE FÜR FREUNDE MUSS GENRIERT WERDEN  --> EINMALIG PRO WG? SPÄTER AUCH IN SETTINGS O.Ä. AUFRUFBAR
    }


    private void start_next_activity() {
        Intent mainActivity = new Intent(ActivitySignUp.this, ActivityMain.class);
        finish();
        startActivity(mainActivity);
    }
}
