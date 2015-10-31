package android.com.solutions.nerd.testapp;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by mookie on 10/24/15.
 * for Nerd.Solutions
 */
public abstract class AbstractFragment<T extends Fragment>
    extends Fragment
{


    public AbstractFragment(T type){

    }

    public T getInstance(T type){
        if (instance==null)
            instance = createInstance(type);
        return instance;
    }

    public abstract T createInstance(T type);


    private  T instance;

    public abstract Fragment getInstance(Class<T> fragmentClass);
}

