package vorlesung.hslu.wgapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ActivitySignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText inputName;
    EditText inputEmail;
    EditText inputPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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

        TextView login = (TextView) this.findViewById(R.id.signup_link_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(ActivitySignUp.this, ActivityLogin.class );
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

    private void signUp(final String name, final String email, final String password) {
        if (!validateForm(name, email, password)) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            Person person = new Person(user.getDisplayName(), user.getEmail());
                            Wohngemeinschaft wg = Wohngemeinschaft.getInstance();
                            wg.addMitbewohner(person);

                            //Nicht in MainAcitivity gehen sondern weiter zum Screen um WG einzugeben, oder neue WG zu gründen
                            Intent mainActivity = new Intent(ActivitySignUp.this, ActivityMain.class );
                            finish();
                            startActivity(mainActivity);
                        } else {
                            Toast.makeText(ActivitySignUp.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private boolean validateForm(String name, String email, String password) {
        boolean valid = true;

        if (TextUtils.isEmpty(name)){
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
}
