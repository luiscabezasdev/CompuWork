/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components.panels;


import com.mycompany.mavenproject1.modelo.Empleado;
import com.mycompany.mavenproject1.modelo.EmpleadoPermanente;
import com.mycompany.mavenproject1.modelo.EmpleadoTemporal;

import com.mycompany.mavenproject1.servicio.EmpleadoService;

import ui.components.Button;
import ui.components.ModernTextField;
import ui.components.Panel;
import ui.components.ModernCard;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.SwingConstants;                    
import javax.swing.table.DefaultTableCellRenderer;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author USUARIO
 */
public class GestionEmpleadosPanel extends ModernCard{
   // ✅ Componentes UI
    private ModernTextField txtNombre;
    private ModernTextField txtCedula;
    private ModernTextField txtSalario;
    private ModernTextField txtHoras;
    private JRadioButton rbPermanente;
    private JRadioButton rbTemporal;
    private JTable tableEmpleados;
    private DefaultTableModel tableModel;
    private JLabel lblTituloTabla;
    
    // ✅ TU SERVICIO (ArrayList integrado)
    private EmpleadoService empleadoService;
    
    public GestionEmpleadosPanel() {
        // ✅ Inicializar servicio
        this.empleadoService = new EmpleadoService();
        
        setLayout(new GridBagLayout());
        setBackground(new Color(255, 255, 255));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 20, 12, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // === HEADER ===
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel lblTitle = new JLabel("Registar Empleado");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(15, 23, 42));
        add(lblTitle, gbc);
        
        // === TIPO DE EMPLEADO ===
        gbc.gridwidth = 1; gbc.gridy = 1;
        JLabel lblTipoTitulo = new JLabel("Tipo de Empleado");
        lblTipoTitulo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTipoTitulo.setForeground(new Color(100, 116, 139));
        add(lblTipoTitulo, gbc);
        
        gbc.gridx = 1;
        Panel panelTipo = new Panel();
        panelTipo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
        panelTipo.setBgColor(new Color(241, 245, 249));
        panelTipo.setPreferredSize(new java.awt.Dimension(250, 40));
        
