package ui.components.panels;

import com.mycompany.mavenproject1.modelo.Departamento;
import com.mycompany.mavenproject1.modelo.Empleado;
import com.mycompany.mavenproject1.modelo.EmpleadoPermanente;
import com.mycompany.mavenproject1.modelo.EmpleadoTemporal;
import com.mycompany.mavenproject1.modelo.TipoEmpleado;
import com.mycompany.mavenproject1.servicio.DepartamentoService;
import com.mycompany.mavenproject1.servicio.EmpleadoService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import ui.components.Button;
import ui.components.ModernCard;
import ui.components.ModernTextField;

public class GestionEmpleadosPanel extends ModernCard implements RefreshablePanel {
    private final EmpleadoService empleadoService;
    private final DepartamentoService departamentoService;
    private final Runnable refreshCallback;

    private ModernTextField txtNombre;
    private ModernTextField txtCedula;
    private ModernTextField txtFechaIngreso;
    private ModernTextField txtSalarioBase;
    private ModernTextField txtBono;
    private ModernTextField txtHoras;
    private ModernTextField txtValorHora;
    private JComboBox<TipoEmpleado> cmbTipoEmpleado;
    private JComboBox<Object> cmbDepartamento;
    private JTable tableEmpleados;
    private DefaultTableModel tableModel;
    private JLabel lblResumen;
    private String empleadoSeleccionadoId;

    public GestionEmpleadosPanel(
            EmpleadoService empleadoService,
            DepartamentoService departamentoService,
            Runnable refreshCallback) {
        this.empleadoService = empleadoService;
        this.departamentoService = departamentoService;
        this.refreshCallback = refreshCallback;
        this.empleadoSeleccionadoId = null;

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

        JLabel lblTitle = new JLabel("Gestion de Empleados");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(15, 23, 42));

        lblResumen = new JLabel("0 empleados registrados");
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
        content.add(buildTable(), BorderLayout.CENTER);
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

        txtNombre = new ModernTextField("Nombre completo");
        txtCedula = new ModernTextField("Cedula");
        txtFechaIngreso = new ModernTextField("YYYY-MM-DD");
        txtFechaIngreso.setText(LocalDate.now().toString());
        txtSalarioBase = new ModernTextField("Salario base");
        txtBono = new ModernTextField("Bono");
        txtHoras = new ModernTextField("Horas trabajadas");
        txtValorHora = new ModernTextField("Valor por hora");
        cmbTipoEmpleado = new JComboBox<TipoEmpleado>(TipoEmpleado.values());
        cmbDepartamento = new JComboBox<Object>();
        cmbTipoEmpleado.addActionListener(e -> updateFieldState());

        addField(form, gbc, 0, "Tipo", cmbTipoEmpleado);
        addField(form, gbc, 1, "Nombre", txtNombre);
        addField(form, gbc, 2, "Cedula", txtCedula);
        addField(form, gbc, 3, "Fecha ingreso", txtFechaIngreso);
        addField(form, gbc, 4, "Departamento", cmbDepartamento);
        addField(form, gbc, 5, "Salario base", txtSalarioBase);
        addField(form, gbc, 6, "Bono", txtBono);
        addField(form, gbc, 7, "Horas", txtHoras);
        addField(form, gbc, 8, "Valor hora", txtValorHora);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        Button btnGuardar = new Button("GUARDAR", true);
        Button btnActualizar = new Button("ACTUALIZAR", false);
        Button btnEliminar = new Button("ELIMINAR", false);
        Button btnLimpiar = new Button("LIMPIAR", false);

        btnGuardar.addActionListener(e -> guardarEmpleado());
        btnActualizar.addActionListener(e -> actualizarEmpleado());
        btnEliminar.addActionListener(e -> eliminarEmpleado());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        actions.add(btnLimpiar);
        actions.add(btnEliminar);
        actions.add(btnActualizar);
        actions.add(btnGuardar);

