package android.com.solutions.nerd.testapp;

import android.support.v4.app.Fragment;

/**
 * Created by mookie on 10/24/15.
 * for Nerd.Solutions
 */
public abstract class AbstractFragment<T extends Fragment>
        extends Fragment {


    private T instance;

    public AbstractFragment(T type) {

    }

    public T getInstance(T type) {
        if (instance == null)
            instance = createInstance(type);
        return instance;
    }

    public abstract T createInstance(T type);

    public abstract Fragment getInstance(Class<T> fragmentClass);
}

