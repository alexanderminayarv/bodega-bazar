package modelo;

public class Pedidos {

    private int id;
    private int idEmpleado;
    private int idProveedor;
    private String fecha;
    private double subtotal;
    private double IGV;
    private double total;

    public Pedidos() {
    }

    public Pedidos(int id, int idEmpleado, int idProveedor, String fecha, double subtotal, double IGV, double total) {
        this.id = id;
        this.idEmpleado = idEmpleado;
        this.idProveedor = idProveedor;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.IGV = IGV;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIGV() {
        return IGV;
    }

    public void setIGV(double IGV) {
        this.IGV = IGV;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
