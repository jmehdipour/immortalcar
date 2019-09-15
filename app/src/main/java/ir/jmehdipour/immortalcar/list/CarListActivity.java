package ir.jmehdipour.immortalcar.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.reactivestreams.Subscription;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.jmehdipour.immortalcar.R;
import ir.jmehdipour.immortalcar.base.BaseActivity;
import ir.jmehdipour.immortalcar.data.model.Car;
import ir.jmehdipour.immortalcar.home.CarAdapter;
import ir.jmehdipour.immortalcar.search.SearchActivity;
import ir.jmehdipour.immortalcar.utils.SpacesItemDecoration;

public class CarListActivity extends BaseActivity {
    public static String EXTRA_KEY_SORT = "sort";
    public static String EXTRA_KEY_CATEGORY = "category";
    public static String EXTRA_KEY_TITLE = "title";
    public static String EXTRA_KEY_CATEGORY_TYPE = "category_type";
    private int sortType;
    private String category;
    private String categoryType;
    private String title;
    private CarListViewModel viewModel;
    private RecyclerView carListRv;
    private CarAdapter carAdapter;
    private Subscription subscription;
    private TextView titleTv;
    private ImageView backIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        viewModel = new CarListViewModel(this);
        if (getIntent().hasExtra(EXTRA_KEY_SORT)) {
            sortType = getIntent().getIntExtra(EXTRA_KEY_SORT, Car.ON_SORT_LATEST);
        } else if (getIntent().hasExtra(EXTRA_KEY_CATEGORY) && getIntent().hasExtra(EXTRA_KEY_CATEGORY_TYPE)) {
            category = getIntent().getStringExtra(EXTRA_KEY_CATEGORY);
            categoryType = getIntent().getStringExtra(EXTRA_KEY_CATEGORY_TYPE);
            sortType = -1;
        } else {
            finish();
        }

        title = getIntent().getStringExtra(EXTRA_KEY_TITLE);
        setupViews();
        observe();
    }

    private void setupViews() {
        carListRv = findViewById(R.id.rv_list_cars);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.carItem);
        carListRv.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        carListRv.setLayoutManager(
                new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        );
        carAdapter = new CarAdapter();
        carListRv.setAdapter(carAdapter);

        titleTv = findViewById(R.id.tv_list_title);
        titleTv.setText(title);

        backIv = findViewById(R.id.iv_list_back);
        backIv.setOnClickListener(v -> finish());

        ImageView searchIv = findViewById(R.id.iv_list_search);
        searchIv.setOnClickListener(v -> {
            startActivity(new Intent(this, SearchActivity.class));
        });
    }

    private void observe() {
        if (sortType != -1) {
            viewModel.getCarList(sortType).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(cars -> {
                        carAdapter.setCars(cars);
                    })
                    .doOnError(throwable -> {

                    })
                    .doOnSubscribe(subscription -> {
                        this.subscription = subscription;
                    })
                    .subscribe();
        }

        if ( (category != null) && (categoryType != null) ) {

            viewModel.getCarListBaseOnCategory(category, categoryType).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(cars -> {
                        carAdapter.setCars(cars);
                    })
                    .doOnError(throwable -> {

                    })
                    .doOnSubscribe(subscription -> {
                        this.subscription = subscription;
                    })
                    .subscribe();

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.cancel();
    }
}
