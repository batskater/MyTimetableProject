package com.example.tenthana.timetable;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    //SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void buttonClicked(View v){
        int id = v.getId();
        Intent i;
        switch(id) {
            case R.id.btAdd:
                i = new Intent(this,StartDesignActivity.class);
                startActivityForResult(i, 1);
                break;
            case R.id.btShow:
                i = new Intent(this,SelectDayActivity.class);
                startActivity(i);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                DBHelper helper = new DBHelper(this);
                SQLiteDatabase db = helper.getReadableDatabase();
                ContentValues r = new ContentValues();
                String courseid = data.getStringExtra("courseid");
                String coursename = data.getStringExtra("coursename");
                String place = data.getStringExtra("place");
                String instructor = data.getStringExtra("instructor");
                String tstart = data.getStringExtra("tstart");
                String tend = data.getStringExtra("tend");
                String day = data.getStringExtra("day");
                r.put("courseid", courseid);
                r.put("coursename",coursename);
                r.put("place",place);
                r.put("instructor",instructor);
                r.put("tstart",tstart);
                r.put("tend",tend);
                r.put("day",day);
                long new_id = db.insert("timetable",null,r);

                if (new_id == -1) {
                    Toast t = Toast.makeText(this.getApplicationContext(), "Add course failed"
                            , Toast.LENGTH_SHORT);
                    t.show();
                }
                else {
                    Toast t = Toast.makeText(this.getApplicationContext(), "Add course succeeded"
                            , Toast.LENGTH_SHORT);
                    t.show();
                }

                //db.close();
            }
        }

        Log.d("timetable", "onActivityResult");
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
