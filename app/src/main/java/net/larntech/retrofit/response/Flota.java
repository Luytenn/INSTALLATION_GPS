
package net.larntech.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flota {

    @SerializedName("id_flota")
    @Expose
    private Integer idFlota;
    @SerializedName("des_flota")
    @Expose
    private String desFlota;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Flota() {
    }

    /**
     * 
     * @param desFlota
     * @param idFlota
     */
    public Flota(Integer idFlota, String desFlota) {
        super();
        this.idFlota = idFlota;
        this.desFlota = desFlota;
    }

    public Integer getIdFlota() {
        return idFlota;
    }

    public void setIdFlota(Integer idFlota) {
        this.idFlota = idFlota;
    }

    public String getDesFlota() {
        return desFlota;
    }

    public void setDesFlota(String desFlota) {
        this.desFlota = desFlota;
    }

}
