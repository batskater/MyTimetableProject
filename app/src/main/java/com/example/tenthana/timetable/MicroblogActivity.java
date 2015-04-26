package com.example.tenthana.timetable;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MicroblogActivity extends ActionBarActivity implements Runnable {
    int timestamp = 0;
    ArrayList<Map<String, String>> data;
    SimpleAdapter adapter;
    String user,courseid;
    long lastUpdate = 0;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microblog);
        data = new ArrayList<Map<String, String>>();
        adapter = new SimpleAdapter(this,
                data,
                R.layout.microblog_list_item,
                new String[] {"user", "message","time"},
                new int[] {R.id.tvSay, R.id.tvMessage,R.id.tvCTime});
        ListView l = (ListView)findViewById(R.id.listView);
        l.setAdapter(adapter);
        LoadMessageTask task = new LoadMessageTask();
        task.execute();

        Intent i = this.getIntent();
        user = i.getStringExtra("currentuser");
        courseid = i.getStringExtra("courseid");

        handler = new Handler();
        handler.postDelayed(this, 10000);
    }

    public void run() {
        LoadMessageTask task = new LoadMessageTask();
        task.execute();
        handler.postDelayed(this, 10000);

    }

    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(this);
    }

    public void buttonClicked(View v) {
        EditText etMessage = (EditText)findViewById(R.id.etMessage);
        String message = etMessage.getText().toString().trim();
        if (message.length() > 0) {
            PostMessageTask p = new PostMessageTask();
            p.execute(user, message);
        }
    }

    class LoadMessageTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            BufferedReader reader;
            StringBuilder buffer = new StringBuilder();
            String line;

            try {
                //Log.e("LoadMessageTask", "" + timestamp);
                URL u = new URL("http://ict.siit.tu.ac.th/~u5522781541/timetable/fetch.php?time="
                        +timestamp+ "&subj=" +courseid);
                HttpURLConnection h = (HttpURLConnection)u.openConnection();
                h.setRequestMethod("GET");
                h.setDoInput(true);
                h.connect();
                int response = h.getResponseCode();
                if (response == 200) {
                    reader = new BufferedReader(new InputStreamReader(h.getInputStream()));
                    while((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    //Parsing JSON and displaying messages

                    //To append a new message:
                    //Map<String, String> item = new HashMap<String, String>();
                    //item.put("user", u);
                    //item.put("message", m);
                    //data.add(0, item);
                    JSONObject json = new JSONObject(buffer.toString());
                    boolean res = json.getBoolean("response");
                    String error = json.getString("errmsg");


                    if(res == true){
                        timestamp = json.getInt("timestamp");
                        JSONArray msg = json.getJSONArray("msg");
                        //fotmatting timestamp
                        long dv = Long.valueOf(Integer.toString(timestamp))*1000;
                        Date df = new java.util.Date(dv);
                        String vv = new SimpleDateFormat("MM dd, yyyy hh:mma").format(df);

                        for(int i = 0; i<msg.length(); i++)
                        {
                            JSONObject msgele = msg.getJSONObject(i);
                            Map<String,String> item = new HashMap<String,String>();
                            item.put("user",msgele.getString("user"));
                            item.put("message",msgele.getString("message"));
                            item.put("time",vv);
                            data.add(0,item);
                        }

                        return true;
                    }
                    else
                    {
                        Toast t = Toast.makeText(MicroblogActivity.this.getApplicationContext(),
                                error,Toast.LENGTH_SHORT);
                        t.show();
                        return false;
                    }

                }
            } catch (MalformedURLException e) {
                Log.e("LoadMessageTask", "Invalid URL");
            } catch (IOException e) {
                Log.e("LoadMessageTask", "I/O Exception");
            } catch (JSONException e) {
                Log.e("LoadMessageTask", "Invalid JSON");
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                adapter.notifyDataSetChanged();
                lastUpdate = System.currentTimeMillis();
                Toast t = Toast.makeText(MicroblogActivity.this.getApplicationContext(),
                        "Updated the timeline",
                        Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    class PostMessageTask extends AsyncTask<String, Void, Boolean> {
        String line;
        StringBuilder buffer = new StringBuilder();

        @Override
        protected Boolean doInBackground(String... params) {
            String user = params[0];
            String message = params[1];
            HttpClient h = new DefaultHttpClient();
            HttpPost p = new HttpPost("http://ict.siit.tu.ac.th/~u5522781541/timetable/post.php");
            List<NameValuePair> values = new ArrayList<NameValuePair>();
            values.add(new BasicNameValuePair("user", user));
            values.add(new BasicNameValuePair("message", message));
            values.add(new BasicNameValuePair("subject", courseid));

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

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast t = Toast.makeText(MicroblogActivity.this.getApplicationContext(),
                        "Successfully post your status",
                        Toast.LENGTH_SHORT);
                t.show();
                LoadMessageTask task = new LoadMessageTask();
                task.execute();
            }
            else {
                Toast t = Toast.makeText(MicroblogActivity.this.getApplicationContext(),
                        "Unable to post your status",
                        Toast.LENGTH_SHORT);
                t.show();
            }
        }
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
}
