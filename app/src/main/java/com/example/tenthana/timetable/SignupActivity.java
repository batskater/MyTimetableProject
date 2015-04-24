package com.example.tenthana.timetable;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class SignupActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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

    public void SignupClicked(View v){
        EditText etName = (EditText)findViewById(R.id.etName);
        EditText etSurName = (EditText)findViewById(R.id.etSurName);
        EditText etUsername = (EditText)findViewById(R.id.etUsername);
        EditText etPassword = (EditText)findViewById(R.id.etPassword);


        HttpClient h = new DefaultHttpClient();
        HttpPost p = new HttpPost("http://ict.siit.tu.ac.th/~u5522781541/timetable/post.php");
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        values.add(new BasicNameValuePair("name",etName));
        values.add(new BasicNameValuePair("surname",etSurName));

        try {
            p.setEntity(new UrlEncodedFormEntity(values));
            HttpResponse response = h.execute(p);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            JSONObject json = new JSONObject(buffer.toString());
            boolean res = json.getBoolean("response");
            if (res)
                return true;
            else
                return false;


        } catch (UnsupportedEncodingException e) {
            Log.e("Error", "Invalid encoding");
        } catch (ClientProtocolException e) {
            Log.e("Error", "Error in posting a message");
        } catch (IOException e) {
            Log.e("Error", "I/O Exception");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        }

    }
}
