package ir.jmehdipour.immortalcar.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    protected View rootView;
    protected boolean isRendered = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null){
            rootView = inflater.inflate(getLayoutRes(), container, false);
        }
        if (!isRendered){
            setupViews();
            isRendered = true;
        }

        observe();
        return rootView;
    }

    public abstract int getLayoutRes();

    public abstract void setupViews();

    public abstract void observe();
}
