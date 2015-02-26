package com.example.tenthana.timetable;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ShowTimetableActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener, ActionMode.Callback{
    DBHelper helper;
    String input;
    SimpleCursorAdapter adapter;
    long selectedId;
    ActionMode actionMode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_timetable);
        int YELLOW = 0xFFFFFF00;
        int PINK = 0xFFFF00FF;
        int GREEN = 0xFF00FF00;
        int ORANGE = 0xFFFF8000;
        int CYAN = 0xFF00FFFF;
        int PURPLE = 0xFFCC2EFA;
        int RED = 0xFFFF0000;

        Intent i = this.getIntent();
        input = i.getStringExtra("day");

        TextView tvDay = (TextView)findViewById(R.id.tvDay);
        tvDay.setText(input);
        if(input.equals("Monday"))
            tvDay.setTextColor(YELLOW);
        else if(input.equals("Tuesday"))
            tvDay.setTextColor(PINK);
        else if(input.equals("Wednesday"))
            tvDay.setTextColor(GREEN);
        else if(input.equals("Thursday"))
            tvDay.setTextColor(ORANGE);
        else if(input.equals("Friday"))
            tvDay.setTextColor(CYAN);
        else if(input.equals("Saturday"))
            tvDay.setTextColor(PURPLE);
        else if(input.equals("Sunday"))
            tvDay.setTextColor(RED);

        helper = new DBHelper(this.getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT *, coursename," +
                "place," +
                "instructor, " +
                "('     '|| tstart || '  -  ' || tend) time FROM timetable where day = '"+input+"' ORDER BY time ASC;", null);

        adapter = new SimpleCursorAdapter(this,
                //android.R.layout.simple_list_item_2,
                R.layout.myshowlist,
                cursor,
                new String[] {"courseid", "coursename","place","instructor","time"},
                new int[] {R.id.tvCourseid, R.id.tvCoursename, R.id.tvLocation, R.id.tvInstructor, R.id.tvTime},
                0);
        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(this);
        db.close();
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


    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_actionmode, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                deleteClicked();
                mode.finish();
                break;
            case R.id.menu_edit:
                editClicked();
                mode.finish();
                break;
            default:
                return false;
        }
        return true;
    }



    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        view.setSelected(true);
        // Save the selected
        selectedId = id;
        // Start the ActionMode with an item is long-clicked
        actionMode = this.startActionMode(this);
        return true;
    }

    private void deleteClicked() {
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowCount = db.delete("timetable", "_id = ?",
                new String[]{Long.toString(selectedId)});
        if (rowCount == 1) {
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "An event was deleted.", Toast.LENGTH_SHORT);
            t.show();
        }
        else {
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Unable to delete the event.",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        Cursor cursor = db.rawQuery("SELECT *, coursename," +
                "place," +
                "instructor, " +
                "('     '|| tstart || '  -  ' || tend) time FROM timetable where day = '"+input+"' ORDER BY time ASC;", null);
        adapter.changeCursor(cursor);
        db.close();
    }

    private void editClicked() {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM timetable WHERE _id=?;",
                new String[]{Long.toString(selectedId)});
        if (c.getCount() == 1) {
            // Retrieved exactly one row
            c.moveToFirst();
            String courseid = c.getString(c.getColumnIndex("courseid"));
            String coursename = c.getString(c.getColumnIndex("coursename"));
            String place = c.getString(c.getColumnIndex("place"));
            String instructor = c.getString(c.getColumnIndex("instructor"));
            String tstart = c.getString(c.getColumnIndex("tstart"));
            String tend = c.getString(c.getColumnIndex("tend"));
            String day = c.getString(c.getColumnIndex("day"));
            double time = c.getDouble(c.getColumnIndex("time"));
            Intent i = new Intent(this, StartDesignActivity.class);
            i.putExtra("courseid", courseid);
            i.putExtra("coursename", coursename);
            i.putExtra("place", place);
            i.putExtra("instructor", instructor);
            i.putExtra("tstart", tstart);
            i.putExtra("tend", tend);
            i.putExtra("day",day);
            i.putExtra("time",time);
            startActivityForResult(i, 2);
        }
        else {
            // Unable to get the selected id
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Unable to retrieve the selected event.",
                    Toast.LENGTH_SHORT);
            t.show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String courseid = data.getStringExtra("courseid");
                String coursename = data.getStringExtra("coursename");
                String place = data.getStringExtra("place");
                String instructor = data.getStringExtra("instructor");
                String tstart = data.getStringExtra("tstart");
                String tend = data.getStringExtra("tend");
                String day = data.getStringExtra("day");
                double time = data.getDoubleExtra("time",0.0);

                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues r = new ContentValues();
                r.put("courseid", courseid);
                r.put("coursename",coursename);
                r.put("place",place);
                r.put("instructor",instructor);
                r.put("tstart",tstart);
                r.put("tend",tend);
                r.put("time",time);
                r.put("day",day);
                long newId = db.update("timetable", r, "_id = ?",
                        new String[]{Long.toString(selectedId)});

                if (newId != -1) {
                    Toast t = Toast.makeText(this.getApplicationContext(),
                            "Successfully updated the timetable",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
                else {
                    Toast t = Toast.makeText(this.getApplicationContext(),
                            "Unable to update the timetable",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
                Cursor cursor = db.rawQuery("SELECT *, coursename," +
                        "place," +
                        "instructor, " +
                        "('     '|| tstart || '  -  ' || tend) time FROM timetable where day = '"+input+"' ORDER BY time ASC;", null);
                adapter.changeCursor(cursor);
                db.close();
            }
        }
    }
}



