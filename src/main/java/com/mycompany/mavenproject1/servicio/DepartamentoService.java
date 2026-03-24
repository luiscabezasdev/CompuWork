package com.mycompany.mavenproject1.servicio;

import com.mycompany.mavenproject1.excepcion.AsignacionInvalidaException;
import com.mycompany.mavenproject1.excepcion.DepartamentoNoEncontradoException;
import com.mycompany.mavenproject1.modelo.Departamento;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DepartamentoService {
    private final Map<String, Departamento> departamentosPorId;
    private final AtomicInteger secuencia;

    public DepartamentoService() {
        this.departamentosPorId = new LinkedHashMap<String, Departamento>();
        this.secuencia = new AtomicInteger(0);
    }

    public Departamento crearDepartamento(String nombre, String descripcion, double presupuestoAsignado) {
        String id = String.format("DEP-%03d", secuencia.incrementAndGet());
        Departamento departamento = new Departamento(id, nombre, descripcion, presupuestoAsignado);
        departamentosPorId.put(id, departamento);
        return departamento;
    }

    public Departamento actualizarDepartamento(String id, String nombre, String descripcion, double presupuestoAsignado) {
        Departamento departamento = obtenerPorId(id);
        departamento.actualizarDatos(nombre, descripcion, presupuestoAsignado);
        return departamento;
    }

    public void eliminarDepartamento(String id) {
        Departamento departamento = obtenerPorId(id);
        if (departamento.getCantidadEmpleados() > 0) {
            throw new AsignacionInvalidaException(
                    "No se puede eliminar un departamento con empleados asignados.");
        }
        departamentosPorId.remove(id);
    }

    public Departamento obtenerPorId(String id) {
        Departamento departamento = departamentosPorId.get(id);
        if (departamento == null) {
            throw new DepartamentoNoEncontradoException("Departamento no encontrado: " + id);
        }
        return departamento;
    }

    public List<Departamento> obtenerTodos() {
        return new ArrayList<Departamento>(departamentosPorId.values());
    }
}
