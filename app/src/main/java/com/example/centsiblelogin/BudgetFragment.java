package com.example.centsiblelogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.centsiblelogin.model.BudgetModel;
import com.example.centsiblelogin.ui.BudgetViewModel;

import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment {

    private BudgetViewModel budgetViewModel;
    private boolean isSpinnerInitialized = false;

    public BudgetFragment() {}

    public static BudgetFragment newInstance(String param1, String param2) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        view.setBackground(null);

        InitializeSpinner(view);

        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        int id = getSpinnerPostion(view);

        checkExistingData(view, id);

        InitializeButton(view);

        return view;
    }

    private void checkExistingData(View view, int id) {
        budgetViewModel.getBudget(id).removeObservers(getViewLifecycleOwner());
        budgetViewModel.getBudget(id).observe(getViewLifecycleOwner(), existingBudget -> {
            TextView budgetTV = view.findViewById(R.id.budgetmoney);
            TextView targetSavingsTV = view.findViewById(R.id.targetmoney);
            TextView dailyExpensesTV = view.findViewById(R.id.dailymoney);
            Button calculateButton = view.findViewById(R.id.calculate);

            if (existingBudget != null) {
                budgetTV.setText(String.valueOf(existingBudget.getBudget()));
                targetSavingsTV.setText(String.valueOf(existingBudget.getTargetSavings()));
                dailyExpensesTV.setText(String.valueOf(computeDailyExpenses(existingBudget.getBudget(), existingBudget.getTargetSavings(), existingBudget.getId())));
                calculateButton.setText("Update");
            } else {
                budgetTV.setText("0.00");
                targetSavingsTV.setText("0.00");
                dailyExpensesTV.setText("0.00");
                calculateButton.setText("Calculate");

            }
        });
    }

    private void updateUI(View view, BudgetModel existingBudget) {
        TextView budgetTV = view.findViewById(R.id.budgetmoney);
        TextView targetSavingsTV = view.findViewById(R.id.targetmoney);
        TextView dailyExpensesTV = view.findViewById(R.id.dailymoney);
        Button calculateButton = view.findViewById(R.id.calculate);

        if (existingBudget != null) {
            budgetTV.setText(String.valueOf(existingBudget.getBudget()));
            targetSavingsTV.setText(String.valueOf(existingBudget.getTargetSavings()));
            dailyExpensesTV.setText(String.valueOf(computeDailyExpenses(existingBudget.getBudget(), existingBudget.getTargetSavings(), existingBudget.getId())));
            calculateButton.setText("Update");
        } else {
            budgetTV.setText("0.00");
            targetSavingsTV.setText("0.00");
            dailyExpensesTV.setText("0.00");
            calculateButton.setText("Calculate");

        }
    }

    private double computeDailyExpenses(double budget, double targetSavings, int period) {
        return (period == 0) ? (budget - targetSavings) / 7 : (budget - targetSavings) / 28;
    }

    private void InitializeSpinner(View view) {
        Spinner spinner = view.findViewById(R.id.spinner1);
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
                    int spinnerPosition = getSpinnerPostion(view);
                    checkExistingData(view, spinnerPosition);
                } else {
                    isSpinnerInitialized = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    private void InitializeButton(View view) {
        Button calculateButton = view.findViewById(R.id.calculate);
        calculateButton.setOnClickListener(v -> {
            try {
                EditText budgetET = view.findViewById(R.id.budget);
                double budget = Double.parseDouble(budgetET.getText().toString());

                EditText savingsET = view.findViewById(R.id.savings);
                double savings = Double.parseDouble(savingsET.getText().toString());

                int selectedId = getSpinnerPostion(view);
                BudgetModel budgetModel = new BudgetModel(selectedId, budget, savings);

                observeOnce(budgetViewModel.getBudget(selectedId), getViewLifecycleOwner(), existingBudget -> {
                    if (existingBudget != null) {
                        budgetViewModel.update(budgetModel);
                    } else {
                        budgetViewModel.insert(budgetModel);
                    }
                    updateUI(view, existingBudget);
                    // Do not manually call updateUI here — LiveData will trigger it
                });

            } catch (NumberFormatException nfe) {
                Toast.makeText(getContext(), "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getSpinnerPostion(View view) {
        Spinner spinner = view.findViewById(R.id.spinner1);
        return spinner.getSelectedItemPosition();
    }

    // Utility: Observe LiveData only once
    private <T> void observeOnce(final LiveData<T> liveData, final androidx.lifecycle.LifecycleOwner owner, final Observer<T> observer) {
        liveData.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(T t) {
                liveData.removeObserver(this);
                observer.onChanged(t);
            }
        });
    }
}
