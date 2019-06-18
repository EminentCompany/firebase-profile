package com.kamel.eminent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText e3_name,e4_email,e5_password;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        e3_name=(EditText)findViewById(R.id.editText4);
        e4_email=(EditText)findViewById(R.id.editText5);
        e5_password=(EditText)findViewById(R.id.editText6);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
    }

    public void SignUpUser(View view) {
        progressDialog.setMessage("signing up... please wait");
        progressDialog.show();

        final String name=e3_name.getText().toString();
        String email=e4_email.getText().toString();
        String password=e5_password.getText().toString();

        if (name.equals("")||email.equals("")||password.equals("")){
            Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_LONG).show();
        }
        else
        {
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressDialog.hide();
                                Toast.makeText(getApplicationContext(),"User successfully signed up",Toast.LENGTH_LONG).show();

                                databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
                                Users users_object=new Users(e3_name.getText().toString(),e4_email.getText().toString(),e5_password.getText().toString());
                                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

                                databaseReference.child(firebaseUser.getUid()).setValue(users_object)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(getApplicationContext(),"User data saved",Toast.LENGTH_LONG).show();
                                                    Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                                                    startActivity(intent);
                                                }
                                                else {
                                                    Toast.makeText(getApplicationContext(),"User could not be signed up",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                            }
                            else {
                                progressDialog.hide();
                                Toast.makeText(getApplicationContext(),"User could not be signed up",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
