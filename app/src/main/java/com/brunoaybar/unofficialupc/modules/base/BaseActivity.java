package com.brunoaybar.unofficialupc.modules.base;

import android.content.Context;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.brunoaybar.unofficialupc.utils.UiUtils;

import rx.subscriptions.CompositeSubscription;

/**
 * Parent class for activities used in the application.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @NonNull
    protected CompositeSubscription mSubscription = new CompositeSubscription();

    @Override
    protected void onResume() {
        super.onResume();
        mSubscription = new CompositeSubscription();
        bind();
    }


    protected void bind() {
    }


    @Override
    protected void onPause() {
        mSubscription.unsubscribe();
        super.onPause();
    }


    public void displayError(Throwable error){
        showToast(error.getMessage());
    }

    public void showToast(@StringRes int message){
        showToast(getString(message));
    }

    public void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    public void hideKeyboard(){
        UiUtils.hideSoftKeyboard(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
