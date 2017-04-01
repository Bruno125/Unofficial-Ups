package com.brunoaybar.unofficialupc.modules.base;


import android.content.Context;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.brunoaybar.unofficialupc.utils.UiUtils;

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
        displayMessage(error.getMessage());
    }

    public void displayMessage(@StringRes int messageId){
        displayMessage(getActivity().getString(messageId));
    }

    public void displayMessage(String message){
        displayMessage(message, Toast.LENGTH_SHORT);
    }

    public void displayMessage(String message, int duration){
        if(isAdded()) {
            Toast.makeText(getContext(), message, duration).show();
        }
    }

    public void hideKeyboard(){
        UiUtils.hideSoftKeyboard(getActivity());
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
