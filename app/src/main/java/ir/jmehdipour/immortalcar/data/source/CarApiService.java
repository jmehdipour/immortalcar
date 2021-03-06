package ir.jmehdipour.immortalcar.data.source;

import java.util.List;


import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ir.jmehdipour.immortalcar.data.model.Banner;
import ir.jmehdipour.immortalcar.data.model.Car;
import retrofit2.http.GET;

public interface CarApiService {
    @GET("cars")
    Flowable<List<Car>> getCars();



    @GET("bannerss")
    Single<List<Banner>> getBanners();

}
