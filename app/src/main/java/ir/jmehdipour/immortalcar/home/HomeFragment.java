package ir.jmehdipour.immortalcar.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.widget.TextView;
import android.widget.Toast;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.ArrayCompositeSubscription;
import io.reactivex.schedulers.Schedulers;
import ir.jmehdipour.immortalcar.R;
import ir.jmehdipour.immortalcar.base.BaseFragment;
import ir.jmehdipour.immortalcar.data.model.Banner;
import ir.jmehdipour.immortalcar.data.model.Car;
import ir.jmehdipour.immortalcar.list.CarListActivity;

public class HomeFragment extends BaseFragment {
    private HomeViewModel viewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView bannersRv;
    private RecyclerView latestCarsRv;
    private RecyclerView popularCarsRv;
    private RecyclerView expensiveCarsRv;
    private RecyclerView cheapCarsRv;
    private CarAdapter latestCarAdapter;
    private CarAdapter popularCarAdapter;
    private CarAdapter expensiveCarAdapter;
    private CarAdapter cheapCarAdapter;
    private Subscription subscription;
    private BannerAdapter bannerAdapter;
    private ArrayCompositeSubscription compositeSubscription = new ArrayCompositeSubscription(4);
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new HomeViewModel(getContext());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void setupViews() {
        bannersRv = rootView.findViewById(R.id.rv_home_slider);
        latestCarsRv = rootView.findViewById(R.id.rv_home_latestCars);
        popularCarsRv = rootView.findViewById(R.id.rv_home_popularCars);
        expensiveCarsRv = rootView.findViewById(R.id.rv_home_expensiveCars);
        cheapCarsRv = rootView.findViewById(R.id.rv_home_cheapestCars);

        SnapHelper snapHelper = new PagerSnapHelper();

        snapHelper.attachToRecyclerView(bannersRv);
        bannersRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        bannerAdapter = new BannerAdapter();
        bannersRv.setAdapter(bannerAdapter);

        latestCarsRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        latestCarAdapter = new CarAdapter();
        latestCarsRv.setAdapter(latestCarAdapter);
        popularCarsRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularCarAdapter = new CarAdapter();
        popularCarsRv.setAdapter(popularCarAdapter);
        expensiveCarsRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        expensiveCarAdapter = new CarAdapter();
        expensiveCarsRv.setAdapter(expensiveCarAdapter);
        cheapCarsRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        cheapCarAdapter = new CarAdapter();
        cheapCarsRv.setAdapter(cheapCarAdapter);

        TextView viewAllLatestCarsTv = rootView.findViewById(R.id.tv_home_viewAllLatestCars);
        viewAllLatestCarsTv.setOnClickListener(v -> startCarListActivity(Car.ON_SORT_LATEST, "Latest Cars"));

        TextView viewAllPopularCarsTv = rootView.findViewById(R.id.tv_home_viewAllPopularCars);
        viewAllPopularCarsTv.setOnClickListener(v -> startCarListActivity(Car.ON_SORT_POPULAR, "Popular Cars"));

        TextView viewAllCheapCarsTv = rootView.findViewById(R.id.tv_home_viewAllCheapCars);
        viewAllCheapCarsTv.setOnClickListener(v -> startCarListActivity(Car.ON_SORT_LOW_TO_HIGH, "Cheapest Cars"));

        TextView viewAllExpensiveCarsTv = rootView.findViewById(R.id.tv_home_viewAllExpensiveCars);
        viewAllExpensiveCarsTv.setOnClickListener(v -> startCarListActivity(Car.ON_SORT_HIGH_TO_LOW, "Expensive Cars"));



    }

    @Override
    public void observe() {
        viewModel.getBanners().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Banner>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Banner> banners) {
                        bannerAdapter.setBanners(banners);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        viewModel.latestCars().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(cars -> {
                    latestCarAdapter.setCars(cars);
                })
                .doOnSubscribe(subscription -> {
                    compositeSubscription.setResource(0,subscription);
                })
                .doOnError(throwable -> {

                })
                .subscribe();

        viewModel.popularCars().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(cars -> {
                    popularCarAdapter.setCars(cars);
                })
                .doOnSubscribe(subscription -> {
                    compositeSubscription.setResource(1,subscription);
                })
                .doOnError(throwable -> {

                })
                .subscribe();

        viewModel.expensiveCars().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(cars -> {
                    expensiveCarAdapter.setCars(cars);
                })
                .doOnSubscribe(subscription -> {
                    compositeSubscription.setResource(2,subscription);
                })
                .doOnError(throwable -> {

                })
                .subscribe();

        viewModel.cheapCars().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(cars -> {
                    cheapCarAdapter.setCars(cars);
                })
                .doOnSubscribe(subscription -> {
                    compositeSubscription.setResource(3,subscription);
                })
                .doOnError(throwable -> {

                })
                .subscribe();
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
        compositeSubscription.dispose();
    }

    public void startCarListActivity(int sortType, String title){
        Intent intent = new Intent(getActivity(), CarListActivity.class);
        intent.putExtra(CarListActivity.EXTRA_KEY_SORT, sortType);
        intent.putExtra(CarListActivity.EXTRA_KEY_TITLE, title);
        startActivity(intent);
    }
}
