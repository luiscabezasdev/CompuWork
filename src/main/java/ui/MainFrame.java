/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import ui.components.Sidebar;
import ui.components.panels.GestionEmpleadosPanel;  // ✅ Tu panel existente
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
/**
 *
 * @author USUARIO
 */
public class MainFrame extends JFrame {
    
    public MainFrame() {
        setTitle("CompuWork Management Console");
        setSize(1400, 850);  // ✅ Un poco más ancho para el sidebar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // ✅ Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 250, 252));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // ✅ Agregar Sidebar (NUEVO - no afecta tu lógica)
        Sidebar sidebar = new Sidebar();
        mainPanel.add(sidebar, BorderLayout.WEST);
        
        // ✅ Tu panel existente SIN MODIFICAR
        GestionEmpleadosPanel panelEmpleados = new GestionEmpleadosPanel();
        panelEmpleados.setBackground(new Color(248, 250, 252));
        mainPanel.add(panelEmpleados, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
    }
    
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : 
                 javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
    
    
}
