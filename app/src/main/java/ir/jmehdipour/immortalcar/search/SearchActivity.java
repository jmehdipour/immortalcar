package ir.jmehdipour.immortalcar.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ir.jmehdipour.immortalcar.R;
import ir.jmehdipour.immortalcar.base.BaseActivity;
import ir.jmehdipour.immortalcar.data.model.Car;
import ir.jmehdipour.immortalcar.home.CarAdapter;
import ir.jmehdipour.immortalcar.utils.SpacesItemDecoration;

public class SearchActivity extends BaseActivity implements TextWatcher {
    private SearchViewModel viewModel = new SearchViewModel(this);
    private ImageView backIv ;
    private EditText searchEt;
    private ImageView clearIv;
    private RecyclerView searchRv;
    private CarAdapter searchCarAdapter;
    private Subscription subscription;
    private TextView noResultTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
    }

    public void setupViews(){
        backIv = findViewById(R.id.iv_search_back);
        backIv.setOnClickListener(v -> finish());

        searchEt = findViewById(R.id.et_search);
        searchEt.addTextChangedListener(this);
        clearIv = findViewById(R.id.iv_search_clear);
        clearIv.setOnClickListener(v -> {
            searchEt.setText("");
        });

        searchRv = findViewById(R.id.rv_search);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.carItem);
        searchRv.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        searchRv.setLayoutManager(new GridLayoutManager(
                this, 2, GridLayoutManager.VERTICAL, false
        ));
        searchCarAdapter = new CarAdapter();
        searchRv.setAdapter(searchCarAdapter);

        noResultTv = findViewById(R.id.tv_search_noResult);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (s.length() > 0){
            clearIv.setVisibility(View.VISIBLE);
            noResultTv.setVisibility(View.GONE);
            viewModel.searchCars(s.toString()).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(cars -> {
                        if (!cars.isEmpty()){
                            searchRv.setVisibility(View.VISIBLE);
                            searchCarAdapter.setCars(cars);
                        }
                    })
                    .doOnSubscribe(subscription -> {
                        this.subscription = subscription;
                    })
                    .doOnError(throwable -> {

                    })
                    .subscribe();
        }else {
            searchRv.setVisibility(View.GONE);
            clearIv.setVisibility(View.INVISIBLE);
            noResultTv.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null){
            subscription.cancel();
        }

    }
}
