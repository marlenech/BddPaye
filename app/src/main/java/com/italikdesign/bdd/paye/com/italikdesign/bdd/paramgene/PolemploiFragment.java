package com.italikdesign.bdd.paye.com.italikdesign.bdd.paramgene;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.italikdesign.bdd.paye.R;
import com.italikdesign.bdd.paye.mysql.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by italikdesign on 20/11/2016.
 */

public class PolemploiFragment extends Fragment {

    // Progress Dialog
    private ProgressDialog pDialog;

    View rootView;
    TextView txt_pschom, txt_ppchom, txt_mcdd1, txt_mcdd3, txt_mcddu3;

    String id_pschom, id_ppchom, id_mcdd1, id_mcdd3, id_mcddu3;
    String nvtaux_pschom, nvtaux_ppchom, nvtaux_mcdd1, nvtaux_mcdd3, nvtaux_mcddu3;
    String actaux_pschom, actaux_ppchom, actaux_mcdd1, actaux_mcdd3, actaux_mcddu3;
    String date_pschom, date_ppchom, date_mcdd1, date_mcdd3, date_mcddu3;



    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> tauxxList;


    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "tauxx";
    private static final String TAG_PID = "pid";
    private static final String TAG_NVTAUX = "nvtaux";
    private static final String TAG_ANCIENTAUX = "ancientaux";
    private static final String TAG_DATE = "date";



    SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
    Date today = Calendar.getInstance().getTime();

    String reportDate = form.format(today);

    // JSONArray
    JSONArray tauxx = null;

    // JSONObject
    JSONObject objet_pschom, objet_ppchom, objet_mcdd1, objet_mcdd3, objet_mcddu3 = null;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        rootView = inflater.inflate(R.layout.fragment_polemploi, container, false);
        // Inflate the layout for this fragment



        // Hashmap for ListView
        tauxxList = new ArrayList<HashMap<String, String>>();

        txt_pschom = (TextView) rootView.findViewById(R.id.pschom);
        txt_ppchom = (TextView) rootView.findViewById(R.id.ppchom);
        txt_mcdd1 = (TextView) rootView.findViewById(R.id.mcdd1);
        txt_mcdd3 = (TextView) rootView.findViewById(R.id.mcdd3);
        txt_mcddu3 = (TextView) rootView.findViewById(R.id.mcddu3);



        // Loading products in Background Thread
        new LoadAllProducts().execute();

        View.OnClickListener listener = new View.OnClickListener() {

            public void onClick(View v) {

                //Gestion du click sur chaque textview en y affectant les PID correspondant
                switch (v.getId()) {
                    case R.id.pschom:

                        // getting values from selected ListItem

                        // Starting new intent
                        Intent in = new Intent(getActivity().getApplicationContext(),
                                EditData.class);
                        // je récupère le PID de la donnée cliquée
                        in.putExtra(TAG_PID, id_pschom);


                        // starting new activity and expecting some response back
                        startActivityForResult(in, 100);

                        break;
                    case R.id.ppchom:

                        // getting values from selected ListItem

                        // Starting new intent
                        Intent in2 = new Intent(getActivity().getApplicationContext(),
                                EditData.class);
                        // sending pid to next activity
                        in2.putExtra(TAG_PID, id_ppchom);


                        // starting new activity and expecting some response back
                        startActivityForResult(in2, 100);

                        break;
                    case R.id.mcdd1:

                        // getting values from selected ListItem

                        // Starting new intent
                        Intent in3 = new Intent(getActivity().getApplicationContext(),
                                EditData.class);
                        // sending pid to next activity
                        in3.putExtra(TAG_PID, id_mcdd1);


                        // starting new activity and expecting some response back
                        startActivityForResult(in3, 100);

                        break;
                    case R.id.mcdd3:

                        // getting values from selected ListItem

                        // Starting new intent
                        Intent in4 = new Intent(getActivity().getApplicationContext(),
                                EditData.class);
                        // sending pid to next activity
                        in4.putExtra(TAG_PID, id_mcdd3);


                        // starting new activity and expecting some response back
                        startActivityForResult(in4, 100);

                        break;
                    case R.id.mcddu3:

                        // getting values from selected ListItem

                        // Starting new intent
                        Intent in5 = new Intent(getActivity().getApplicationContext(),
                                EditData.class);
                        // sending pid to next activity
                        in5.putExtra(TAG_PID, id_mcddu3);


                        // starting new activity and expecting some response back
                        startActivityForResult(in5, 100);

                        break;
                }
            }
        };
        //Appel de chaque listener
        txt_pschom.setOnClickListener(listener);
        txt_ppchom.setOnClickListener(listener);
        txt_mcdd1.setOnClickListener(listener);
        txt_mcdd3.setOnClickListener(listener);
        txt_mcddu3.setOnClickListener(listener);



