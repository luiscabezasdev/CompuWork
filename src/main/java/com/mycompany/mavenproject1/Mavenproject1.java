package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.modelo.Departamento;
import com.mycompany.mavenproject1.modelo.Empleado;
import com.mycompany.mavenproject1.modelo.ReporteDesempeno;
import com.mycompany.mavenproject1.modelo.TipoEmpleado;
import com.mycompany.mavenproject1.servicio.DepartamentoService;
import com.mycompany.mavenproject1.servicio.EmpleadoService;
import com.mycompany.mavenproject1.servicio.ReporteDesempenoService;
import java.time.LocalDate;
import java.util.Scanner;

public class Mavenproject1 {
    public static void main(String[] args) {
        System.out.println("=== CompuWork ===");
        System.out.println("1. Usar Consola");
        System.out.println("2. Usar Interfaz Grafica");
        System.out.print("Elige: ");

        try (Scanner scanner = new Scanner(System.in)) {
            String opcion = scanner.nextLine().trim();
            if ("1".equals(opcion)) {
                usarConsola();
            } else {
                usarGui();
            }
        }
    }

    private static void usarConsola() {
        DepartamentoService departamentoService = new DepartamentoService();
        EmpleadoService empleadoService = new EmpleadoService(departamentoService);
        ReporteDesempenoService reporteService =
                new ReporteDesempenoService(empleadoService, departamentoService);

        Departamento tecnologia = departamentoService.crearDepartamento(
                "Tecnologia", "Equipo de soporte y desarrollo", 50000);
        Empleado empleado = empleadoService.crearEmpleado(
                TipoEmpleado.PERMANENTE,
                "Pedro Suarez",
                "1098923123",
                LocalDate.now(),
                2500,
                350,
                0,
                0);
        empleadoService.asignarDepartamento(empleado.getIdEmpleado(), tecnologia.getId());
        ReporteDesempeno reporte = reporteService.generarReporteIndividual(
                empleado.getIdEmpleado(), 90, 88, 92, "Buen rendimiento general");

        System.out.println("Empleado registrado: " + empleado.getNombre());
        System.out.println("Departamento asignado: " + tecnologia.getNombre());
        System.out.println("Pago calculado: " + empleado.calcularPago());
        System.out.println("Reporte generado: " + reporte.getIdReporte()
                + " con nota final " + reporte.getCalificacionFinal());
    }

    private static void usarGui() {
        ui.MainFrame.main(new String[]{});
    }
}
