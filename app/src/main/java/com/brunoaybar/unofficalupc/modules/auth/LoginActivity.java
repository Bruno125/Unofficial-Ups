package com.brunoaybar.unofficalupc.modules.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brunoaybar.unofficalupc.modules.general.MainActivity;
import com.brunoaybar.unofficalupc.R;
import com.brunoaybar.unofficalupc.data.source.preferences.UserPreferencesDataSource;
import com.brunoaybar.unofficalupc.data.source.remote.UpcServiceDataSource;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.brunoaybar.unofficalupc.utils.UiUtils.setVisibility;

public class LoginActivity extends AppCompatActivity {

    @Nullable private LoginViewModel mViewModel;

    @BindView(R.id.iviLogo) ImageView iviLogo;
    @BindView(R.id.llaContainer) ViewGroup llaContainer;
    @BindView(R.id.llaForm) ViewGroup llaForm;
    @BindView(R.id.tviWarning) TextView tviWarning;
    @BindView(R.id.btnUnderstood) Button btnUnderstood;

    @BindView(R.id.eteUser) EditText eteUser;
    @BindView(R.id.tilUser) TextInputLayout tilUser;
    @BindView(R.id.etePassword) EditText etePassword;
    @BindView(R.id.tilPassword) TextInputLayout tilPassword;
    @BindView(R.id.btnLogin) Button btnLogin;

    @NonNull private CompositeSubscription mSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mViewModel = new LoginViewModel(new UserPreferencesDataSource(this),UpcServiceDataSource.getInstance());

        runInitialLogoAnimation();

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
                    //Hide views
                    setVisibility(View.GONE,tviWarning,btnUnderstood);
                    //Show login form
                    llaForm.setVisibility(View.VISIBLE);
                    //Animate button login
                    btnLogin.setScaleX(0);
                    ViewCompat.animate(btnLogin)
                            .scaleX(1.0f)
                            .setStartDelay(500);
                }, 0));

    }

    private ViewPropertyAnimatorCompat displayWarning(boolean visible){
        setVisibility(visible ? View.VISIBLE : View.INVISIBLE,tviWarning,btnUnderstood);
        return ViewCompat.animate(btnUnderstood)
                .scaleX(visible ? 1.0f : 0.0f)
                .setStartDelay(500);
    }

    @OnClick(R.id.btnLogin) void actionLogin(){

        String user = eteUser.getText().toString();
        String password = etePassword.getText().toString();
        mSubscription.add(mViewModel.login(user,password)
                .subscribe(success -> {
                    redirectToHome();       //4Oe#9Xzb
                }, throwable -> {
                    Toast.makeText(LoginActivity.this,throwable.getMessage(),Toast.LENGTH_SHORT).show();
                }));
    }

    private void redirectToHome(){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }


    @Override
    protected void onResume() {
        super.onResume();
        bind();

    }

    @Override
    protected void onPause() {
        unBind();
        super.onPause();
    }

    private void bind() {
        assert mViewModel != null;

        mSubscription = new CompositeSubscription();
        mSubscription.add(mViewModel.verifyUserSession()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(verified -> { // Token is provided
                    redirectToHome();
                }, throwable -> {
                    displayWarning(true);
                })
        );

        //Validate user format
        /*Observable<Boolean> userValidation = RxTextView.textChanges(eteUser)
                .map(user -> mViewModel.userIsValid(user.toString()))
                .debounce(1, TimeUnit.SECONDS)
                .distinctUntilChanged();
        mSubscription.add(userValidation.observeOn(AndroidSchedulers.mainThread()).
                subscribe( isValid -> tilUser.setErrorEnabled(!isValid)));

        //Validate password format
        Observable<Boolean> passValidation = RxTextView.textChanges(etePassword)
                .map(password -> mViewModel.passwordIsValid(password.toString()))
                .debounce(1, TimeUnit.SECONDS)
                .distinctUntilChanged();
        mSubscription.add(passValidation.observeOn(AndroidSchedulers.mainThread()).
                subscribe( isValid -> tilUser.setErrorEnabled(!isValid)));

        //Enable or disable button based on inputs
        mSubscription.add(Observable.combineLatest(userValidation, passValidation,
                (userValid, passValid) -> userValid && passValid)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(valid -> btnLogin.setEnabled(valid)));*/

    }

    private void unBind(){
        mSubscription.unsubscribe();
    }
}
