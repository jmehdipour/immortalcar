package ir.jmehdipour.immortalcar.Categories;

import android.content.Context;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.internal.operators.flowable.FlowableToList;
import ir.jmehdipour.immortalcar.data.model.Car;
import ir.jmehdipour.immortalcar.data.source.CarDataSource;
import ir.jmehdipour.immortalcar.providers.CarRepositoryProvider;

public class CategoriesViewModel {
    private CarDataSource repository;

    public CategoriesViewModel(Context context) {
        repository = CarRepositoryProvider.provide(context);
    }

    public Flowable<int[]> getCarModels(){
        return repository.getCarModels();
    }

    public Flowable<String[]> getCarBrands(){
        return repository.getCarBrands();
    }

}
