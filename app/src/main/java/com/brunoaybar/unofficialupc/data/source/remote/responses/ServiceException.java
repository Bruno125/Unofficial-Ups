package com.brunoaybar.unofficialupc.data.source.remote.responses;

import com.brunoaybar.unofficialupc.data.models.errors.AppException;

/**
 * Created by brunoaybar on 15/10/2016.
 */

public class ServiceException extends AppException{

    public ServiceException(BaseResponse failedRequest){
        super(false,true,failedRequest.getErrorMessage());
    }


}
