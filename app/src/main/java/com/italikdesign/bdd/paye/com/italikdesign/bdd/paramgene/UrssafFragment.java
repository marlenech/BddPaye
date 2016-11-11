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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.italikdesign.bdd.paye.MainActivity;
import com.italikdesign.bdd.paye.R;
import com.italikdesign.bdd.paye.mysql.Downloader;
import com.italikdesign.bdd.paye.mysql.JSONParser;
import com.italikdesign.bdd.paye.mysql.Sender;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.italikdesign.bdd.paye.R.id.inputAncienTaux;
import static com.italikdesign.bdd.paye.R.id.inputDate;
import static com.italikdesign.bdd.paye.R.id.inputName;
import static com.italikdesign.bdd.paye.R.id.inputNewTaux;


public class UrssafFragment extends Fragment {

        // Progress Dialog
        private ProgressDialog pDialog;

        View rootView;
        GridView grid;




        // Creating JSON Parser object
        JSONParser jParser = new JSONParser();

        ArrayList<HashMap<String, String>> tauxxList;


        // JSON Node names
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_PRODUCTS = "tauxx";
        private static final String TAG_PID = "pid";
        private static final String TAG_NVTAUX = "nvtaux";

        // products JSONArray
        JSONArray tauxx = null;



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                rootView = inflater.inflate(R.layout.fragment_urssaf, container, false);
                // Inflate the layout for this fragment



                // Hashmap for ListView
                tauxxList = new ArrayList<HashMap<String, String>>();

                grid = (GridView) rootView.findViewById(R.id.grid);

                // Loading products in Background Thread
                new LoadAllProducts().execute();


                // on seleting single product
                // launching Edit Product Screen
                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                                // getting values from selected ListItem
                                String pid = ((TextView) rootView.findViewById(R.id.pid)).getText()
                                        .toString();

                                // Starting new intent
                                Intent in = new Intent(getActivity().getApplicationContext(),
                                        EditData.class);
                                // sending pid to next activity
                                in.putExtra(TAG_PID, pid);

                                // starting new activity and expecting some response back
                                startActivityForResult(in, 100);
                        }
                });


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
                        pDialog.setMessage("Loading products. Please wait...");
                        pDialog.setIndeterminate(false);
                        pDialog.setCancelable(false);
                        pDialog.show();
                }

                /**
                 * getting All products from url
                 * */
                protected String doInBackground(String... args) {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        // getting JSON string from URL
                        JSONObject json = jParser.makeHttpRequest(getResources().getString(R.string.all_products), "GET", params);

                        // Check your log cat for JSON reponse
                        Log.d("All Products: ", json.toString());

                        try {
                                // Checking for SUCCESS TAG
                                int success = json.getInt(TAG_SUCCESS);

                                if (success == 1) {
                                        // products found
                                        // Getting Array of Products
                                        tauxx = json.getJSONArray(TAG_PRODUCTS);

                                        // looping through All Products
                                        for (int i = 0; i < tauxx.length(); i++) {
                                                JSONObject c = tauxx.getJSONObject(i);

                                                // Storing each json item in variable
                                                String id = c.getString(TAG_PID);
                                                String nvtaux = c.getString(TAG_NVTAUX);

                                                // creating new HashMap
                                                HashMap<String, String> map = new HashMap<String, String>();

                                                // adding each child node to HashMap key => value
                                                map.put(TAG_PID, id);
                                                map.put(TAG_NVTAUX, nvtaux);

                                                // adding HashList to ArrayList
                                                tauxxList.add(map);
                                        }
                                } else {
                                        // no products found
                                        // Launch Add New product Activity

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


                        // updating UI from Background Thread
                        getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                        /**
                                         * Updating parsed JSON data into ListView
                                         * */

                                                ListAdapter adapter = new SimpleAdapter(
                                                        getActivity(), tauxxList,
                                                        R.layout.list_item, new String[]{TAG_NVTAUX, TAG_NVTAUX},
                                                        new int[]{R.id.psmaladie, R.id.psvieillesse});

                                        // updating listview
                                        grid.setAdapter(adapter);
                                }
                        });



                }

        }
}

