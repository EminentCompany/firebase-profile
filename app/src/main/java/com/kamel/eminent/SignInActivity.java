package com.kamel.eminent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    EditText e1_email,e2_password;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    Button btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        e1_email=(EditText)findViewById(R.id.editText2);
        e2_password=(EditText)findViewById(R.id.editText3);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        btnReset = (Button) findViewById(R.id.btn_reset_password);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, ResetPasswordActivity.class));
            }
        });
    }
    public void SignInUser(View view)
    {
        progressDialog.setMessage("signing in... please wait");
        progressDialog.show();

        if (e1_email.getText().toString().equals("")&& e2_password.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_LONG).show();
        }
        else
        {
            firebaseAuth.signInWithEmailAndPassword(e1_email.getText().toString(),e2_password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressDialog.hide();
                                Toast.makeText(getApplicationContext(),"User successfully signed in",Toast.LENGTH_LONG).show();
                                Intent i=new Intent(SignInActivity.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else {
                                progressDialog.hide();
                                Toast.makeText(getApplicationContext(),"User not found",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
    }
}
