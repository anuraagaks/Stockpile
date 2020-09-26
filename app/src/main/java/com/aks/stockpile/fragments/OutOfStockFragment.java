package com.aks.stockpile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.R;
import com.aks.stockpile.activities.GroceryActivity;
import com.aks.stockpile.adapters.GroceryDetailsAdapter;
import com.aks.stockpile.models.dtos.GroceryDetailsDto;
import com.aks.stockpile.services.StockpileDaoService;
import com.aks.stockpile.utils.Utilities;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class OutOfStockFragment extends Fragment {

    private RecyclerView recyclerView;
    private StockpileDaoService daoService;
    private FrameLayout frameLayout;
    private Snackbar snackbar;
    private boolean showSnackbar;

    public OutOfStockFragment(StockpileDaoService daoService) {
        this.daoService = daoService;
        showSnackbar = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setLayoutFields() {
        recyclerView = getView().findViewById(R.id.grocery_oos_recycle);
        frameLayout = getView().findViewById(R.id.grocery_oos_fragment);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setLayoutFields();
        initializeRecyclerView();
    }

    public void initializeRecyclerView() {
        List<GroceryDetailsDto> data = daoService.getOutOfStock();
        if (data.size() > 0) {
            showSnackbar = false;
            GroceryDetailsAdapter groceryDetailsAdapter = new GroceryDetailsAdapter(getContext(), data, ((GroceryActivity) getActivity()).daoService);
            recyclerView.setAdapter(groceryDetailsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            showSnackbar = true;
            snackbar = Utilities.makeSnackbarInfinite(getContext(), frameLayout, "Great! No grocery is out if stock. Keep going...");
            snackbar.setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
        }
    }

    @Override
    public void setMenuVisibility(boolean isvisible) {
        super.setMenuVisibility(isvisible);
        if (isvisible && showSnackbar && snackbar != null && !snackbar.isShown()) {
            snackbar.show();
        } else if (snackbar != null) {
            snackbar.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeRecyclerView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_out_of_stock, container, false);
    }
}