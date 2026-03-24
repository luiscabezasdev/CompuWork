package com.mycompany.mavenproject1.servicio;

import com.mycompany.mavenproject1.excepcion.EmpleadoDuplicadoException;
import com.mycompany.mavenproject1.modelo.Empleado;
import com.mycompany.mavenproject1.modelo.TipoEmpleado;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmpleadoServiceTest {
    private DepartamentoService departamentoService;
    private EmpleadoService empleadoService;

    @BeforeEach
    void setUp() {
        departamentoService = new DepartamentoService();
        empleadoService = new EmpleadoService(departamentoService);
    }

    @Test
    void creaEmpleadoPermanenteYCalculaPago() {
        Empleado empleado = empleadoService.crearEmpleado(
                TipoEmpleado.PERMANENTE,
                "Ana Lopez",
                "1001",
                LocalDate.of(2026, 1, 10),
                3200,
                400,
                0,
                0);

        Assertions.assertEquals(TipoEmpleado.PERMANENTE, empleado.getTipoEmpleado());
        Assertions.assertEquals(3600, empleado.calcularPago(), 0.001);
    }

    @Test
    void evitaCedulasDuplicadas() {
        empleadoService.crearEmpleado(
                TipoEmpleado.TEMPORAL,
                "Luis Gomez",
                "1002",
                LocalDate.now(),
                0,
                0,
                20,
                15);

        Assertions.assertThrows(EmpleadoDuplicadoException.class, () ->
                empleadoService.crearEmpleado(
                        TipoEmpleado.PERMANENTE,
                        "Luisa Gomez",
                        "1002",
                        LocalDate.now(),
                        2500,
                        100,
                        0,
                        0));
    }

    @Test
    void asignaYDesasignaDepartamento() {
        Empleado empleado = empleadoService.crearEmpleado(
                TipoEmpleado.TEMPORAL,
                "Carlos Ruiz",
                "1003",
                LocalDate.now(),
                0,
                0,
                40,
                12);
        String departamentoId = departamentoService
                .crearDepartamento("Operacion", "Soporte operativo", 15000)
                .getId();

        empleadoService.asignarDepartamento(empleado.getIdEmpleado(), departamentoId);
        Assertions.assertEquals("Operacion", empleadoService.obtenerPorId(empleado.getIdEmpleado())
                .getDepartamento().getNombre());

        empleadoService.desasignarDepartamento(empleado.getIdEmpleado());
        Assertions.assertNull(empleadoService.obtenerPorId(empleado.getIdEmpleado()).getDepartamento());
    }
}
