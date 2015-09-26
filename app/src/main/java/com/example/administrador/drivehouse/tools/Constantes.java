package com.example.administrador.drivehouse.tools;

/**
 * Created by Jhonatan Quilca
 */
public class Constantes {
    /**
     * Transición Home -> Detalle
     */
    public static final int CODIGO_DETALLE = 100;

    /**
     * Transición Detalle -> Actualización
     */
    public static final int CODIGO_ACTUALIZACION = 101;

    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta carácteristica.
     */
    private static final String PUERTO_HOST = "80";

    /**
     * Dirección IP de genymotion o AVD
     */
    private static final String IP = "http://192.168.1.61:";
    /**
     * URLs del Web Service
     */
    public static final String ADMIN = IP + PUERTO_HOST + "/driveworkhouse/cliente/clienteWs/admin";
    public static final String SEARCH = IP + PUERTO_HOST + "/driveworkhouse/cliente/clienteWs/search/param/";
    public static final String VIEW = IP + PUERTO_HOST + "/driveworkhouse/cliente/clienteWs/view/id/";
    public static final String UPDATE = IP + PUERTO_HOST + "/driveworkhouse/cliente/clienteWs/update/";
    public static final String DELETE = IP + PUERTO_HOST + "/webservicewish/delete_meta.php";
    public static final String INSERT = IP + PUERTO_HOST + "/driveworkhouse/cliente/clienteWs/create";

    /**
     * Clave para el valor extra que representa al identificador de una meta
     */
    public static final String EXTRA_ID = "IDEXTRA";
}
