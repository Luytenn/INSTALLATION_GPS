
package net.larntech.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModeloVehiculo {

    @SerializedName("id_modelo")
    @Expose
    private Integer idModelo;
    @SerializedName("des_modelo")
    @Expose
    private String desModelo;
    @SerializedName("id_marca")
    @Expose
    private Integer id_marca;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModeloVehiculo() {
    }

    /**
     * 
     * @param desModelo
     * @param idModelo
     */
    public ModeloVehiculo(Integer idModelo, String desModelo, Integer id_marca) {
        super();
        this.idModelo = idModelo;
        this.desModelo = desModelo;
        this.id_marca = id_marca;
    }

    public Integer getId_marca() {
        return id_marca;
    }

    public void setId_marca(Integer id_marca) {
        this.id_marca = id_marca;
    }

    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public String getDesModelo() {
        return desModelo;
    }

    public void setDesModelo(String desModelo) {
        this.desModelo = desModelo;
    }

}
