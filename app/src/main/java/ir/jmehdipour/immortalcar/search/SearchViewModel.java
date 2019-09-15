package ir.jmehdipour.immortalcar.search;

import android.content.Context;

import java.util.List;

import io.reactivex.Flowable;
import ir.jmehdipour.immortalcar.data.model.Car;
import ir.jmehdipour.immortalcar.data.source.CarDataSource;
import ir.jmehdipour.immortalcar.data.source.CarLocalDataSource;
import ir.jmehdipour.immortalcar.providers.CarRepositoryProvider;

public class SearchViewModel {
    private CarDataSource repository ;

    public SearchViewModel(Context context) {
        repository = CarRepositoryProvider.provide(context);
    }

    public Flowable<List<Car>> searchCars(String keyword){
        return repository.searchCar(keyword);
    }
}
