package com.mycompany.mavenproject1.modelo;

import com.mycompany.mavenproject1.excepcion.ValidacionException;
import java.time.LocalDate;

public class EmpleadoPermanente extends Empleado {
    private double salarioBase;
    private double bono;

    public EmpleadoPermanente(
            String idEmpleado,
            String nombre,
            String cedula,
            LocalDate fechaIngreso,
            double salarioBase,
            double bono) {
        super(idEmpleado, nombre, cedula, fechaIngreso);
        actualizarCompensacion(salarioBase, bono);
    }

    public final void actualizarCompensacion(double salarioBase, double bono) {
        validarNoNegativo(salarioBase, "El salario base no puede ser negativo.");
        validarNoNegativo(bono, "El bono no puede ser negativo.");
        this.salarioBase = salarioBase;
        this.bono = bono;
    }

    @Override
    public double calcularPago() {
        return salarioBase + bono;
    }

    @Override
    public TipoEmpleado getTipoEmpleado() {
        return TipoEmpleado.PERMANENTE;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public double getBono() {
        return bono;
    }

    private void validarNoNegativo(double value, String message) {
        if (value < 0) {
            throw new ValidacionException(message);
        }
    }
}
