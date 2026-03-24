package com.mycompany.mavenproject1.modelo;

import com.mycompany.mavenproject1.excepcion.AsignacionInvalidaException;
import com.mycompany.mavenproject1.excepcion.ValidacionException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Departamento {
    private final String id;
    private String nombre;
    private String descripcion;
    private double presupuestoAsignado;
    private final Set<Empleado> empleados;

    public Departamento(String id, String nombre, String descripcion, double presupuestoAsignado) {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidacionException("El id del departamento es obligatorio.");
        }
        this.id = id;
        this.empleados = new LinkedHashSet<Empleado>();
        actualizarDatos(nombre, descripcion, presupuestoAsignado);
    }

    public final void actualizarDatos(String nombre, String descripcion, double presupuestoAsignado) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre del departamento es obligatorio.");
        }
        if (presupuestoAsignado < 0) {
            throw new ValidacionException("El presupuesto asignado no puede ser negativo.");
        }
        this.nombre = nombre.trim();
        this.descripcion = descripcion == null ? "" : descripcion.trim();
        this.presupuestoAsignado = presupuestoAsignado;
    }

    public void agregarEmpleado(Empleado empleado) {
        if (empleado == null) {
            throw new ValidacionException("El empleado es obligatorio.");
        }
        Departamento departamentoActual = empleado.getDepartamento();
        if (departamentoActual != null && !id.equals(departamentoActual.getId())) {
            throw new AsignacionInvalidaException("El empleado ya pertenece a otro departamento.");
        }
        empleados.add(empleado);
        empleado.asignarDepartamentoInterno(this);
    }

    public void removerEmpleado(Empleado empleado) {
        if (empleado == null) {
            throw new ValidacionException("El empleado es obligatorio.");
        }
        if (empleados.remove(empleado) && empleado.getDepartamento() == this) {
            empleado.asignarDepartamentoInterno(null);
        }
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPresupuestoAsignado() {
        return presupuestoAsignado;
    }

    public List<Empleado> getEmpleados() {
        return Collections.unmodifiableList(new ArrayList<Empleado>(empleados));
    }

    public int getCantidadEmpleados() {
        return empleados.size();
    }

    @Override
    public String toString() {
        return nombre;
    }
}
