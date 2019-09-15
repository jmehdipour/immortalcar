package ir.jmehdipour.immortalcar.data.source;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ir.jmehdipour.immortalcar.data.model.Banner;
import ir.jmehdipour.immortalcar.data.model.Car;

@Dao
public abstract class CarLocalDataSource implements CarDataSource{


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void saveCars(List<Car> cars);

    @Query("SELECT * FROM tbl_cars ORDER BY id DESC")
    @Override
    public abstract Flowable<List<Car>> getLatestCars();

    @Query("SELECT * FROM tbl_cars ORDER BY likes DESC")
    @Override
    public abstract Flowable<List<Car>> getPopularCars();

    @Query("SELECT * FROM tbl_cars ORDER BY price DESC")
    @Override
    public abstract Flowable<List<Car>> getExpensiveCars();

    @Query("SELECT * FROM tbl_cars ORDER BY price ASC")
    @Override
    public abstract Flowable<List<Car>> getCheapCars();

    @Override
    public Single<List<Banner>> getBanners() {
        return null;
    }

    @Query("SELECT * FROM tbl_cars WHERE is_favorite = 1")
    @Override
    public abstract Flowable<List<Car>> getFavoriteCars();

    @Update
    public abstract void favoriteCar(Car car);

    @Query("SELECT * FROM tbl_cars WHERE id = :carId")
    @Override
    public abstract Single<Car> getCar(int carId);

    @Query("SELECT DISTINCT model FROM tbl_cars ORDER BY model ASC")
    @Override
    public abstract Flowable<int[]> getCarModels();

    @Query("SELECT DISTINCT brand FROM tbl_cars ORDER BY brand ASC")
    @Override
    public abstract Flowable<String[]> getCarBrands();

    @Query("SELECT * FROM tbl_cars WHERE model = :model ")
    @Override
    public abstract Flowable<List<Car>> getCarsBaseOnModel(String model);

    @Query("SELECT * FROM tbl_cars WHERE brand = :brand ")
    @Override
    public abstract Flowable<List<Car>> getCarsBaseOnBrand(String brand);

    @Query("SELECT * FROM tbl_cars WHERE title LIKE '%'|| :keyword ||'%' OR model LIKE '%'|| :keyword ||'%' OR brand LIKE '%'|| :keyword ||'%'")
    @Override
    public abstract Flowable<List<Car>> searchCar(String keyword);

}
