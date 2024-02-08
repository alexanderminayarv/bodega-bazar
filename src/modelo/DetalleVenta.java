
package modelo;


public class DetalleVenta {
    private int id;
    private int idVenta;
    private int idProducto;
    private int cantidad;
    private double preVenta;

    public DetalleVenta() {
    }

    public DetalleVenta(int id, int idVenta, int idProducto, int cantidad, double preVenta) {
        this.id = id;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.preVenta = preVenta;
    }

    public double getPreVenta() {
        return preVenta;
    }

    public void setPreVenta(double preVenta) {
        this.preVenta = preVenta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    
}
