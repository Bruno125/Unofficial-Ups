package com.brunoaybar.unofficalupc.modules.base;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import rx.subscriptions.CompositeSubscription;

/**
 * Parent class for activities used in the application.
 */

public abstract class BaseActivity extends AppCompatActivity {


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
        mSubscription = null;
        super.onPause();
    }


    public void displayError(Throwable error){
            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
    }
}
