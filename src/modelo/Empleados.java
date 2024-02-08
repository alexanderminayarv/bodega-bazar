/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author minay
 */
public class Empleados {
    private int id;
    private String DNI;
    private String nombres;
    private String apellido_paterno;
    private String apellid_materno;
    private String celular;
    private String correo;
    private String password;
    private String estado;
    private int id_rol;
    private String rol;

    public Empleados() {
    }

    public Empleados(int id, String DNI, String nombres, String apellido_paterno, String apellid_materno, String celular, String correo, String password, String estado, int id_rol, String rol) {
        this.id = id;
        this.DNI = DNI;
        this.nombres = nombres;
        this.apellido_paterno = apellido_paterno;
        this.apellid_materno = apellid_materno;
        this.celular = celular;
        this.correo = correo;
        this.password = password;
        this.estado=estado;
        this.id_rol = id_rol;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellid_materno() {
        return apellid_materno;
    }

    public void setApellid_materno(String apellid_materno) {
        this.apellid_materno = apellid_materno;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }
    
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

  
    
}
