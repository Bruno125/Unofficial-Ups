

package com.brunoaybar.unofficialupc.data.source.remote.responses;

import com.brunoaybar.unofficialupc.data.models.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginResponse extends BaseResponse {

    @SerializedName("Codigo")
    @Expose
    private String codigo;
    @SerializedName("CodigoAlumno")
    @Expose
    private String codigoAlumno;
    @SerializedName("Nombres")
    @Expose
    private String nombres;
    @SerializedName("Apellidos")
    @Expose
    private String apellidos;
    @SerializedName("Genero")
    @Expose
    private String genero;
    @SerializedName("EsAlumno")
    @Expose
    private String esAlumno;
    @SerializedName("Estado")
    @Expose
    private String estado;
    @SerializedName("TipoUser")
    @Expose
    private String tipoUser;
    @SerializedName("Token")
    @Expose
    private String token;
    @SerializedName("Datos")
    @Expose
    private Datos datos;


    public User transform(){
        if (isError()){
            throw new ServiceException(this);
        }

        User user = new User();
        checkNotNull(token);
        user.setToken(token);

        checkNotNull(codigo);
        user.setUserCode(codigo);

        user.setNames(nombres);
        user.setLastnames(apellidos);
        user.setGenre(genero);

        return user;
    }

    /**
     *
     * @return
     * The codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     *
     * @param codigo
     * The Codigo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     *
     * @return
     * The codigoAlumno
     */
    public String getCodigoAlumno() {
        return codigoAlumno;
    }

    /**
     *
     * @param codigoAlumno
     * The CodigoAlumno
     */
    public void setCodigoAlumno(String codigoAlumno) {
        this.codigoAlumno = codigoAlumno;
    }

    /**
     *
     * @return
     * The nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     *
     * @param nombres
     * The Nombres
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     *
     * @return
     * The apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     *
     * @param apellidos
     * The Apellidos
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     *
     * @return
     * The genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     *
     * @param genero
     * The Genero
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     *
     * @return
     * The esAlumno
     */
    public String getEsAlumno() {
        return esAlumno;
    }

    /**
     *
     * @param esAlumno
     * The EsAlumno
     */
    public void setEsAlumno(String esAlumno) {
        this.esAlumno = esAlumno;
    }

    /**
     *
     * @return
     * The estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     *
     * @param estado
     * The Estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     *
     * @return
     * The tipoUser
     */
    public String getTipoUser() {
        return tipoUser;
    }

    /**
     *
     * @param tipoUser
     * The TipoUser
     */
    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The Token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return
     * The datos
     */
    public Datos getDatos() {
        return datos;
    }

    /**
     *
     * @param datos
     * The Datos
     */
    public void setDatos(Datos datos) {
        this.datos = datos;
    }

    public class Datos {

        @SerializedName("CodLinea")
        @Expose
        private String codLinea;
        @SerializedName("DscLinea")
        @Expose
        private String dscLinea;
        @SerializedName("CodModal")
        @Expose
        private String codModal;
        @SerializedName("DscModal")
        @Expose
        private String dscModal;
        @SerializedName("CodSede")
        @Expose
        private String codSede;
        @SerializedName("DscSede")
        @Expose
        private String dscSede;
        @SerializedName("Ciclo")
        @Expose
        private String ciclo;

        /**
         *
         * @return
         * The codLinea
         */
        public String getCodLinea() {
            return codLinea;
        }

        /**
         *
         * @param codLinea
         * The CodLinea
         */
        public void setCodLinea(String codLinea) {
            this.codLinea = codLinea;
        }

        /**
         *
         * @return
         * The dscLinea
         */
        public String getDscLinea() {
            return dscLinea;
        }

        /**
         *
         * @param dscLinea
         * The DscLinea
         */
        public void setDscLinea(String dscLinea) {
            this.dscLinea = dscLinea;
        }

        /**
         *
         * @return
         * The codModal
         */
        public String getCodModal() {
            return codModal;
        }

        /**
         *
         * @param codModal
         * The CodModal
         */
        public void setCodModal(String codModal) {
            this.codModal = codModal;
        }

        /**
         *
         * @return
         * The dscModal
         */
        public String getDscModal() {
            return dscModal;
        }

        /**
         *
         * @param dscModal
         * The DscModal
         */
        public void setDscModal(String dscModal) {
            this.dscModal = dscModal;
        }

        /**
         *
         * @return
         * The codSede
         */
        public String getCodSede() {
            return codSede;
        }

        /**
         *
         * @param codSede
         * The CodSede
         */
        public void setCodSede(String codSede) {
            this.codSede = codSede;
        }

        /**
         *
         * @return
         * The dscSede
         */
        public String getDscSede() {
            return dscSede;
        }

        /**
         *
         * @param dscSede
         * The DscSede
         */
        public void setDscSede(String dscSede) {
            this.dscSede = dscSede;
        }

        /**
         *
         * @return
         * The ciclo
         */
        public String getCiclo() {
            return ciclo;
        }

        /**
         *
         * @param ciclo
         * The Ciclo
         */
        public void setCiclo(String ciclo) {
            this.ciclo = ciclo;
        }

    }
}