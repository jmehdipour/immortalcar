package ir.jmehdipour.immortalcar.list;

import android.content.Context;

import java.util.List;

import io.reactivex.Flowable;
import ir.jmehdipour.immortalcar.data.model.Car;
import ir.jmehdipour.immortalcar.data.source.CarDataSource;
import ir.jmehdipour.immortalcar.providers.CarRepositoryProvider;

public class CarListViewModel {
    private CarDataSource repository;

    public CarListViewModel(Context context) {
        this.repository = CarRepositoryProvider.provide(context);
    }

    public Flowable<List<Car>> getCarList(int sortType){
        switch (sortType){
            case Car.ON_SORT_LATEST:
                return repository.getLatestCars();
            case Car.ON_SORT_POPULAR:
                return repository.getPopularCars();
            case Car.ON_SORT_HIGH_TO_LOW:
                return repository.getExpensiveCars();
            case Car.ON_SORT_LOW_TO_HIGH:
                return repository.getCheapCars();
            default:
                return repository.getLatestCars();
        }
    }

    public Flowable<List<Car>> getCarListBaseOnCategory(String category, String categorytype){
        switch (categorytype){
            case Car.ON_MODELS:
                return repository.getCarsBaseOnModel(category);
            case Car.ON_BRANDS:
                return repository.getCarsBaseOnBrand(category);
            default:
                return null;
        }
    }
}
