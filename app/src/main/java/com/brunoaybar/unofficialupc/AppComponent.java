package com.brunoaybar.unofficialupc;

import com.brunoaybar.unofficialupc.data.models.errors.NoInternetException;
import com.brunoaybar.unofficialupc.data.source.DataModule;
import com.brunoaybar.unofficialupc.data.source.RepositoryModule;
import com.brunoaybar.unofficialupc.modules.attendance.AbsencesViewModel;
import com.brunoaybar.unofficialupc.modules.auth.LoginViewModel;
import com.brunoaybar.unofficialupc.modules.classmates.ClassmatesViewModel;
import com.brunoaybar.unofficialupc.modules.courses.CoursesViewModel;
import com.brunoaybar.unofficialupc.modules.courses.calculate.CalculateActivity;
import com.brunoaybar.unofficialupc.modules.general.MainViewModel;
import com.brunoaybar.unofficialupc.modules.timetable.TimetableViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by brunoaybar on 11/03/2017.
 */

@Singleton
@Component( modules = { AppModule.class})
public interface AppComponent {
    void inject(DataModule target);
    void inject(NoInternetException target);
}
