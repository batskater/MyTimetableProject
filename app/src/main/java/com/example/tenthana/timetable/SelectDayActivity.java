package com.example.tenthana.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;

public class SelectDayActivity extends ActionBarActivity {
    String input;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_day);
        Intent i = this.getIntent();
        input = i.getStringExtra("currentuser");
    }


    public void SelectDayClicked (View v) {
        String day = "";
        int id = v.getId();
        Intent i;
        switch (id) {
            case R.id.Mon:
                i = new Intent(this,ShowTimetableActivity.class);
                i.putExtra("day", "Monday");
                i.putExtra("currentuser",input);
                startActivity(i);

                break;
            case R.id.Tue:
                i = new Intent(this,ShowTimetableActivity.class);
                i.putExtra("day", "Tuesday");
                startActivity(i);
                break;
            case R.id.Wed:
                i = new Intent(this,ShowTimetableActivity.class);
                i.putExtra("day", "Wednesday");
                startActivity(i);
                break;
            case R.id.Thur:
                i = new Intent(this,ShowTimetableActivity.class);
                i.putExtra("day", "Thursday");
                startActivity(i);
                break;
            case R.id.Fri:
                i = new Intent(this,ShowTimetableActivity.class);
                i.putExtra("day", "Friday");
                startActivity(i);
                break;
            case R.id.Sat:
                i = new Intent(this,ShowTimetableActivity.class);
                i.putExtra("day", "Saturday");
                startActivity(i);
                break;
            case R.id.Sun:
                i = new Intent(this,ShowTimetableActivity.class);
                i.putExtra("day", "Sunday");
                startActivity(i);
                break;
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
