
package net.larntech.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TipoVehiculo {

    @SerializedName("id_tipo")
    @Expose
    private Integer idTipo;
    @SerializedName("des_tipo")
    @Expose
    private String desTipo;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TipoVehiculo() {
    }

    /**
     * 
     * @param desTipo
     * @param idTipo
     */
    public TipoVehiculo(Integer idTipo, String desTipo) {
        super();
        this.idTipo = idTipo;
        this.desTipo = desTipo;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getDesTipo() {
        return desTipo;
    }

    public void setDesTipo(String desTipo) {
        this.desTipo = desTipo;
    }

}
