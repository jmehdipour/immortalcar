package ir.jmehdipour.immortalcar.data.source;

import android.arch.persistence.room.OnConflictStrategy;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.jmehdipour.immortalcar.data.model.Banner;
import ir.jmehdipour.immortalcar.data.model.Car;

public class CarRepository implements CarDataSource {
    private CarLocalDataSource local;
    private CarCloudDataSource cloud;

    public CarRepository(Context context) {
        this.local = AppDatabase.getInstance(context).getCarLocalDataSource();
        this.cloud = new CarCloudDataSource();
    }

    @Override
    public Flowable<List<Car>> getLatestCars() {
        cloud.getLatestCars().subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .doOnNext(cars -> local.saveCars(cars))
                .subscribe();
        return local.getLatestCars();
    }

    @Override
    public Flowable<List<Car>> getPopularCars() {
        return local.getPopularCars();
    }

    @Override
    public Flowable<List<Car>> getExpensiveCars() {
        return local.getExpensiveCars();
    }

    @Override
    public Flowable<List<Car>> getCheapCars() {
        return local.getCheapCars();
    }

    @Override
    public Single<List<Banner>> getBanners() {
        return cloud.getBanners();
    }

    @Override
    public Flowable<List<Car>> getFavoriteCars() {
        return local.getFavoriteCars();
    }

    @Override
    public void favoriteCar(Car car) {
        local.favoriteCar(car);
    }

    public Single<Car> getCar(int carId){
        return local.getCar(carId);
    }

    @Override
    public Flowable<int[]> getCarModels(){
        return local.getCarModels();
    }

    @Override
    public Flowable<String[]> getCarBrands(){
        return local.getCarBrands();
    }

    @Override
    public Flowable<List<Car>> getCarsBaseOnModel(String model) {
        return local.getCarsBaseOnModel(model);
    }

    @Override
    public Flowable<List<Car>> getCarsBaseOnBrand(String brand) {
        return local.getCarsBaseOnBrand(brand);
    }

    @Override
    public Flowable<List<Car>> searchCar(String keyword) {
        return local.searchCar(keyword);
    }

}
