package com.brunoaybar.unofficialupc.modules.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brunoaybar.unofficialupc.Injection;
import com.brunoaybar.unofficialupc.analytics.AnalyticsManager;
import com.brunoaybar.unofficialupc.modules.general.MainActivity;
import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.utils.ColorizedDrawable;
import com.brunoaybar.unofficialupc.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.brunoaybar.unofficialupc.utils.UiUtils.setVisibility;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel mViewModel;

    @BindView(R.id.iviBackground) ImageView iviBackground;
    @BindView(R.id.iviLogo) ImageView iviLogo;
    @BindView(R.id.llaContainer) ViewGroup llaContainer;
    @BindView(R.id.llaForm) ViewGroup llaForm;
    @BindView(R.id.tviWarning) TextView tviWarning;
    @BindView(R.id.btnUnderstood) Button btnUnderstood;

    @BindView(R.id.eteUser) EditText eteUser;
    @BindView(R.id.tilUser) TextInputLayout tilUser;
    @BindView(R.id.etePassword) EditText etePassword;
    @BindView(R.id.tilPassword) TextInputLayout tilPassword;
    @BindView(R.id.cboRemember) CheckBox cboRemember;
    @BindView(R.id.btnLogin) Button btnLogin;

    @NonNull private CompositeSubscription mSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mViewModel = new LoginViewModel();

        runInitialLogoAnimation();
        bind();

    }

    private void bind(){
        mSubscription = new CompositeSubscription();
        mSubscription.add(mViewModel.verifyState()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(verified -> { // Token is provided
                    if(verified)
                        redirectToHome();
                    else
                        displayWarning(true);
                })
        );
    }

    private void runInitialLogoAnimation(){
        iviLogo.setAlpha(0f);
        iviLogo.setScaleX(0f);
        iviLogo.setScaleY(0f);

        btnUnderstood.setScaleX(0);

        ViewCompat.animate(iviLogo)
                .alpha(1)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(600);
    }

    @OnClick(R.id.btnUnderstood) void actionUnderstood(){

        displayWarning(false)
                .withEndAction(() -> new Handler().postDelayed(() -> {
                    //States to animate the background transition
                    List<Integer> states = new ArrayList<>();
                    for(int i=0;i<=100;i+=2)
                        states.add(i);
                    Observable<Long> mTimerObservable = Observable.interval(10, TimeUnit.MILLISECONDS);
                    Observable<Integer> mProgressObservable = Observable.just(states).flatMapIterable(i -> i);

                    Observable.zip(mTimerObservable, mProgressObservable,(t,p) -> p)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(p ->{
                                //Update background and item color and alpha
                                updateBackground(p);
                                if(p<100)
                                    return;
                                //Finished animating background!

                                //Hide views
                                setVisibility(View.GONE,tviWarning,btnUnderstood);
                                //Show login form
                                llaForm.setVisibility(View.VISIBLE);
                                //Animate button login
                                btnLogin.setScaleX(0);
                                ViewCompat.animate(btnLogin)
                                        .scaleX(1.0f)
                                        .setStartDelay(500);
                            },e ->{
                            });
                }, 0));

    }

    private void updateBackground(int progress){
        float state = progress / 100.0f;
        iviBackground.setAlpha(1 - state );
        int color = UiUtils.blendColors(ContextCompat.getColor(this,R.color.primary),ContextCompat.getColor(this,R.color.white),state);
        Drawable res = ColorizedDrawable.mutateDrawableWithColor(iviLogo.getDrawable(),color);
        iviLogo.setImageDrawable(res);
    }

    private ViewPropertyAnimatorCompat displayWarning(boolean visible){
        setVisibility(View.GONE,llaForm);
        setVisibility(visible ? View.VISIBLE : View.INVISIBLE,tviWarning,btnUnderstood);
        return ViewCompat.animate(btnUnderstood)
                .scaleX(visible ? 1.0f : 0.0f)
                .setStartDelay(500);
    }

    @OnClick(R.id.btnLogin) void actionLogin(){
        String user = eteUser.getText().toString();
        String password = etePassword.getText().toString();

        //Disable button and show progress
        setupViewsForLogin(true);
        //Request login
        mSubscription.add(mViewModel.login(user,password,cboRemember.isChecked())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {
                    //Notify event to analytics
                    AnalyticsManager.eventLogin(this,success);
                    //Continue app flow
                    redirectToHome();
                }, throwable -> {
                    //Notify event to analytics
                    AnalyticsManager.eventLogin(this,false);
                    //Re-enable login
                    setupViewsForLogin(false);
                    //Display error message
                    Toast.makeText(LoginActivity.this,throwable.getMessage(),Toast.LENGTH_SHORT).show();
                }));
    }

    private ProgressDialog mDialog;
    private void setupViewsForLogin(boolean loading){
        btnLogin.setEnabled(!loading);
        if(loading) {
            mDialog = new ProgressDialog(this);
            mDialog.setIndeterminate(true);
            mDialog.setMessage(getString(R.string.text_loading_login));
            mDialog.show();
        }else if(mDialog.isShowing()){
            mDialog.dismiss();
        }

    }

    private void redirectToHome(){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }

}
