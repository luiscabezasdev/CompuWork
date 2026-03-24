package ui;

import com.mycompany.mavenproject1.servicio.DepartamentoService;
import com.mycompany.mavenproject1.servicio.EmpleadoService;
import com.mycompany.mavenproject1.servicio.ReporteDesempenoService;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ui.components.Sidebar;
import ui.components.panels.GestionDepartamentosPanel;
import ui.components.panels.GestionEmpleadosPanel;
import ui.components.panels.GestionReportesPanel;

public class MainFrame extends JFrame {
    private final Sidebar sidebar;
    private final JPanel cardPanel;
    private final GestionEmpleadosPanel panelEmpleados;
    private final GestionDepartamentosPanel panelDepartamentos;
    private final GestionReportesPanel panelReportes;

    public MainFrame() {
        DepartamentoService departamentoService = new DepartamentoService();
        EmpleadoService empleadoService = new EmpleadoService(departamentoService);
        ReporteDesempenoService reporteService =
                new ReporteDesempenoService(empleadoService, departamentoService);

        setTitle("CompuWork Management Console");
        setSize(1500, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 250, 252));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        sidebar = new Sidebar();
        mainPanel.add(sidebar, BorderLayout.WEST);

        cardPanel = new JPanel(new CardLayout());
        cardPanel.setBackground(new Color(248, 250, 252));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(24, 20, 24, 20));

        Runnable refreshCallback = new Runnable() {
            @Override
            public void run() {
                refreshAllPanels();
            }
        };

        panelEmpleados = new GestionEmpleadosPanel(empleadoService, departamentoService, refreshCallback);
        panelDepartamentos = new GestionDepartamentosPanel(departamentoService, refreshCallback);
        panelReportes = new GestionReportesPanel(reporteService, empleadoService, departamentoService);

        cardPanel.add(panelEmpleados, "empleados");
        cardPanel.add(panelDepartamentos, "departamentos");
        cardPanel.add(panelReportes, "reportes");

        sidebar.setNavigationListener(new Sidebar.NavigationListener() {
            @Override
            public void onNavigate(String viewId) {
                showView(viewId);
            }
        });

        mainPanel.add(cardPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
        refreshAllPanels();
        showView("empleados");
    }

    private void showView(String viewId) {
        CardLayout layout = (CardLayout) cardPanel.getLayout();
        layout.show(cardPanel, viewId);
        sidebar.select(viewId);
    }

    private void refreshAllPanels() {
        panelDepartamentos.refreshData();
        panelEmpleados.refreshData();
        panelReportes.refreshData();
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info
                    : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
