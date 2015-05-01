package com.example.tenthana.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;

public class StartDesignActivity extends ActionBarActivity {
    DBHelper helper;
    SessionManager session;
    String user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_design);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> detail = session.getUserDetails();
        user = detail.get(SessionManager.KEY_NAME);
        Intent i = this.getIntent();
        if (i.hasExtra("courseid")) {
            String courseid = i.getStringExtra("courseid");
            String coursename = i.getStringExtra("coursename");
            String place = i.getStringExtra("place");
            String instructor = i.getStringExtra("instructor");
            String tstart = i.getStringExtra("tstart");
            String tend = i.getStringExtra("tend");
            String day = i.getStringExtra("day");

            EditText etCoursename = (EditText) findViewById(R.id.etCourseName);
            etCoursename.setText(coursename);

            EditText etCourseid = (EditText) findViewById(R.id.etCourseID);
            etCourseid.setText(courseid);

            EditText etPlace = (EditText) findViewById(R.id.etPlace);
            etPlace.setText(place);

            EditText etInstructor = (EditText) findViewById(R.id.etInstructor);
            etInstructor.setText(instructor);

            EditText etTstart = (EditText) findViewById(R.id.etStart);
            etTstart.setText(tstart);

            EditText etTend = (EditText) findViewById(R.id.etEnd);
            etTend.setText(tend);

            RadioGroup rgDay = (RadioGroup) findViewById(R.id.rgDay);
            if (day.equals("Monday")) {
                rgDay.check(R.id.rbMon);
            } else if (day.equals("Tuesday")) {
                rgDay.check(R.id.rbTue);
            } else if (day.equals("Wednesday")) {
                rgDay.check(R.id.rbWed);
            } else if (day.equals("Thursday")) {
                rgDay.check(R.id.rbThur);
            } else if (day.equals("Friday")) {
                rgDay.check(R.id.rbFri);
            } else if (day.equals("Saturday")) {
                rgDay.check(R.id.rbSat);
            } else if (day.equals("Sunday")) {
                rgDay.check(R.id.rbSun);
            }



            Button btSubmit = (Button) findViewById(R.id.submit);
            btSubmit.setText("Edit!");
        }
    }

    public void SubmitClicked(View v){
        EditText etCourseID = (EditText)findViewById(R.id.etCourseID);
        EditText etCourseName = (EditText)findViewById(R.id.etCourseName);
        EditText etPlace = (EditText)findViewById(R.id.etPlace);
        EditText etInstructor = (EditText)findViewById(R.id.etInstructor);
        EditText etStart = (EditText)findViewById(R.id.etStart);
        EditText etEnd = (EditText)findViewById(R.id.etEnd);
        RadioGroup rg = (RadioGroup)findViewById(R.id.rgDay);

        int rID = rg.getCheckedRadioButtonId();
        String rgDay = ((RadioButton)findViewById(rID)).getText().toString();


        String sCourseId = etCourseID.getText().toString();
        String sCourseName = etCourseName.getText().toString();
        String sPlace = etPlace.getText().toString();
        String sInstructor = etInstructor.getText().toString();
        String ststart = etStart.getText().toString();
        String sTend = etEnd.getText().toString();



        if (sCourseId.trim().length() == 0 || sCourseName.trim().length() == 0 || sPlace.trim().length() == 0
                || sInstructor.trim().length() == 0 || sTend.trim().length() == 0 || ststart.trim().length() == 0) {
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Please fill in all necessary input.",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        else {
            double time = Double.parseDouble(ststart);
            Intent result = new Intent();
            result.putExtra("courseid", etCourseID.getText().toString());
            result.putExtra("coursename", etCourseName.getText().toString());
            result.putExtra("place", etPlace.getText().toString());
            result.putExtra("instructor", etInstructor.getText().toString());
            result.putExtra("tstart", etStart.getText().toString());
            result.putExtra("tend", etEnd.getText().toString());
            result.putExtra("day", rgDay);
            result.putExtra("time",time);

            this.setResult(RESULT_OK, result);
            this.finish();
        }
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
