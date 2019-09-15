package ir.jmehdipour.immortalcar.Categories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ir.jmehdipour.immortalcar.R;
import ir.jmehdipour.immortalcar.base.BaseFragment;

public class ViewPagerItemFragment extends BaseFragment {
    public static final String EXTRA_KEY_CATEGORIES = "categories";
    private RecyclerView categoriesRv;
    private CategoryAdapter categoryAdapter;
    private String[] categories;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories = getArguments().getStringArray(EXTRA_KEY_CATEGORIES);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_viewpager_item;
    }

    @Override
    public void setupViews() {
        categoriesRv = rootView.findViewById(R.id.rv_viewPagerItem);
        categoriesRv.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false
                ));
        categoryAdapter = new CategoryAdapter(categories);
        categoriesRv.setAdapter(categoryAdapter);

    }

    @Override
    public void observe() {

    }

    public static ViewPagerItemFragment newInstance(String[] categories) {

        Bundle args = new Bundle();
        args.putStringArray(EXTRA_KEY_CATEGORIES, categories);
        ViewPagerItemFragment fragment = new ViewPagerItemFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
