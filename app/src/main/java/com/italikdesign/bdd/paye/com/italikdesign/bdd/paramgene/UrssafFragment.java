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




public class UrssafFragment extends Fragment {

        // Progress Dialog
        private ProgressDialog pDialog;

        View rootView;
        TextView txt_psm, txt_ppm, txt_psv, txt_ppv, txt_psvp, txt_ppvp, txt_ppc, txt_ppf, txt_ppa, txt_ppac, txt_ppfor, txt_ppforpl, txt_ppforc, txt_ppforr;


        String id_psm, id_ppm, id_psv, id_ppv, id_psvp, id_ppvp, id_ppc, id_ppf, id_ppa, id_ppac, id_ppfor, id_ppforpl, id_ppforc, id_ppforr;
        String nvtaux_psm, nvtaux_ppm, nvtaux_psv, nvtaux_ppv, nvtaux_psvp, nvtaux_ppvp, nvtaux_ppc, nvtaux_ppf, nvtaux_ppa, nvtaux_ppac, nvtaux_ppfor, nvtaux_ppforpl, nvtaux_ppforc, nvtaux_ppforr;
        String actaux_psm, actaux_ppm, actaux_psv, actaux_ppv, actaux_psvp, actaux_ppvp, actaux_ppc, actaux_ppf, actaux_ppa, actaux_ppac, actaux_ppfor, actaux_ppforpl, actaux_ppforc, actaux_ppforr;
        String date_psm, date_ppm, date_psv, date_ppv, date_psvp, date_ppvp, date_ppc, date_ppf, date_ppa, date_ppac, date_ppfor, date_ppforpl, date_ppforc, date_ppforr;



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
        JSONObject objet_psm, objet_ppm, objet_psv, objet_ppv, objet_psvp, objet_ppvp, objet_ppc, objet_ppf, objet_ppa, objet_ppac, objet_ppfor, objet_ppforpl, objet_ppforc, objet_ppforr = null;




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                rootView = inflater.inflate(R.layout.fragment_urssaf, container, false);
                // Inflate the layout for this fragment



                // Hashmap for ListView
                tauxxList = new ArrayList<HashMap<String, String>>();

                txt_psm = (TextView) rootView.findViewById(R.id.psmaladie);
                txt_ppm = (TextView) rootView.findViewById(R.id.ppmaladie);
                txt_psv = (TextView) rootView.findViewById(R.id.psvieillesse);
                txt_ppv = (TextView) rootView.findViewById(R.id.ppvieillesse);
                txt_psvp = (TextView) rootView.findViewById(R.id.psvieillessep);
                txt_ppvp = (TextView) rootView.findViewById(R.id.ppvieillessep);
                txt_ppc = (TextView) rootView.findViewById(R.id.ppcontribution);
                txt_ppf = (TextView) rootView.findViewById(R.id.ppfnalp);
                txt_ppa = (TextView) rootView.findViewById(R.id.ppallocfam);
                txt_ppac = (TextView) rootView.findViewById(R.id.ppallocfamcomp);
                txt_ppfor = (TextView) rootView.findViewById(R.id.ppforfaitsoc);
                txt_ppforpl = (TextView) rootView.findViewById(R.id.ppforfaitsocpl);
                txt_ppforc= (TextView) rootView.findViewById(R.id.ppforfaitsoc50);
                txt_ppforr = (TextView) rootView.findViewById(R.id.ppforfaitsocred);


                // Loading products in Background Thread
                new LoadAllProducts().execute();

