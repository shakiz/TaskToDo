package com.tasktodo.com.tasktodo.DatabaseConnector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tasktodo.com.tasktodo.Models.AssignmentModel;
import com.tasktodo.com.tasktodo.Models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TheHumbleAssignment.db";

    // User table name
    private static final String TABLE_USER = "user";

    //Assignment table name
    private static final String TABLE_ASSIGNMENT = "assignment";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    //Assignment lists table columns
    private static final String COLUMN_ASSIGNMENT_ID = "assignment_id";
    private static final String COLUMN_ASSIGNMENT_NAME = "assignment_name";
    private static final String COLUMN_COURSE_NAME = "course_name";
    private static final String COLUMN_ASSIGNMENT_NUMBER = "assignment_number";
    private static final String COLUMN_ASSIGNMENT_DATE = "assignment_date";

    //Creatint table sql query for user table
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    //Creating query for assignment table query
    private String CREATE_ASSIGNMENT_TABLE = "CREATE TABLE " + TABLE_ASSIGNMENT + "("
            + COLUMN_ASSIGNMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ASSIGNMENT_NAME + " TEXT,"+ COLUMN_COURSE_NAME + " TEXT,"
            + COLUMN_ASSIGNMENT_NUMBER + " INTEGER," + COLUMN_ASSIGNMENT_DATE + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_ASSIGNMENT_TABLE = "DROP TABLE IF EXISTS " + TABLE_ASSIGNMENT;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_ASSIGNMENT_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_ASSIGNMENT_TABLE);
        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create assignment record
     *
     * @param assignment
     */
    public void addAssignment(AssignmentModel assignment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ASSIGNMENT_NAME, assignment.getAssignmentName());
        values.put(COLUMN_ASSIGNMENT_NUMBER, assignment.getAssignmentNumber());
        values.put(COLUMN_COURSE_NAME,assignment.getCourseName());
        values.put(COLUMN_ASSIGNMENT_DATE, assignment.getAssignmentDueDate());

        // Inserting Row to the assignment table
        db.insert(TABLE_ASSIGNMENT, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of assignment records
     *
     * @return assignmentList
     */
    public List<AssignmentModel> getAllAssignment() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_ASSIGNMENT_ID,
                COLUMN_ASSIGNMENT_NAME,
                COLUMN_ASSIGNMENT_NUMBER,
                COLUMN_COURSE_NAME,
                COLUMN_ASSIGNMENT_DATE
        };
        // sorting orders
        String sortOrder =
                COLUMN_ASSIGNMENT_ID + " ASC";
        List<AssignmentModel> assignmentList = new ArrayList<AssignmentModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_ASSIGNMENT, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AssignmentModel assignment = new AssignmentModel();
                assignment.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ASSIGNMENT_ID))));
                assignment.setAssignmentName(cursor.getString(cursor.getColumnIndex(COLUMN_ASSIGNMENT_NAME)));
                assignment.setCourseName(cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_NAME)));
                assignment.setAssignmentNumber(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ASSIGNMENT_NUMBER))));
                assignment.setAssignmentDueDate(cursor.getString(cursor.getColumnIndex(COLUMN_ASSIGNMENT_DATE)));
                // Adding user record to list
                assignmentList.add(assignment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return assignmentList;
    }


    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row to the user table
        db.insert(TABLE_USER, null, values);
        db.close();
    }


    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<UserModel> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                //COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<UserModel> userList = new ArrayList<UserModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserModel user = new UserModel();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                //user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    /**
     * This method to check user exist or not
     *
     * @param username
     * @param password
     * @return true/false
     */
    public boolean checkUser(String username, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_NAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {username, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

}
