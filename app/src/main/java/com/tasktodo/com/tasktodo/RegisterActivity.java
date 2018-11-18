package com.tasktodo.com.tasktodo;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tasktodo.com.tasktodo.DatabaseConnector.DatabaseHelper;
import com.tasktodo.com.tasktodo.Models.UserModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText username,password,email,confirmPassword;
    private Button signUp;
    private DatabaseHelper databaseHelper;
    private UserModel user;
    private static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor=null;
    public static final String MyPREFERENCES = "TASKPREFERENCE" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //This method will be used to initialize all the attributes
        init();

        //Setting on click listener on signup button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    //This method will be used to initialize all the attributes
    public void init(){
        username=findViewById(R.id.usernameXML);
        password=findViewById(R.id.passwordXML);
        confirmPassword=findViewById(R.id.confirmpasswordXML);
        email=findViewById(R.id.emailXML);
        signUp=findViewById(R.id.signupButtonXML);
        databaseHelper=new DatabaseHelper(this);
        sharedPreferences= getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        user=new UserModel();
    }

    //This method will be used for validating inputs
    public void validate(){
        if (username.getText().toString().isEmpty()){
            username.setError("Username can not be empty.");
        }
        if (password.getText().toString().isEmpty()){
            password.setError("Password can not be empty.");
        }
        if (confirmPassword.getText().toString().isEmpty()){
            confirmPassword.setError("Password can not be empty.");
        }
        if (email.getText().toString().isEmpty()){
            email.setError("Email can not be empty.");
        }
        if (!(password.getText().toString().equals((confirmPassword.getText().toString())))){
            confirmPassword.setError("Password do not match.");
            Log.d("Pass : ","Confirm : "+confirmPassword.getText().toString()+"\nPassword : "+password.getText().toString());
        }

        if ((!username.getText().toString().equals("")) && (!password.getText().toString().equals("")) && (!confirmPassword.getText().toString().equals("")) && (!email.getText().toString().equals(""))){
            registerForNew();
        }

    }

    //This method will create a new user into sqlite database
    public void registerForNew(){
        if (!databaseHelper.checkUser(email.getText().toString().trim())){

            user.setName(username.getText().toString().trim());
            user.setEmail(email.getText().toString().trim());
            user.setPassword(password.getText().toString().trim());

            databaseHelper.addUser(user);
            //passing username or taking usernme in order to make session
            editor.putString("USERNAME",username.getText().toString());
            editor.commit();
            // Snack Bar to show success message that record saved successfully
            Toast.makeText(getApplicationContext(),"User registered successfully.",Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
        }
        else{
            Toast.makeText(getApplicationContext(),"Email already exists.",Toast.LENGTH_LONG).show();
        }
    }
}
