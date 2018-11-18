package com.tasktodo.com.tasktodo;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.tasktodo.com.tasktodo.Adapter.AssignmentListAdapter;
import com.tasktodo.com.tasktodo.DatabaseConnector.DatabaseHelper;
import com.tasktodo.com.tasktodo.Models.AssignmentModel;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView assignmentListView;
    private DatabaseHelper databaseHelper;
    private AssignmentListAdapter assignmentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        //This method will be used to initialize all the attributes with XML
        init();
        //calling the  method populateListview() in order to populate the listview with user data
        populateListView();
        //setting whenever a user swips what actions will happen
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        populateListView();
                        assignmentListAdapter.notifyDataSetChanged();
                        assignmentListView.smoothScrollToPosition(0);
                        Toast.makeText(ItemListActivity.this,"New record loaded.",Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
    }


    //This method will be used to initialize all the attributes with XML
    public void init(){
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        assignmentListView=findViewById(R.id.assignmentListViewXML);
        databaseHelper=new DatabaseHelper(this);
    }

    //This method will be used to populate the listview
    public void populateListView(){
        final ArrayList<AssignmentModel> assignments = new ArrayList<>(databaseHelper.getAllAssignment());
        assignmentListAdapter=new AssignmentListAdapter(ItemListActivity.this, assignments);
        assignmentListView.setAdapter(assignmentListAdapter);
    }

}
