package ir.jmehdipour.immortalcar.providers;

import android.content.Context;

import ir.jmehdipour.immortalcar.data.source.CarDataSource;
import ir.jmehdipour.immortalcar.data.source.CarRepository;

public class CarRepositoryProvider {
    private static CarDataSource carRepository;

    private CarRepositoryProvider() {

    }

    public static CarDataSource provide(Context context){
        if (carRepository == null) {
            carRepository = new CarRepository(context);
        }
        return carRepository;
    }


}
