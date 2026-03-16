/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.servicio;
import com.mycompany.mavenproject1.modelo.Empleado;
import java.time.LocalDate;

/**
 *
 * @author USUARIO
 */
public class EmpleadoPermanente extends Empleado {
    private double bono;

    public EmpleadoPermanente(String nombre, String cedula, LocalDate fechaDeIngreso, double salario, String idEmpleado, double bono) {
        super(nombre, cedula, fechaDeIngreso, salario, idEmpleado);
        this.bono = bono;
    }
    
    public double getBono() {
        return bono;
    }
    
    public void setBono(double bono) {
        this.bono = bono;
    }
  
}
