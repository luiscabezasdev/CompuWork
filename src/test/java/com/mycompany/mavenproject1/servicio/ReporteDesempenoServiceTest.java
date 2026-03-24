package com.mycompany.mavenproject1.servicio;

import com.mycompany.mavenproject1.excepcion.ReporteInvalidoException;
import com.mycompany.mavenproject1.modelo.Departamento;
import com.mycompany.mavenproject1.modelo.Empleado;
import com.mycompany.mavenproject1.modelo.ReporteDesempeno;
import com.mycompany.mavenproject1.modelo.TipoEmpleado;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReporteDesempenoServiceTest {
    private DepartamentoService departamentoService;
    private EmpleadoService empleadoService;
    private ReporteDesempenoService reporteService;

    @BeforeEach
    void setUp() {
        departamentoService = new DepartamentoService();
        empleadoService = new EmpleadoService(departamentoService);
        reporteService = new ReporteDesempenoService(empleadoService, departamentoService);
    }

    @Test
    void generaReporteIndividualConPromedioCorrecto() {
        Empleado empleado = empleadoService.crearEmpleado(
                TipoEmpleado.PERMANENTE,
                "Paula Diaz",
                "3001",
                LocalDate.now(),
                2800,
                200,
                0,
                0);

        ReporteDesempeno reporte = reporteService.generarReporteIndividual(
                empleado.getIdEmpleado(), 90, 80, 70, "Buen avance");

        Assertions.assertEquals(80, reporte.getCalificacionFinal(), 0.001);
    }

    @Test
    void generaReporteDepartamentoDesdeReportesIndividuales() {
        Departamento departamento = departamentoService.crearDepartamento(
                "Analitica", "Equipo de datos", 30000);
        Empleado empleadoA = empleadoService.crearEmpleado(
                TipoEmpleado.PERMANENTE,
                "Nora Vega",
                "3002",
                LocalDate.now(),
                3500,
                300,
                0,
                0);
        Empleado empleadoB = empleadoService.crearEmpleado(
                TipoEmpleado.TEMPORAL,
                "Ivan Mora",
                "3003",
                LocalDate.now(),
                0,
                0,
                30,
                20);

        empleadoService.asignarDepartamento(empleadoA.getIdEmpleado(), departamento.getId());
        empleadoService.asignarDepartamento(empleadoB.getIdEmpleado(), departamento.getId());
        reporteService.generarReporteIndividual(empleadoA.getIdEmpleado(), 90, 80, 70, "A");
        reporteService.generarReporteIndividual(empleadoB.getIdEmpleado(), 60, 70, 80, "B");

        ReporteDesempeno reporteDepartamento = reporteService.generarReporteDepartamento(
                departamento.getId(), "Promedio del equipo");

        Assertions.assertEquals(75, reporteDepartamento.getProductividad(), 0.001);
        Assertions.assertEquals(75, reporteDepartamento.getPuntualidad(), 0.001);
        Assertions.assertEquals(75, reporteDepartamento.getCalidad(), 0.001);
    }

    @Test
    void fallaSiElDepartamentoNoTieneReportesPrevios() {
        Departamento departamento = departamentoService.crearDepartamento(
                "Calidad", "Control de procesos", 9000);
        Empleado empleado = empleadoService.crearEmpleado(
                TipoEmpleado.PERMANENTE,
                "Sara Nino",
                "3004",
                LocalDate.now(),
                2600,
                150,
                0,
                0);
        empleadoService.asignarDepartamento(empleado.getIdEmpleado(), departamento.getId());

        Assertions.assertThrows(ReporteInvalidoException.class,
                () -> reporteService.generarReporteDepartamento(departamento.getId(), "Sin base"));
    }
}
