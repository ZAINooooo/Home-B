package com.example.asad.homebuyerproject;

import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by hassan on 11/01/2017.
 */
public class JSONParser {

    static InputStream in = null;
    static JSONObject jsonObj =null;

    public JSONObject getJSONFromURL(String urlStr) {
        //progressDialog = ProgressDialog.show(this, "", "Getting Places Nearby from " + urlStr);
        final String url = urlStr;

        // new Thread() {
        // public void run() {


        Message msg = Message.obtain();
        msg.what = 1;

        try {
            in = openHttpConnection(url);
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String line;
            while ((line = streamReader.readLine()) != null) {
                responseStrBuilder.append(line);
            }



                    /*bitmap = BitmapFactory.decodeStream(in);
                    Bundle b = new Bundle();
                    b.putParcelable("bitmap", bitmap);
                    msg.setData(b);*/
            jsonObj = new JSONObject(responseStrBuilder.toString());
            //System.out.print("JSONObject="+jsonObj);





            in.close();

        }

        catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // messageHandler.sendMessage(msg);
        // }
        //}.start();

        // progressDialog.hide();
        return jsonObj;
    }

    private InputStream openHttpConnection(String urlStr) {
        InputStream in = null;
        int resCode = -1;

        try {
            URL url = new URL(urlStr);
            URLConnection urlConn = url.openConnection();

            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

}