                View.OnClickListener listener = new View.OnClickListener() {

                        public void onClick(View v) {

                                //Gestion du click sur chaque textview en y affectant les PID correspondant
                                switch (v.getId()) {
                                        case R.id.psmaladie:

                                                // getting values from selected ListItem

                                                // Starting new intent
                                                Intent in = new Intent(getActivity().getApplicationContext(),
                                                        EditData.class);
                                                // je récupère le PID de la donnée cliquée
                                                in.putExtra(TAG_PID, id_psm);


                                                // starting new activity and expecting some response back
                                                startActivityForResult(in, 100);

                                                break;
                                        case R.id.ppmaladie:

                                                // getting values from selected ListItem

                                                // Starting new intent
                                                Intent in2 = new Intent(getActivity().getApplicationContext(),
                                                        EditData.class);
                                                // sending pid to next activity
                                                in2.putExtra(TAG_PID, id_ppm);


                                                // starting new activity and expecting some response back
                                                startActivityForResult(in2, 100);

                                                break;
                                        case R.id.psvieillesse:

                                                // getting values from selected ListItem

                                                // Starting new intent
                                                Intent in3 = new Intent(getActivity().getApplicationContext(),
                                                        EditData.class);
                                                // sending pid to next activity
                                                in3.putExtra(TAG_PID, id_psv);


                                                // starting new activity and expecting some response back
                                                startActivityForResult(in3, 100);

                                                break;
                                        case R.id.ppvieillesse:

                                                // getting values from selected ListItem

                                                // Starting new intent
                                                Intent in4 = new Intent(getActivity().getApplicationContext(),
                                                        EditData.class);
                                                // sending pid to next activity
                                                in4.putExtra(TAG_PID, id_ppv);


                                                // starting new activity and expecting some response back
                                                startActivityForResult(in4, 100);

                                                break;
                                        case R.id.psvieillessep:

                                                // getting values from selected ListItem

                                                // Starting new intent
                                                Intent in5 = new Intent(getActivity().getApplicationContext(),
                                                        EditData.class);
                                                // sending pid to next activity
                                                in5.putExtra(TAG_PID, id_psvp);


                                                // starting new activity and expecting some response back
                                                startActivityForResult(in5, 100);

                                                break;
                                        case R.id.ppvieillessep:

                                                // getting values from selected ListItem

                                                // Starting new intent
                                                Intent in6 = new Intent(getActivity().getApplicationContext(),
                                                        EditData.class);
                                                // sending pid to next activity
                                                in6.putExtra(TAG_PID, id_ppvp);


                                                // starting new activity and expecting some response back
                                                startActivityForResult(in6, 100);

                                                break;
                                        case R.id.ppcontribution:

                                                // getting values from selected ListItem

                                                // Starting new intent
                                                Intent in7 = new Intent(getActivity().getApplicationContext(),
                                                        EditData.class);
                                                // sending pid to next activity
                                                in7.putExtra(TAG_PID, id_ppc);


                                                // starting new activity and expecting some response back
                                                startActivityForResult(in7, 100);

                                                break;
                                        case R.id.ppfnalp:

                                                // getting values from selected ListItem

                                                // Starting new intent
                                                Intent in8 = new Intent(getActivity().getApplicationContext(),
                                                        EditData.class);
                                                // sending pid to next activity
                                                in8.putExtra(TAG_PID, id_ppf);


                                                // starting new activity and expecting some response back
                                                startActivityForResult(in8, 100);

                                                break;
                                        case R.id.ppallocfam:

                                                // getting values from selected ListItem

                                                // Starting new intent
                                                Intent in9 = new Intent(getActivity().getApplicationContext(),
                                                        EditData.class);
                                                // sending pid to next activity
                                                in9.putExtra(TAG_PID, id_ppa);


                                                // starting new activity and expecting some response back
                                                startActivityForResult(in9, 100);

                                                break;
                                        case R.id.ppallocfamcomp:

                                                // getting values from selected ListItem

                                                // Starting new intent
                                                Intent in10 = new Intent(getActivity().getApplicationContext(),
                                                        EditData.class);
                                                // sending pid to next activity
                                                in10.putExtra(TAG_PID, id_ppac);


                                                // starting new activity and expecting some response back
                                                startActivityForResult(in10, 100);

                                                break;
                                        case R.id.ppforfaitsoc:

                                                // getting values from selected ListItem

                                                // Starting new intent
                                                Intent in11 = new Intent(getActivity().getApplicationContext(),
                                                        EditData.class);
                                                // sending pid to next activity
                                                in11.putExtra(TAG_PID, id_ppfor);


                                                // starting new activity and expecting some response back
                                                startActivityForResult(in11, 100);

                                                break;
                                        case R.id.ppforfaitsocpl:

                                                // getting values from selected ListItem

                                                // Starting new intent
                                                Intent in12 = new Intent(getActivity().getApplicationContext(),
                                                        EditData.class);
                                                // sending pid to next activity
                                                in12.putExtra(TAG_PID, id_ppforpl);


                                                // starting new activity and expecting some response back
                                                startActivityForResult(in12, 100);

                                                break;
                                        case R.id.ppforfaitsoc50:

                                                // getting values from selected ListItem

                                                // Starting new intent
                                                Intent in13 = new Intent(getActivity().getApplicationContext(),
                                                        EditData.class);
                                                // sending pid to next activity
                                                in13.putExtra(TAG_PID, id_ppforc);


                                                // starting new activity and expecting some response back
                                                startActivityForResult(in13, 100);

                                                break;
                                        case R.id.ppforfaitsocred:

                                                // getting values from selected ListItem

                                                // Starting new intent
                                                Intent in14 = new Intent(getActivity().getApplicationContext(),
                                                        EditData.class);
                                                // sending pid to next activity
                                                in14.putExtra(TAG_PID, id_ppforr);


                                                // starting new activity and expecting some response back
                                                startActivityForResult(in14, 100);

                                                break;
                                }
                        }
                };
                        //Appel de chaque listener
                        txt_psm.setOnClickListener(listener);
                        txt_ppm.setOnClickListener(listener);
                        txt_psv.setOnClickListener(listener);
                        txt_ppv.setOnClickListener(listener);
                        txt_psvp.setOnClickListener(listener);
                        txt_ppvp.setOnClickListener(listener);
                        txt_ppc.setOnClickListener(listener);
                        txt_ppf.setOnClickListener(listener);
                        txt_ppa.setOnClickListener(listener);
                        txt_ppac.setOnClickListener(listener);
                        txt_ppfor.setOnClickListener(listener);
                        txt_ppforpl.setOnClickListener(listener);
                        txt_ppforc.setOnClickListener(listener);
                        txt_ppforr.setOnClickListener(listener);


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
                                                objet_psm = tauxx.getJSONObject(0);
                                                objet_ppm = tauxx.getJSONObject(1);
                                                objet_psv = tauxx.getJSONObject(2);
                                                objet_ppv = tauxx.getJSONObject(3);
                                                objet_psvp = tauxx.getJSONObject(4);
                                                objet_ppvp = tauxx.getJSONObject(5);
                                                objet_ppc = tauxx.getJSONObject(6);
                                                objet_ppf = tauxx.getJSONObject(7);
                                                objet_ppa = tauxx.getJSONObject(8);
                                                objet_ppac = tauxx.getJSONObject(9);
                                                objet_ppfor = tauxx.getJSONObject(10);
                                                objet_ppforpl = tauxx.getJSONObject(11);
                                                objet_ppforc = tauxx.getJSONObject(12);
                                                objet_ppforr = tauxx.getJSONObject(13);

