/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.modelo;
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

    public EmpleadoPermanente(String nombre, String cedula, LocalDate now, double salario, String string, String string0, int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public double getBono() {
        return bono;
    }
    
    public void setBono(double bono) {
        this.bono = bono;
    }

    public String getSalarioBase() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
  
}
