package com.italikdesign.bdd.paye.mysql;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by italikdesign on 24/10/2016.
 */

public class Sender extends AsyncTask<Void, Void, String> {

    Context c;
    String urlAddress;
    EditText psmaladieTxt;
    String psmaladie;

    ProgressDialog pd;

    public Sender(Context c, String urlAddress, EditText... editTexts) {
        this.c = c;
        this.urlAddress = urlAddress;

        this.psmaladieTxt = editTexts[0];

        psmaladie = psmaladieTxt.getText().toString();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Send psmaladie");
        pd.setMessage("Sending data...Please wait");
        pd.show();

    }

    @Override
    protected String doInBackground(Void... params) {
        return this.send();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        pd.dismiss();

        //Quand ça marche

        if (s != null) {
            Toast.makeText(c, s, Toast.LENGTH_SHORT).show();

            psmaladieTxt.setText("");

            //Quand ça marche pas
        } else {
            Toast.makeText(c, "Ca marche pas" + s, Toast.LENGTH_LONG).show();
        }
    }

    private String send() {

        //Connect
        HttpURLConnection con = Connector.connect(urlAddress);
        if (con == null) {
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();

            //ECRITURE
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bw.write(new DataUrssaf(psmaladie).packData());

            //RELEASE RES
            bw.flush();
            bw.close();
            os.close();

            //GET EXACT RESPONSE
            int responseCode = con.getResponseCode();

            if (responseCode == con.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer response = new StringBuffer();

                String line = null;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                //RELEASE RES
                br.close();


                return response.toString();

            } else {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}



