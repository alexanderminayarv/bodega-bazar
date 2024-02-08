package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ProductoDAO;
import modelo.Productos;
import modelo.DetalleVenta;
import modelo.VentaDAO;
import modelo.Ventas;
import vista.FrmPrincipal;

public class VentaControlador implements ActionListener, MouseListener, KeyListener {

    private Ventas v;
    private VentaDAO vDao;
    private FrmPrincipal vista;
    private Productos p;
    private ProductoDAO proDAO;
    DefaultTableModel modelo = new DefaultTableModel();
    int idp, cant;
    double t, tpagar, pre, igv;
    private DetalleVenta dv;
    String nombre_o;
    int id;

    public VentaControlador(Ventas v, VentaDAO vDao, int id, FrmPrincipal vista, String almacenero_o_vendedor_o_administrador) {
        this.v = v;
        String rol = almacenero_o_vendedor_o_administrador;
        this.vDao = vDao;
        this.vista = vista;
        this.id = id;
        this.vista.btnBuscarProducto.addActionListener(this);
        this.vista.btnAgregarProductoCarrito.addActionListener(this);
        this.vista.btnGenerarVenta.addActionListener(this);
        this.proDAO = new ProductoDAO();
        this.vDao = new VentaDAO();
        this.dv = new DetalleVenta();
        fecha();
        switch (rol) {
            case "Almacenero":
                //Venta
                this.vista.txtidproducto.setEnabled(false);
                this.vista.txtcantidad.setEnabled(false);
                this.vista.txtTotalPagar.setEnabled(false);
                this.vista.btnBuscarProducto.setEnabled(false);
                this.vista.btnAgregarProductoCarrito.setEnabled(false);
                this.vista.btnGenerarVenta.setEnabled(false);
                break;
            case "Vendedor":
                //Venta
                this.vista.txtidproducto.setEnabled(true);
                this.vista.txtcantidad.setEnabled(true);
                this.vista.txtTotalPagar.setEnabled(true);
                this.vista.btnBuscarProducto.setEnabled(true);
                this.vista.btnAgregarProductoCarrito.setEnabled(true);
                this.vista.btnGenerarVenta.setEnabled(true);
                break;
            case "Administrador":
                //Venta
                this.vista.txtidproducto.setEnabled(true);
                this.vista.txtcantidad.setEnabled(true);
                this.vista.txtTotalPagar.setEnabled(true);
                this.vista.btnBuscarProducto.setEnabled(true);
                this.vista.btnAgregarProductoCarrito.setEnabled(true);
                this.vista.btnGenerarVenta.setEnabled(true);
                break;
            default:
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnBuscarProducto) {
            if (vista.txtidproducto.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Debe ingresar el ID del producto");
            } else {
                buscarProductos();
            }
        } else if (e.getSource() == vista.btnGenerarVenta) {
            if (vista.txtTotalPagar.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Debe Ingresar Datos");
            } else {
                guardarVenta();
                guardarDetalle();
                actualizarStock();
                JOptionPane.showMessageDialog(null, "Se Realizo con Exito");
                nuevo();
            }
        } else if (e.getSource() == vista.btnAgregarProductoCarrito) {
            if (vista.txtidproducto.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Debe ingresar el ID del producto");
            } else if (Integer.parseInt(vista.txtcantidad.getValue().toString()) <= 0) {
                JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0");
            } else {
                agregarProducto();
            }
        }
    }

    //metodo buscar producto
    void buscarProductos() {
        String id = vista.txtidproducto.getText();
        p = proDAO.ListarID(Integer.parseInt(id));
        if (p.getIdPro() != 0) {
            vista.txtproducto.setText(p.getNombre());
            vista.txtprecio.setText("" + p.getPrecioV());
            vista.txtstock.setText("" + p.getStock());
        } else {
            JOptionPane.showMessageDialog(null, "Producto NO Registrado");
            vista.txtidproducto.requestFocus();
        }
    }

