
package modelo;

public class Proveedores {
    private int idProv;
    private String ruc;
    private String nombre;
    private String direccion;
    private String email;
    private String celular;
    private int id_categoria;
    private String categoria;

    public Proveedores() {
    }

    public Proveedores(int idProv, String ruc, String nombre, String direccion, String email, String celular, int id_categoria, String categoria) {
        this.idProv = idProv;
        this.ruc = ruc;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.celular = celular;
        this.id_categoria = id_categoria;
        this.categoria = categoria;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public int getIdProv() {
        return idProv;
    }

    public void setIdProv(int idProv) {
        this.idProv = idProv;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

   

}