                                                // je récupère les données de chaque ligne liée à chaque variable définie au dessus
                                                id_psm = objet_psm.getString(TAG_PID);
                                                id_ppm = objet_ppm.getString(TAG_PID);
                                                id_psv = objet_psv.getString(TAG_PID);
                                                id_ppv = objet_ppv.getString(TAG_PID);
                                                id_psvp = objet_psvp.getString(TAG_PID);
                                                id_ppvp = objet_ppvp.getString(TAG_PID);
                                                id_ppc = objet_ppc.getString(TAG_PID);
                                                id_ppf = objet_ppf.getString(TAG_PID);
                                                id_ppa = objet_ppa.getString(TAG_PID);
                                                id_ppac = objet_ppac.getString(TAG_PID);
                                                id_ppfor = objet_ppfor.getString(TAG_PID);
                                                id_ppforpl = objet_ppforpl.getString(TAG_PID);
                                                id_ppforc = objet_ppforc.getString(TAG_PID);
                                                id_ppforr = objet_ppforr.getString(TAG_PID);


                                                actaux_psm = objet_psm.getString(TAG_ANCIENTAUX);
                                                actaux_ppm = objet_ppm.getString(TAG_ANCIENTAUX);
                                                actaux_psv = objet_psv.getString(TAG_ANCIENTAUX);
                                                actaux_ppv = objet_ppv.getString(TAG_ANCIENTAUX);
                                                actaux_psvp = objet_psvp.getString(TAG_ANCIENTAUX);
                                                actaux_ppvp = objet_ppvp.getString(TAG_ANCIENTAUX);
                                                actaux_ppc = objet_ppc.getString(TAG_ANCIENTAUX);
                                                actaux_ppf = objet_ppf.getString(TAG_ANCIENTAUX);
                                                actaux_ppa = objet_ppa.getString(TAG_ANCIENTAUX);
                                                actaux_ppac = objet_ppac.getString(TAG_ANCIENTAUX);
                                                actaux_ppfor = objet_ppfor.getString(TAG_ANCIENTAUX);
                                                actaux_ppforpl = objet_ppforpl.getString(TAG_ANCIENTAUX);
                                                actaux_ppforc = objet_ppforc.getString(TAG_ANCIENTAUX);
                                                actaux_ppforr = objet_ppforr.getString(TAG_ANCIENTAUX);


                                                nvtaux_psm = objet_psm.getString(TAG_NVTAUX);
                                                nvtaux_ppm = objet_ppm.getString(TAG_NVTAUX);
                                                nvtaux_psv = objet_psv.getString(TAG_NVTAUX);
                                                nvtaux_ppv = objet_ppv.getString(TAG_NVTAUX);
                                                nvtaux_psvp = objet_psvp.getString(TAG_NVTAUX);
                                                nvtaux_ppvp = objet_ppvp.getString(TAG_NVTAUX);
                                                nvtaux_ppc = objet_ppc.getString(TAG_NVTAUX);
                                                nvtaux_ppf = objet_ppf.getString(TAG_NVTAUX);
                                                nvtaux_ppa = objet_ppa.getString(TAG_NVTAUX);
                                                nvtaux_ppac = objet_ppac.getString(TAG_NVTAUX);
                                                nvtaux_ppfor = objet_ppfor.getString(TAG_NVTAUX);
                                                nvtaux_ppforpl = objet_ppforpl.getString(TAG_NVTAUX);
                                                nvtaux_ppforc = objet_ppforc.getString(TAG_NVTAUX);
                                                nvtaux_ppforr = objet_ppforr.getString(TAG_NVTAUX);


