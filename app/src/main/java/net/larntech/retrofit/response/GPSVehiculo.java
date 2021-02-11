
package net.larntech.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GPSVehiculo {

    @SerializedName("id_gps")
    @Expose
    private Integer idGps;
    @SerializedName("des_tipo")
    @Expose
    private String des_tipo;
    @SerializedName("imei_gps")
    @Expose
    private String imeiGps;
    @SerializedName("fecha_asignacion")
    @Expose
    private Object fechaAsignacion;
    @SerializedName("id_estado")
    @Expose
    private Integer idEstado;
    @SerializedName("fec_registro")
    @Expose
    private String fecRegistro;
    @SerializedName("usu_registro")
    @Expose
    private Integer usuRegistro;
    @SerializedName("id_existencia")
    @Expose
    private Integer idExistencia;
    @SerializedName("num_linea")
    @Expose
    private String num_linea;
    @SerializedName("id_chip")
    @Expose
    private String id_chip;
    @SerializedName("des_chip")
    @Expose
    private String des_chip;



    /**
     * No args constructor for use in serialization
     * 
     */
    public GPSVehiculo() {
    }


    public GPSVehiculo(Integer idGps, String des_tipo, String imeiGps, Object fechaAsignacion, Integer idEstado, String fecRegistro, Integer usuRegistro, Integer idExistencia, String num_linea, String id_chip, String des_chip) {
        super();
        this.idGps = idGps;
        this.des_tipo = des_tipo;
        this.imeiGps = imeiGps;
        this.fechaAsignacion = fechaAsignacion;
        this.idEstado = idEstado;
        this.fecRegistro = fecRegistro;
        this.usuRegistro = usuRegistro;
        this.idExistencia = idExistencia;
        this.num_linea = num_linea;
        this.id_chip = id_chip;
        this.des_chip = des_chip;
    }

    public String getDes_chip() {
        return des_chip;
    }

    public void setDes_chip(String des_chip) {
        this.des_chip = des_chip;
    }

    public String getId_chip() {
        return id_chip;
    }

    public void setId_chip(String id_chip) {
        this.id_chip = id_chip;
    }

    public String getNum_linea() {
        return num_linea;
    }

    public void setNum_linea(String num_linea) {
        this.num_linea = num_linea;
    }

    public Integer getIdGps() {
        return idGps;
    }

    public void setIdGps(Integer idGps) {
        this.idGps = idGps;
    }

    public String getDes_tipo() {
        return des_tipo;
    }

    public void setDes_tipo(String des_tipo) {
        this.des_tipo = des_tipo;
    }

    public String getImeiGps() {
        return imeiGps;
    }

    public void setImeiGps(String imeiGps) {
        this.imeiGps = imeiGps;
    }

    public Object getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Object fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getFecRegistro() {
        return fecRegistro;
    }

    public void setFecRegistro(String fecRegistro) {
        this.fecRegistro = fecRegistro;
    }

    public Integer getUsuRegistro() {
        return usuRegistro;
    }

    public void setUsuRegistro(Integer usuRegistro) {
        this.usuRegistro = usuRegistro;
    }

    public Integer getIdExistencia() {
        return idExistencia;
    }

    public void setIdExistencia(Integer idExistencia) {
        this.idExistencia = idExistencia;
    }

}
