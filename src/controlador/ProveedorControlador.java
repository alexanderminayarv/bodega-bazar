package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ProveedorDAO;
import modelo.Proveedores;
import vista.FrmPrincipal;

public class ProveedorControlador implements ActionListener, MouseListener, KeyListener {

    JDesktopPane escritorio;
    private final Proveedores prov;
    private final ProveedorDAO provDao;
    private final FrmPrincipal vista;

    DefaultTableModel modelo = new DefaultTableModel();

    public ProveedorControlador(Proveedores prov, ProveedorDAO provDao, FrmPrincipal vista,
            String vendedor_o_administrador) {
        this.prov = prov;
        String rol = vendedor_o_administrador;
        this.provDao = provDao;
        this.vista = vista;
        this.vista.btnAgregarProv.addActionListener(this);
        this.vista.btnModificarProv.addActionListener(this);
        this.vista.btnEliminarProv.addActionListener(this);
        switch (rol) {
            case "Almacenero":
                this.vista.btnAgregarProv.setEnabled(false);
                this.vista.btnModificarProv.setEnabled(false);
                this.vista.btnEliminarProv.setEnabled(false);
                this.vista.txtRUCProveedor.setEnabled(false);
                this.vista.txtNombreProveedor.setEnabled(false);
                this.vista.txtDireccionProveedor.setEnabled(false);
                this.vista.txtEmailProveedor.setEnabled(false);
                this.vista.txtCelularProveedor.setEnabled(false);
                this.vista.cboCategoriasp.setEnabled(false);
                break;
                
            case "Vendedor":
                this.vista.btnAgregarProv.setEnabled(false);
                this.vista.btnModificarProv.setEnabled(false);
                this.vista.btnEliminarProv.setEnabled(false);
                this.vista.txtRUCProveedor.setEnabled(false);
                this.vista.txtNombreProveedor.setEnabled(false);
                this.vista.txtDireccionProveedor.setEnabled(false);
                this.vista.txtEmailProveedor.setEnabled(false);
                this.vista.txtCelularProveedor.setEnabled(false);
                this.vista.cboCategoriasp.setEnabled(false);
                break;
                
            case "Administrador":
                this.vista.btnAgregarProv.setEnabled(true);
                this.vista.btnModificarProv.setEnabled(true);
                this.vista.btnEliminarProv.setEnabled(true);
                this.vista.txtRUCProveedor.setEnabled(true);
                this.vista.txtNombreProveedor.setEnabled(true);
                this.vista.txtDireccionProveedor.setEnabled(true);
                this.vista.txtEmailProveedor.setEnabled(true);
                this.vista.txtCelularProveedor.setEnabled(true);
                this.vista.cboCategoriasp.setEnabled(true);
                break;
            default:
        }
        this.vista.txtBusquedaProv.addKeyListener(this);
        this.vista.listadoProveedores.addMouseListener(this);
        listarProv();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnAgregarProv) {
            if (vista.txtNombreProveedor.getText().equals("") 
                    || vista.txtDireccionProveedor.getText().equals("")
                    || vista.txtRUCProveedor.getText().equals("")
                    || vista.txtEmailProveedor.getText().equals("") 
                    || vista.txtCelularProveedor.getText().equals("")
                    || vista.cboCategoriasp.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            } else {
                prov.setRuc(vista.txtRUCProveedor.getText());
                prov.setNombre(vista.txtNombreProveedor.getText());
                prov.setDireccion(vista.txtDireccionProveedor.getText());
                prov.setEmail(vista.txtEmailProveedor.getText());
                prov.setCelular(vista.txtCelularProveedor.getText());
                prov.setId_categoria(vista.cboCategoriasp.getSelectedIndex());

                if (provDao.RegistrarProveedor(prov)) {
                    JOptionPane.showMessageDialog(null, "Proveedor Registrado");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar");
                }
                limpiarTabla();
                listarProv();
                limpiarCampos();

            }
        } else if (e.getSource() == vista.btnModificarProv) {
            if (vista.txtidProv.getText().equals("")
                    || vista.txtRUCProveedor.getText().equals("")
                    || vista.txtNombreProveedor.getText().equals("") 
                    || vista.txtDireccionProveedor.getText().equals("")
                    || vista.txtEmailProveedor.getText().equals("") 
                    || vista.txtCelularProveedor.getText().equals("")
                    || vista.cboCategoriasp.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "¡Ingrese todos los campos!");
            } else {
                prov.setRuc(vista.txtRUCProveedor.getText());
                prov.setNombre(vista.txtNombreProveedor.getText());
                prov.setDireccion(vista.txtDireccionProveedor.getText());
                prov.setEmail(vista.txtEmailProveedor.getText());
                prov.setCelular(vista.txtCelularProveedor.getText());
                prov.setId_categoria(vista.cboCategoriasp.getSelectedIndex());
                prov.setIdProv(Integer.parseInt(vista.txtidProv.getText()));
                if (provDao.ModificarProveedor(prov)) {

                    limpiarTabla();
                    listarProv();
                    JOptionPane.showMessageDialog(null, "Proveedor Modificado correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar");
                }
                limpiarCampos();
            }
        } else if (e.getSource() == vista.btnEliminarProv) {
            if (JOptionPane.showConfirmDialog(null,
                    "¿Estás seguro que quieres eliminar el?", "Aviso", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {
                int idProv = Integer.parseInt(vista.txtidProv.getText());
                boolean delete = provDao.EliminarProveedor(idProv);
                if (delete) {
                    JOptionPane.showMessageDialog(null, "Proveedor eliminado con exito");
                    limpiarTabla();
                    listarProv();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar al proveedor");
                }
                limpiarCampos();
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.listadoProveedores) {
            int fila = vista.listadoProveedores.rowAtPoint(e.getPoint());
            vista.txtidProv.setText(vista.listadoProveedores.getValueAt(fila, 0).toString());
            vista.txtRUCProveedor.setText(vista.listadoProveedores.getValueAt(fila, 1).toString());
            vista.txtNombreProveedor.setText(vista.listadoProveedores.getValueAt(fila, 2).toString());
            vista.txtDireccionProveedor.setText(vista.listadoProveedores.getValueAt(fila, 3).toString());
            vista.txtEmailProveedor.setText(vista.listadoProveedores.getValueAt(fila, 4).toString());
            vista.txtCelularProveedor.setText(vista.listadoProveedores.getValueAt(fila, 5).toString());
            vista.cboCategoriasp.setSelectedIndex(Integer.parseInt(vista.listadoProveedores.getValueAt(fila, 6).toString()));
        }
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
        if (e.getSource() == vista.txtBusquedaProv) {
            limpiarTabla();
            listarProv();
        }
    }

    private void listarProv() {
        List< Proveedores> lista = provDao.ListarProveedor(vista.txtBusquedaProv.getText());
        modelo = (DefaultTableModel) vista.listadoProveedores.getModel();
        Object[] ob = new Object[8];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getIdProv();
            ob[1] = lista.get(i).getRuc();
            ob[2] = lista.get(i).getNombre();
            ob[3] = lista.get(i).getDireccion();
            ob[4] = lista.get(i).getEmail();
            ob[5] = lista.get(i).getCelular();
            ob[6] = lista.get(i).getId_categoria();
            ob[7] = lista.get(i).getCategoria();
            modelo.addRow(ob);
        }
        vista.listadoProveedores.setModel(modelo);

    }

    private void limpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    private void limpiarCampos() {
        vista.txtidProv.setText("");
        vista.txtRUCProveedor.setText("");
        vista.txtDireccionProveedor.setText("");
        vista.txtNombreProveedor.setText("");
        vista.txtEmailProveedor.setText("");
        vista.txtCelularProveedor.setText("");
        vista.cboCategoriasp.setSelectedIndex(0);
    }

}
