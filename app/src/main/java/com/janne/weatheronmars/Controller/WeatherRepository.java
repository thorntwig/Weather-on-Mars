package com.janne.weatheronmars.Controller;

import android.content.Context;
import android.net.Uri;

import com.janne.weatheronmars.Model.Sol;
import com.janne.weatheronmars.Model.Unit;
import com.janne.weatheronmars.R;

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
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class WeatherRepository {

    public static List<Sol> getSol(Context context) {

        HttpsURLConnection connection = null;
        BufferedReader reader = null;
        try {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("api.nasa.gov")
                    .appendPath("insight_weather")
                    .appendPath("")
                    .appendQueryParameter("api_key", "pqYoj1vWsXGV8UwlhRbSCeReZEDytTeep3rkghkH")
                    .appendQueryParameter("feedtype", "json")
                    .appendQueryParameter("ver", "1.0");

            String builtUrl = builder.build().toString();
            URL url = new URL(builtUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String result = "";

            while ((result = reader.readLine()) != null) {
                buffer.append(result + "\n");
            }

            return parse(buffer.toString(), context);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private static ArrayList<Sol> parse(String json, Context context) {
        ArrayList<Sol> sols = new ArrayList<>();
        try {

            JSONObject obj = new JSONObject(json);
            JSONArray solsArray = obj.getJSONArray("sol_keys");


            for (int i = 0; i < solsArray.length(); i++) {

                int number = Integer.valueOf(solsArray.getString(i));
                JSONObject jsonObj = obj.getJSONObject(String.valueOf(number));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date date = format.parse(jsonObj.getString("First_UTC"));

                Unit temp = parseTemp(jsonObj, "AT");
                if (temp != null) {
                    temp.setTitle(context.getString(R.string.temp));
                    temp.setSign(context.getString(R.string.temp_sign));
                    temp.setDate(date);
                }


                Unit pressure = parseUnit(jsonObj, "PRE");
                if (pressure != null) {
                    pressure.setTitle(context.getString(R.string.pressure));
                    pressure.setSign(context.getString(R.string.pressure_sign));
                    pressure.setDate(date);
                }


                Unit wind = parseUnit(jsonObj, "HWS");
                if (wind != null) {
                    wind.setTitle(context.getString(R.string.wind));
                    wind.setSign(context.getString(R.string.wind_sign));
                    wind.setDate(date);
                }

                String season = jsonObj.getString("Season");

                Sol sol = new Sol.Builder()
                        .number(number)
                        .temp(temp)
                        .wind(wind)
                        .pressure(pressure)
                        .season(season)
                        .date(date)
                        .build();

                sols.add(sol);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Collections.sort(sols);
        return sols;
    }

    private static Unit parseUnit(JSONObject sol, String key) {
        try {
            JSONObject o = sol.getJSONObject(key);
            double avg = o.getDouble("av");
            double min = o.getDouble("mn");
            double max = o.getDouble("mx");
            return new Unit(avg, min, max);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Unit parseTemp(JSONObject o, String key) {
        Unit temp = parseUnit(o, key);
        temp.setAvg(fahrenheitToCelsius(temp.getAvg()));
        temp.setMin(fahrenheitToCelsius(temp.getMin()));
        temp.setMax(fahrenheitToCelsius(temp.getMax()));
        return temp;
    }

    private static double fahrenheitToCelsius(double fahrenheit) {
        return ((fahrenheit - 32) * 5) / 9;
    }
}
