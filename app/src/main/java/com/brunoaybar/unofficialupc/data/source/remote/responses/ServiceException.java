package com.brunoaybar.unofficialupc.data.source.remote.responses;

/**
 * Created by brunoaybar on 15/10/2016.
 */

public class ServiceException extends RuntimeException{

    public ServiceException(BaseResponse failedRequest){
        super(failedRequest.getErrorMessage());
    }


}
