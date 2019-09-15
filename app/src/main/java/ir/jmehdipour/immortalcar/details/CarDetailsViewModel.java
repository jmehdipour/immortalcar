package ir.jmehdipour.immortalcar.details;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.Single;
import ir.jmehdipour.immortalcar.data.model.Car;
import ir.jmehdipour.immortalcar.data.source.CarDataSource;
import ir.jmehdipour.immortalcar.providers.CarRepositoryProvider;

public class CarDetailsViewModel {
    private CarDataSource repository;

    public CarDetailsViewModel(Context context) {
        repository = CarRepositoryProvider.provide(context);;
    }

    public Single<Car> getCar(int carId){
        return repository.getCar(carId);
    }

    public void favoriteCar(Car car){
        repository.favoriteCar(car);
    }

}
