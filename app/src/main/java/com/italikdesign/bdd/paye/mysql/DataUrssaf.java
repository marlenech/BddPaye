package com.italikdesign.bdd.paye.mysql;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by italikdesign on 24/10/2016.
 */

//Envoi des données de l'interface URSSAF sur la bdd

public class DataUrssaf {

    String psmaladie;

    public DataUrssaf(String psmaladie) {
        this.psmaladie = psmaladie;
    }

    //Data qui doit être envoyé

    public String packData() {
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();

        try {
            jo.put("psmaladie", psmaladie);

            //TODO Ajouter autre data

            Boolean firstValue = true;

            Iterator it=jo.keys();

            do {
                String key = it.next().toString();
                String value = jo.get(key).toString();

                if (firstValue)

                {
                    firstValue = false;
                } else {

                    sb.append("&");
                }
                sb.append(URLEncoder.encode(key, "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(value, "UTF-8"));

            }while (it.hasNext());

            return sb.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

}