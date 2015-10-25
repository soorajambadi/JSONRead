package com.example.sooraj.omgapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by sooraj on 23.10.15.
 */


    public class JsonTest extends AsyncTask {

        @Override
        public Object doInBackground(Object... params) {
            String url = (String) params[0];
            Double latitude;
            Double longitude;
            System.out.println("====================" + url +"______________________");
            try {
                JSONObject jsonObject = new JSONObject(DownloadJSONData(url));
                JSONObject jsonChildObject = (JSONObject)jsonObject.get("nodes");

                for(int i = 0; i<jsonChildObject.length(); i++){
                    JSONObject jsonChildObject1; /*= (JSONObject)jsonChildObject.getJSONObject(jsonChildObject.names().getString(i)).get("nodeinfo");*/
                    //JSONObject jsonChildObject2;

                    try {
                        jsonChildObject1 = (JSONObject)((JSONObject) jsonChildObject.getJSONObject(jsonChildObject.names().getString(i)).get("nodeinfo")).getJSONObject("location");
                    }catch (Exception e) {
                        Log.e("KEY", i + "\tKey = " + jsonChildObject.names().getString(i));
                        continue;
                    }
                    try {
                        latitude = (Double) jsonChildObject1.get("latitude");
                    }catch (Exception e) {
                        latitude = 0.0;
                    }

                    try {
                        longitude = (Double) jsonChildObject1.get("longitude");
                    }catch (Exception e) {
                        longitude = 0.0;
                    }
                    Log.e("KEY", i + "\tKey = " + jsonChildObject.names().getString(i) + "\t" + latitude + "\t" + longitude); /*+ " value = " + jsonObject.get(jsonObject.names().getString(i)));*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        public String DownloadJSONData(String string_url) {
            BufferedReader reader = null;
            String webc = "";
            try {
                URL url = new URL(string_url);
                System.out.println(string_url);
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                System.out.println("after");
                StringBuffer buffer = new StringBuffer();
                int read;
                char[] chars = new char[1024];
                while ((read = reader.read(chars)) != -1) {
                    buffer.append(chars, 0, read);
                }
                webc = buffer.toString();
                //System.out.println("printing web -----" + webc);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                        return webc;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("printing web -----" + webc);
            return webc;
        }

    }

