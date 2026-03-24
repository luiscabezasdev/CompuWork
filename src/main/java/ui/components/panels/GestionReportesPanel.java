package ui.components.panels;

import com.mycompany.mavenproject1.modelo.Departamento;
import com.mycompany.mavenproject1.modelo.Empleado;
import com.mycompany.mavenproject1.modelo.ReporteDesempeno;
import com.mycompany.mavenproject1.modelo.TipoObjetivoReporte;
import com.mycompany.mavenproject1.servicio.DepartamentoService;
import com.mycompany.mavenproject1.servicio.EmpleadoService;
import com.mycompany.mavenproject1.servicio.ReporteDesempenoService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import ui.components.Button;
import ui.components.ModernCard;
import ui.components.ModernTextField;

public class GestionReportesPanel extends ModernCard implements RefreshablePanel {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final ReporteDesempenoService reporteService;
    private final EmpleadoService empleadoService;
    private final DepartamentoService departamentoService;

    private JComboBox<TipoObjetivoReporte> cmbTipoObjetivo;
    private JComboBox<Object> cmbObjetivo;
    private ModernTextField txtProductividad;
    private ModernTextField txtPuntualidad;
    private ModernTextField txtCalidad;
    private JTextArea txtObservaciones;
    private JTextArea txtDetalleReporte;
    private JTable tableReportes;
    private DefaultTableModel tableModel;
    private JLabel lblResumen;

    public GestionReportesPanel(
            ReporteDesempenoService reporteService,
            EmpleadoService empleadoService,
            DepartamentoService departamentoService) {
        this.reporteService = reporteService;
        this.empleadoService = empleadoService;
        this.departamentoService = departamentoService;

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

        JLabel lblTitle = new JLabel("Reportes de Desempeno");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(15, 23, 42));

        lblResumen = new JLabel("0 reportes generados");
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

        cmbTipoObjetivo = new JComboBox<TipoObjetivoReporte>(TipoObjetivoReporte.values());
        cmbObjetivo = new JComboBox<Object>();
        txtProductividad = new ModernTextField("0 - 100");
        txtPuntualidad = new ModernTextField("0 - 100");
        txtCalidad = new ModernTextField("0 - 100");
        txtObservaciones = new JTextArea(3, 30);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        txtObservaciones.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        cmbTipoObjetivo.addActionListener(e -> {
            refreshObjetivos();
            updateMetricState();
        });

        addField(form, gbc, 0, "Tipo objetivo", cmbTipoObjetivo);
        addField(form, gbc, 1, "Objetivo", cmbObjetivo);
        addField(form, gbc, 2, "Productividad", txtProductividad);
        addField(form, gbc, 3, "Puntualidad", txtPuntualidad);
        addField(form, gbc, 4, "Calidad", txtCalidad);
        addField(form, gbc, 5, "Observaciones", new JScrollPane(txtObservaciones));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        Button btnLimpiar = new Button("LIMPIAR", false);
        Button btnGenerar = new Button("GENERAR", true);
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnGenerar.addActionListener(e -> generarReporte());
        actions.add(btnLimpiar);
        actions.add(btnGenerar);

