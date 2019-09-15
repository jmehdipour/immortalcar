package ir.jmehdipour.immortalcar.favorites;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.jmehdipour.immortalcar.R;
import ir.jmehdipour.immortalcar.base.BaseFragment;
import ir.jmehdipour.immortalcar.home.CarAdapter;
import ir.jmehdipour.immortalcar.utils.SpacesItemDecoration;

public class FavoritesFragment extends BaseFragment {
    private FavoritesViewModel viewModel = new FavoritesViewModel(getContext());
    private RecyclerView favoritesCarsRv;
    private CarAdapter carAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_favorites;
    }

    @Override
    public void setupViews() {
        favoritesCarsRv = rootView.findViewById(R.id.rv_favorites);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.carItem) ;
        favoritesCarsRv.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        favoritesCarsRv.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        carAdapter = new CarAdapter();
        favoritesCarsRv.setAdapter(carAdapter);
    }

    @Override
    public void observe() {

        viewModel.getFavoritesCars().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(cars -> {
                    carAdapter.setCars(cars);

                })
                .doOnError(throwable -> {
                    Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                })
                .subscribe();

    }
}



