package ir.jmehdipour.immortalcar.data.source;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ir.jmehdipour.immortalcar.data.model.Banner;
import ir.jmehdipour.immortalcar.data.model.Car;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarCloudDataSource implements CarDataSource {
    private CarApiService carApiService;

    public CarCloudDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.myjson.com/bins/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        carApiService = retrofit.create(CarApiService.class);
    }


    @Override
    public Flowable<List<Car>> getLatestCars() {
        return carApiService.getCars();
    }

    @Override
    public Flowable<List<Car>> getPopularCars() {
        return null;
    }

    @Override
    public Flowable<List<Car>> getExpensiveCars() {
        return null;
    }

    @Override
    public Flowable<List<Car>> getCheapCars() {
        return null;
    }

    @Override
    public Single<List<Banner>> getBanners() {
        return carApiService.getBanners();
    }

    @Override
    public Flowable<List<Car>> getFavoriteCars() {
        return null;
    }

    @Override
    public void favoriteCar(Car car) {

    }

    @Override
    public Single<Car> getCar(int carId) {
        return null;
    }

    @Override
    public Flowable<int[]> getCarModels() {
        return null;
    }

    @Override
    public Flowable<String[]> getCarBrands() {
        return null;
    }

    @Override
    public Flowable<List<Car>> getCarsBaseOnModel(String model) {
        return null;
    }

    @Override
    public Flowable<List<Car>> getCarsBaseOnBrand(String brand) {
        return null;
    }

    @Override
    public Flowable<List<Car>> searchCar(String keyword) {
        return null;
    }
}
