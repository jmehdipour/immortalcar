package ir.jmehdipour.immortalcar.home;

import android.content.Context;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ir.jmehdipour.immortalcar.data.model.Banner;
import ir.jmehdipour.immortalcar.data.model.Car;
import ir.jmehdipour.immortalcar.data.source.CarDataSource;
import ir.jmehdipour.immortalcar.providers.CarRepositoryProvider;

public class HomeViewModel {

    private CarDataSource carRepository;

    public HomeViewModel(Context context) {
        carRepository = CarRepositoryProvider.provide(context);
    }



    public Flowable<List<Car>> latestCars () {
        return carRepository.getLatestCars();
    }

    public Flowable<List<Car>> popularCars(){
        return carRepository.getPopularCars();
    }

    public Flowable<List<Car>> expensiveCars(){
        return carRepository.getExpensiveCars();
    }

    public Flowable<List<Car>> cheapCars(){
        return carRepository.getCheapCars();
    }

    public Single<List<Banner>> getBanners(){
        return carRepository.getBanners();
    }





}
