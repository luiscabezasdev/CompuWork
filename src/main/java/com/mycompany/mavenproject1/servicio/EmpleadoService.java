/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.servicio;

import com.mycompany.mavenproject1.modelo.Empleado;
import com.mycompany.mavenproject1.modelo.EmpleadoPermanente;
import com.mycompany.mavenproject1.modelo.EmpleadoTemporal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author USUARIO
 */
public class EmpleadoService {
     // ✅ ArrayList para almacenar empleados
    private List<Empleado> listaEmpleados;
    
    public EmpleadoService() {
        this.listaEmpleados = new ArrayList<>();
    }
    
    // ✅ Método para crear empleado (integra tu lógica original)
    public Empleado crearEmpleado(String nombre, String cedula, int tipo, 
                                  double salario, int horas, double valorHora) {
        Empleado empleado;
        
        if (tipo == 1) { // Permanente
            empleado = new EmpleadoPermanente(
                nombre, 
                cedula, 
                LocalDate.now(), 
                salario, 
                "PERM-" + System.currentTimeMillis(), 
                "SS-" + cedula, 
                100
            );
        } else { // Temporal
            empleado = new EmpleadoTemporal(
                nombre, 
                cedula, 
                LocalDate.now(), 
                valorHora, 
                "TEMP-" + System.currentTimeMillis(), 
                horas, 
                valorHora
            );
        }
        
        listaEmpleados.add(empleado); // ✅ Guardar en ArrayList
        return empleado;
    }
    
    // ✅ Obtener todos los empleados
    public List<Empleado> obtenerTodos() {
        return listaEmpleados;
    }
    
    // ✅ Obtener cantidad
    public int getCantidad() {
        return listaEmpleados.size();
    }
    
    // ✅ Buscar por cédula
    public Empleado buscarPorCedula(String cedula) {
        for (Empleado emp : listaEmpleados) {
            if (emp.getCedula().equals(cedula)) {
                return emp;
            }
        }
        return null;
    }
    
    // ✅ Eliminar empleado
    public boolean eliminar(String cedula) {
        return listaEmpleados.removeIf(emp -> emp.getCedula().equals(cedula));
    }   
 }
