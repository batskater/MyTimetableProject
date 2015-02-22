package com.example.tenthana.timetable;

import java.util.ArrayList;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

public class ShowTimetableActivity extends Activity {
        DBHelper helper;
        //SimpleCursorAdapter adapter;
        ListView lvCustomList;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_show_timetable);
            lvCustomList = (ListView) findViewById(R.id.srListView);

            ArrayList<EventListItem> eventlist = new ArrayList<EventListItem>();
            eventlist.clear();
            helper = new DBHelper(this.getApplicationContext());
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor c1 = db.rawQuery("SELECT * FROM timetable;", null);
            c1.moveToFirst();
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

            EventListAdapter eventListAdapter = new EventListAdapter(this, eventlist);
            lvCustomList.setAdapter(eventListAdapter);

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
