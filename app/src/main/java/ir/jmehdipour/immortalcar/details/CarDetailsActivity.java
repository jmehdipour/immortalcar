package ir.jmehdipour.immortalcar.details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscription;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ir.jmehdipour.immortalcar.R;
import ir.jmehdipour.immortalcar.base.BaseActivity;
import ir.jmehdipour.immortalcar.data.model.Car;
import ir.jmehdipour.immortalcar.search.SearchActivity;
import ir.jmehdipour.immortalcar.utils.PriceConverter;

public class CarDetailsActivity extends BaseActivity {
    public static final String EXTRA_KEY_CAR_ID = "car_id";
    private CarDetailsViewModel viewModel;
    private int carId;
    private Car car;
    private ImageView carImageIv;
    private TextView carTitleTv;
    private TextView descriptionTv;
    private TextView modelTv;
    private TextView brandTv;
    private TextView priceTv;
    private ImageView favoriteIv;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        viewModel = new CarDetailsViewModel(this);
        carId = getIntent().getIntExtra(EXTRA_KEY_CAR_ID, -1);
        if (carId == -1) {
            finish();
            Toast.makeText(this, "there is not this car id", Toast.LENGTH_SHORT).show();
        }

        observe();
        setupViews();

    }

    private void setupViews() {
        carImageIv = findViewById(R.id.iv_details_carImage);

        carTitleTv =  findViewById(R.id.tv_details_carTitle);

        descriptionTv = findViewById(R.id.tv_details_description);


        modelTv = findViewById(R.id.tv_details_model);

        brandTv = findViewById(R.id.tv_details_brand);

        priceTv = findViewById(R.id.tv_details_price);


        ImageView backIv = findViewById(R.id.iv_details_back);
        backIv.setOnClickListener(v -> finish());

        favoriteIv = findViewById(R.id.iv_details_favorite);

        ImageView searchIv = findViewById(R.id.iv_details_search);
        searchIv.setOnClickListener(v -> {
            startActivity(new Intent(this, SearchActivity.class));
        });

    }

    private void observe() {

        viewModel.getCar(carId).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Car>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Car car) {
                        CarDetailsActivity.this.car = car;
                        Picasso.get().load(car.getImageUrl()).into(carImageIv);
                        carTitleTv.setText(car.getTitle());
                        descriptionTv.setText(car.getDescription());
                        modelTv.setText(String.valueOf(car.getModel()));
                        brandTv.setText(car.getBrand());
                        priceTv.setText(PriceConverter.convert(car.getPrice()));


                        if (car.isFavorite()){
                            favoriteIv.setImageResource(R.drawable.ic_favorite_red_24dp);
                        }

                        favoriteIv.setOnClickListener(v -> {
                            car.setFavorite(!car.isFavorite());
                            viewModel.favoriteCar(car);
                            //EventBus.getDefault().post(car);
                            if (car.isFavorite()){
                                favoriteIv.setImageResource(R.drawable.ic_favorite_red_24dp);
                            }else{
                                favoriteIv.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                            }

                        });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
