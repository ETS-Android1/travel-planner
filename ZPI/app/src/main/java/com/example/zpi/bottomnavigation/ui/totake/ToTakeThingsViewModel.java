package com.example.zpi.bottomnavigation.ui.totake;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.zpi.models.PreparationPoint;
import com.example.zpi.models.ProductToTake;

import java.util.ArrayList;
import java.util.List;

public class ToTakeThingsViewModel extends ViewModel {

    private MutableLiveData<List<ProductToTake>> toTakeMLD;

    public LiveData<List<ProductToTake>> getProductToTakeList() {
        if (toTakeMLD == null) {
            toTakeMLD = new MutableLiveData<>(new ArrayList<>());
            loadProductToTake();
        }
        return toTakeMLD;
    }

    private void loadProductToTake() {
        // Do an asynchronous operation to fetch points.
    }
}