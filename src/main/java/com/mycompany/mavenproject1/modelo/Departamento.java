/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.modelo;

/**
 *
 * @author USUARIO
 */
public class Departamento {
    
    private String nombre;
    private String descripcion;
    private Double presupuestoasignado;
    private int iddepartamento;
    

    public Departamento() {
    }

    public Departamento(String nombre, String descripcion, double presupuestoasignado, int iddepartamento) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.presupuestoasignado = presupuestoasignado;
        this.iddepartamento =iddepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getdescripcion() {
        return descripcion;
    }

    public void setdescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getpresupuestoasignado() {
        return presupuestoasignado;
    }

    public void setpresupuestoasignado(double presupuestoasignado) {
        this.presupuestoasignado = presupuestoasignado;
    }
    
    public int getiddepartamento() {
        return iddepartamento;
    }

    public void setiddepartamento(int iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

}
