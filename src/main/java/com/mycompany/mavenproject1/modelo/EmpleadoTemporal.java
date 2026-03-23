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
public class EmpleadoTemporal extends Empleado{
   private int horasTrabajadas;
   private double valorHora;

    public EmpleadoTemporal(String nombre, String cedula, LocalDate fechaDeIngreso, double salario, String idEmpleado, int horasTrabajadas, double valorHora) {
        super(nombre, cedula, fechaDeIngreso, salario, idEmpleado);
        this.horasTrabajadas = horasTrabajadas;
        this.valorHora = valorHora;
    }
    
    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }
    
    public double getValorHora() {
        return valorHora;
    }
    
    public void setHorasTrabajadas(int horasTrabajadas){
        this.horasTrabajadas = horasTrabajadas;
    }
    
    public void setValorHora(double valorHora) {
        this.valorHora = valorHora;
    }
      
}
