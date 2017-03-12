package com.brunoaybar.unofficialupc.data.source.injection;

import com.brunoaybar.unofficialupc.data.repository.impl.UpcLoginRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.UpcSessionRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.UpcUserRepository;
import com.brunoaybar.unofficialupc.data.source.remote.UpcServiceDataSource;

/**
 * Created by brunoaybar on 12/03/2017.
 */

public interface BaseDataComponent {
    //User repos
    void inject(UpcSessionRepository target);
    void inject(UpcLoginRepository target);
    void inject(UpcUserRepository target);
    void inject (UpcServiceDataSource target);
}
