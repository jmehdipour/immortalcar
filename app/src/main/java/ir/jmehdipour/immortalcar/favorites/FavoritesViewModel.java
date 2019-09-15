package ir.jmehdipour.immortalcar.favorites;

import android.content.Context;

import java.util.List;

import io.reactivex.Flowable;
import ir.jmehdipour.immortalcar.data.model.Car;
import ir.jmehdipour.immortalcar.data.source.CarDataSource;
import ir.jmehdipour.immortalcar.providers.CarRepositoryProvider;

public class FavoritesViewModel {
    private CarDataSource repository;

    public FavoritesViewModel(Context context) {
        repository = CarRepositoryProvider.provide(context);
    }

    public Flowable<List<Car>> getFavoritesCars (){
        return repository.getFavoriteCars();
    }
}
