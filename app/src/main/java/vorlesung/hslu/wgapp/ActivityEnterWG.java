package vorlesung.hslu.wgapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ActivityEnterWG extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private Person currentUser;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private Wohngemeinschaft wg;
    private static boolean exists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screen_enter_wg();

        //Setting up Database connection
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("wg");
        mAuth = FirebaseAuth.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("personName");
        currentUser = new Person(name, firebaseUser.getEmail(), firebaseUser.getUid());

    }


    private void screen_enter_wg() {
        setContentView(R.layout.activity_signup_enter_wg);

        Button enter_wg = (Button) this.findViewById(R.id.signup_btn_wg_enter);
        final EditText inputCode = (EditText) this.findViewById(R.id.signup_input_wgcode);
        enter_wg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String code = inputCode.getText().toString();

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> snapshot = dataSnapshot.getChildren();

                        for (DataSnapshot singlesnap : snapshot) {
                            if (singlesnap.getKey().equals(code)) {
                                wg = singlesnap.getValue(Wohngemeinschaft.class);
                                wg.addMitbewohner(currentUser);
                                mAuth = FirebaseAuth.getInstance();
                                Map<String, Object> postValues = currentUser.toMap();
                                Map<String, Object> childUpdates = new HashMap<>();
                                childUpdates.put("/mitbewohner/" + mAuth.getCurrentUser().getUid() + "/", postValues);
                                mDatabase.child(code).updateChildren(childUpdates);
                                Wohngemeinschaft.setInstance(wg);
                                start_next_activity();
                                return;
                            }
                        }
                        if (wg == null) {
                            Toast.makeText(ActivityEnterWG.this, "Das ist keine Butze.", Toast.LENGTH_SHORT).show();
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
        final Context context = this.getApplicationContext();

        create_wg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = inputWGname.getText().toString();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Checken ob WG Name nicht bereits belegt ist
                        Iterable<DataSnapshot> wohniter = dataSnapshot.getChildren();
                        for (DataSnapshot snap : wohniter) {
                            String currentWG = snap.getKey().toString();
                            //Check if WG exists
                            if (currentWG.equals(name)) {
                                Toast.makeText(context, "Diesen WG Namen gibt es bereits.", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }

                        Wohngemeinschaft.getInstance();
                        mDatabase.child(name);
                        Map<String, Object> postValues = currentUser.toMap();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/mitbewohner/" + mAuth.getCurrentUser().getUid() + "/", postValues);
                        mDatabase.child(inputWGname.getText().toString()).updateChildren(childUpdates);
                        mDatabase.child(name).child("name").setValue(name);
                        Wohngemeinschaft.getInstance();

                        start_next_activity();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }

                });
            }

        });
    }


    private void start_next_activity() {
        Intent mainActivity = new Intent(ActivityEnterWG.this, ActivityMain.class);
        finish();
        startActivity(mainActivity);
    }
}

