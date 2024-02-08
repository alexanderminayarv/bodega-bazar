package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.DashboardDAO;
import modelo.Pedidos;
import modelo.Ventas;
import vista.FrmPrincipal;

public class DashboardControlador implements ActionListener, MouseListener, KeyListener{

    private DashboardDAO dsDao;
    private FrmPrincipal vista;
    
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel modelo1 = new DefaultTableModel();

    int cantProductos,cantVentas, cantProveedores, cantEmpleados,cantPedidos;

    public DashboardControlador(DashboardDAO dsDao, FrmPrincipal vista) {
        this.dsDao = dsDao;
        this.vista = vista;
        cantProductos = dsDao.getCantidadProductos();
        vista.lblProductos.setText("" + cantProductos);
        cantProveedores = dsDao.getCantidadProveedores();
        vista.lblProveedores.setText("" + cantProveedores);
        cantEmpleados = dsDao.getCantidadEmpleados();
        vista.lblEmpleados.setText("" + cantEmpleados);
        cantVentas = dsDao.getCantidadVentas();
        vista.lblVentas.setText("" + cantVentas);
        cantPedidos = dsDao.getCantidadPedidos();
        vista.lblPedidos.setText("" + cantPedidos);
        this.vista.btnActualizarDashboard.addActionListener(this);
        listarTotalPorDiaDePedidos();
        listarTotalPorDiaDeVentas();
    }
    
    
    public void listarTotalPorDiaDePedidos() {
        List<Pedidos> lista = dsDao.listarTotalXDiaPedidos();
        modelo = (DefaultTableModel) vista.TablaTotalXDiaPedidos.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getSubtotal();
            ob[1] = lista.get(i).getIGV();
            ob[2] = lista.get(i).getTotal();
            ob[3] = lista.get(i).getFecha();
            modelo.addRow(ob);
        }
        vista.TablaTotalXDiaPedidos.setModel(modelo);

    }
    
    public void listarTotalPorDiaDeVentas() {
        List<Ventas> lista = dsDao.listarTotalXDiaVentas();
        modelo1 = (DefaultTableModel) vista.TablaTotalXDiaVentas.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getSubtotal();
            ob[1] = lista.get(i).getIGV();
            ob[2] = lista.get(i).getTotal();
            ob[3] = lista.get(i).getFecha();
            modelo1.addRow(ob);
        }
        vista.TablaTotalXDiaVentas.setModel(modelo1);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource() == vista.btnActualizarDashboard) {
            limpiarTabla();
            listarTotalPorDiaDePedidos();
            limpiarTabla1();
            listarTotalPorDiaDeVentas();
        }
    }
    
    private void limpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }
    
    private void limpiarTabla1() {
        for (int i = 0; i < modelo1.getRowCount(); i++) {
            modelo1.removeRow(i);
            i = i - 1;
        }
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
