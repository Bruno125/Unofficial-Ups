package com.brunoaybar.unofficialupc.injection;

import com.brunoaybar.unofficialupc.data.models.errors.NoInternetException;
import com.brunoaybar.unofficialupc.data.source.injection.DataModule;

/**
 * Created by brunoaybar on 12/03/2017.
 */

public interface BaseAppComponent {
    void inject(DataModule target);
    void inject(NoInternetException target);
}
