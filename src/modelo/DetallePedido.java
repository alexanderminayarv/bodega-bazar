
package modelo;


public class DetallePedido {
    private int id;
    private int idPedido;
    private int idProducto;
    private int cantidad;
    private double preCompra;

    public DetallePedido() {
    }

    public DetallePedido(int id, int idPedido, int idProducto, int cantidad, double preCompra) {
        this.id = id;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.preCompra = preCompra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
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

    public double getPreCompra() {
        return preCompra;
    }

    public void setPreCompra(double preCompra) {
        this.preCompra = preCompra;
    }

   
}
