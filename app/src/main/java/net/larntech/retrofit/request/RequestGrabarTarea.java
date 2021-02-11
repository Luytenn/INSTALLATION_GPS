package net.larntech.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestGrabarTarea {

    @SerializedName("tipo_trabajo")
    @Expose
    private Integer tipo_trabajo;
    @SerializedName("posicion_gps")
    @Expose
    private Integer posicion_gps;


    public RequestGrabarTarea(){

    }


    public RequestGrabarTarea(Integer tipo_trabajo, Integer posicion_gps) {
        super();
        this.tipo_trabajo = tipo_trabajo;
        this.posicion_gps = posicion_gps;
    }

    public Integer getTipo_trabajo() {
        return tipo_trabajo;
    }

    public void setTipo_trabajo(Integer tipo_trabajo) {
        this.tipo_trabajo = tipo_trabajo;
    }

    public Integer getPosicion_gps() {
        return posicion_gps;
    }

    public void setPosicion_gps(Integer posicion_gps) {
        this.posicion_gps = posicion_gps;
    }
}
