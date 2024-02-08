
package modelo;

public class Productos {
    private int idPro;
    private String nombre;
    private String descripcion;
    private int stock;
    private double precioC;
    private double precioV;
    private int id_categoria;
    private String categoria;

    public Productos() {
    }

    public Productos(int idPro, String nombre, String descripcion, int stock, 
            double precioC, double precioV, int id_categoria, String categoria) 
    {
        this.idPro = idPro;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precioC = precioC;
        this.precioV = precioV;
        this.id_categoria = id_categoria;
        this.categoria = categoria;
    }
    
    

    public int getIdPro() {
        return idPro;
    }

    public void setIdPro(int idPro) {
        this.idPro = idPro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecioC() {
        return precioC;
    }

    public void setPrecioC(double precioC) {
        this.precioC = precioC;
    }

    public double getPrecioV() {
        return precioV;
    }

    public void setPrecioV(double precioV) {
        this.precioV = precioV;
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
