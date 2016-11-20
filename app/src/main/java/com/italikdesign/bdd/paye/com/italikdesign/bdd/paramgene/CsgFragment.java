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

public class CsgFragment extends Fragment {

    // Progress Dialog
    private ProgressDialog pDialog;

    View rootView;
    TextView txt_csg, txt_csgi, txt_crds, txt_abatcsg;

    String id_csg, id_csgi, id_crds, id_abatcsg;
    String nvtaux_csg, nvtaux_csgi, nvtaux_crds, nvtaux_abatcsg;
    String actaux_csg, actaux_csgi, actaux_crds, actaux_abatcsg;
    String date_csg, date_csgi, date_crds, date_abatcsg;



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
    JSONObject objet_csg, objet_csgi, objet_crds, objet_abatcsg = null;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        rootView = inflater.inflate(R.layout.fragment_csg, container, false);
        // Inflate the layout for this fragment



        // Hashmap for ListView
        tauxxList = new ArrayList<HashMap<String, String>>();

        txt_csg = (TextView) rootView.findViewById(R.id.csg);
        txt_csgi = (TextView) rootView.findViewById(R.id.csgi);
        txt_crds = (TextView) rootView.findViewById(R.id.crds);
        txt_abatcsg = (TextView) rootView.findViewById(R.id.abatcsg);


        // Loading products in Background Thread
        new LoadAllProducts().execute();

        View.OnClickListener listener = new View.OnClickListener() {

            public void onClick(View v) {

                //Gestion du click sur chaque textview en y affectant les PID correspondant
                switch (v.getId()) {
                    case R.id.csg:

                        // getting values from selected ListItem

                        // Starting new intent
                        Intent in = new Intent(getActivity().getApplicationContext(),
                                EditData.class);
                        // je récupère le PID de la donnée cliquée
                        in.putExtra(TAG_PID, id_csg);


                        // starting new activity and expecting some response back
                        startActivityForResult(in, 100);

                        break;
                    case R.id.csgi:

                        // getting values from selected ListItem

                        // Starting new intent
                        Intent in2 = new Intent(getActivity().getApplicationContext(),
                                EditData.class);
                        // sending pid to next activity
                        in2.putExtra(TAG_PID, id_csgi);


                        // starting new activity and expecting some response back
                        startActivityForResult(in2, 100);

                        break;
                    case R.id.crds:

                        // getting values from selected ListItem

                        // Starting new intent
                        Intent in3 = new Intent(getActivity().getApplicationContext(),
                                EditData.class);
                        // sending pid to next activity
                        in3.putExtra(TAG_PID, id_crds);


                        // starting new activity and expecting some response back
                        startActivityForResult(in3, 100);

                        break;
                    case R.id.abatcsg:

                        // getting values from selected ListItem

                        // Starting new intent
                        Intent in4 = new Intent(getActivity().getApplicationContext(),
                                EditData.class);
                        // sending pid to next activity
                        in4.putExtra(TAG_PID, id_abatcsg);


                        // starting new activity and expecting some response back
                        startActivityForResult(in4, 100);

                        break;
                }
            }
        };
        //Appel de chaque listener
        txt_csg.setOnClickListener(listener);
        txt_csgi.setOnClickListener(listener);
        txt_crds.setOnClickListener(listener);
        txt_abatcsg.setOnClickListener(listener);



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
                        objet_csg = tauxx.getJSONObject(17);
                        objet_csgi = tauxx.getJSONObject(18);
                        objet_crds = tauxx.getJSONObject(19);
                        objet_abatcsg = tauxx.getJSONObject(20);


                        // je récupère les données de chaque ligne liée à chaque variable définie au dessus
                        id_csg = objet_csg.getString(TAG_PID);
                        id_csgi = objet_csgi.getString(TAG_PID);
                        id_crds = objet_crds.getString(TAG_PID);
                        id_abatcsg = objet_abatcsg.getString(TAG_PID);



                        actaux_csg = objet_csg.getString(TAG_ANCIENTAUX);
                        actaux_csgi = objet_csgi.getString(TAG_ANCIENTAUX);
                        actaux_crds = objet_crds.getString(TAG_ANCIENTAUX);
                        actaux_abatcsg = objet_abatcsg.getString(TAG_ANCIENTAUX);



                        nvtaux_csg = objet_csg.getString(TAG_NVTAUX);
                        nvtaux_csgi = objet_csgi.getString(TAG_NVTAUX);
                        nvtaux_crds = objet_crds.getString(TAG_NVTAUX);
                        nvtaux_abatcsg = objet_abatcsg.getString(TAG_NVTAUX);


                        date_csg = objet_csg.getString(TAG_DATE);
                        date_csgi = objet_csgi.getString(TAG_DATE);
                        date_crds = objet_crds.getString(TAG_DATE);
                        date_abatcsg = objet_abatcsg.getString(TAG_DATE);


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

                    if (reportDate.compareTo(date_csg) <= 0) {
                        txt_csg.setText(actaux_csg);
                    }
                    else {
                        txt_csg.setText(nvtaux_csg);
                    }
                    if (reportDate.compareTo(date_csgi) <= 0) {
                        txt_csgi.setText(actaux_csgi);
                    }
                    else {
                        txt_csgi.setText(nvtaux_csgi);
                    }
                    if (reportDate.compareTo(date_crds) <= 0) {
                        txt_crds.setText(actaux_crds);
                    }
                    else {
                        txt_crds.setText(nvtaux_crds);
                    }
                    if (reportDate.compareTo(date_abatcsg) <= 0) {
                        txt_abatcsg.setText(actaux_abatcsg);
                    }
                    else {
                        txt_abatcsg.setText(nvtaux_abatcsg);
                    }


                }
            });



        }

    }
}