                                                date_psm = objet_psm.getString(TAG_DATE);
                                                date_ppm = objet_ppm.getString(TAG_DATE);
                                                date_psv = objet_psv.getString(TAG_DATE);
                                                date_ppv = objet_ppv.getString(TAG_DATE);
                                                date_psvp = objet_psvp.getString(TAG_DATE);
                                                date_ppvp = objet_ppvp.getString(TAG_DATE);
                                                date_ppc = objet_ppc.getString(TAG_DATE);
                                                date_ppf = objet_ppf.getString(TAG_DATE);
                                                date_ppa = objet_ppa.getString(TAG_DATE);
                                                date_ppac = objet_ppac.getString(TAG_DATE);
                                                date_ppfor = objet_ppfor.getString(TAG_DATE);
                                                date_ppforpl = objet_ppforpl.getString(TAG_DATE);
                                                date_ppforc = objet_ppforc.getString(TAG_DATE);
                                                date_ppforr = objet_ppforr.getString(TAG_DATE);

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

                                        if (reportDate.compareTo(date_psm) <= 0) {
                                                txt_psm.setText(actaux_psm);
                                        }
                                        else {
                                                txt_psm.setText(nvtaux_psm);
                                        }
                                        if (reportDate.compareTo(date_ppm) <= 0) {
                                                txt_ppm.setText(actaux_ppm);
                                        }
                                        else {
                                                txt_ppm.setText(nvtaux_ppm);
                                        }
                                        if (reportDate.compareTo(date_psv) <= 0) {
                                                txt_psv.setText(actaux_psv);
                                        }
                                        else {
                                                txt_psv.setText(nvtaux_psv);
                                        }
                                        if (reportDate.compareTo(date_ppv) <= 0) {
                                                txt_ppv.setText(actaux_ppv);
                                        }
                                        else {
                                                txt_ppv.setText(nvtaux_ppv);
                                        }
                                        if (reportDate.compareTo(date_psvp) <= 0) {
                                                txt_psvp.setText(actaux_psvp);
                                        }
                                        else {
                                                txt_psvp.setText(nvtaux_psvp);
                                        }
                                        if (reportDate.compareTo(date_ppvp) <= 0) {
                                                txt_ppvp.setText(actaux_ppvp);
                                        }
                                        else {
                                                txt_ppvp.setText(nvtaux_ppvp);
                                        }
                                        if (reportDate.compareTo(date_ppc) <= 0) {
                                                txt_ppc.setText(actaux_ppc);
                                        }
                                        else {
                                                txt_ppc.setText(nvtaux_ppc);
                                        }
                                        if (reportDate.compareTo(date_ppf) <= 0) {
                                                txt_ppf.setText(actaux_ppf);
                                        }
                                        else {
                                                txt_ppf.setText(nvtaux_ppf);
                                        }
                                        if (reportDate.compareTo(date_ppa) <= 0) {
                                                txt_ppa.setText(actaux_ppa);
                                        }
                                        else {
                                                txt_ppa.setText(nvtaux_ppa);
                                        }
                                        if (reportDate.compareTo(date_ppac) <= 0) {
                                                txt_ppac.setText(actaux_ppac);
                                        }
                                        else {
                                                txt_ppac.setText(nvtaux_ppac);
                                        }
                                        if (reportDate.compareTo(date_ppfor) <= 0) {
                                                txt_ppfor.setText(actaux_ppfor);
                                        }
                                        else {
                                                txt_ppfor.setText(nvtaux_ppfor);
                                        }
                                        if (reportDate.compareTo(date_ppforpl) <= 0) {
                                                txt_ppforpl.setText(actaux_ppforpl);
                                        }
                                        else {
                                                txt_ppforpl.setText(nvtaux_ppforpl);
                                        }
                                        if (reportDate.compareTo(date_ppforc) <= 0) {
                                                txt_ppforc.setText(actaux_ppforc);
                                        }
                                        else {
                                                txt_ppforc.setText(nvtaux_ppforc);
                                        }
                                        if (reportDate.compareTo(date_ppforr) <= 0) {
                                                txt_ppforr.setText(actaux_ppforr);
                                        }
                                        else {
                                                txt_ppforr.setText(nvtaux_ppforr);
                                        }

                                }
                        });



                }

        }
}