    void actualizarStock() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            idp = Integer.parseInt(vista.TablaDetalle.getValueAt(i, 1).toString());
            cant = Integer.parseInt(vista.TablaDetalle.getValueAt(i, 3).toString());
            p = proDAO.ListarID(idp);
            int sa = p.getStock() - cant;
            proDAO.actualizarStock(sa, idp);
        }
    }

    void guardarVenta() {
        String fecha = vista.txtfecha.getText();
        double subtotal = t;
        double _igv = igv;
        double total = tpagar;
        v.setIdEmpleado(id);
        v.setFecha(fecha);
        v.setSubtotal(subtotal);
        v.setIGV(_igv);
        v.setTotal(total);
        vDao.GuardarVentas(v);
    }

    void guardarDetalle() {
        String idv = vDao.IdVta();
        int idve = Integer.parseInt(idv);
        for (int i = 0; i < vista.TablaDetalle.getRowCount(); i++) {
            int idp = Integer.parseInt(vista.TablaDetalle.getValueAt(i, 1).toString());
            int cant = Integer.parseInt(vista.TablaDetalle.getValueAt(i, 3).toString());
            double pre = Double.parseDouble(vista.TablaDetalle.getValueAt(i, 4).toString());
            dv.setIdVenta(idve);
            dv.setIdProducto(idp);
            dv.setCantidad(cant);
            dv.setPreVenta(pre);
            vDao.GuardarDetalleVentas(dv);
        }
    }

    void agregarProducto() {
        double subtotal;
        int item = 0;
        modelo = (DefaultTableModel) vista.TablaDetalle.getModel();
        item = item + 1;
        idp = p.getIdPro();
        String nomp = vista.txtproducto.getText();
        pre = Double.parseDouble(vista.txtprecio.getText());
        cant = Integer.parseInt(vista.txtcantidad.getValue().toString());
        int stock = Integer.parseInt(vista.txtstock.getText());
        subtotal = cant * pre;
        ArrayList lista = new ArrayList();
        if (stock > 0 && cant <= stock) {
            lista.add(item);
            lista.add(idp);
            lista.add(nomp);
            lista.add(cant);
            lista.add(pre);
            lista.add(subtotal);
            Object[] ob = new Object[6];
            ob[0] = lista.get(0);
            ob[1] = lista.get(1);
            ob[2] = lista.get(2);
            ob[3] = lista.get(3);
            ob[4] = lista.get(4);
            ob[5] = lista.get(5);
            modelo.addRow(ob);
            vista.TablaDetalle.setModel(modelo);
            calcularTotal();
            calcularIGV();
            calcularTotalPagar();
            vista.txtcantidad.setValue(0);
            vista.txtidproducto.setText("");
            vista.txtprecio.setText("");
            vista.txtproducto.setText("");
            vista.txtstock.setText("");
            JOptionPane.showMessageDialog(null, "Se Agregó correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "Stock de Producto NO Disponible");
        }
    }

    void calcularTotal() {
        t = 0;
        for (int i = 0; i < vista.TablaDetalle.getRowCount(); i++) {
            //System.out.println("123 " + vista.TablaDetalle);
            cant = Integer.parseInt(vista.TablaDetalle.getValueAt(i, 3).toString());
            pre = Double.parseDouble(vista.TablaDetalle.getValueAt(i, 4).toString());
            t = t + (cant * pre);
        }
        vista.txtTotal.setText("" + t);
    }
    
      void calcularIGV(){
       igv=Double.parseDouble(vista.txtTotal.getText()) * 0.18;
       vista.txtIGV.setText("" +  Math.round(igv*100.0)/100.0);
    }
    
     void calcularTotalPagar(){
       tpagar=Double.parseDouble(vista.txtTotal.getText()) + Double.parseDouble(vista.txtIGV.getText());
       vista.txtTotalPagar.setText("" + Math.round(tpagar*100.0)/100.0);
    }

    void fecha() {
        LocalDate calendar = LocalDate.now();
        vista.txtfecha.setText("" + calendar);
    }

    void limpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    void nuevo() {
        limpiarTabla();
        vista.txtidproducto.setText("");
        vista.txtproducto.setText("");
        vista.txtcantidad.setValue(0);
        vista.txtprecio.setText("");
        vista.txtstock.setText("");
        vista.txtTotal.setText("");
        vista.txtIGV.setText("");
        vista.txtTotalPagar.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
