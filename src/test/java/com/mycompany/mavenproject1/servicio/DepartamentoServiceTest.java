package com.mycompany.mavenproject1.servicio;

import com.mycompany.mavenproject1.excepcion.AsignacionInvalidaException;
import com.mycompany.mavenproject1.modelo.Departamento;
import com.mycompany.mavenproject1.modelo.Empleado;
import com.mycompany.mavenproject1.modelo.TipoEmpleado;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepartamentoServiceTest {
    private DepartamentoService departamentoService;
    private EmpleadoService empleadoService;

    @BeforeEach
    void setUp() {
        departamentoService = new DepartamentoService();
        empleadoService = new EmpleadoService(departamentoService);
    }

    @Test
    void noEliminaDepartamentoConEmpleadosAsignados() {
        Departamento departamento = departamentoService.crearDepartamento(
                "Tecnologia", "Equipo de desarrollo", 40000);
        Empleado empleado = empleadoService.crearEmpleado(
                TipoEmpleado.PERMANENTE,
                "Maria Perez",
                "2001",
                LocalDate.now(),
                4200,
                600,
                0,
                0);

        empleadoService.asignarDepartamento(empleado.getIdEmpleado(), departamento.getId());

        Assertions.assertThrows(AsignacionInvalidaException.class,
                () -> departamentoService.eliminarDepartamento(departamento.getId()));
    }

    @Test
    void actualizaDatosDelDepartamento() {
        Departamento departamento = departamentoService.crearDepartamento(
                "RRHH", "Gestion humana", 12000);

        departamentoService.actualizarDepartamento(
                departamento.getId(),
                "Talento Humano",
                "Seleccion y bienestar",
                18000);

        Departamento actualizado = departamentoService.obtenerPorId(departamento.getId());
        Assertions.assertEquals("Talento Humano", actualizado.getNombre());
        Assertions.assertEquals(18000, actualizado.getPresupuestoAsignado(), 0.001);
    }
}
