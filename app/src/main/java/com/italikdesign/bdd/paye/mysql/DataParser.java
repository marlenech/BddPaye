package com.italikdesign.bdd.paye.mysql;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by italikdesign on 23/10/2016.
 */

public class DataParser extends AsyncTask<Void, Void, Integer> {

    Context c;
    GridView grid;
    int jsonData;

    ProgressDialog pd;

    ArrayList<String> spacecrafts = new ArrayList<>();

    public DataParser(Context c, GridView grid, Integer jsonData) {
        this.c = c;
        this.grid = grid;
        this.jsonData = jsonData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Parse");
        pd.setMessage("Parsing...Please wait");
        pd.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parseData();
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        pd.dismiss();

        if (result==0)
        {
            Toast.makeText(c, "Unable to parse", Toast.LENGTH_SHORT).show();
        }else
        {
            ArrayAdapter adapter = new ArrayAdapter(c, android.R.layout.simple_list_item_single_choice, spacecrafts);
            grid.setAdapter(adapter);

            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(c, spacecrafts.get(position), Toast.LENGTH_SHORT).show();
                }
            });
        }



    }

    private int parseData() {
        try {

            JSONArray ja = new JSONArray(jsonData);
            JSONObject jo = null;

            spacecrafts.clear();

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);

                String name = jo.getString("name");

                spacecrafts.add(name);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }
}