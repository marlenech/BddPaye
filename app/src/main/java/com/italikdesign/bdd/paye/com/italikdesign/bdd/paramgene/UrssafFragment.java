package com.italikdesign.bdd.paye.com.italikdesign.bdd.paramgene;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.italikdesign.bdd.paye.R;
import com.italikdesign.bdd.paye.mysql.Downloader;
import com.italikdesign.bdd.paye.mysql.JSONParser;
import com.italikdesign.bdd.paye.mysql.Sender;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;




public class UrssafFragment extends Fragment {

        // Progress Dialog
        private ProgressDialog pDialog;

        View rootView;
        TextView txone, txtwo;


        String id, id2, pid, nvtaux, nvtaux2, actaux, actaux2, date1, date2;


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

        // JSONArray et JSONObject
        JSONArray tauxx = null;
        JSONObject objet = null;
        JSONObject objet1 = null;



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                rootView = inflater.inflate(R.layout.fragment_urssaf, container, false);
                // Inflate the layout for this fragment



                // Hashmap for ListView
                tauxxList = new ArrayList<HashMap<String, String>>();

                txone = (TextView) rootView.findViewById(R.id.psmaladie);
                txtwo = (TextView) rootView.findViewById(R.id.ppmaladie);




                // Loading products in Background Thread
                new LoadAllProducts().execute();

                View.OnClickListener listener = new View.OnClickListener() {

                        public void onClick(View v) {

                                switch (v.getId()) {
                                        case R.id.psmaladie:

                                                // getting values from selected ListItem

                                                // Starting new intent
                                                Intent in = new Intent(getActivity().getApplicationContext(),
                                                        EditData.class);
                                                // sending pid to next activity
                                                in.putExtra(TAG_PID, id);


                                                // starting new activity and expecting some response back
                                                startActivityForResult(in, 100);

                                                break;
                                        case R.id.ppmaladie:

                                                // getting values from selected ListItem

                                                // Starting new intent
                                                Intent in2 = new Intent(getActivity().getApplicationContext(),
                                                        EditData.class);
                                                // sending pid to next activity
                                                in2.putExtra(TAG_PID, id2);


                                                // starting new activity and expecting some response back
                                                startActivityForResult(in2, 100);

                                                break;

                                }
                        }
                };

                        txone.setOnClickListener(listener);
                        txtwo.setOnClickListener(listener);

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
                        Intent intent = getActivity().getIntent();
                        getActivity().finish();
                        startActivity(intent);
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
                                                objet = tauxx.getJSONObject(0);
                                                objet1 = tauxx.getJSONObject(1);

                                                // je récupère les données de chaque ligne liée à chaque variable définie au dessus
                                                id = objet.getString(TAG_PID);
                                                id2 = objet1.getString(TAG_PID);
                                                actaux = objet.getString(TAG_ANCIENTAUX);
                                                actaux2 = objet1.getString(TAG_ANCIENTAUX);
                                                nvtaux = objet.getString(TAG_NVTAUX);
                                                nvtaux2 = objet1.getString(TAG_NVTAUX);
                                                date1 = objet.getString(TAG_DATE);
                                                date2 = objet1.getString(TAG_DATE);

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

                                        if (reportDate.compareTo(date1) <= 0) {
                                                txone.setText(actaux);
                                        }
                                        else {
                                                txone.setText(nvtaux);
                                        }
                                                txtwo.setText(nvtaux2);



                                }
                        });



                }

        }
}

