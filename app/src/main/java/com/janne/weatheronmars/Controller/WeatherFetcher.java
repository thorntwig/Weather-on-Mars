package com.janne.weatheronmars.Controller;

import android.util.Log;

import com.janne.weatheronmars.Model.Sol;

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
                //Log.d("Response: ", "> " + result);   //here u ll get whole response...... :-)

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
                sol.setAverageTemp(farenheitToCelsius(at.getDouble("av")));
                sol.setMinTemp(farenheitToCelsius(at.getDouble("mn")));
                sol.setMaxTemp(farenheitToCelsius(at.getDouble("mx")));
/*
                // Wind speed
                JSONObject hws = solObj.getJSONObject("HWS");
                sol.setAverageWind(hws.getDouble("av"));
                sol.setMinWind(hws.getDouble("mn"));
                sol.setMaxWind(hws.getDouble("mx"));
*/
                // Pressure
                JSONObject pre = solObj.getJSONObject("PRE");
                sol.setAveragePressure(pre.getDouble("av"));
                sol.setMinPressure(pre.getDouble("mn"));
                sol.setMaxPressure(pre.getDouble("mx"));

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

            // print the results
            for(int i  = 0; i < sols.size(); i++) {
                Log.d("Response: ", "> " + sols.get(i).getNumber() + " Av temp: " + sols.get(i).getAverageTemp());
            }

            //this.progressDialog.dismiss();
        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sols;
    }

    private static double farenheitToCelsius(double fareheit) {
        return ((fareheit - 32)*5)/9;
    }
}
