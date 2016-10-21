package com.brunoaybar.unofficialupc.data.source.remote.requests;

/**
 * Request model that will by passed by POST to the Auth request
 */

public final class LoginRequest {
    final String Usuario;
    final String Contrasena;
    final String Plataforma;

    public LoginRequest(String user, String password, String platform){
        this.Usuario = user;
        this.Contrasena = password;
        this.Plataforma = platform;
    }

}
