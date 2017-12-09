package vorlesung.hslu.wgapp;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static vorlesung.hslu.wgapp.ActivitySignUp.person;

public class ActivityLogin extends AppCompatActivity {

    private
    EditText inputEmail;
    EditText inputPassword;
    public Wohngemeinschaft wg ;
    public FirebaseDatabase database;
    public DatabaseReference mDatabase;
    public FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("wg");

        //Views and onClickListener!
        Button signInEmail = (Button) this.findViewById(R.id.login_btn_login);
        inputEmail = (EditText) this.findViewById(R.id.login_input_email);
        inputPassword = (EditText) this.findViewById(R.id.login_input_password);

        signInEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                signIn(email, password);
            }
        });

        TextView signUp = (TextView) this.findViewById(R.id.login_link_signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(ActivityLogin.this, ActivitySignUp.class );
                finish();
                startActivity(signUp);
            }
        });
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void signIn(String email, String password) {
        if (!validateForm(email, password)) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent mainActivity = new Intent(ActivityLogin.this, ActivityMain.class );
                            final String uID = mAuth.getCurrentUser().getUid();
                             mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Iterable<DataSnapshot> snapshot = dataSnapshot.getChildren();
                                    for (DataSnapshot singlesnap : snapshot) {
                                        if (singlesnap.child("mitbewohner").hasChild(uID)) {
                                            wg  = singlesnap.getValue(Wohngemeinschaft.class);
                                            Wohngemeinschaft.setInstance(wg);
                                        }
                                    }
                                }
                                public void onCancelled(DatabaseError databaseError) {}
                            });
                            finish();
                            startActivity(mainActivity);
                        } else {
                            Toast.makeText(ActivityLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private boolean validateForm(String email, String password) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(ActivityLogin.this, "Please check the email adress", Toast.LENGTH_LONG).show();
            valid = false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(ActivityLogin.this, "Please check the password", Toast.LENGTH_LONG).show();
            valid = false;
        }
        return valid;
    }
}
