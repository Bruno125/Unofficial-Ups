package com.brunoaybar.unofficialupc.data.source.injection;

import com.brunoaybar.unofficialupc.data.repository.impl.demo.DemoLoginRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.demo.DemoSessionRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.demo.DemoUniversityInfoRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.demo.DemoUserRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.upc.UpcLoginRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.upc.UpcSessionRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.upc.UpcUniversityInfoRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.upc.UpcUserRepository;
import com.brunoaybar.unofficialupc.data.source.remote.UpcServiceDataSource;

public interface BaseDataComponent {
    void inject(UpcSessionRepository target);
    void inject(UpcLoginRepository target);
    void inject(UpcUserRepository target);
    void inject(UpcServiceDataSource target);
    void inject(UpcUniversityInfoRepository target);

    void inject(DemoSessionRepository target);
    void inject(DemoLoginRepository target);
    void inject(DemoUserRepository target);
    void inject(DemoUniversityInfoRepository target);
}
