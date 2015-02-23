package com.example.tenthana.timetable;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowTimetableActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{
    DBHelper helper;
    ListView lvCustomList;
    EventListAdapter eventListAdapter;
    long selectedId;
    ActionMode actionMode;
    String input;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_timetable);
        lvCustomList = (ListView) findViewById(R.id.srListView);

        Intent i = this.getIntent();
        input = i.getStringExtra("day");

        TextView tvDay = (TextView)findViewById(R.id.tvDay);
        tvDay.setText(input);


        ArrayList<EventListItem> eventlist = new ArrayList<EventListItem>();
        eventlist.clear();
        helper = new DBHelper(this.getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c1 = db.rawQuery("SELECT * FROM timetable;", null);
        //c1.moveToFirst();
        if (c1 != null && c1.getCount() != 0) {

            if (c1.moveToFirst()) {
                do {
                    EventListItem eventListItem = new EventListItem();

                    eventListItem.setCourseid(c1.getString(c1
                            .getColumnIndex("courseid")));
                    eventListItem.setCoursename(c1.getString(c1
                            .getColumnIndex("coursename")));
                    eventListItem.setInstructor(c1.getString(c1
                            .getColumnIndex("instructor")));
                    eventListItem.setPlace(c1.getString(c1
                            .getColumnIndex("place")));
                    eventListItem.setTend(c1.getString(c1
                            .getColumnIndex("tend")));
                    eventListItem.setTstart(c1.getString(c1
                            .getColumnIndex("tstart")));
                    eventlist.add(eventListItem);

                } while (c1.moveToNext());
            }
        }
        c1.close();

        eventListAdapter = new EventListAdapter(this, eventlist);
        lvCustomList.setAdapter(eventListAdapter);
        lvCustomList.setOnItemClickListener(this);
    }


    public void onItemClick(AdapterView<?> parent, View strings,int position, long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int n = db.delete("timetable", "_id = ?", new String[]{Long.toString(id)});
        if (n == 1) {
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Successfully deleted the selected item.",
                    Toast.LENGTH_SHORT);
            t.show();
            Cursor c1 = db.rawQuery("SELECT * FROM timetable;", null);
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



