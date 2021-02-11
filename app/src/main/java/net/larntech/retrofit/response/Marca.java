
package net.larntech.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Marca {

    @SerializedName("id_marca")
    @Expose
    private Integer idMarca;
    @SerializedName("des_marca")
    @Expose
    private String desMarca;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Marca() {
    }

    /**
     * 
     * @param desMarca
     * @param idMarca
     */
    public Marca(Integer idMarca, String desMarca) {
        super();
        this.idMarca = idMarca;
        this.desMarca = desMarca;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public String getDesMarca() {
        return desMarca;
    }

    public void setDesMarca(String desMarca) {
        this.desMarca = desMarca;
    }

}
