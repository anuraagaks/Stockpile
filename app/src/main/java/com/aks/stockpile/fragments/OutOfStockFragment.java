package com.aks.stockpile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.R;
import com.aks.stockpile.activities.GroceryActivity;
import com.aks.stockpile.adapters.GroceryDetailsAdapter;
import com.aks.stockpile.models.dtos.GroceryDetailsDto;

import java.util.List;

public class OutOfStockFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setLayoutFields() {
        recyclerView = getView().findViewById(R.id.grocery_oos_recycle);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setLayoutFields();
        initializeRecyclerView();
    }

    public void initializeRecyclerView() {
        List<GroceryDetailsDto> data = ((GroceryActivity) getActivity()).daoService.getOutOfStock();
        GroceryDetailsAdapter groceryDetailsAdapter = new GroceryDetailsAdapter(getContext(), data, ((GroceryActivity) getActivity()).daoService);
        recyclerView.setAdapter(groceryDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_out_of_stock, container, false);
    }
}