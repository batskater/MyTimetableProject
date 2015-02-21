package com.example.tenthana.timetable;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class StartDesignActivity extends ActionBarActivity {
    DBHelper helper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_design);
    }

    public void SubmitClicked(View v){
        EditText etCourseID = (EditText)findViewById(R.id.CourseID);
        EditText etCourseName = (EditText)findViewById(R.id.etCourseName);
        EditText etPlace = (EditText)findViewById(R.id.etPlace);
        EditText etInstructor = (EditText)findViewById(R.id.etInstructor);
        EditText etStart = (EditText)findViewById(R.id.etStart);
        EditText etEnd = (EditText)findViewById(R.id.etEnd);
        RadioGroup rg = (RadioGroup)findViewById(R.id.rgDay);

        int rID = rg.getCheckedRadioButtonId();
        String rgDay = ((RadioButton)findViewById(rID)).getText().toString();

        helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues r = new ContentValues();
        r.put("courseid",etCourseID.toString());
        r.put("coursename",etCourseName.toString());
        r.put("place", etPlace.toString());
        r.put("instructor",etInstructor.toString());
        r.put("tstart",etStart.toString());
        r.put("tend",etEnd.toString());
        r.put("day",rgDay);
        long new_id = db.insert("timetable",null,r);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
