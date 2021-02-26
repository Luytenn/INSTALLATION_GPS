
package net.larntech.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TecnicoTareas implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("placa")
    @Expose
    private String placa;
    @SerializedName("cliente")
    @Expose
    private String cliente;
    @SerializedName("numero_vin")
    @Expose
    private String numero_vin;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("marca")
    @Expose
    private String marca;
    @SerializedName("modelo")
    @Expose
    private String modelo;
    @SerializedName("concecionario")
    @Expose
    private String concecionario;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("tipo_solicitud")
    @Expose
    private String tipo_solicitud;
    @SerializedName("celular")
    @Expose
    private String celular;
    @SerializedName("estado")
    @Expose
    private boolean estado;



    /**
     * No args constructor for use in serialization
     * 
     */
    public TecnicoTareas() {
    }


    public TecnicoTareas(Integer id, String placa, String cliente, String numero_vin, String color,String marca, String modelo, String concecionario, String direccion, String tipo_solicitud, String celular, boolean estado) {
        super();
        this.id = id;
        this.placa = placa;
        this.cliente = cliente;
        this.numero_vin = numero_vin;
        this.color = color;
        this.marca = marca;
        this.modelo = modelo;
        this.concecionario = concecionario;
        this.direccion = direccion;
        this.tipo_solicitud = tipo_solicitud;
        this.celular = celular;
        this.estado = estado;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNumero_vin() {
        return numero_vin;
    }

    public void setNumero_vin(String numero_vin) {
        this.numero_vin = numero_vin;
    }

    public String getColor() {
        return color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo_solicitud() {
        return tipo_solicitud;
    }

    public void setTipo_solicitud(String tipo_solicitud) {
        this.tipo_solicitud = tipo_solicitud;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getConcecionario() {
        return concecionario;
    }

    public void setConcecionario(String concecionario) {
        this.concecionario = concecionario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoSolicitud() {
        return tipo_solicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipo_solicitud = tipo_solicitud;
    }

}
