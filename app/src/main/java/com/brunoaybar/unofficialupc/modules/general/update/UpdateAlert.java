package com.brunoaybar.unofficialupc.modules.general.update;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.analytics.AnalyticsManager;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by brunoaybar on 29/03/2017.
 */

public class UpdateAlert {

    private Context activity;
    private UpdateAlertViewModel viewModel = new UpdateAlertViewModel();

    public UpdateAlert(Activity activity) {
        this.activity = activity;
    }

    public void start(){
        viewModel.shouldDisplay()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( shouldDisplay -> {
           if (shouldDisplay){
               viewModel.getDownloadUrl()
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(this::show, e-> { /* do nothing*/});
           }
        }, e -> { /* do nothing*/ });

    }

    private void show(String url){
        new AlertDialog.Builder(activity)
                .setTitle(activity.getString(R.string.title_update_app))
                .setMessage(R.string.hint_update_app)
                .setPositiveButton(R.string.text_update, (dialog, which) -> {
                    redirectToUrl(url);
                    viewModel.updated();
                })
                .setNegativeButton(R.string.text_remind_later, (dialog, which) -> {})
                .show();
    }

    public void redirectToUrl(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(browserIntent);
    }

}
