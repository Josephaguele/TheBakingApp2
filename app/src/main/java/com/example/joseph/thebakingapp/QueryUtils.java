package com.example.joseph.thebakingapp;

import android.text.TextUtils;
import android.util.Log;

import com.example.joseph.thebakingapp.ingredients.Ingredients;
import com.example.joseph.thebakingapp.main.Baking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;



public class QueryUtils {

    public final static String BASEURL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static String ID = "id";
    private static String NAME = "name";
    private static String SERVINGS = "servings";

    // Tag for the log messages
    private static final String TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){}

    // Query the website dataset and return a list of {@Link Baking} objects.
    public static List<Baking> fetchBakingData(String requestUrl){

        // Create URL Object
        Log.i(TAG, "Creating URL Object to fetch baking data");
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e){
            Log.e(TAG, "Problem making the HTTP request", e);
        }

        Log.i(TAG, "Now about to extract features from json");
        // Extract relevant fields from the JSON response and create a list of bakingDetails
        List<Baking> bakingDetails = extractFeatureFromJson(jsonResponse);

        return bakingDetails;

    }

    // Returns new URL object from the given request URL
    private static URL createUrl(String requestUrl) {
        URL url = null;
        try{url = new URL(requestUrl);}
        catch (MalformedURLException e){
            Log.e(TAG, "Problem building the URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url)throws IOException{
        String jsonResponse = "";
        // If the URL is null, then return early.
        if(url == null){
            return jsonResponse;
        }


        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving the BAKING api JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Baking> extractFeatureFromJson(String bakingJSON){
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(bakingJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding baking details to
        List<Baking> bakings = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        try{
            // Create a JSONObject from the JSON response string
            JSONArray rootJSON = new JSONArray(bakingJSON);
            for(int i = 0; i< rootJSON.length(); i++){
                JSONObject root2 = rootJSON.optJSONObject(i);
                int fId = root2.optInt(ID);
                String foodName = root2.optString(NAME);
                int servings = root2.optInt("servings");

                // since the result of id is in integers, this converts food id back to a string value
                String foodId = Integer.toString(fId);
                String fServings = Integer.toString(servings);

                Baking bakingList = new Baking(foodId,foodName,fServings);
                bakings.add(bakingList);
                Log.i("QueryUtils2",bakingList.toString());

            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JSON result",e);
        }
        return bakings;

    }





















    /*
    * Query Utils for ingredients activities and json parsing*/
    // Query the website dataset and return a list of ingredients array objects
    public static List<Ingredients> fetchIngredientsData(String requestUrl) {

        // Create URL Object
        Log.i(TAG, "Creating URL Object to fetch ingredients data");

        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Problem making the HTTP request", e);
        }

        // Extract relevant fields from the JSON response and create a list of ingredients
        Log.i(TAG, "Now about to extract ingredients from json");

        List<Ingredients> foodIngredients = extractIngredientsFromJsonResponse( jsonResponse);
        return foodIngredients;

    }

    private static List<Ingredients> extractIngredientsFromJsonResponse(String ingredientJson) {

        // IF the JSON string is empty or null, then return early
        if (TextUtils.isEmpty(ingredientJson)) {
            return null;
        }

        List<Ingredients> ing = new ArrayList<>();
        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.


        try {
            // Create a JSONObject from the JSON response string
            JSONArray rootJSON = new JSONArray(ingredientJson);
            for (int i = 0; i < rootJSON.length(); i++) {
                JSONObject root2 = rootJSON.optJSONObject(i);
                int fId = root2.optInt(ID);
                String foodName = root2.optString(NAME);

                // going deeper into the JSON parsing
                JSONArray ingredientsArray = root2.optJSONArray("ingredients");
                for (int iArray = 0; i < ingredientsArray.length(); iArray++) {
                    JSONObject root4 = ingredientsArray.optJSONObject(iArray);
                    int foodQuantity = root4.optInt("quantity");
                    String foodMeasure = root4.optString("measure");
                    String foodIngredient = root4.optString("ingredient");
                    // since the result of id is in integers, this converts food id back to a string value
                    String foodId = Integer.toString(fId);
                    // conversion of foodquantity from integer to String
                    String quantity = Integer.toString(foodQuantity);

                    Ingredients ingredientList = new Ingredients(quantity, foodMeasure, foodIngredient,foodName);
                    ing.add(ingredientList);
                    Log.i("Ingredients Checks",ingredientList.toString());
                }

            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JSON result", e);
        }
        return  ing;
    }

}