package net.larntech.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ubicacion {


    @SerializedName("ultima_ubicacion")
    @Expose
    private String ultima_ubicacion;
    @SerializedName("ultima_transmision")
    @Expose
    private String ultima_transmision;
    @SerializedName("flag_bloqueado")
    @Expose
    private String flag_bloqueado;

    public Ubicacion(){

    }


    public Ubicacion(String ultima_ubicacion, String ultima_transmision,String flag_bloqueado) {
        this.ultima_ubicacion = ultima_ubicacion;
        this.ultima_transmision = ultima_transmision;
        this.flag_bloqueado = flag_bloqueado;
    }

    public String getFlag_bloqueado() {
        return flag_bloqueado;
    }

    public void setFlag_bloqueado(String flag_bloqueado) {
        this.flag_bloqueado = flag_bloqueado;
    }

    public String getUltima_ubicacion() {
        return ultima_ubicacion;
    }

    public void setUltima_ubicacion(String ultima_ubicacion) {
        this.ultima_ubicacion = ultima_ubicacion;
    }

    public String getUltima_transmision() {
        return ultima_transmision;
    }

    public void setUltima_transmision(String ultima_transmision) {
        this.ultima_transmision = ultima_transmision;
    }
}
