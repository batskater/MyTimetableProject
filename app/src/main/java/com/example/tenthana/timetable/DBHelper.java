package com.example.tenthana.timetable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String name = "timetable.sqlite3";
    private static final int version = 3;


    public DBHelper(Context ctx) {
        super(ctx, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE timetable (" +
                "_id integer primary key autoincrement," +
                "courseid text not null," +             // course id
                "coursename text default 0," +           // course name
                "place text not null," +            // place
                "instructor text not null," +            // instructor
                "tstart text not null," +            // start
                "tend text not null," +
                "day  text not null," +// end
                "time real default 0.0," +
                "user text not null); ";         // day
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS timetable;";
        db.execSQL(sql);
        this.onCreate(db);
    }
}

