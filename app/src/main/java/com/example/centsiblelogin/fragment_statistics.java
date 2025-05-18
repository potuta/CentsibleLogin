package com.example.centsiblelogin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.centsiblelogin.ui.BudgetViewModel;
import com.example.centsiblelogin.ui.RecordsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_statistics#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_statistics extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BudgetViewModel budgetViewModel;
    private RecordsViewModel recordsViewModel;
    private boolean isSpinnerInitialized = false;


    public fragment_statistics() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_statistics.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_statistics newInstance(String param1, String param2) {
        fragment_statistics fragment = new fragment_statistics();
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
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        // Remove background when fragment is loaded
        view.setBackground(null);

        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        recordsViewModel = new ViewModelProvider(this).get(RecordsViewModel.class);

        InitializeSpinner(view);

        Spinner spinner = view.findViewById(R.id.spinner3);
        int id = spinner.getSelectedItemPosition();

        recordsViewModel.getAllRecords().observe(getViewLifecycleOwner(), existingRecords -> {
            if (!existingRecords.isEmpty()) {
                TextView count1 = view.findViewById(R.id.count1);
                TextView money1 = view.findViewById(R.id.money1);
                TextView count2 = view.findViewById(R.id.count2);
                TextView money2 = view.findViewById(R.id.money2);
                TextView count3 = view.findViewById(R.id.count3);
                TextView money3 = view.findViewById(R.id.money3);
                TextView count4 = view.findViewById(R.id.count4);
                TextView money4 = view.findViewById(R.id.money4);

                count1.setText("Food");
                count2.setText("Transportation");
                count3.setText("Shopping");
                count4.setText("Bills");

                int total = 0;
                int foodCount = 0;
                int foodExpense = 0;
                int transportationCount = 0;
                int transportationExpense = 0;
                int shoppingCount = 0;
                int shoppingExpense = 0;
                int billsCount = 0;
                int billsExpense = 0;

                for (int i = 0; i < existingRecords.size(); i++) {
                    total += existingRecords.get(i).getValue();
                    switch (existingRecords.get(i).getCategory()) {
                        case "Food":
                            foodCount++;
                            foodExpense += existingRecords.get(i).getValue();
                            break;
                        case "Transportation":
                            transportationCount++;
                            transportationExpense += existingRecords.get(i).getValue();
                            break;
                        case "Shopping":
                            shoppingCount++;
                            shoppingExpense += existingRecords.get(i).getValue();
                            break;
                        case "Bills":
                            billsCount++;
                            billsExpense += existingRecords.get(i).getValue();
                            break;
                    }
                }

                // Set the money TextViews AFTER calculating everything
                money1.setText(String.valueOf(foodExpense));
                money2.setText(String.valueOf(transportationExpense));
                money3.setText(String.valueOf(shoppingExpense));
                money4.setText(String.valueOf(billsExpense));

                int highest = Math.max(Math.max(foodExpense, transportationExpense), Math.max(shoppingExpense, billsExpense));
                String mostPurchasedCategory = "";

                if (highest == foodExpense) {
                    mostPurchasedCategory = "Food";
                } else if (highest == transportationExpense) {
                    mostPurchasedCategory = "Transportation";
                } else if (highest == shoppingExpense) {
                    mostPurchasedCategory = "Shopping";
                } else if (highest == billsExpense) {
                    mostPurchasedCategory = "Bills";
                }

                TextView mostPurchases = view.findViewById(R.id.mostpurchased);
                mostPurchases.setText(mostPurchasedCategory);

                TextView totalExpenses = view.findViewById(R.id.totalexpenses);
                totalExpenses.setText(String.valueOf(total));
            }
        });

        budgetViewModel.getBudget(id).observe(getViewLifecycleOwner(), existingBudget -> {
            recordsViewModel.getAllRecords().observe(getViewLifecycleOwner(), existingRecords -> {
                if (!existingRecords.isEmpty()) {
                    int total = 0;
                    for (int i = 0; i < existingRecords.size(); i++) {
                        total += existingRecords.get(i).getValue();
                    }

                    TextView moneyLeftTextView = view.findViewById(R.id.moneyleft);

                    double moneyLeft = existingBudget.getBudget() - Double.parseDouble(String.valueOf(total));

                    moneyLeftTextView.setText(String.valueOf(moneyLeft));
                }
            });
        });


        // Inflate the layout for this fragment
        return view;
    }

    private void InitializeSpinner(View view) {
        Spinner spinner = view.findViewById(R.id.spinner3);
        List<String> periods = new ArrayList<>();
        periods.add("Weekly");
        periods.add("Monthly");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, periods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (isSpinnerInitialized) {
                    isSpinnerInitialized = false;
                } else {
                    isSpinnerInitialized = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }
}