
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.EntradaDAO;
import modelo.Entradas;
import vista.FrmPrincipal;


public class EntradaControlador implements ActionListener, MouseListener, KeyListener{
    private final Entradas ent;
    private final EntradaDAO entDAO;
    private final FrmPrincipal vista;
    DefaultTableModel modelo = new DefaultTableModel();
    
    public EntradaControlador(Entradas ent, EntradaDAO entDAO, FrmPrincipal vista) {
        this.ent = ent;
        this.entDAO = entDAO;
        this.vista = vista;
        this.vista.btnActualizarEntradas.addActionListener(this);
        this.vista.txtBusquedaEntrada.addKeyListener(this);
        this.vista.listadoEntradas.addMouseListener(this);
        listarEnt();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnActualizarEntradas) {
            limpiarTabla();
            listarEnt();
        }
    }
    
    private void listarEnt() {
        List<Entradas> lista = entDAO.ListarEntradas(vista.txtBusquedaEntrada.getText());
        modelo = (DefaultTableModel) vista.listadoEntradas.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getIdProducto();
            ob[1] = lista.get(i).getNombre();
            ob[2] = lista.get(i).getCantidad();
            ob[3] = lista.get(i).getFecha();
            ob[4] = lista.get(i).getEmpleado();
            ob[5] = lista.get(i).getTotal();
            
            modelo.addRow(ob);
        }
        vista.listadoEntradas.setModel(modelo);
    }
    
    private void limpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
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
        if (e.getSource() == vista.txtBusquedaEntrada) {
            limpiarTabla();
            listarEnt();
        }
    }
}
