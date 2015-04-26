package com.example.tenthana.timetable;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void btnLoginClicked(View v) {
        int id = v.getId();
        Intent i;
        switch (id) {
            case R.id.btLogin:
                LoginClicked();
                break;
            case R.id.btSignup:
                i = new Intent(this,SignupActivity.class);
                startActivity(i);
                break;

        }

    }

    public void LoginClicked() {
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        String sUsername = etUsername.getText().toString();
        String sPassword = etPassword.getText().toString();
        LoginTask p = new LoginTask();
        p.execute(sUsername, sPassword);
        //i = new Intent(this,MainActivity.class);
        //i.putExtra("username",etUsername.getText().toString());
        //i.putExtra("password",etPassword.getText().toString());
        //startActivity(i);

    }

    class LoginTask extends AsyncTask<String, Void, Boolean> {
        String line;
        StringBuilder buffer = new StringBuilder();
        Intent i;

        protected Boolean doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            BufferedReader reader;
            StringBuilder buffer = new StringBuilder();
            String line;

            try {
                URL u = new URL("http://ict.siit.tu.ac.th/~u5522781541/timetable/login.php?user="
                        +username+ "&pass=" +password);
                HttpURLConnection h = (HttpURLConnection)u.openConnection();
                h.setRequestMethod("GET");
                h.setDoInput(true);
                h.connect();
                int response = h.getResponseCode();
                if (response == 200) {
                    reader = new BufferedReader(new InputStreamReader(h.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }


                    JSONObject json = new JSONObject(buffer.toString());
                    boolean res = json.getBoolean("response");
                    String uu = json.getString("user");

                    if (res == true) {
                        i = new Intent(LoginActivity.this, MainActivity.class);
                        i.putExtra("currentuser", uu);
                        return true;
                    } else
                        return false;
                }

            } catch (UnsupportedEncodingException e) {
                Log.e("Error", "Invalid encoding");
            } catch (ClientProtocolException e) {
                Log.e("Error", "Error in posting a message");
            } catch (IOException e) {
                Log.e("Error", "I/O Exception");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {

                startActivity(i);

            } else {
                Toast t = Toast.makeText(LoginActivity.this.getApplicationContext(),
                        "Unable to login to system.",
                        Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }


}
