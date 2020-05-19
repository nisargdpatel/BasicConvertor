package com.example.basicconvertor;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;

public class DistanceFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private SharedPreferences sharedPreferences;
    private String file = "com.example.basicconvertor";

    private final String FROM_NUM_KEY_DIST = "FROM_NUM_DIST";
    private final String TO_NUM_KEY_DIST = "TO_NUM_DIST";
    private final String FROM_UNIT_KEY_DIST = "FROM_UNIT_DIST";
    private final String TO_UNIT_KEY_DIST = "TO_UNIT_DIST";

    private EditText fromTextDist;
    private TextView toTextDist;
    private Spinner spinnerFromDist;
    private Spinner spinnerToDist;
    private Button calculateButtonDist;

    private double fromNumDist;
    private double toNumDist;
    private String fromUnitDist = "mi";
    private String toUnitDist = "km";

    final String[] valuesMK = {"mi", "km"};
    final String[] valuesKM = {"km", "mi"};

    public static final DecimalFormat formatter = new DecimalFormat("#.##");


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.distance_fragment, container, false);

        //Initializing specific views
        fromTextDist = view.findViewById(R.id.from_dist_text);
        spinnerFromDist = view.findViewById(R.id.spinner_dist_from);
        toTextDist = view.findViewById(R.id.to_dist_text);
        spinnerToDist = view.findViewById(R.id.spinner_dist_to);
        calculateButtonDist = view.findViewById(R.id.button_dist_calculate);

        //Default values for degrees
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesMK);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerFromDist.setAdapter(adapter);
        spinnerFromDist.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesKM);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerToDist.setAdapter(adapter2);
        spinnerToDist.setOnItemSelectedListener(this);

        //Calculate button
        calculateButtonDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromUnitDist = spinnerFromDist.getSelectedItem().toString();
                toUnitDist = spinnerToDist.getSelectedItem().toString();
                if (fromUnitDist.equals("mi") && toUnitDist.equals("km") && !fromTextDist.getText().toString().isEmpty())
                {
                    fromNumDist = Double.parseDouble(fromTextDist.getText().toString());
                    toNumDist = (fromNumDist * 1.609);
                    toTextDist.setText(formatter.format(toNumDist));

                }
                else if (fromUnitDist.equals("km") && toUnitDist.equals("mi") && !fromTextDist.getText().toString().isEmpty())
                {
                    fromNumDist = Double.parseDouble(fromTextDist.getText().toString());
                    toNumDist = (fromNumDist / 1.609);
                    toTextDist.setText(formatter.format(toNumDist));
                }
                else if (fromTextDist.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(), "FROM field is empty", Toast.LENGTH_LONG).show();
                }
                else {
                    fromNumDist = Double.parseDouble(fromTextDist.getText().toString());
                    toNumDist = fromNumDist;
                    toTextDist.setText(formatter.format(toNumDist));
                }
            }
        });




        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Setting spinner text style
        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) parent.getChildAt(0)).setTextSize(24);
        ((TextView) parent.getChildAt(0)).setTypeface(Typeface.MONOSPACE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Empty on purpose
    }

    @Override
    public void onResume()
    {
        super.onResume();

        sharedPreferences = this.getActivity().getSharedPreferences(file, Context.MODE_PRIVATE);

        //Assign data from Shared Preferences to individual variables
        fromNumDist = Double.parseDouble(sharedPreferences.getString(FROM_NUM_KEY_DIST, "0.0"));
        fromUnitDist = sharedPreferences.getString(FROM_UNIT_KEY_DIST, "");
        toNumDist = Double.parseDouble(sharedPreferences.getString(TO_NUM_KEY_DIST, "0.0"));
        toUnitDist = sharedPreferences.getString(TO_UNIT_KEY_DIST, "");


        //Assign data from individual variables to specific widgets
        fromTextDist.setText(String.valueOf(fromNumDist));
        if (fromUnitDist.equals("mi"))
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesMK);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinnerFromDist.setAdapter(adapter);
            spinnerFromDist.setOnItemSelectedListener(this);
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesKM);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinnerFromDist.setAdapter(adapter);
            spinnerFromDist.setOnItemSelectedListener(this);
        }
        if (toUnitDist.equals("mi"))
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesMK);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinnerToDist.setAdapter(adapter);
            spinnerToDist.setOnItemSelectedListener(this);
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesKM);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinnerToDist.setAdapter(adapter);
            spinnerToDist.setOnItemSelectedListener(this);
        }

        toTextDist.setText(String.valueOf(toNumDist));

    }

    @Override
    public void onPause()
    {
        super.onPause();

        sharedPreferences = this.getActivity().getSharedPreferences(file, Context.MODE_PRIVATE);

        if (fromTextDist.getText().toString().isEmpty())
        {
            fromNumDist = 0.0;
            toNumDist = 0.0;
        } else {
            fromNumDist = Double.parseDouble(fromTextDist.getText().toString());
            toNumDist = Double.parseDouble(toTextDist.getText().toString());
        }

        fromUnitDist = spinnerFromDist.getSelectedItem().toString();
        toUnitDist = spinnerToDist.getSelectedItem().toString();




        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FROM_NUM_KEY_DIST, String.valueOf(fromNumDist));
        editor.putString(TO_NUM_KEY_DIST, String.valueOf(toNumDist));
        editor.putString(FROM_UNIT_KEY_DIST, fromUnitDist);
        editor.putString(TO_UNIT_KEY_DIST, toUnitDist);
        editor.apply();
        editor.commit();


    }
}