        return rootView;
    }

    // Response from Edit Product Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again

        }

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Chargement des données...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Je construis mon array
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // je convertis mes données en json via mon URL
            JSONObject json =    jParser.makeHttpRequest(getResources().getString(R.string.all_products), "GET", params);

            // log cat du JSON reponse
            Log.d("Les données: ", json.toString());

            try {
                // Je vérifie le SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // les données ont été trouvées
                    // j'inclus l'ensemble de ces données dans un array (TAG_PRODUCTS)
                    tauxx = json.getJSONArray(TAG_PRODUCTS);

                    // Boucle sur l'ensemble des lignes de la table
                    for (int i = 0; i < tauxx.length(); i++) {
                        JSONObject c = tauxx.getJSONObject(i);

                        // j'affecte une variable à chaque ligne
                        objet_pschom = tauxx.getJSONObject(21);
                        objet_ppchom = tauxx.getJSONObject(22);
                        objet_mcdd1 = tauxx.getJSONObject(23);
                        objet_mcdd3 = tauxx.getJSONObject(24);
                        objet_mcddu3 = tauxx.getJSONObject(25);


                        // je récupère les données de chaque ligne liée à chaque variable définie au dessus
                        id_pschom = objet_pschom.getString(TAG_PID);
                        id_ppchom = objet_ppchom.getString(TAG_PID);
                        id_mcdd1 = objet_mcdd1.getString(TAG_PID);
                        id_mcdd3 = objet_mcdd3.getString(TAG_PID);
                        id_mcddu3 = objet_mcddu3.getString(TAG_PID);



                        actaux_pschom = objet_pschom.getString(TAG_ANCIENTAUX);
                        actaux_ppchom = objet_ppchom.getString(TAG_ANCIENTAUX);
                        actaux_mcdd1 = objet_mcdd1.getString(TAG_ANCIENTAUX);
                        actaux_mcdd3 = objet_mcdd3.getString(TAG_ANCIENTAUX);
                        actaux_mcddu3 = objet_mcddu3.getString(TAG_ANCIENTAUX);



                        nvtaux_pschom = objet_pschom.getString(TAG_NVTAUX);
                        nvtaux_ppchom = objet_ppchom.getString(TAG_NVTAUX);
                        nvtaux_mcdd1 = objet_mcdd1.getString(TAG_NVTAUX);
                        nvtaux_mcdd3 = objet_mcdd3.getString(TAG_NVTAUX);
                        nvtaux_mcddu3 = objet_mcddu3.getString(TAG_NVTAUX);


                        date_pschom = objet_pschom.getString(TAG_DATE);
                        date_ppchom = objet_ppchom.getString(TAG_DATE);
                        date_mcdd1 = objet_mcdd1.getString(TAG_DATE);
                        date_mcdd3 = objet_mcdd3.getString(TAG_DATE);
                        date_mcddu3 = objet_mcddu3.getString(TAG_DATE);


                        // créer un nouvel HashMap
                        HashMap<String, String> map = new HashMap<String, String>();


                    }
                } else {
                    // Rien est trouvé


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();


            // j'affiche le résultat de la tâche effectuée en arrière plan
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Je compare la date de validité (database)
                     * avec la date du jour;
                     * J'affiche les taux en fonction des résultats
                     * */

                    if (reportDate.compareTo(date_pschom) <= 0) {
                        txt_pschom.setText(actaux_pschom);
                    }
                    else {
                        txt_pschom.setText(nvtaux_pschom);
                    }
                    if (reportDate.compareTo(date_ppchom) <= 0) {
                        txt_ppchom.setText(actaux_ppchom);
                    }
                    else {
                        txt_ppchom.setText(nvtaux_ppchom);
                    }
                    if (reportDate.compareTo(date_mcdd1) <= 0) {
                        txt_mcdd1.setText(actaux_mcdd1);
                    }
                    else {
                        txt_mcdd1.setText(nvtaux_mcdd1);
                    }
                    if (reportDate.compareTo(date_mcdd3) <= 0) {
                        txt_mcdd3.setText(actaux_mcdd3);
                    }
                    else {
                        txt_mcdd3.setText(nvtaux_mcdd3);
                    }
                    if (reportDate.compareTo(date_mcddu3) <= 0) {
                        txt_mcddu3.setText(actaux_mcddu3);
                    }
                    else {
                        txt_mcddu3.setText(nvtaux_mcddu3);
                    }


                }
            });



        }

    }
}


