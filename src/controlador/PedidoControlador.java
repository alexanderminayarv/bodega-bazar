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
import modelo.DetallePedido;
import modelo.PedidoDAO;
import modelo.Pedidos;
import modelo.ProductoDAO;
import modelo.Productos;
import modelo.ProveedorDAO;
import modelo.Proveedores;
import vista.FrmPrincipal;

public class PedidoControlador implements ActionListener, MouseListener, KeyListener {

    private Pedidos pe;
    private PedidoDAO peDao;
    private FrmPrincipal vista;
    private Productos p;
    private ProductoDAO proDAO;
    private Proveedores prov;
    private ProveedorDAO provDAO;
    DefaultTableModel modelo = new DefaultTableModel();
    int idp, cant;
    double t, tpagar, pre, igv;
    private DetallePedido dp;
    String nombre_o;
    int id;

    public PedidoControlador(Pedidos pe, PedidoDAO peDao, int id, FrmPrincipal vista, String almacenero_o_vendedor_o_administrador) {
        this.pe = pe;
        String rol = almacenero_o_vendedor_o_administrador;
        this.peDao = peDao;
        this.vista = vista;
        this.id = id;
        this.vista.btnBuscarProductop.addActionListener(this);
        this.vista.btnBuscarProveedor.addActionListener(this);
        this.vista.btnAgregarProductoCarritop.addActionListener(this);
        this.vista.btnGenerarPedido.addActionListener(this);
        this.proDAO = new ProductoDAO();
        this.provDAO = new ProveedorDAO();
        this.peDao = new PedidoDAO();
        this.dp = new DetallePedido();
        fecha();
        switch (rol) {
            case "Almacenero":
                //Pedido
                this.vista.txtidproductop.setEnabled(true);
                this.vista.txtcantidadp.setEnabled(true);
                this.vista.txtTotalPagarp.setEnabled(true);
                this.vista.btnBuscarProductop.setEnabled(true);
                this.vista.btnAgregarProductoCarritop.setEnabled(true);
                this.vista.btnBuscarProveedor.setEnabled(true);
                this.vista.btnGenerarPedido.setEnabled(true);
                break;
            case "Vendedor":
                //Pedido
                this.vista.txtidproductop.setEnabled(false);
                this.vista.txtcantidadp.setEnabled(false);
                this.vista.txtTotalPagarp.setEnabled(false);
                this.vista.btnBuscarProductop.setEnabled(false);
                this.vista.btnAgregarProductoCarritop.setEnabled(false);
                this.vista.btnBuscarProveedor.setEnabled(false);
                this.vista.btnGenerarPedido.setEnabled(false);
                break;
            case "Administrador":
                //Pedido
                this.vista.txtidproductop.setEnabled(true);
                this.vista.txtcantidadp.setEnabled(true);
                this.vista.txtTotalPagarp.setEnabled(true);
                this.vista.btnBuscarProductop.setEnabled(true);
                this.vista.btnAgregarProductoCarritop.setEnabled(true);
                this.vista.btnBuscarProveedor.setEnabled(true);
                this.vista.btnGenerarPedido.setEnabled(true);
                break;
            default:
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnBuscarProductop) {
            if (vista.txtidproductop.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Debe ingresar el ID del producto");
            } else {
                buscarProductos();
            }
        } else if (e.getSource() == vista.btnBuscarProveedor) {
            if (vista.txtidproveedor.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Debe ingresar el ID del producto");
            } else {
                buscarProveedores();
            }
        } else if (e.getSource() == vista.btnGenerarPedido) {
            if (vista.txtTotalPagarp.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Debe Ingresar Datos");
            } else {
                guardarPedido();
                guardarDetalle();
                actualizarStock();
                JOptionPane.showMessageDialog(null, "Se Realizo con Exito");
                nuevo();
                vista.txtidproveedor.setEnabled(true);
            }
        } else if (e.getSource() == vista.btnAgregarProductoCarritop) {
            if (vista.txtidproductop.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Debe ingresar el ID del producto");
            } else if (vista.txtidproveedor.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Debe ingresar el ID del proveedor");
            } else if (Integer.parseInt(vista.txtcantidadp.getValue().toString()) <= 0) {
                JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0");
            } else {
                if (Integer.parseInt(vista.txtIDCategoriaProveedor.getText().toString()) != Integer.parseInt(vista.txtIDCategoriaProducto.getText().toString())) {
                    JOptionPane.showMessageDialog(null, "El proveedor no ofrece este producto");
                    vista.txtcantidadp.setValue(0);
                    vista.txtidproductop.setText("");
                    vista.txtpreciop.setText("");
                    vista.txtproductop.setText("");
                    vista.txtstockp.setText("");
                } else {
                    agregarProducto();
                    vista.txtidproveedor.setEnabled(false);
                }
            }
        }
    }

    //metodo buscar producto
    void buscarProductos() {
        String id = vista.txtidproductop.getText();
        p = proDAO.ListarID(Integer.parseInt(id));
        if (p.getIdPro() != 0) {
            vista.txtproductop.setText(p.getNombre());
            vista.txtpreciop.setText("" + p.getPrecioC());
            vista.txtstockp.setText("" + p.getStock());
            vista.txtIDCategoriaProducto.setText("" + p.getId_categoria());
        } else {
            JOptionPane.showMessageDialog(null, "Producto NO Registrado");
            vista.txtidproductop.requestFocus();
        }
    }

