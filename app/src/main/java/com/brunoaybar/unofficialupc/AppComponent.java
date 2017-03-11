package com.brunoaybar.unofficialupc;

import com.brunoaybar.unofficialupc.data.source.DataModule;
import com.brunoaybar.unofficialupc.modules.courses.CoursesViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by brunoaybar on 11/03/2017.
 */

@Singleton
@Component( modules = { AppModule.class })
public interface AppComponent {
    void inject(DataModule target);

    void inject(CoursesViewModel target);
}
