package com.brunoaybar.unofficialupc.modules;

import com.brunoaybar.unofficialupc.data.source.injection.RepositoryModule;
import com.brunoaybar.unofficialupc.modules.attendance.AbsencesViewModel;
import com.brunoaybar.unofficialupc.modules.auth.LoginViewModel;
import com.brunoaybar.unofficialupc.modules.classmates.ClassmatesViewModel;
import com.brunoaybar.unofficialupc.modules.courses.CoursesViewModel;
import com.brunoaybar.unofficialupc.modules.courses.calculate.CalculateViewModel;
import com.brunoaybar.unofficialupc.modules.general.MainViewModel;
import com.brunoaybar.unofficialupc.modules.reserve.ReserveViewModel;
import com.brunoaybar.unofficialupc.modules.timetable.TimetableViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by brunoaybar on 11/03/2017.
 */

@Singleton
@Component(modules = { RepositoryModule.class })
public interface ViewModelsComponent {
    void inject(MainViewModel target);
    void inject(LoginViewModel target);
    void inject(AbsencesViewModel target);
    void inject(ClassmatesViewModel target);
    void inject(CoursesViewModel target);
    void inject(CalculateViewModel target);
    void inject(TimetableViewModel target);
    void inject(ReserveViewModel target);
}
