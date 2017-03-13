package com.brunoaybar.unofficialupc.data.source.remote.responses;

import com.brunoaybar.unofficialupc.data.models.Payment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunoaybar on 12/03/2017.
 */

public class PaymentsResponse extends BaseResponse{

    private List<SinglePayment> PagosPendientes;

    public List<Payment> transform(){
        //TODO
        return new ArrayList<>();
    }


    class SinglePayment{
        private String CodAlumno;
        private int NroCuota;
        private String TipoDocumento;
        private String NroDocumento;
        private String Moneda;
        private String Importe;
        private String Descuento;
        private String Impuesto;
        private String ImporteCancelado;
        private String Saldo;
        private String FecEmision;
        private String FecVencimiento;
        private String Mora;
        private String Total;

        public String getCodAlumno() {
            return CodAlumno;
        }

        public void setCodAlumno(String codAlumno) {
            CodAlumno = codAlumno;
        }

        public int getNroCuota() {
            return NroCuota;
        }

        public void setNroCuota(int nroCuota) {
            NroCuota = nroCuota;
        }

        public String getTipoDocumento() {
            return TipoDocumento;
        }

        public void setTipoDocumento(String tipoDocumento) {
            TipoDocumento = tipoDocumento;
        }

        public String getNroDocumento() {
            return NroDocumento;
        }

        public void setNroDocumento(String nroDocumento) {
            NroDocumento = nroDocumento;
        }

        public String getMoneda() {
            return Moneda;
        }

        public void setMoneda(String moneda) {
            Moneda = moneda;
        }

        public String getImporte() {
            return Importe;
        }

        public void setImporte(String importe) {
            Importe = importe;
        }

        public String getDescuento() {
            return Descuento;
        }

        public void setDescuento(String descuento) {
            Descuento = descuento;
        }

        public String getImpuesto() {
            return Impuesto;
        }

        public void setImpuesto(String impuesto) {
            Impuesto = impuesto;
        }

        public String getImporteCancelado() {
            return ImporteCancelado;
        }

        public void setImporteCancelado(String importeCancelado) {
            ImporteCancelado = importeCancelado;
        }

        public String getSaldo() {
            return Saldo;
        }

        public void setSaldo(String saldo) {
            Saldo = saldo;
        }

        public String getFecEmision() {
            return FecEmision;
        }

        public void setFecEmision(String fecEmision) {
            FecEmision = fecEmision;
        }

        public String getFecVencimiento() {
            return FecVencimiento;
        }

        public void setFecVencimiento(String fecVencimiento) {
            FecVencimiento = fecVencimiento;
        }

        public String getMora() {
            return Mora;
        }

        public void setMora(String mora) {
            Mora = mora;
        }

        public String getTotal() {
            return Total;
        }

        public void setTotal(String total) {
            Total = total;
        }
    }

}
