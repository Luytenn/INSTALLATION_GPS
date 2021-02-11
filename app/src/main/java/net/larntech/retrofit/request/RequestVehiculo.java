
package net.larntech.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;

public class RequestVehiculo {

    @SerializedName("id_modelo")
    @Expose
    private Integer idModelo;
    @SerializedName("id_marca")
    @Expose
    private Integer idMarca;
    @SerializedName("id_chip")
    @Expose
    private Integer idChip;
    @SerializedName("id_gps")
    @Expose
    private Integer idGps;
    @SerializedName("id_tipo")
    @Expose
    private Integer idTipo;
    @SerializedName("nom_vehiculo")
    @Expose
    private String nomVehiculo;
    @SerializedName("num_credito")
    @Expose
    private String numCredito;
    @SerializedName("flag_sutran")
    @Expose
    private String flagSutran;
    @SerializedName("id_flota")
    @Expose
    private Integer id_flota;
    @SerializedName("estado")
    @Expose
    private Integer estado;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;



    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestVehiculo() {
    }

    /**
     * 
     * @param flagSutran
     * @param idChip
     * @param idGps
     * @param nomVehiculo
     * @param idTipo
     * @param idModelo
     * @param idMarca
     * @param numCredito
     */
    public RequestVehiculo(Integer idModelo, Integer idMarca, Integer idChip, Integer idGps, Integer idTipo, String nomVehiculo, String numCredito, String flagSutran, Integer id_flota,Integer estado, String mensaje) {
        super();
        this.idModelo = idModelo;
        this.idMarca = idMarca;
        this.idChip = idChip;
        this.idGps = idGps;
        this.idTipo = idTipo;
        this.nomVehiculo = nomVehiculo;
        this.numCredito = numCredito;
        this.flagSutran = flagSutran;
        this.id_flota = id_flota;
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getId_flota() {
        return id_flota;
    }

    public void setId_flota(Integer id_flota) {
        this.id_flota = id_flota;
    }

    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public Integer getIdChip() {
        return idChip;
    }

    public void setIdChip(Integer idChip) {
        this.idChip = idChip;
    }

    public Integer getIdGps() {
        return idGps;
    }

    public void setIdGps(Integer idGps) {
        this.idGps = idGps;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getNomVehiculo() {
        return nomVehiculo;
    }

    public void setNomVehiculo(String nomVehiculo) {
        this.nomVehiculo = nomVehiculo;
    }

    public String getNumCredito() {
        return numCredito;
    }

    public void setNumCredito(String numCredito) {
        this.numCredito = numCredito;
    }

    public String getFlagSutran() {
        return flagSutran;
    }

    public void setFlagSutran(String flagSutran) {
        this.flagSutran = flagSutran;
    }

}
