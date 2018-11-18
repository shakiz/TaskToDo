package com.tasktodo.com.tasktodo.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tasktodo.com.tasktodo.Models.AssignmentModel;
import com.tasktodo.com.tasktodo.R;

import java.util.ArrayList;

public class AssignmentListAdapter extends ArrayAdapter<AssignmentModel>{
    //for getting the context
    Context context;
    //Arraylist of assignment information like assignment number,name,course name and deadline
    ArrayList<AssignmentModel> assignmentData;


    public AssignmentListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    //This is the constructor for passing the current context and the list of assignment data
    public AssignmentListAdapter(Context context, ArrayList<AssignmentModel> assignmentData){
        super(context, R.layout.list_row_item, assignmentData);
        this.context=context;
        this.assignmentData = assignmentData;
    }

    //Creating the class holder to hold the view
    public  class  Holder{
        //taking three textviews  in order to initialize with xml textviews
        //using asgn as the short form of assignment
        TextView asgnNumber;
        TextView asgnName;
        TextView courseName;
        TextView deadline;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position

        AssignmentModel data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row_item, parent, false);
            //initializing the textviews that we declared into the holder class
            viewHolder.asgnNumber = convertView.findViewById(R.id.assignmentIDXML);
            viewHolder.asgnName = convertView.findViewById(R.id.assignmentNameXML);
            viewHolder.courseName = convertView.findViewById(R.id.courseNameXML);
            viewHolder.deadline=convertView.findViewById(R.id.deadlineXML);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }
        //setting the text into those textviews
        viewHolder.asgnNumber.setText("Assignment number : "+data.getAssignmentNumber());
        viewHolder.asgnName.setText("Assignment name : "+data.getAssignmentName());
        viewHolder.courseName.setText("Course name : "+data.getCourseName());
       // Toast.makeText(context,""+data.getCourseName(),Toast.LENGTH_LONG).show();
        viewHolder.deadline.setText("Deadline :"+data.getAssignmentDueDate());

        // Return the completed view to render on screen
        return convertView;
    }
}