        rbPermanente = new JRadioButton("Permanente");
        rbTemporal = new JRadioButton("Temporal");
        rbPermanente.setBackground(new Color(241, 245, 249));
        rbTemporal.setBackground(new Color(241, 245, 249));
        rbPermanente.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rbTemporal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbPermanente);
        grupo.add(rbTemporal);
        rbPermanente.setSelected(true);
        
        panelTipo.add(rbPermanente);
        panelTipo.add(rbTemporal);
        add(panelTipo, gbc);
        
        // === NOMBRE ===
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblNombre = new JLabel("NOMBRE COMPLETO");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblNombre.setForeground(new Color(100, 116, 139));
        add(lblNombre, gbc);
        
        gbc.gridx = 1;
        txtNombre = new ModernTextField("Pedro Suarez");
        add(txtNombre, gbc);
        
        // === CÉDULA ===
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblCedula = new JLabel("CEDULA");
        lblCedula.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblCedula.setForeground(new Color(100, 116, 139));
        add(lblCedula, gbc);
        
        gbc.gridx = 1;
        txtCedula = new ModernTextField("1098923123");
        add(txtCedula, gbc);
        
        // === SALARIO ===
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblSalario = new JLabel("SALARIO MENSUAL");
        lblSalario.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblSalario.setForeground(new Color(100, 116, 139));
        add(lblSalario, gbc);
        
        gbc.gridx = 1;
        txtSalario = new ModernTextField("1.750.000");
        add(txtSalario, gbc);
        
        // === HORAS ===
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel lblHoras = new JLabel("HORAS TRABAJADAS");
        lblHoras.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblHoras.setForeground(new Color(100, 116, 139));
        add(lblHoras, gbc);
        
        gbc.gridx = 1;
        txtHoras = new ModernTextField("25");
        add(txtHoras, gbc);
        
        // === BOTONES ===
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(25, 20, 20, 20);
        
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(
            new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        
        Button btnLimpiar = new Button("LIMPIAR", false);
        Button btnGuardar = new Button("GUARDAR", true);
        
        btnGuardar.addActionListener(e -> guardarEmpleado());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        
        buttonPanel.add(btnLimpiar);
        buttonPanel.add(btnGuardar);
        add(buttonPanel, gbc);
        
        // === TABLA ===
        gbc.gridy = 7;
        gbc.insets = new Insets(20, 20, 20, 20);
        
        // ✅ USAR EL SERVICIO PARA OBTENER CANTIDAD
        lblTituloTabla = new JLabel("Lista de Empleados (" + empleadoService.getCantidad() + ")");
        lblTituloTabla.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTituloTabla.setForeground(new Color(15, 23, 42));
        gbc.gridx = 0; gbc.gridwidth = 2;
        add(lblTituloTabla, gbc);
        
        gbc.gridy = 8;
        String[] columnas = {"Tipo", "Nombre", "Cédula", "Salario/Base", "Estado"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableEmpleados = new JTable(tableModel);
        tableEmpleados.setBackground(Color.WHITE);
        tableEmpleados.setForeground(new Color(15, 23, 42));
        tableEmpleados.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableEmpleados.setRowHeight(35);
        tableEmpleados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tableEmpleados.getTableHeader().setBackground(new Color(241, 245, 249));
        tableEmpleados.getTableHeader().setForeground(new Color(100, 116, 139));
        
        // Centrar contenido
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columnas.length; i++) {
            tableEmpleados.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scroll = new JScrollPane(tableEmpleados);
        scroll.setBorder(null);
        add(scroll, gbc);
        
        // ✅ CARGAR DATOS INICIALES DEL SERVICIO
        actualizarTabla();
    }
    
    // ✅ MÉTODO QUE USA EL SERVICIO (CONEXIÓN FUNCIONAL)
    private void guardarEmpleado() {
        try {
            String nombre = txtNombre.getText().trim();
            String cedula = txtCedula.getText().trim();
            
            if (nombre.isEmpty() || cedula.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Complete nombre y cédula", 
                    "Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int tipo = rbPermanente.isSelected() ? 1 : 2;
            double salario = 0;
            int horas = 0;
            double valorHora = 0;
            
            if (tipo == 1) {
                try {
                    salario = Double.parseDouble(txtSalario.getText().trim());
                } catch (NumberFormatException e) {
                    salario = 12800;
                }
            } else {
                try {
                    horas = Integer.parseInt(txtHoras.getText().trim());
                } catch (NumberFormatException e) {
                    horas = 14;
                }
                valorHora = 300;
            }
            
            // ✅ LLAMAR AL SERVICIO (CONEXIÓN AQUÍ)
            Empleado empleado = empleadoService.crearEmpleado(
                nombre, cedula, tipo, salario, horas, valorHora
            );
            
            // ✅ ACTUALIZAR VISTA DESDE EL SERVICIO
            actualizarTabla();
            limpiarCampos();
            
            JOptionPane.showMessageDialog(this, 
                "Empleado creado:\n" + empleado.getNombre() + "\n" +
                "Cédula: " + empleado.getCedula() + "\n" +
                "Total: " + empleadoService.getCantidad(),
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "❌ Error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ✅ ACTUALIZAR TABLA DESDE EL ARRAYLIST DEL SERVICIO
    private void actualizarTabla() {
        tableModel.setRowCount(0);
        
        // ✅ OBTENER LISTA DEL SERVICIO
        List<Empleado> lista = empleadoService.obtenerTodos();
        
        for (Empleado emp : lista) {
            Object[] row = new Object[5];
            
            if (emp instanceof EmpleadoPermanente) {
                EmpleadoPermanente perm = (EmpleadoPermanente) emp;
                row[0] = "Permanente";
                row[1] = emp.getNombre();
                row[2] = emp.getCedula();
                row[3] = "$" + perm.getSalarioBase();
                row[4] = "Activo";
            } else {
                EmpleadoTemporal temp = (EmpleadoTemporal) emp;
                row[0] = "Temporal";
                row[1] = emp.getNombre();
                row[2] = emp.getCedula();
                row[3] = temp.getHorasTrabajadas() + " hrs";
                row[4] = "Temporal";
            }
            
            tableModel.addRow(row);
        }
        
        // ✅ ACTUALIZAR TÍTULO CON CANTIDAD DEL SERVICIO
        lblTituloTabla.setText("📋 Employee List (" + empleadoService.getCantidad() + ")");
    }
    
    private void limpiarCampos() {
        txtNombre.setText("");
        txtCedula.setText("");
        txtSalario.setText("");
        txtHoras.setText("");
        rbPermanente.setSelected(true);
        txtNombre.requestFocus();
    }
    
    // ✅ GETTER PARA ACCEDER AL SERVICIO DESDE FUERA (OPCIONAL)
    public EmpleadoService getEmpleadoService() {
        return empleadoService;
    }
}
