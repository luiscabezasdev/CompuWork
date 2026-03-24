package com.mycompany.mavenproject1.modelo;

import com.mycompany.mavenproject1.excepcion.ValidacionException;
import java.time.LocalDate;
import java.util.Objects;

public abstract class Empleado {
    private final String idEmpleado;
    private String nombre;
    private String cedula;
    private LocalDate fechaIngreso;
    private Departamento departamento;

    protected Empleado(String idEmpleado, String nombre, String cedula, LocalDate fechaIngreso) {
        if (idEmpleado == null || idEmpleado.trim().isEmpty()) {
            throw new ValidacionException("El id del empleado es obligatorio.");
        }
        this.idEmpleado = idEmpleado;
        actualizarDatosBasicos(nombre, cedula, fechaIngreso);
    }

    public final void actualizarDatosBasicos(String nombre, String cedula, LocalDate fechaIngreso) {
        validarTexto(nombre, "El nombre es obligatorio.");
        validarTexto(cedula, "La cedula es obligatoria.");
        if (fechaIngreso == null) {
            throw new ValidacionException("La fecha de ingreso es obligatoria.");
        }

        this.nombre = nombre.trim();
        this.cedula = cedula.trim();
        this.fechaIngreso = fechaIngreso;
    }

    public abstract double calcularPago();

    public abstract TipoEmpleado getTipoEmpleado();

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    void asignarDepartamentoInterno(Departamento departamento) {
        this.departamento = departamento;
    }

    private void validarTexto(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidacionException(message);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado empleado = (Empleado) object;
        return Objects.equals(idEmpleado, empleado.idEmpleado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmpleado);
    }

    @Override
    public String toString() {
        return nombre + " (" + cedula + ")";
    }
}
