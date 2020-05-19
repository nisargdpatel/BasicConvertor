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

public class TemperatureFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private SharedPreferences sharedPreferences;
    private String file = "com.example.basicconvertor";

    private final String FROM_NUM_KEY = "FROM_NUM";
    private final String TO_NUM_KEY = "TO_NUM";
    private final String FROM_DEGREE_KEY = "FROM_DEGREE";
    private final String TO_DEGREE_KEY = "TO_DEGREE";

    private EditText fromText;
    private TextView toText;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private Button calculateButton;

    private double fromNum;
    private double toNum;
    private String fromDegree = "°F";
    private String toDegree = "°C";

    final String[] valuesFC = {"°F", "°C"};
    final String[] valuesCF = {"°C", "°F"};

    public static final DecimalFormat formatter = new DecimalFormat("#.##");

//    public void setSpinner(String temp, Spinner spinner)
//    {
//        if (temp == "°F")
//        {
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesFC);
//            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//            spinnerFrom.setAdapter(adapter);
//            spinnerFrom.setOnItemSelectedListener(this);
//        } else if (temp == "°C"){
//            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesCF);
//            adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//            spinnerTo.setAdapter(adapter2);
//            spinnerTo.setOnItemSelectedListener(this);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.temperature_fragment, container, false);

        //Initializing specific views
        fromText = view.findViewById(R.id.from_temp_text);
        spinnerFrom = view.findViewById(R.id.spinner_temp_from);
        toText = view.findViewById(R.id.to_temp_text);
        spinnerTo = view.findViewById(R.id.spinner_temp_to);
        calculateButton = view.findViewById(R.id.button_temp_calculate);

        //Default values for degrees
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesFC);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerFrom.setAdapter(adapter);
        spinnerFrom.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesCF);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerTo.setAdapter(adapter2);
        spinnerTo.setOnItemSelectedListener(this);

        //Calculate button
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDegree = spinnerFrom.getSelectedItem().toString();
                toDegree = spinnerTo.getSelectedItem().toString();
                if (fromDegree.equals("°F") && toDegree.equals("°C") && !fromText.getText().toString().isEmpty())
                {
                    fromNum = Double.parseDouble(fromText.getText().toString());
                    toNum = ((fromNum - 32)*(5.0/9.0));
                    toText.setText(formatter.format(toNum));
                }
                else if (fromDegree.equals("°C") && toDegree.equals("°F") && !fromText.getText().toString().isEmpty())
                {
                    fromNum = Double.parseDouble(fromText.getText().toString());
                    toNum = ((fromNum * (9.0/5.0))+32);
                    toText.setText(formatter.format(toNum));
                }
                else if (fromText.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(), "FROM field is empty", Toast.LENGTH_LONG).show();
                }
                else {
                    fromNum = Double.parseDouble(fromText.getText().toString());
                    toNum = fromNum;
                    toText.setText(formatter.format(toNum));
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
        fromNum = Double.parseDouble(sharedPreferences.getString(FROM_NUM_KEY, "0.0"));
        fromDegree = sharedPreferences.getString(FROM_DEGREE_KEY, "");
        toNum = Double.parseDouble(sharedPreferences.getString(TO_NUM_KEY, "0.0"));
        toDegree = sharedPreferences.getString(TO_DEGREE_KEY, "");


        //Assign data from individual variables to specific widgets
        fromText.setText(String.valueOf(fromNum));
        if (fromDegree.equals("°F"))
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesFC);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinnerFrom.setAdapter(adapter);
            spinnerFrom.setOnItemSelectedListener(this);
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesCF);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinnerFrom.setAdapter(adapter);
            spinnerFrom.setOnItemSelectedListener(this);
        }
        if (toDegree.equals("°F"))
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesFC);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinnerTo.setAdapter(adapter);
            spinnerTo.setOnItemSelectedListener(this);
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesCF);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinnerTo.setAdapter(adapter);
            spinnerTo.setOnItemSelectedListener(this);
        }

        toText.setText(String.valueOf(toNum));
    }

    @Override
    public void onPause()
    {
        super.onPause();

        sharedPreferences = this.getActivity().getSharedPreferences(file, Context.MODE_PRIVATE);

        if (fromText.getText().toString().isEmpty())
        {
            fromNum = 0.0;
            toNum = 0.0;
        } else {
            fromNum = Double.parseDouble(fromText.getText().toString());
            toNum = Double.parseDouble(toText.getText().toString());
        }

        fromDegree = spinnerFrom.getSelectedItem().toString();
        toDegree = spinnerTo.getSelectedItem().toString();




        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FROM_NUM_KEY, String.valueOf(fromNum));
        editor.putString(TO_NUM_KEY, String.valueOf(toNum));
        editor.putString(FROM_DEGREE_KEY, fromDegree);
        editor.putString(TO_DEGREE_KEY, toDegree);
        editor.apply();
        editor.commit();

    }
}
