package net.larntech.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TareaCompleta {

    @SerializedName("gps")
    @Expose
    private GPSVehiculo gps;
    @SerializedName("modelo")
    @Expose
    private String modelo;
    @SerializedName("chip")
    @Expose
    private GPSVehiculo chip;
    @SerializedName("id_flota")
    @Expose
    private Integer id_flota;
    @SerializedName("id_marca")
    @Expose
    private Integer id_marca;
    @SerializedName("id_modelo")
    @Expose
    private Integer id_modelo;
    @SerializedName("id_tipo")
    @Expose
    private Integer id_tipo;
    @SerializedName("nom_vehiculo")
    @Expose
    private String nom_vehiculo;
    @SerializedName("num_credito")
    @Expose
    private String num_credito;
    @SerializedName("flag_sutran")
    @Expose
    private String flag_sutran;
    @SerializedName("ultima_direccion")
    @Expose
    private String ultima_direccion;
    @SerializedName("flag_bloqueo")
    @Expose
    private Integer flag_bloqueo;
    @SerializedName("ultima_transmision")
    @Expose
    private String ultima_transmision;

    public TareaCompleta(GPSVehiculo gps, String modelo, GPSVehiculo chip, Integer id_flota, Integer id_marca, Integer id_modelo, Integer id_tipo, String nom_vehiculo, String num_credito, String flag_sutran, String ultima_direccion, Integer flag_bloqueo, String ultima_transmision) {
        super();
        this.gps = gps;
        this.modelo = modelo;
        this.chip = chip;
        this.id_flota = id_flota;
        this.id_marca = id_marca;
        this.id_modelo = id_modelo;
        this.id_tipo = id_tipo;
        this.nom_vehiculo = nom_vehiculo;
        this.num_credito = num_credito;
        this.flag_sutran = flag_sutran;
        this.ultima_direccion = ultima_direccion;
        this.flag_bloqueo = flag_bloqueo;
        this.ultima_transmision = ultima_transmision;
    }

    public GPSVehiculo getGps() {
        return gps;
    }

    public void setGps(GPSVehiculo gps) {
        this.gps = gps;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public GPSVehiculo getChip() {
        return chip;
    }

    public void setChip(GPSVehiculo chip) {
        this.chip = chip;
    }

    public Integer getId_flota() {
        return id_flota;
    }

    public void setId_flota(Integer id_flota) {
        this.id_flota = id_flota;
    }

    public Integer getId_marca() {
        return id_marca;
    }

    public void setId_marca(Integer id_marca) {
        this.id_marca = id_marca;
    }

    public Integer getId_modelo() {
        return id_modelo;
    }

    public void setId_modelo(Integer id_modelo) {
        this.id_modelo = id_modelo;
    }

    public Integer getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(Integer id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getNom_vehiculo() {
        return nom_vehiculo;
    }

    public void setNom_vehiculo(String nom_vehiculo) {
        this.nom_vehiculo = nom_vehiculo;
    }

    public String getNum_credito() {
        return num_credito;
    }

    public void setNum_credito(String num_credito) {
        this.num_credito = num_credito;
    }

    public String getFlag_sutran() {
        return flag_sutran;
    }

    public void setFlag_sutran(String flag_sutran) {
        this.flag_sutran = flag_sutran;
    }

    public String getUltima_direccion() {
        return ultima_direccion;
    }

    public void setUltima_direccion(String ultima_direccion) {
        this.ultima_direccion = ultima_direccion;
    }

    public Integer getFlag_bloqueo() {
        return flag_bloqueo;
    }

    public void setFlag_bloqueo(Integer flag_bloqueo) {
        this.flag_bloqueo = flag_bloqueo;
    }

    public String getUltima_transmision() {
        return ultima_transmision;
    }

    public void setUltima_transmision(String ultima_transmision) {
        this.ultima_transmision = ultima_transmision;
    }
}
