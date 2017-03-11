package com.brunoaybar.unofficialupc.modules;

import com.brunoaybar.unofficialupc.data.source.RepositoryModule;
import com.brunoaybar.unofficialupc.modules.auth.LoginViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by brunoaybar on 11/03/2017.
 */

@Singleton
@Component(modules = { RepositoryModule.class })
public interface RepositoryComponent {
    void inject(LoginViewModel target);

    /*void inject(AbsencesViewModel target);
    void inject(ClassmatesViewModel target);
    void inject(CoursesViewModel target);
    void inject(CalculateViewModel target);
    void inject(TimetableViewModel target);*/
}
