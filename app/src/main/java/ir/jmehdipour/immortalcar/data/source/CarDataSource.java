package ir.jmehdipour.immortalcar.data.source;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ir.jmehdipour.immortalcar.data.model.Banner;
import ir.jmehdipour.immortalcar.data.model.Car;

public interface CarDataSource {
    Flowable<List<Car>> getLatestCars();
    Flowable<List<Car>> getPopularCars();
    Flowable<List<Car>> getExpensiveCars();
    Flowable<List<Car>> getCheapCars();
    Single<List<Banner>> getBanners();
    Flowable<List<Car>> getFavoriteCars();
    void favoriteCar(Car car);
    Single<Car> getCar(int carId);
    Flowable<int[]> getCarModels();
    Flowable<String[]> getCarBrands();
    Flowable<List<Car>> getCarsBaseOnModel(String model);
    Flowable<List<Car>> getCarsBaseOnBrand(String brand);
    Flowable<List<Car>> searchCar(String keyword);

}
