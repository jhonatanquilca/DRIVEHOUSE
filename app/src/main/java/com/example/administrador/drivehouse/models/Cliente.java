package com.example.administrador.drivehouse.models;

/**
 * Created by Cliente on 08/09/2015.
 */
public class Cliente {
    // atributos

    private String id; // int(11) NOT NULL AUTO_INCREMENT,
    private String nombre; // varchar(32) NOT NULL,
    private String apellido; // varchar(32) NOT NULL,
    private String documento; // varchar(20) DEFAULT NULL,
    private String telefono; // varchar(24) DEFAULT NULL,
    private String celular; // varchar(24) DEFAULT NULL,
    private String email_1; // varchar(255) DEFAULT NULL,
    private String email_2; // varchar(255) DEFAULT NULL,
    private String estado; // enum('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
    private String usuario_creacion_id; // int(11) NOT NULL,
    private String usuario_actualizacion_id; // int(11) DEFAULT NULL,
    private String fecha_creacion; // datetime NOT NULL,
    private String fecha_actualizacion; // datetime DEFAULT NULL,

    public Cliente(String id, String nombre, String apellido, String documento, String telefono, String celular, String email_1, String email_2, String estado, String usuario_creacion_id, String usuario_actualizacion_id, String fecha_creacion, String fecha_actualizacion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.telefono = telefono;
        this.celular = celular;
        this.email_1 = email_1;
        this.email_2 = email_2;
        this.estado = estado;
        this.usuario_creacion_id = usuario_creacion_id;
        this.usuario_actualizacion_id = usuario_actualizacion_id;
        this.fecha_creacion = fecha_creacion;
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCelular() {
        return celular;
    }

    public String getEmail_1() {
        return email_1;
    }

    public String getEmail_2() {
        return email_2;
    }

    public String getEstado() {
        return estado;
    }

    public String getUsuario_creacion_id() {
        return usuario_creacion_id;
    }

    public String getUsuario_actualizacion_id() {
        return usuario_actualizacion_id;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public String getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setEmail_1(String email_1) {
        this.email_1 = email_1;
    }

    public void setEmail_2(String email_2) {
        this.email_2 = email_2;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setUsuario_creacion_id(String usuario_creacion_id) {
        this.usuario_creacion_id = usuario_creacion_id;
    }

    public void setUsuario_actualizacion_id(String usuario_actualizacion_id) {
        this.usuario_actualizacion_id = usuario_actualizacion_id;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public void setFecha_actualizacion(String fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

    /**
     * Compara los atributos de dos Clientes
     *
     * @param cliente Cliente externa
     * @return true si son iguales, false si hay cambios
     */
    public boolean compararCon(Cliente cliente) {
        return this.nombre.compareTo(cliente.nombre) == 0 &&
                this.apellido.compareTo(cliente.apellido) == 0 &&
                this.documento.compareTo(cliente.documento) == 0 &&
                this.telefono.compareTo(cliente.telefono) == 0 &&
                this.celular.compareTo(cliente.celular) == 0;


    }
}
