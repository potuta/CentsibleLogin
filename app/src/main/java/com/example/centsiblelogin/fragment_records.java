package com.example.centsiblelogin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.centsiblelogin.model.CategoriesModel;
import com.example.centsiblelogin.model.RecordsModel;
import com.example.centsiblelogin.ui.BudgetViewModel;
import com.example.centsiblelogin.ui.CategoriesViewModel;
import com.example.centsiblelogin.ui.RecordsViewModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_records#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_records extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CategoriesViewModel categoriesViewModel;
    private RecordsViewModel recordsViewModel;
    private boolean isSpinnerInitialized = false;

    public fragment_records() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_records.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_records newInstance(String param1, String param2) {
        fragment_records fragment = new fragment_records();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_records, container, false);
        // Remove background when fragment is loaded
        view.setBackground(null);

        InitializeSpinner(view);

        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        recordsViewModel = new ViewModelProvider(this).get(RecordsViewModel.class);

        checkExistingData(view);

        Button addButton = view.findViewById(R.id.add);
        addButton.setOnClickListener(v -> {
            Spinner spinner = view.findViewById(R.id.spinner2);
            String spinnerText = spinner.getSelectedItem().toString();
            EditText price = view.findViewById(R.id.price);

            RecordsModel recordsModel = new RecordsModel(spinnerText, Integer.parseInt(price.getText().toString()));
            recordsViewModel.insert(recordsModel);

            recordsViewModel.getAllRecords().observe(getViewLifecycleOwner(), existingRecords -> {
                EditText record1 = view.findViewById(R.id.record1);
                EditText price1 = view.findViewById(R.id.recordprice1);

                EditText record2 = view.findViewById(R.id.record2);
                EditText price2 = view.findViewById(R.id.recordprice2);

                EditText record3 = view.findViewById(R.id.record3);
                EditText price3 = view.findViewById(R.id.recordprice3);

                if (!existingRecords.isEmpty()) {
                    // Get the LAST record (newest one)
                    RecordsModel lastRecord = existingRecords.get(existingRecords.size() - 1);

                    record1.setText(lastRecord.getCategory());
                    price1.setText(String.valueOf(lastRecord.getValue()));

                    // Shift the older records down
                    if (existingRecords.size() > 1) {
                        record2.setText(existingRecords.get(existingRecords.size() - 2).getCategory());
                        price2.setText(String.valueOf(existingRecords.get(existingRecords.size() - 2).getValue()));
                    } else {
                        record2.setText("");
                        price2.setText("");
                    }

                    if (existingRecords.size() > 2) {
                        record3.setText(existingRecords.get(existingRecords.size() - 3).getCategory());
                        price3.setText(String.valueOf(existingRecords.get(existingRecords.size() - 3).getValue()));
                    } else {
                        record3.setText("");
                        price3.setText("");
                    }
                }
            });
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void checkExistingData(View view){

        recordsViewModel.getAllRecords().observe(getViewLifecycleOwner(), existingRecords -> {
            EditText record1 = view.findViewById(R.id.record1);
            EditText price1 = view.findViewById(R.id.recordprice1);

            EditText record2 = view.findViewById(R.id.record2);
            EditText price2 = view.findViewById(R.id.recordprice2);

            EditText record3 = view.findViewById(R.id.record3);
            EditText price3 = view.findViewById(R.id.recordprice3);

            if (!existingRecords.isEmpty()) {
                // Get the LAST record (newest one)
                RecordsModel lastRecord = existingRecords.get(existingRecords.size() - 1);

                record1.setText(lastRecord.getCategory());
                price1.setText(String.valueOf(lastRecord.getValue()));

                // Shift the older records down
                if (existingRecords.size() > 1) {
                    record2.setText(existingRecords.get(existingRecords.size() - 2).getCategory());
                    price2.setText(String.valueOf(existingRecords.get(existingRecords.size() - 2).getValue()));
                } else {
                    record2.setText("");
                    price2.setText("");
                }

                if (existingRecords.size() > 2) {
                    record3.setText(existingRecords.get(existingRecords.size() - 3).getCategory());
                    price3.setText(String.valueOf(existingRecords.get(existingRecords.size() - 3).getValue()));
                } else {
                    record3.setText("");
                    price3.setText("");
                }
            }
        });

    }

    private void InitializeSpinner(View view) {
        Spinner spinner = view.findViewById(R.id.spinner2);
        List<String> periods = new ArrayList<>();
        periods.add("Food");
        periods.add("Bills");
        periods.add("Transportation");
        periods.add("Shopping");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, periods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (isSpinnerInitialized) {
                    checkExistingData(view);
                } else {
                    isSpinnerInitialized = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

}