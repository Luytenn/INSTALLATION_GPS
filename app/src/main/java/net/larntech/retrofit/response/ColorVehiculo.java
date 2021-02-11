
package net.larntech.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ColorVehiculo {

    @SerializedName("id_color")
    @Expose
    private Integer idColor;
    @SerializedName("des_color")
    @Expose
    private String desColor;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ColorVehiculo() {
    }

    /**
     * 
     * @param desColor
     * @param idColor
     */
    public ColorVehiculo(Integer idColor, String desColor) {
        super();
        this.idColor = idColor;
        this.desColor = desColor;
    }

    public Integer getIdColor() {
        return idColor;
    }

    public void setIdColor(Integer idColor) {
        this.idColor = idColor;
    }

    public String getDesColor() {
        return desColor;
    }

    public void setDesColor(String desColor) {
        this.desColor = desColor;
    }

}
