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
    
    public Empleado(String nombre, String cedula, LocalDate fechaDeIngreso, double salario, String idEmpleado) {
        this.nombre = nombre;
        this.fechaDeIngreso = fechaDeIngreso;
        this.cedula = cedula;
        this.idEmpleado = idEmpleado;
        this.salario = salario;       
        
    }
       
}
