package com.aks.stockpile.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.R;
import com.aks.stockpile.adapters.ExpenditureDetailsAdapter;
import com.aks.stockpile.models.dtos.AggregatedExpenditure;
import com.aks.stockpile.models.dtos.ExpenditureBreakdown;
import com.aks.stockpile.models.dtos.GroceryExpenditureCardDto;
import com.aks.stockpile.models.enums.InventoryUpdateType;
import com.aks.stockpile.services.StockpileDaoService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static android.content.Context.MODE_PRIVATE;

public class ExpenditureFragment extends Fragment {

    private RecyclerView recyclerView;
    private MaterialButton sort;
    private StockpileDaoService daoService;
    private TextView amount, monthly;

    public ExpenditureFragment(StockpileDaoService daoService) {
        this.daoService = daoService;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setLayoutFields() {
        recyclerView = getView().findViewById(R.id.expenditure_recycle);
        sort = getView().findViewById(R.id.expenditure_view_btn);
        amount = getView().findViewById(R.id.expenditure_amount);
        monthly = getView().findViewById(R.id.expenditure_monthly_avg);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setLayoutFields();
        initializeRecyclerView(buildData(true));
        setOnclickListeners();
    }

    private void setOnclickListeners() {
        final String[] choices = {"Category", "Grocery Item"};
        final Integer[] selectedValue = {0};
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("View Expenditure By")
                        .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                initializeRecyclerView(buildData(selectedValue[0] == 0));
                            }
                        })
                        .setSingleChoiceItems(choices, selectedValue[0], new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                selectedValue[0] = selectedIndex;
                            }
                        })
                        .setCancelable(false)
                        .show();

            }
        });
    }

    public void initializeRecyclerView(List<GroceryExpenditureCardDto> data) {
        ExpenditureDetailsAdapter expenditureDetailsAdapter = new ExpenditureDetailsAdapter(getContext(), data);
        recyclerView.setAdapter(expenditureDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeRecyclerView(buildData(true));
    }

    private List<GroceryExpenditureCardDto> buildData(boolean isCategory) {
        int grossTotal = 0, maxMonths = 0;
        List<GroceryExpenditureCardDto> dtoList = new ArrayList<>();
        List<AggregatedExpenditure> expenditures = daoService.getAllExpenditure();
        Map<Integer, List<AggregatedExpenditure>> expenditureMap = new HashMap<>();
        for (AggregatedExpenditure expenditure : expenditures) {
            if (expenditure.getExpenditure().getUpdateType() == InventoryUpdateType.ADDED) {
                if (isCategory) {
                    if (expenditureMap.get(expenditure.getCategory().getId()) == null) {
                        List<AggregatedExpenditure> expenditures1 = new ArrayList<>();
                        expenditures1.add(expenditure);
                        expenditureMap.put(expenditure.getCategory().getId(), expenditures1);
                    } else {
                        expenditureMap.get(expenditure.getCategory().getId()).add(expenditure);
                    }
                } else {
                    if (expenditureMap.get(expenditure.getInventory().getId()) == null) {
                        List<AggregatedExpenditure> expenditures1 = new ArrayList<>();
                        expenditures1.add(expenditure);
                        expenditureMap.put(expenditure.getInventory().getId(), expenditures1);
                    } else {
                        expenditureMap.get(expenditure.getInventory().getId()).add(expenditure);
                    }
                }
            }
        }
        for (Map.Entry<Integer, List<AggregatedExpenditure>> entry : expenditureMap.entrySet()) {
            Map<Integer, Integer> monthlyTotal = new TreeMap<>(), weeklyTotal = new TreeMap<>();
            Integer total = 0;
            for (AggregatedExpenditure expenditure : entry.getValue()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(expenditure.getExpenditure().getEntryDate());
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                int weekNum = calendar.get(Calendar.WEEK_OF_YEAR);
                if (monthlyTotal.get(year * 100 + month) != null) {
                    monthlyTotal.put(year * 100 + month, monthlyTotal.get(year * 100 + month) + expenditure.getExpenditure().getPrice());
                } else {
                    monthlyTotal.put(year * 100 + month, expenditure.getExpenditure().getPrice());
                }
                if (weeklyTotal.get(year * 100 + weekNum) != null) {
                    weeklyTotal.put(year * 100 + weekNum, weeklyTotal.get(year * 100 + weekNum) + expenditure.getExpenditure().getPrice());
                } else {
                    weeklyTotal.put(year * 100 + weekNum, expenditure.getExpenditure().getPrice());
                }
                total += expenditure.getExpenditure().getPrice();
            }
            maxMonths = Math.max(maxMonths, monthlyTotal.size());
            if (!entry.getValue().isEmpty()) {
                GroceryExpenditureCardDto dto = new GroceryExpenditureCardDto();
                dto.setId(entry.getKey());
                if (isCategory) {
                    dto.setName(entry.getValue().get(0).getCategory().getName());
                } else {
                    dto.setName(entry.getValue().get(0).getInventory().getName());
                }
                grossTotal += total;
                dto.setWeeklyAmount(weeklyTotal.isEmpty() ? total : total / weeklyTotal.size());
                dto.setMonthlyAmount(monthlyTotal.isEmpty() ? total : total / monthlyTotal.size());
                dto.setTotalAmount(total);
                dto.setImageResourceId(entry.getValue().get(0).getCategory().getImageResource());
                dto.setCategory(isCategory);
                List<ExpenditureBreakdown> breakdowns = new ArrayList<>();
                for (Map.Entry<Integer, Integer> monthly : monthlyTotal.entrySet()) {
                    breakdowns.add(build(monthly.getKey(), monthly.getValue()));
                }
                dto.setBreakdown(breakdowns);
                dtoList.add(dto);
            }
        }
        String symbol = getContext().getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("CURRENCY_SYMBOL", "₹");
        monthly.setText(String.format("Monthly Average: %s %d", symbol, maxMonths == 0 ? grossTotal : grossTotal / maxMonths));
        amount.setText(String.format("%s %d", symbol, grossTotal));
        return dtoList;
    }

    private ExpenditureBreakdown build(Integer val, Integer amount) {
        ExpenditureBreakdown breakdown = new ExpenditureBreakdown();
        int month = val % 100;
        int year = val / 100;
        breakdown.setMonthName(new DateFormatSymbols().getMonths()[month]);
        breakdown.setYearName(year);
        breakdown.setAmountSpent(amount);
        return breakdown;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expenditure, container, false);
    }

}