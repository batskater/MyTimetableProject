package com.example.tenthana.timetable;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ShowTimetableActivity extends ActionBarActivity {
        DBHelper helper;
        SimpleCursorAdapter adapter;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_show_timetable);

            //Intent i = this.getIntent();
            //TextView tv = (TextView)findViewById(R.id.textview);
            //tv.setText(i.getStringExtra("day"));
            helper = new DBHelper(this);
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM timetable where day='Monday' ;",null);
            cursor.moveToFirst();

            adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2,
                    cursor,
                    new String[] {"courseid","coursename"},
                    new int[] {android.R.id.text1,android.R.id.text2},0);
            ListView lv = (ListView)findViewById(R.id.listView);
            lv.setAdapter(adapter);
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
