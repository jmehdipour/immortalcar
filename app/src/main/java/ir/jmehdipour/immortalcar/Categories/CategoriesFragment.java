package ir.jmehdipour.immortalcar.Categories;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ir.jmehdipour.immortalcar.R;
import ir.jmehdipour.immortalcar.base.BaseFragment;

public class CategoriesFragment extends BaseFragment {
    private CategoriesViewModel viewModel = new CategoriesViewModel(getContext());
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Subscription modelsSubscription;
    private Subscription brandsSubscription;
    private String[] carModels;
    private String[] carBrands;


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_categories;
    }

    @Override
    public void setupViews() {
        viewPager= rootView.findViewById(R.id.viewPager_categories_fragmentContainer);
        tabLayout = rootView.findViewById(R.id.tabLayout_categories_viewpager);

    }

    @Override
    public void observe() {
        viewModel.getCarModels()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(carModels -> {
                    this.carModels = getStringModels(carModels);
                    onObserveComplete();
                })
                .doOnSubscribe(subscription -> {
                    modelsSubscription = subscription;
                })
                .doOnError(throwable -> {

                })
                .subscribe();

        viewModel.getCarBrands()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(carBrands -> {
                    this.carBrands = carBrands;
                    onObserveComplete();
                })
                .doOnSubscribe(subscription -> {
                    brandsSubscription = subscription;
                })
                .doOnError(throwable -> {

                })
                .subscribe();
    }


    public String[] getStringModels(int[] models){
        String[] modelsInString = new String[models.length];

        for (int i=0; i< models.length ; i++){
            modelsInString[i] = String.valueOf(models[i]);
        }

        return modelsInString;
    }

    public void onObserveComplete(){
        if (carBrands != null && carModels != null){
            viewPager.setAdapter(new CategoriesFragmentPagerAdapter(
                    getFragmentManager(), carModels, carBrands
                    ));
            tabLayout.setupWithViewPager(viewPager);
        }
    }
}
