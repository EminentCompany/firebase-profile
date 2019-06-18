package com.kamel.eminent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            Intent intent=new Intent(Main1Activity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            setContentView(R.layout.activity_main1);
        }
    }
    public void open_SignIn(View view)
    {
        Intent i=new Intent(Main1Activity.this,SignInActivity.class);
        startActivity(i);
    }

    public void open_SignUp(View view)
    {
        Intent i=new Intent(Main1Activity.this,SignUpActivity.class);
        startActivity(i);
    }
}
