package modelo;

public class Entradas {

    private int idProducto;
    private String nombre;
    private int cantidad;
    private String fecha;
    private String empleado;
    private double total;

    public Entradas() {
    }

    public Entradas(int idProducto, String nombre, int cantidad, String fecha, String empleado, double total) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.empleado = empleado;
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

}
