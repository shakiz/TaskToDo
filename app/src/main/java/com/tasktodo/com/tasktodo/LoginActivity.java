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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.tasktodo.com.tasktodo.DatabaseConnector.DatabaseHelper;
import com.tasktodo.com.tasktodo.Models.UserModel;

public class LoginActivity extends AppCompatActivity {

    private EditText username,password;
    private CheckBox rememberMe;
    private Button forgetPassword,signin,createNewAccount;
    private ProgressDialog progressDialog;
    private DatabaseHelper databaseHelper;
    private UserModel user;
    private static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor=null;
    public static final String MyPREFERENCES = "TASKPREFERENCE" ;
    public static boolean IS_LOGIN = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //this method initialize all the attributes with XML
        init();

        //This will manage the session that whenever a user is logged in into the system the value will change based on the scenario
        if(IS_LOGIN){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This method will be used to check the inputs
                inputValidation();
            }
        });
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
    /*public boolean isLoggedIn(){
        return true;
    }*/

    //This method initialize all the attributes with XML
    public void init(){
        username=findViewById(R.id.usernameXML);
        password=findViewById(R.id.passwordXML);
        rememberMe=findViewById(R.id.checkboxXML);
        forgetPassword=findViewById(R.id.forgetPasswordXML);
        signin=findViewById(R.id.signinButtonXML);
        createNewAccount=findViewById(R.id.createNewAccountXML);
        databaseHelper=new DatabaseHelper(this);
        user=new UserModel();
        sharedPreferences= getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        progressDialog=new ProgressDialog(this,R.style.AppTheme_Dark_Dialog);
    }

    //This method will be used to check the inputs
    public void inputValidation(){
        if (username.getText().toString().isEmpty()){
            username.setError("Username Can Not Be Empty.");
        }
        if (password.getText().toString().isEmpty()){
            password.setError("Password Can Not Be Emprty.");
        }
        if (rememberMe.isChecked()){
            //Make session
        }
        if ((!username.getText().toString().isEmpty()) && (!password.getText().toString().isEmpty())){
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Please wait , Logging in..");
            progressDialog.show();
            //This method will be used to login into the system
            signInIntoTheSystem();
        }
    }
    //This method will be used to login into the system
    public void signInIntoTheSystem(){
        if (databaseHelper.checkUser(username.getText().toString()
                , password.getText().toString())) {
            IS_LOGIN=true;
            progressDialog.dismiss();
            //passing username or taking usernme in order to make session
            editor.putString("USERNAME",username.getText().toString());
            editor.commit();
            //isLoggedIn();
            Intent accountsIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(accountsIntent);

        } else {
            // toast to show success message that record is wrong
            Toast.makeText(getApplicationContext(),"Username and password do not match.",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishActivity(0);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}