    //metodo buscar proveedor
    void buscarProveedores() {
        String id = vista.txtidproveedor.getText();
        //System.out.println("ID" + id);
        prov = provDAO.ListarID(Integer.parseInt(id));
        if (prov.getIdProv() != 0) {
            vista.txtproveedor.setText(prov.getNombre());
            vista.txtIDCategoriaProveedor.setText("" + prov.getId_categoria());
        } else {
            JOptionPane.showMessageDialog(null, "Proveedor NO Registrado");
            vista.txtproveedor.requestFocus();
        }
    }

    void actualizarStock() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            idp = Integer.parseInt(vista.TablaDetallep.getValueAt(i, 1).toString());
            cant = Integer.parseInt(vista.TablaDetallep.getValueAt(i, 3).toString());
            p = proDAO.ListarID(idp);
            int sa = p.getStock() + cant;
            proDAO.actualizarStock(sa, idp);
        }
    }

    void guardarPedido() {
        String fecha = vista.txtfechap.getText();
        double subtotal = t;
        double _igv = igv;
        double total = tpagar;
        int idproveedor = Integer.parseInt(vista.txtidproveedor.getText());
        pe.setIdEmpleado(id);
        pe.setIdProveedor(idproveedor);
        pe.setFecha(fecha);
        pe.setSubtotal(subtotal);
        pe.setIGV(_igv);
        pe.setTotal(total);
        peDao.GuardarPedidos(pe);
    }

    void guardarDetalle() {
        String idp = peDao.IdPed();
        int idpe = Integer.parseInt(idp);
        for (int i = 0; i < vista.TablaDetallep.getRowCount(); i++) {
            int idpr = Integer.parseInt(vista.TablaDetallep.getValueAt(i, 1).toString());
            int cant = Integer.parseInt(vista.TablaDetallep.getValueAt(i, 3).toString());
            double pre = Double.parseDouble(vista.TablaDetallep.getValueAt(i, 4).toString());
            dp.setIdPedido(idpe);
            dp.setIdProducto(idpr);
            dp.setCantidad(cant);
            dp.setPreCompra(pre);
            peDao.GuardarDetallePedidos(dp);
        }
    }

    void agregarProducto() {
        double subtotal;
        int item = 0;
        modelo = (DefaultTableModel) vista.TablaDetallep.getModel();
        item = item + 1;
        idp = p.getIdPro();
        String nomp = vista.txtproductop.getText();
        pre = Double.parseDouble(vista.txtpreciop.getText());
        cant = Integer.parseInt(vista.txtcantidadp.getValue().toString());
        int stock = Integer.parseInt(vista.txtstockp.getText());
        subtotal = cant * pre;
        ArrayList lista = new ArrayList();
        if (stock >= 0) {
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
            vista.TablaDetallep.setModel(modelo);
            calcularTotal();
            calcularIGV();
            calcularTotalPagar();
            vista.txtcantidadp.setValue(0);
            vista.txtidproductop.setText("");
            vista.txtpreciop.setText("");
            vista.txtproductop.setText("");
            vista.txtstockp.setText("");
            JOptionPane.showMessageDialog(null, "Se Agregó correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "Stock de Producto NO Disponible");
        }
    }

    void calcularTotal() {
        t = 0;
        for (int i = 0; i < vista.TablaDetallep.getRowCount(); i++) {
            //System.out.println("123 " + vista.TablaDetallep);
            cant = Integer.parseInt(vista.TablaDetallep.getValueAt(i, 3).toString());
            pre = Double.parseDouble(vista.TablaDetallep.getValueAt(i, 4).toString());
            t = t + (cant * pre);
        }
        vista.txtTotalp.setText("" + t);
    }

    void calcularIGV() {
        igv = Double.parseDouble(vista.txtTotalp.getText()) * 0.18;
        vista.txtIGVp.setText("" + Math.round(igv * 100.0) / 100.0);
    }

    void calcularTotalPagar() {
        tpagar = Double.parseDouble(vista.txtTotalp.getText()) + Double.parseDouble(vista.txtIGVp.getText());
        vista.txtTotalPagarp.setText("" + Math.round(tpagar * 100.0) / 100.0);
    }

    void fecha() {
        LocalDate calendar = LocalDate.now();
        vista.txtfechap.setText("" + calendar);
    }

    void limpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    void nuevo() {
        limpiarTabla();
        vista.txtidproductop.setText("");
        vista.txtIDCategoriaProducto.setText("");
        vista.txtIDCategoriaProveedor.setText("");
        vista.txtproductop.setText("");
        vista.txtidproveedor.setText("");
        vista.txtproveedor.setText("");
        vista.txtcantidadp.setValue(0);
        vista.txtpreciop.setText("");
        vista.txtstockp.setText("");
        vista.txtTotalp.setText("");
        vista.txtIGVp.setText("");
        vista.txtTotalPagarp.setText("");
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
