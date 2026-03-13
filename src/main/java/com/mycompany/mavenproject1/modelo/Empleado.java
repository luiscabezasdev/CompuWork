/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.modelo;

import java.time.LocalDate;

/**
 *
 * @author USUARIO
 */
public class Empleado {
    
    private String nombre;
    private String cedula;
    private LocalDate fechaDeIngreso;
    private double salario;
    private String idEmpleado;
    
    public Empleado(String nombre) {
        this.nombre = nombre;
    }
    
    public Empleado(String nombre, String cedula, LocalDate fechaDeIngreso, double salario, String idEmpleado) {
        this.nombre = nombre; 
        this.cedula = cedula;
        this.fechaDeIngreso = fechaDeIngreso;
        this.salario = salario;
        this.idEmpleado = idEmpleado;
   }
    
   public String getNombre() {
       return nombre;
   }
   
   public String getCedula(){
       return cedula;
   }
   
   public LocalDate getFechaDeIngreso(){
       return fechaDeIngreso;
   }
   
   public double getSalario() {
       return salario;
   }
   
   public String getIdEmpleado() {
       return idEmpleado;
   }
       
}
