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

public class ActivityLogin extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private FirebaseAuth mAuth;
    Wohngemeinschaft wg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                Intent signUp = new Intent(ActivityLogin.this, ActivitySignUp.class);
                finish();
                startActivity(signUp);
            }
        });
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
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
                            wg = Wohngemeinschaft.getInstance();
                            start_main_activity();
                        } else {

                            Toast.makeText(ActivityLogin.this, getString(R.string.ToastAuthentication),
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

    private void start_main_activity() {
        Intent mainActivity = new Intent(ActivityLogin.this, ActivityMain.class);
        finish();
        startActivity(mainActivity);

    }
}
