package ui.components.panels;

import com.mycompany.mavenproject1.modelo.Departamento;
import com.mycompany.mavenproject1.modelo.Empleado;
import com.mycompany.mavenproject1.servicio.DepartamentoService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import ui.components.Button;
import ui.components.ModernCard;
import ui.components.ModernTextField;

public class GestionDepartamentosPanel extends ModernCard implements RefreshablePanel {
    private final DepartamentoService departamentoService;
    private final Runnable refreshCallback;

    private ModernTextField txtNombre;
    private ModernTextField txtPresupuesto;
    private JTextArea txtDescripcion;
    private JTextArea txtDetalleEmpleados;
    private JTable tableDepartamentos;
    private DefaultTableModel tableModel;
    private JLabel lblResumen;
    private String departamentoSeleccionadoId;

    public GestionDepartamentosPanel(
            DepartamentoService departamentoService,
            Runnable refreshCallback) {
        this.departamentoService = departamentoService;
        this.refreshCallback = refreshCallback;
        this.departamentoSeleccionadoId = null;

        setLayout(new BorderLayout(0, 18));
        setBackground(Color.WHITE);
        setBorderRadius(18);

        add(buildHeader(), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
        refreshData();
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel lblTitle = new JLabel("Gestion de Departamentos");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(15, 23, 42));

        lblResumen = new JLabel("0 departamentos registrados");
        lblResumen.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblResumen.setForeground(new Color(100, 116, 139));

        header.add(lblTitle, BorderLayout.NORTH);
        header.add(lblResumen, BorderLayout.SOUTH);
        return header;
    }

    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout(18, 18));
        content.setOpaque(false);
        content.add(buildForm(), BorderLayout.NORTH);
        content.add(buildDataSection(), BorderLayout.CENTER);
        return content;
    }

    private JPanel buildForm() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        txtNombre = new ModernTextField("Nombre del departamento");
        txtPresupuesto = new ModernTextField("Presupuesto asignado");
        txtDescripcion = new JTextArea(3, 30);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        addField(form, gbc, 0, "Nombre", txtNombre);
        addField(form, gbc, 1, "Presupuesto", txtPresupuesto);
        addField(form, gbc, 2, "Descripcion", new JScrollPane(txtDescripcion));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        Button btnGuardar = new Button("GUARDAR", true);
        Button btnActualizar = new Button("ACTUALIZAR", false);
        Button btnEliminar = new Button("ELIMINAR", false);
        Button btnLimpiar = new Button("LIMPIAR", false);

        btnGuardar.addActionListener(e -> guardarDepartamento());
        btnActualizar.addActionListener(e -> actualizarDepartamento());
        btnEliminar.addActionListener(e -> eliminarDepartamento());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        actions.add(btnLimpiar);
        actions.add(btnEliminar);
        actions.add(btnActualizar);
        actions.add(btnGuardar);

        wrapper.add(form, BorderLayout.CENTER);
        wrapper.add(actions, BorderLayout.SOUTH);
        return wrapper;
    }

    private JPanel buildDataSection() {
        JPanel section = new JPanel(new BorderLayout(18, 18));
        section.setOpaque(false);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Presupuesto", "Empleados"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableDepartamentos = new JTable(tableModel);
        tableDepartamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableDepartamentos.setRowHeight(30);
        tableDepartamentos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    cargarSeleccion();
                }
            }
        });

        JScrollPane tableScroll = new JScrollPane(tableDepartamentos);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Listado de departamentos"));
        tableScroll.setPreferredSize(new Dimension(800, 300));

        txtDetalleEmpleados = new JTextArea();
        txtDetalleEmpleados.setEditable(false);
        txtDetalleEmpleados.setLineWrap(true);
        txtDetalleEmpleados.setWrapStyleWord(true);
        txtDetalleEmpleados.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        JScrollPane detalleScroll = new JScrollPane(txtDetalleEmpleados);
        detalleScroll.setPreferredSize(new Dimension(320, 300));
        detalleScroll.setBorder(BorderFactory.createTitledBorder("Empleados asignados"));

        section.add(tableScroll, BorderLayout.CENTER);
        section.add(detalleScroll, BorderLayout.EAST);
        return section;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, String labelText, java.awt.Component component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(71, 85, 105));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(component, gbc);
    }

    private void guardarDepartamento() {
        try {
            departamentoService.crearDepartamento(
                    txtNombre.getText().trim(),
                    txtDescripcion.getText().trim(),
                    parseDouble(txtPresupuesto.getText().trim()));
            postAction("Departamento creado correctamente.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void actualizarDepartamento() {
        if (departamentoSeleccionadoId == null) {
            showError("Selecciona un departamento para actualizar.");
            return;
        }
        try {
            departamentoService.actualizarDepartamento(
                    departamentoSeleccionadoId,
                    txtNombre.getText().trim(),
                    txtDescripcion.getText().trim(),
                    parseDouble(txtPresupuesto.getText().trim()));
            postAction("Departamento actualizado correctamente.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void eliminarDepartamento() {
        if (departamentoSeleccionadoId == null) {
            showError("Selecciona un departamento para eliminar.");
            return;
        }
        try {
            departamentoService.eliminarDepartamento(departamentoSeleccionadoId);
            postAction("Departamento eliminado correctamente.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void cargarSeleccion() {
        int selectedRow = tableDepartamentos.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }
        departamentoSeleccionadoId = tableModel.getValueAt(selectedRow, 0).toString();
        Departamento departamento = departamentoService.obtenerPorId(departamentoSeleccionadoId);
        txtNombre.setText(departamento.getNombre());
        txtPresupuesto.setText(String.valueOf(departamento.getPresupuestoAsignado()));
        txtDescripcion.setText(departamento.getDescripcion());
        renderDetalle(departamento);
    }

    private void renderDetalle(Departamento departamento) {
        if (departamento == null) {
            txtDetalleEmpleados.setText("Selecciona un departamento para ver detalle.");
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Departamento: ").append(departamento.getNombre()).append("\n");
        builder.append("Presupuesto: ").append(departamento.getPresupuestoAsignado()).append("\n\n");
        if (departamento.getEmpleados().isEmpty()) {
            builder.append("Sin empleados asignados.");
        } else {
            for (Empleado empleado : departamento.getEmpleados()) {
                builder.append("- ")
                        .append(empleado.getNombre())
                        .append(" (")
                        .append(empleado.getTipoEmpleado())
                        .append(")\n");
            }
        }
        txtDetalleEmpleados.setText(builder.toString());
    }

    private void limpiarFormulario() {
        departamentoSeleccionadoId = null;
        txtNombre.setText("");
        txtPresupuesto.setText("");
        txtDescripcion.setText("");
        txtDetalleEmpleados.setText("Selecciona un departamento para ver detalle.");
        tableDepartamentos.clearSelection();
    }

    private void postAction(String message) {
        refreshCallback.run();
        limpiarFormulario();
        JOptionPane.showMessageDialog(this, message, "CompuWork", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El presupuesto es invalido.");
        }
    }

    @Override
    public void refreshData() {
        tableModel.setRowCount(0);
        List<Departamento> departamentos = departamentoService.obtenerTodos();
        for (Departamento departamento : departamentos) {
            tableModel.addRow(new Object[]{
                departamento.getId(),
                departamento.getNombre(),
                departamento.getPresupuestoAsignado(),
                departamento.getCantidadEmpleados()
            });
        }
        lblResumen.setText(departamentos.size() + " departamentos registrados");
        if (departamentoSeleccionadoId != null) {
            try {
                renderDetalle(departamentoService.obtenerPorId(departamentoSeleccionadoId));
            } catch (RuntimeException ex) {
                txtDetalleEmpleados.setText("Selecciona un departamento para ver detalle.");
            }
        } else {
            txtDetalleEmpleados.setText("Selecciona un departamento para ver detalle.");
        }
    }
}
