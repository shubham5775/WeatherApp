package com.example.studio.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class  MainActivity extends AppCompatActivity {

    Button button;
    EditText city;
    TextView result;
    //api.openweathermap.org/data/2.5/weather?q=London,uk&appid=51fe1ea342f61762a03c33446bcb46d1

    //http://api.openweathermap.org/data/2.5/weather?q=paris&appid=51fe1ea342f61762a03c33446bcb46d1

    String baseUrl = "http://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=51fe1ea342f61762a03c33446bcb46d1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button);
        city = (EditText)findViewById(R.id.getCity);
        result = (TextView)findViewById(R.id.result);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("TAP", "tapped");
                String myUrl = baseUrl+city.getText().toString()+API;
                Log.i("show","URl: "+ myUrl);
                 //Json = new JsonObjectRequest(Request.Method.GET,myUr)JsonObjectRequest
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET, myUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Log.i("JSON", "json: " + jsonObject);
                                try {
                                    String info = jsonObject.getString("weather");
                                    Log.i("INFO", "info: " + info);
                                    JSONArray ar = new JSONArray(info);
                                    for (int i = 0; i < ar.length(); i++) {
                                        JSONObject parObj = ar.getJSONObject(i);
                                        String myWeather = parObj.getString("main");
                                        result.setText(myWeather);

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },

                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.i("Error" , "something went wrong" + error);
                                    }


                        }

                );
                mySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);
            }
        });


    }
}