        wrapper.add(form, BorderLayout.CENTER);
        wrapper.add(actions, BorderLayout.SOUTH);
        return wrapper;
    }

    private JScrollPane buildTable() {
        String[] columnas = {
            "ID", "Tipo", "Nombre", "Cedula", "Ingreso", "Pago", "Departamento"
        };
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableEmpleados = new JTable(tableModel);
        tableEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableEmpleados.setRowHeight(30);
        tableEmpleados.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    cargarSeleccion();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableEmpleados);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Listado de empleados"));
        scrollPane.setPreferredSize(new Dimension(900, 320));
        return scrollPane;
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

    private void guardarEmpleado() {
        try {
            Empleado empleado = empleadoService.crearEmpleado(
                    (TipoEmpleado) cmbTipoEmpleado.getSelectedItem(),
                    txtNombre.getText().trim(),
                    txtCedula.getText().trim(),
                    parseFecha(txtFechaIngreso.getText().trim()),
                    parseDouble(txtSalarioBase.getText().trim(), "El salario base es invalido."),
                    parseDouble(txtBono.getText().trim(), "El bono es invalido."),
                    parseInt(txtHoras.getText().trim(), "Las horas son invalidas."),
                    parseDouble(txtValorHora.getText().trim(), "El valor por hora es invalido."));
            syncDepartamento(empleado.getIdEmpleado());
            postAction("Empleado creado correctamente.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void actualizarEmpleado() {
        if (empleadoSeleccionadoId == null) {
            showError("Selecciona un empleado para actualizar.");
            return;
        }

        try {
            empleadoService.actualizarEmpleado(
                    empleadoSeleccionadoId,
                    (TipoEmpleado) cmbTipoEmpleado.getSelectedItem(),
                    txtNombre.getText().trim(),
                    txtCedula.getText().trim(),
                    parseFecha(txtFechaIngreso.getText().trim()),
                    parseDouble(txtSalarioBase.getText().trim(), "El salario base es invalido."),
                    parseDouble(txtBono.getText().trim(), "El bono es invalido."),
                    parseInt(txtHoras.getText().trim(), "Las horas son invalidas."),
                    parseDouble(txtValorHora.getText().trim(), "El valor por hora es invalido."));
            syncDepartamento(empleadoSeleccionadoId);
            postAction("Empleado actualizado correctamente.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void eliminarEmpleado() {
        if (empleadoSeleccionadoId == null) {
            showError("Selecciona un empleado para eliminar.");
            return;
        }
        try {
            empleadoService.eliminarEmpleado(empleadoSeleccionadoId);
            postAction("Empleado eliminado correctamente.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void syncDepartamento(String idEmpleado) {
        Object selected = cmbDepartamento.getSelectedItem();
        if (selected instanceof Departamento) {
            empleadoService.asignarDepartamento(idEmpleado, ((Departamento) selected).getId());
        } else {
            empleadoService.desasignarDepartamento(idEmpleado);
        }
    }

    private void cargarSeleccion() {
        int selectedRow = tableEmpleados.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }
        empleadoSeleccionadoId = tableModel.getValueAt(selectedRow, 0).toString();
        Empleado empleado = empleadoService.obtenerPorId(empleadoSeleccionadoId);

        txtNombre.setText(empleado.getNombre());
        txtCedula.setText(empleado.getCedula());
        txtFechaIngreso.setText(empleado.getFechaIngreso().toString());
        cmbTipoEmpleado.setSelectedItem(empleado.getTipoEmpleado());
        if (empleado instanceof EmpleadoPermanente) {
            EmpleadoPermanente permanente = (EmpleadoPermanente) empleado;
            txtSalarioBase.setText(String.valueOf(permanente.getSalarioBase()));
            txtBono.setText(String.valueOf(permanente.getBono()));
            txtHoras.setText("0");
            txtValorHora.setText("0");
        } else if (empleado instanceof EmpleadoTemporal) {
            EmpleadoTemporal temporal = (EmpleadoTemporal) empleado;
            txtSalarioBase.setText("0");
            txtBono.setText("0");
            txtHoras.setText(String.valueOf(temporal.getHorasTrabajadas()));
            txtValorHora.setText(String.valueOf(temporal.getValorHora()));
        }

        if (empleado.getDepartamento() != null) {
            cmbDepartamento.setSelectedItem(empleado.getDepartamento());
        } else {
            cmbDepartamento.setSelectedIndex(0);
        }
        updateFieldState();
    }

    private void updateFieldState() {
        TipoEmpleado tipoEmpleado = (TipoEmpleado) cmbTipoEmpleado.getSelectedItem();
        boolean esPermanente = tipoEmpleado == TipoEmpleado.PERMANENTE;
        txtSalarioBase.setEnabled(esPermanente);
        txtBono.setEnabled(esPermanente);
        txtHoras.setEnabled(!esPermanente);
        txtValorHora.setEnabled(!esPermanente);
        if (esPermanente) {
            txtHoras.setText("0");
            txtValorHora.setText("0");
        } else {
            txtSalarioBase.setText("0");
            txtBono.setText("0");
        }
    }

    private void limpiarFormulario() {
        empleadoSeleccionadoId = null;
        txtNombre.setText("");
        txtCedula.setText("");
        txtFechaIngreso.setText(LocalDate.now().toString());
        txtSalarioBase.setText("0");
        txtBono.setText("0");
        txtHoras.setText("0");
        txtValorHora.setText("0");
        cmbTipoEmpleado.setSelectedItem(TipoEmpleado.PERMANENTE);
        if (cmbDepartamento.getItemCount() > 0) {
            cmbDepartamento.setSelectedIndex(0);
        }
        tableEmpleados.clearSelection();
        updateFieldState();
    }

    private void postAction(String message) {
        refreshCallback.run();
        limpiarFormulario();
        JOptionPane.showMessageDialog(this, message, "CompuWork", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private LocalDate parseFecha(String value) {
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("La fecha debe estar en formato YYYY-MM-DD.");
        }
    }

    private double parseDouble(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(message);
        }
    }

    private int parseInt(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public void refreshData() {
        refreshDepartamentos();
        refreshTabla();
        updateFieldState();
    }

    private void refreshDepartamentos() {
        Object selected = cmbDepartamento == null ? null : cmbDepartamento.getSelectedItem();
        cmbDepartamento.removeAllItems();
        cmbDepartamento.addItem("Sin departamento");
        List<Departamento> departamentos = departamentoService.obtenerTodos();
        for (Departamento departamento : departamentos) {
            cmbDepartamento.addItem(departamento);
        }
        if (selected != null) {
            cmbDepartamento.setSelectedItem(selected);
        }
    }

    private void refreshTabla() {
        tableModel.setRowCount(0);
        List<Empleado> empleados = empleadoService.obtenerTodos();
        for (Empleado empleado : empleados) {
            tableModel.addRow(new Object[]{
                empleado.getIdEmpleado(),
                empleado.getTipoEmpleado(),
                empleado.getNombre(),
                empleado.getCedula(),
                empleado.getFechaIngreso(),
                empleado.calcularPago(),
                empleado.getDepartamento() == null ? "Sin departamento" : empleado.getDepartamento().getNombre()
            });
        }
        lblResumen.setText(empleados.size() + " empleados registrados");
    }
}
