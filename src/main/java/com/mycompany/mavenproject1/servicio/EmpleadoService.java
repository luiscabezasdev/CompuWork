package com.mycompany.mavenproject1.servicio;

import com.mycompany.mavenproject1.excepcion.EmpleadoDuplicadoException;
import com.mycompany.mavenproject1.excepcion.EmpleadoNoEncontradoException;
import com.mycompany.mavenproject1.modelo.Departamento;
import com.mycompany.mavenproject1.modelo.Empleado;
import com.mycompany.mavenproject1.modelo.EmpleadoPermanente;
import com.mycompany.mavenproject1.modelo.EmpleadoTemporal;
import com.mycompany.mavenproject1.modelo.TipoEmpleado;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class EmpleadoService {
    private final Map<String, Empleado> empleadosPorId;
    private final Map<String, String> empleadosPorCedula;
    private final AtomicInteger secuencia;
    private final DepartamentoService departamentoService;

    public EmpleadoService(DepartamentoService departamentoService) {
        this.empleadosPorId = new LinkedHashMap<String, Empleado>();
        this.empleadosPorCedula = new LinkedHashMap<String, String>();
        this.secuencia = new AtomicInteger(0);
        this.departamentoService = departamentoService;
    }

    public Empleado crearEmpleado(
            TipoEmpleado tipoEmpleado,
            String nombre,
            String cedula,
            LocalDate fechaIngreso,
            double salarioBase,
            double bono,
            int horasTrabajadas,
            double valorHora) {
        validarCedulaUnica(cedula, null);
        String id = String.format("EMP-%03d", secuencia.incrementAndGet());
        Empleado empleado = construirEmpleado(
                id,
                tipoEmpleado,
                nombre,
                cedula,
                fechaIngreso,
                salarioBase,
                bono,
                horasTrabajadas,
                valorHora);
        empleadosPorId.put(id, empleado);
        empleadosPorCedula.put(empleado.getCedula(), id);
        return empleado;
    }

    public Empleado actualizarEmpleado(
            String idEmpleado,
            TipoEmpleado tipoEmpleado,
            String nombre,
            String cedula,
            LocalDate fechaIngreso,
            double salarioBase,
            double bono,
            int horasTrabajadas,
            double valorHora) {
        Empleado actual = obtenerPorId(idEmpleado);
        validarCedulaUnica(cedula, idEmpleado);

        Empleado actualizado = construirEmpleado(
                idEmpleado,
                tipoEmpleado,
                nombre,
                cedula,
                fechaIngreso,
                salarioBase,
                bono,
                horasTrabajadas,
                valorHora);

        Departamento departamento = actual.getDepartamento();
        if (departamento != null) {
            departamento.removerEmpleado(actual);
        }

        empleadosPorId.put(idEmpleado, actualizado);
        empleadosPorCedula.remove(actual.getCedula());
        empleadosPorCedula.put(actualizado.getCedula(), idEmpleado);

        if (departamento != null) {
            departamento.agregarEmpleado(actualizado);
        }
        return actualizado;
    }

    public void eliminarEmpleado(String idEmpleado) {
        Empleado empleado = obtenerPorId(idEmpleado);
        if (empleado.getDepartamento() != null) {
            empleado.getDepartamento().removerEmpleado(empleado);
        }
        empleadosPorId.remove(idEmpleado);
        empleadosPorCedula.remove(empleado.getCedula());
    }

    public Empleado obtenerPorId(String idEmpleado) {
        Empleado empleado = empleadosPorId.get(idEmpleado);
        if (empleado == null) {
            throw new EmpleadoNoEncontradoException("Empleado no encontrado: " + idEmpleado);
        }
        return empleado;
    }

    public Empleado buscarPorCedula(String cedula) {
        String idEmpleado = empleadosPorCedula.get(cedula);
        if (idEmpleado == null) {
            throw new EmpleadoNoEncontradoException("Empleado no encontrado con cedula: " + cedula);
        }
        return obtenerPorId(idEmpleado);
    }

    public void asignarDepartamento(String idEmpleado, String idDepartamento) {
        Empleado empleado = obtenerPorId(idEmpleado);
        Departamento nuevoDepartamento = departamentoService.obtenerPorId(idDepartamento);

        if (empleado.getDepartamento() != null && empleado.getDepartamento().getId().equals(idDepartamento)) {
            return;
        }

        if (empleado.getDepartamento() != null) {
            empleado.getDepartamento().removerEmpleado(empleado);
        }
        nuevoDepartamento.agregarEmpleado(empleado);
    }

    public void desasignarDepartamento(String idEmpleado) {
        Empleado empleado = obtenerPorId(idEmpleado);
        if (empleado.getDepartamento() != null) {
            empleado.getDepartamento().removerEmpleado(empleado);
        }
    }

    public List<Empleado> obtenerTodos() {
        return new ArrayList<Empleado>(empleadosPorId.values());
    }

    public int getCantidad() {
        return empleadosPorId.size();
    }

    private Empleado construirEmpleado(
            String id,
            TipoEmpleado tipoEmpleado,
            String nombre,
            String cedula,
            LocalDate fechaIngreso,
            double salarioBase,
            double bono,
            int horasTrabajadas,
            double valorHora) {
        if (tipoEmpleado == TipoEmpleado.PERMANENTE) {
            return new EmpleadoPermanente(id, nombre, cedula, fechaIngreso, salarioBase, bono);
        }
        return new EmpleadoTemporal(id, nombre, cedula, fechaIngreso, horasTrabajadas, valorHora);
    }

    private void validarCedulaUnica(String cedula, String idActual) {
        String idExistente = empleadosPorCedula.get(cedula);
        if (idExistente != null && !idExistente.equals(idActual)) {
            throw new EmpleadoDuplicadoException("Ya existe un empleado con la cedula " + cedula + ".");
        }
    }
}