        wrapper.add(form, BorderLayout.CENTER);
        wrapper.add(actions, BorderLayout.SOUTH);
        return wrapper;
    }

    private JPanel buildDataSection() {
        JPanel section = new JPanel(new BorderLayout(18, 18));
        section.setOpaque(false);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Tipo", "Objetivo", "Productividad", "Puntualidad", "Calidad", "Final", "Fecha"},
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableReportes = new JTable(tableModel);
        tableReportes.setRowHeight(30);
        tableReportes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                renderDetalleSeleccionado();
            }
        });

        JScrollPane tableScroll = new JScrollPane(tableReportes);
        tableScroll.setPreferredSize(new Dimension(820, 320));
        tableScroll.setBorder(BorderFactory.createTitledBorder("Historial de reportes"));

        txtDetalleReporte = new JTextArea();
        txtDetalleReporte.setEditable(false);
        txtDetalleReporte.setLineWrap(true);
        txtDetalleReporte.setWrapStyleWord(true);
        txtDetalleReporte.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        JScrollPane detalleScroll = new JScrollPane(txtDetalleReporte);
        detalleScroll.setPreferredSize(new Dimension(320, 320));
        detalleScroll.setBorder(BorderFactory.createTitledBorder("Detalle del reporte"));

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

    private void generarReporte() {
        Object objetivo = cmbObjetivo.getSelectedItem();
        if (objetivo == null) {
            showError("Selecciona un objetivo para generar el reporte.");
            return;
        }

        try {
            ReporteDesempeno reporte;
            TipoObjetivoReporte tipoObjetivo = (TipoObjetivoReporte) cmbTipoObjetivo.getSelectedItem();
            if (tipoObjetivo == TipoObjetivoReporte.INDIVIDUAL) {
                Empleado empleado = (Empleado) objetivo;
                reporte = reporteService.generarReporteIndividual(
                        empleado.getIdEmpleado(),
                        parseDouble(txtProductividad.getText().trim(), "La productividad es invalida."),
                        parseDouble(txtPuntualidad.getText().trim(), "La puntualidad es invalida."),
                        parseDouble(txtCalidad.getText().trim(), "La calidad es invalida."),
                        txtObservaciones.getText().trim());
            } else {
                Departamento departamento = (Departamento) objetivo;
                reporte = reporteService.generarReporteDepartamento(
                        departamento.getId(),
                        txtObservaciones.getText().trim());
            }

            refreshData();
            txtDetalleReporte.setText(buildDetalle(reporte));
            JOptionPane.showMessageDialog(this, "Reporte generado correctamente.", "CompuWork", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void renderDetalleSeleccionado() {
        int selectedRow = tableReportes.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }
        String idReporte = tableModel.getValueAt(selectedRow, 0).toString();
        for (ReporteDesempeno reporte : reporteService.obtenerTodos()) {
            if (reporte.getIdReporte().equals(idReporte)) {
                txtDetalleReporte.setText(buildDetalle(reporte));
                break;
            }
        }
    }

    private String buildDetalle(ReporteDesempeno reporte) {
        StringBuilder builder = new StringBuilder();
        builder.append("Reporte: ").append(reporte.getIdReporte()).append("\n");
        builder.append("Tipo: ").append(reporte.getTipoObjetivo()).append("\n");
        builder.append("Objetivo: ").append(reporte.getNombreObjetivo()).append("\n");
        builder.append("Fecha: ").append(reporte.getFechaGeneracion().format(FORMATTER)).append("\n");
        builder.append("Productividad: ").append(reporte.getProductividad()).append("\n");
        builder.append("Puntualidad: ").append(reporte.getPuntualidad()).append("\n");
        builder.append("Calidad: ").append(reporte.getCalidad()).append("\n");
        builder.append("Calificacion final: ").append(reporte.getCalificacionFinal()).append("\n\n");
        builder.append("Observaciones: ").append(reporte.getObservaciones().isEmpty() ? "Sin observaciones" : reporte.getObservaciones());
        return builder.toString();
    }

    private void limpiarFormulario() {
        cmbTipoObjetivo.setSelectedItem(TipoObjetivoReporte.INDIVIDUAL);
        txtProductividad.setText("");
        txtPuntualidad.setText("");
        txtCalidad.setText("");
        txtObservaciones.setText("");
        updateMetricState();
        refreshObjetivos();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private double parseDouble(String value, String message) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(message);
        }
    }

    private void refreshObjetivos() {
        Object selected = cmbObjetivo.getSelectedItem();
        cmbObjetivo.removeAllItems();
        TipoObjetivoReporte tipoObjetivo = (TipoObjetivoReporte) cmbTipoObjetivo.getSelectedItem();
        if (tipoObjetivo == TipoObjetivoReporte.INDIVIDUAL) {
            List<Empleado> empleados = empleadoService.obtenerTodos();
            for (Empleado empleado : empleados) {
                cmbObjetivo.addItem(empleado);
            }
        } else {
            List<Departamento> departamentos = departamentoService.obtenerTodos();
            for (Departamento departamento : departamentos) {
                cmbObjetivo.addItem(departamento);
            }
        }
        if (selected != null) {
            cmbObjetivo.setSelectedItem(selected);
        }
    }

    private void updateMetricState() {
        TipoObjetivoReporte tipoObjetivo = (TipoObjetivoReporte) cmbTipoObjetivo.getSelectedItem();
        boolean individual = tipoObjetivo == TipoObjetivoReporte.INDIVIDUAL;
        txtProductividad.setEnabled(individual);
        txtPuntualidad.setEnabled(individual);
        txtCalidad.setEnabled(individual);
        if (!individual) {
            txtProductividad.setText("Promedio automatico");
            txtPuntualidad.setText("Promedio automatico");
            txtCalidad.setText("Promedio automatico");
        } else if ("Promedio automatico".equals(txtProductividad.getText())) {
            txtProductividad.setText("");
            txtPuntualidad.setText("");
            txtCalidad.setText("");
        }
    }

    @Override
    public void refreshData() {
        refreshObjetivos();
        updateMetricState();
        tableModel.setRowCount(0);
        List<ReporteDesempeno> reportes = reporteService.obtenerTodos();
        for (ReporteDesempeno reporte : reportes) {
            tableModel.addRow(new Object[]{
                reporte.getIdReporte(),
                reporte.getTipoObjetivo(),
                reporte.getNombreObjetivo(),
                reporte.getProductividad(),
                reporte.getPuntualidad(),
                reporte.getCalidad(),
                reporte.getCalificacionFinal(),
                reporte.getFechaGeneracion().format(FORMATTER)
            });
        }
        lblResumen.setText(reportes.size() + " reportes generados");
        if (reportes.isEmpty()) {
            txtDetalleReporte.setText("Aun no existen reportes generados.");
        }
    }
}
