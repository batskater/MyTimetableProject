package com.example.tenthana.timetable;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microblog_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_microblog, menu);
        return true;
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

    public void buttonClicked(View v) {
        EditText etUser = (EditText)findViewById(R.id.etUser);
        String user = etUser.getText().toString().trim();
        if (user.length() == 0) {
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Please input the user name",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        else {
            Intent i =  new Intent(this, MicroblogActivity.class);
            i.putExtra("user", user);
            startActivity(i);
        }
    }
}
