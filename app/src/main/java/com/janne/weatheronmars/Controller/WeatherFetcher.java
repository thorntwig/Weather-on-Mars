package com.janne.weatheronmars.Controller;

import android.util.Log;

import com.janne.weatheronmars.Model.Sol;
import com.janne.weatheronmars.Model.Unit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class WeatherFetcher {

    public static List<Sol> fetch() {
        HttpsURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("https://api.nasa.gov/insight_weather/?api_key=pqYoj1vWsXGV8UwlhRbSCeReZEDytTeep3rkghkH&feedtype=json&ver=1.0");
            connection = (HttpsURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String result = "";

            while ((result = reader.readLine()) != null) {
                buffer.append(result + "\n");
            }

            return parse(buffer.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private static ArrayList<Sol> parse(String json) {
        ArrayList<Sol> sols = new ArrayList<>();
        try {

            // Get the sols
            JSONObject obj = new JSONObject(json);
            JSONArray solsArray = obj.getJSONArray("sol_keys");

            // Loop through all the sols
            for(int i  = 0; i < solsArray.length(); i++) {
                Sol sol = new Sol();

                // Number
                int number = Integer.valueOf(solsArray.getString(i));
                sol.setNumber(number);
                JSONObject solObj = obj.getJSONObject(String.valueOf(number));

                // Temperatures
                JSONObject at = solObj.getJSONObject("AT");
                sol.setTemp(parseTemp(at));

                // Pressure
                JSONObject pre = solObj.getJSONObject("PRE");
                sol.setPressure(parseUnit(pre));

                // Wind speed
                JSONObject hws = solObj.getJSONObject("HWS");
                sol.setWind(parseUnit(hws));

                // Dates
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); // your format
                String firstUTC = solObj.getString("First_UTC");
                String lastUTC = solObj.getString("Last_UTC");
                sol.setStartTime(format.parse(firstUTC));
                sol.setEndTime(format.parse(lastUTC));

                // Season
                String season = solObj.getString("Season");
                sol.setSeason(season);

                sols.add(sol);
            }


            //this.progressDialog.dismiss();
        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Collections.sort(sols);
        return sols;
    }

    private static Unit parseUnit(JSONObject o){
        Unit u = null;
        try {
            u = new Unit();
            u.setAvg(o.getDouble("av"));
            u.setMin(o.getDouble("mn"));
            u.setMax(o.getDouble("mx"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }

    private static Unit parseTemp(JSONObject o) {
        Unit temp = parseUnit(o);
        temp.setAvg(fahrenheitToCelsius(temp.getAvg()));
        temp.setMin(fahrenheitToCelsius(temp.getMin()));
        temp.setMax(fahrenheitToCelsius(temp.getMax()));
        return temp;
    }

    private static double fahrenheitToCelsius(double fahrenheit) {
        return ((fahrenheit - 32)*5)/9;
    }
}
