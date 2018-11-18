package com.tasktodo.com.tasktodo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tasktodo.com.tasktodo.DatabaseConnector.DatabaseHelper;
import com.tasktodo.com.tasktodo.Models.AssignmentModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private RelativeLayout mainLayout;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static String usernameSession="";
    private Button selectdateButton,submitButton;
    private TextView dateTextView,username;
    private EditText assignmentName,courseName,assignmentNumber;
    private int mYear, mMonth, mDay;
    private int dueYear,dueMonth;
    private String dueDayName,assignmentNameStr,assignmentNumberStr;
    public static String GET_ACTIVITY_CODE;
    public static int CODE,courseInt;
    private DatabaseHelper databaseHelper;
    private AssignmentModel assignmentModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //This method will be used to initialize the attributes of xml
        init();
        username.setText("User : "+usernameSession);

        selectdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This method will be used to select date and set the date into a textview
                selectDateMethod();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This method will be used to check the inputs
                checkInputs();
            }
        });
    }
    //This method will be used to select date and set the date into a textview
    public void selectDateMethod(){
        // Getting the current Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        //Storing the month and year value into this two variables
                        dueYear=year;
                        dueMonth=monthOfYear;
                        //This will be used to get the day name of the select date , like whether it is sundya or monday etc.
                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                        Date date = new Date(year, monthOfYear, dayOfMonth-1);
                        dueDayName = simpledateformat.format(date);
                        dateTextView.setText("Date : "+dueDayName + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        try{
            datePickerDialog.show();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GET_ACTIVITY_CODE="HOME";
    }

    //This method will be used to sinitialize the attributes of xml
    public void init(){
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        usernameSession=sharedPreferences.getString("USERNAME","");
        //Toast.makeText(getApplicationContext(),"Username : "+usernameSession,Toast.LENGTH_SHORT).show();
        selectdateButton=findViewById(R.id.dateButtonXML);
        dateTextView=findViewById(R.id.dateTextViewXML);
        assignmentName=findViewById(R.id.assignmentNameXML);
        courseName=findViewById(R.id.courseNameOrCodeXML);
        assignmentNumber=findViewById(R.id.assignmentNumberXML);
        submitButton=findViewById(R.id.submitButtonXML);
        username=findViewById(R.id.usernameXMLHOME);
        mainLayout=findViewById(R.id.relativeLayoutForHomeContent);
        databaseHelper=new DatabaseHelper(this);
        assignmentModel=new AssignmentModel();
    }

    //This method will be used to check the inputs
    public void checkInputs(){
        assignmentNameStr=assignmentName.getText().toString();
        assignmentNumberStr=assignmentNumber.getText().toString();

        if (assignmentName.getText().toString().isEmpty()){
            assignmentName.setError("Assignment name can not be empty.");
        }
        if (courseName.getText().toString().isEmpty()){
            courseName.setError("Course nam can not be empty.");
        }
        if (assignmentNumber.getText().toString().isEmpty()){
            assignmentNumber.setError("Assignment number can not be empty");
        }

        if (dateTextView.getText().toString().equals("Date:")){
            Toast.makeText(getApplicationContext(),"Please select due date.",Toast.LENGTH_LONG).show();
        }
        if ((!assignmentName.getText().toString().isEmpty()) && (!courseName.getText().toString().isEmpty()) &&
            (!assignmentNumber.getText().toString().isEmpty()) && (!dateTextView.getText().toString().equals(("Date:")))){
            assignmentModel.setAssignmentName(assignmentNameStr);
            assignmentModel.setAssignmentNumber(Integer.parseInt(assignmentNumberStr));
            assignmentModel.setCourseName(courseName.getText().toString());
            assignmentModel.setAssignmentDueDate(dateTextView.getText().toString());
            databaseHelper.addAssignment(assignmentModel);
            Toast.makeText(getApplicationContext(),"Name :"+assignmentNameStr+"\nNumber : "+assignmentNumberStr+"\nCourse name : "+courseName.getText().toString()+
                            "\nDate : "+dateTextView.getText().toString(),Toast.LENGTH_LONG).show();
            Snackbar.make(mainLayout,"Assignment added to the list.",Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.itemLogout:
                editor.clear();
                editor.commit();
                usernameSession="";
                LoginActivity.IS_LOGIN=false;
                Toast.makeText(getApplicationContext(),"Logged out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                break;
            case R.id.itemShowList:
                startActivity(new Intent(HomeActivity.this,ItemListActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
