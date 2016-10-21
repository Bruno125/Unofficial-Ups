package com.brunoaybar.unofficialupc.modules.base;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import rx.subscriptions.CompositeSubscription;

/**
 * Base fragment class for fragments implemented on this application
 */

public abstract class BaseFragment extends Fragment {

    @NonNull
    protected CompositeSubscription mSubscription = mSubscription = new CompositeSubscription();

    private @StringRes int mTitle;

    public String getTitle(Context context) {
        return context.getString(mTitle);
    }

    public void setFragmentTitle(@StringRes int title) {
        this.mTitle = title;
    }

    public void displayError(Throwable error){
        Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        bind();
    }

    protected void bind() {
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void onPause() {
        unbind();
        super.onPause();
    }

    private void unbind() {
        mSubscription.unsubscribe();
    }

}
