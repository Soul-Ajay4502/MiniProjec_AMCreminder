package com.example.amcremind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private Button Login;
    private TextView passwordreset;
    private ProgressBar progressBar;

    private ProgressDialog processDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Email = (EditText) findViewById(R.id.emailSignIn);
        Password = (EditText) findViewById(R.id.password);
        Login = (Button) findViewById(R.id.Login);

        passwordreset = findViewById(R.id.forgotpassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbars);
        progressBar.setVisibility(View.GONE);

        processDialog = new ProgressDialog(this);



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Email.getText().toString(), Password.getText().toString());
            }

        });

        passwordreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpasword();
            }
        });
    }

    public void resetpasword(){

        Toast.makeText(MainActivity.this, "THIS FEATURE WILL BE ADDED", Toast.LENGTH_SHORT).show();

        progressBar.setVisibility(View.GONE);

    }
    public void validate(String userEmail,String userPassword){

        processDialog.setMessage("................Please Wait.............");
        processDialog.show();


        if(userEmail.equals("admin")&&userPassword.equals("password")){
            processDialog.dismiss();
            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,dashboardActivity.class));
        }
        else{
            Toast.makeText(MainActivity.this,"Login Failed check Details Entered", Toast.LENGTH_SHORT).show();
            processDialog.dismiss();
        }
    }


}
