package com.mycompany.mavenproject1.modelo;

import com.mycompany.mavenproject1.excepcion.ValidacionException;
import java.time.LocalDate;

public class EmpleadoTemporal extends Empleado {
    private int horasTrabajadas;
    private double valorHora;

    public EmpleadoTemporal(
            String idEmpleado,
            String nombre,
            String cedula,
            LocalDate fechaIngreso,
            int horasTrabajadas,
            double valorHora) {
        super(idEmpleado, nombre, cedula, fechaIngreso);
        actualizarCondiciones(horasTrabajadas, valorHora);
    }

    public final void actualizarCondiciones(int horasTrabajadas, double valorHora) {
        if (horasTrabajadas <= 0) {
            throw new ValidacionException("Las horas trabajadas deben ser mayores que cero.");
        }
        if (valorHora < 0) {
            throw new ValidacionException("El valor por hora no puede ser negativo.");
        }
        this.horasTrabajadas = horasTrabajadas;
        this.valorHora = valorHora;
    }

    @Override
    public double calcularPago() {
        return horasTrabajadas * valorHora;
    }

    @Override
    public TipoEmpleado getTipoEmpleado() {
        return TipoEmpleado.TEMPORAL;
    }

    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public double getValorHora() {
        return valorHora;
    }
}
