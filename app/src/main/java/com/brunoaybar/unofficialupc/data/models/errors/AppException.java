package com.brunoaybar.unofficialupc.data.models.errors;

/**
 * Created by brunoaybar on 11/03/2017.
 */

public class AppException extends RuntimeException {

    private boolean isInternetError;
    private boolean isServiceError;

    public AppException(boolean isInternetError, boolean isServiceError) {
        this.isInternetError = isInternetError;
        this.isServiceError = isServiceError;
    }

    public AppException(boolean isInternetError, boolean isServiceError, String message){
        super(message);
        this.isInternetError = isInternetError;
        this.isServiceError = isServiceError;
    }

    public static boolean isInternetError(Exception e){
        return (e instanceof AppException) && ((AppException)e).isInternetError;
    }
    public static boolean isServiceError(Exception e){
        return (e instanceof AppException) && ((AppException)e).isServiceError;
    }
}
