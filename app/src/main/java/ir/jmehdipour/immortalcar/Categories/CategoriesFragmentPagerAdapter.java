package ir.jmehdipour.immortalcar.Categories;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CategoriesFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private CategoriesViewModel viewModel;
    private String[] carModels ;
    private String[] carBrands;
    private Subscription modelsSubscription;
    private Subscription brandsSubscription;

    public CategoriesFragmentPagerAdapter(FragmentManager fm, String[] carModels, String[] carBrands) {
        super(fm);
        this.carModels = carModels;
        this.carBrands = carBrands;

    }


    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return ViewPagerItemFragment.newInstance(carModels);
            case 1:
                return ViewPagerItemFragment.newInstance(carBrands);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Models";
            case 1:
                return "Brands";
            default:
                return null;
        }
    }


}
