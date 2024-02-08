package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ProductoDAO;
import modelo.Productos;
import vista.FrmPrincipal;

public class ProductoControlador implements ActionListener, MouseListener, KeyListener {

    private final Productos pro;
    private final ProductoDAO proDAO;
    private final FrmPrincipal vista;
    DefaultTableModel modelo = new DefaultTableModel();

    public ProductoControlador(Productos pro, ProductoDAO proDAO, FrmPrincipal vista,
            String almacenero_o_vendedor_o_administrador) {
        this.pro = pro;
        String rol = almacenero_o_vendedor_o_administrador;
        this.proDAO = proDAO;
        this.vista = vista;
        this.vista.btnAgregarProducto.addActionListener(this);
        this.vista.btnModificarProducto.addActionListener(this);
        this.vista.btnEliminarProducto.addActionListener(this);
        this.vista.btnActualizarProducto.addActionListener(this);
        switch (rol) {
            case "Almacenero":
                this.vista.btnAgregarProducto.setEnabled(false);
                this.vista.btnModificarProducto.setEnabled(false);
                this.vista.btnEliminarProducto.setEnabled(false);
                this.vista.txtNombreProducto.setEnabled(false);
                this.vista.txtDescripcionProducto.setEnabled(false);
                this.vista.txtStockProducto.setEnabled(false);
                this.vista.txtPrecioCompraProducto.setEnabled(false);
                this.vista.txtPrecioVentaProducto.setEnabled(false);
                this.vista.cboCategorias.setEnabled(false);
                break;
            case "Vendedor":
                this.vista.btnAgregarProducto.setEnabled(false);
                this.vista.btnModificarProducto.setEnabled(false);
                this.vista.btnEliminarProducto.setEnabled(false);
                this.vista.txtNombreProducto.setEnabled(false);
                this.vista.txtDescripcionProducto.setEnabled(false);
                this.vista.txtStockProducto.setEnabled(false);
                this.vista.txtPrecioCompraProducto.setEnabled(false);
                this.vista.txtPrecioVentaProducto.setEnabled(false);
                this.vista.cboCategorias.setEnabled(false);
                break;
            case "Administrador":
                this.vista.btnAgregarProducto.setEnabled(true);
                this.vista.btnModificarProducto.setEnabled(true);
                this.vista.btnEliminarProducto.setEnabled(true);
                this.vista.txtDescripcionProducto.setEnabled(true);
                this.vista.txtPrecioCompraProducto.setEnabled(true);
                this.vista.txtNombreProducto.setEnabled(true);
                this.vista.txtStockProducto.setEnabled(true);
                this.vista.cboCategorias.setEnabled(true);
                break;
            default:
        }
        this.vista.txtBusquedaProducto.addKeyListener(this);
        this.vista.listadoProductos.addMouseListener(this);
        listarProd();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btnAgregarProducto) {
            if (vista.txtNombreProducto.getText().equals("")
                    || vista.txtDescripcionProducto.getText().equals("")
                    || vista.txtPrecioCompraProducto.getText().equals("")
                    || vista.txtPrecioVentaProducto.getText().equals("")
                    || vista.cboCategorias.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            } else if (Double.parseDouble(vista.txtPrecioCompraProducto.getText()) > Double.parseDouble(vista.txtPrecioVentaProducto.getText())) {
                JOptionPane.showMessageDialog(null, "Precio Compra no puede ser mayor que el Precio Venta");
            } else if (Double.parseDouble(vista.txtPrecioCompraProducto.getText()) == Double.parseDouble(vista.txtPrecioVentaProducto.getText())) {
                JOptionPane.showMessageDialog(null, "Precio Compra no puede ser igual que el Precio Venta");
            } else {
                pro.setNombre(vista.txtNombreProducto.getText());
                pro.setDescripcion(vista.txtDescripcionProducto.getText());
                pro.setStock(0);
                pro.setPrecioC(Double.parseDouble(vista.txtPrecioCompraProducto.getText()));
                pro.setPrecioV(Double.parseDouble(vista.txtPrecioVentaProducto.getText()));
                pro.setId_categoria(vista.cboCategorias.getSelectedIndex());

                if (proDAO.RegistrarProductos(pro)) {
                    JOptionPane.showMessageDialog(null, "Producto Registrado");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar");
                }
                limpiarTabla();
                listarProd();
                limpiarCampos();

            }
        } else if (e.getSource() == vista.btnModificarProducto) {
            if (vista.txtidProducto.getText().equals("")
                    || vista.txtNombreProducto.getText().equals("")
                    || vista.txtDescripcionProducto.getText().equals("")
                    || vista.txtStockProducto.getText().equals("")
                    || vista.txtPrecioCompraProducto.getText().equals("")
                    || vista.txtPrecioVentaProducto.getText().equals("")
                    || vista.cboCategorias.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "¡Ingrese todos los campos!");
            } else {
                pro.setNombre(vista.txtNombreProducto.getText());
                pro.setDescripcion(vista.txtDescripcionProducto.getText());
                pro.setStock(Integer.parseInt(vista.txtStockProducto.getText()));
                pro.setPrecioC(Double.parseDouble(vista.txtPrecioCompraProducto.getText()));
                pro.setPrecioV(Double.parseDouble(vista.txtPrecioVentaProducto.getText()));
                pro.setId_categoria(vista.cboCategorias.getSelectedIndex());
                pro.setIdPro(Integer.parseInt(vista.txtidProducto.getText()));
                if (proDAO.ActualizarProductos(pro)) {
                    JOptionPane.showMessageDialog(null, "Producto Modificado correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar");
                }
                limpiarTabla();
                listarProd();
                limpiarCampos();
            }

        } else if (e.getSource() == vista.btnEliminarProducto) {
            if (JOptionPane.showConfirmDialog(null, "¿Seguro q desea eliminar el producto?", "Warning ", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                int idProd = Integer.parseInt(vista.txtidProducto.getText());
                boolean delete = proDAO.EliminarProducto(idProd);
                if (delete) {
                    JOptionPane.showMessageDialog(null, "Producto eliminado");
                    limpiarTabla();
                    listarProd();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar");
                }
                limpiarCampos();

            }
        } else if (e.getSource() == vista.btnActualizarProducto) {
            limpiarTabla();
            listarProd();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.listadoProductos) {
            int fila = vista.listadoProductos.rowAtPoint(e.getPoint());
            vista.txtidProducto.setText(vista.listadoProductos.getValueAt(fila, 0).toString());
            vista.txtNombreProducto.setText(vista.listadoProductos.getValueAt(fila, 1).toString());
            vista.txtDescripcionProducto.setText(vista.listadoProductos.getValueAt(fila, 2).toString());
            vista.txtStockProducto.setText(vista.listadoProductos.getValueAt(fila, 3).toString());
            vista.txtPrecioCompraProducto.setText(vista.listadoProductos.getValueAt(fila, 4).toString());
            vista.txtPrecioVentaProducto.setText(vista.listadoProductos.getValueAt(fila, 5).toString());
            vista.cboCategorias.setSelectedIndex(Integer.parseInt(vista.listadoProductos.getValueAt(fila, 6).toString()));
        }
    }

    private void limpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    private void listarProd() {
        List<Productos> lista = proDAO.ListarProductos(vista.txtBusquedaProducto.getText());
        modelo = (DefaultTableModel) vista.listadoProductos.getModel();
        Object[] ob = new Object[8];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getIdPro();
            ob[1] = lista.get(i).getNombre();
            ob[2] = lista.get(i).getDescripcion();
            ob[3] = lista.get(i).getStock();
            ob[4] = lista.get(i).getPrecioC();
            ob[5] = lista.get(i).getPrecioV();
            ob[6] = lista.get(i).getId_categoria();
            ob[7] = lista.get(i).getCategoria();
            modelo.addRow(ob);
        }
        vista.listadoProductos.setModel(modelo);
    }

    private void limpiarCampos() {
        vista.txtidProducto.setText("");
        vista.txtNombreProducto.setText("");
        vista.txtDescripcionProducto.setText("");
        vista.txtStockProducto.setText("");
        vista.txtPrecioCompraProducto.setText("");
        vista.txtPrecioVentaProducto.setText("");
        vista.cboCategorias.setSelectedIndex(0);
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
        if (e.getSource() == vista.txtBusquedaProducto) {
            limpiarTabla();
            listarProd();
        }

    }

}
