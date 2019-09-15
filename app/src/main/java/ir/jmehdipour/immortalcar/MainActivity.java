package ir.jmehdipour.immortalcar;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.ss.bottomnavigation.BottomNavigation;
import com.ss.bottomnavigation.events.OnSelectedItemChangeListener;

import ir.jmehdipour.immortalcar.Categories.CategoriesFragment;
import ir.jmehdipour.immortalcar.base.BaseActivity;
import ir.jmehdipour.immortalcar.favorites.FavoritesFragment;
import ir.jmehdipour.immortalcar.home.HomeFragment;
import ir.jmehdipour.immortalcar.search.SearchActivity;

public class MainActivity extends BaseActivity {
    private BottomNavigation bottomNavigation;
    private Fragment homeFragment;
    private Fragment categoriesFragment;
    private Fragment favoritesFragment;
    private ImageView searchIv;
    private Drawer drawer;
    private ImageView drawerIv;
    private AccountHeader accountHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDrawer();
        setupViews();

    }

    private void setupViews() {
        drawerIv = findViewById(R.id.iv_main_drawer);
        drawerIv.setOnClickListener(v -> {
            drawer.openDrawer();
        });

        searchIv = findViewById(R.id.iv_main_search);
        searchIv.setOnClickListener(v -> {
            startActivity(new Intent(this, SearchActivity.class));
        });

        bottomNavigation = findViewById(R.id.bottomNavigation_main);

        bottomNavigation.setOnSelectedItemChangeListener(new OnSelectedItemChangeListener() {
            @Override
            public void onSelectedItemChanged(int i) {
                switch (i){
                    case R.id.tab_home:
                        if (homeFragment == null){
                            homeFragment = new HomeFragment();
                        }
                        replaceTransaction(homeFragment);
                        break;
                    case R.id.tab_categories:
                        if (categoriesFragment == null){
                            categoriesFragment = new CategoriesFragment();
                        }
                        replaceTransaction(categoriesFragment);
                        break;
                    case R.id.tab_favorites:
                        if (favoritesFragment == null){
                            favoritesFragment = new FavoritesFragment();
                        }
                        replaceTransaction(favoritesFragment);
                }
            }
        });



    }

    private void setupDrawer(){
        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(ContextCompat.getDrawable(this ,R.color.colorPrimary))
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("Guest user")
                                .withEmail("Sign in to your User account")
                                .withIcon(ContextCompat.getDrawable(this ,R.drawable.user_circle))
                )
                .build();

        PrimaryDrawerItem informationDrawerItem = new PrimaryDrawerItem()
                .withName("Information")
                .withIdentifier(1)
                .withSelectable(false)
                .withIcon(ContextCompat.getDrawable(this ,R.drawable.ic_info_gray_24dp));

        PrimaryDrawerItem authDrawerItem = new PrimaryDrawerItem()
                .withName("Sign in / Sign up")
                .withIdentifier(2)
                .withSelectable(false)
                .withIcon(R.drawable.ic_person_gray_24dp);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withSelectedItem(-1)
                .addDrawerItems(informationDrawerItem, authDrawerItem)
                .withAccountHeader(accountHeader)
                .build();
    }

    public void replaceTransaction(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main_fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
