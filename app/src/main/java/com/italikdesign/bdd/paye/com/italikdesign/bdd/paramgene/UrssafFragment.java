package com.italikdesign.bdd.paye.com.italikdesign.bdd.paramgene;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.italikdesign.bdd.paye.R;



public class UrssafFragment extends Fragment {

private View rootView;


public UrssafFragment() {
        // Required empty public constructor
        }


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_urssaf, container, false);
        // Inflate the layout for this fragment




        return rootView;

        }


        }
