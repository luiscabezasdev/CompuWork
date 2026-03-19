/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.modelo.Empleado;
import com.mycompany.mavenproject1.servicio.EmpleadoPermanente;
import com.mycompany.mavenproject1.servicio.EmpleadoTemporal;
        
import java.util.Scanner;

import java.time.LocalDate;

/**
 *
 * @author USUARIO
 */
public class Mavenproject1 {
    
    
    public static void main(String[] args) { 
       Scanner sc = new Scanner(System.in);  
       int elegirTipodeEmpleado;
        
        do {   
        System.out.print("Elige el tipo de empleado \n El número 1 para Empleado Permanente \n El número 2 para empleado Temporal \n Ingresa aquí: ");
         elegirTipodeEmpleado = sc.nextInt();    
        }
        while ((elegirTipodeEmpleado != 1) && (elegirTipodeEmpleado != 2));
        
        System.out.println("Has introducido el nùmero " + elegirTipodeEmpleado);
       
        Empleado tipodeEmpleado;
        
        if (elegirTipodeEmpleado == 1) {
            tipodeEmpleado = new EmpleadoPermanente("Pedro", "198923", LocalDate.of(2025, 5, 23), 12.800, "2342343", 100);
        } else {
            tipodeEmpleado = new EmpleadoTemporal("Juan", "678243", LocalDate.of(2025, 3, 18), 13456, "1098944", 14, 300);
        }

        System.out.println("Has introducido el nùmero " + elegirTipodeEmpleado +" " + tipodeEmpleado.getCedula());
        
       
//        EmpleadoPermanente empleado1 = new EmpleadoPermanente();
//        EmpleadoTemporal empleado2 = new EmpleadoTemporal();
//        
//        
//        public int respuesta()
//        if (elegirTipodeEmpleado == 1) {
//            EmpleadoPermanente empleado1 = new EmpleadoPermanente();
//        } else {
//            EmpleadoTemporal empleado2 = new EmpleadoTemporal();
//        }
//        
//       
//        System.out.println("la opcion ingresada es " + elegirTipodeEmpleado);
    }
        
    
        
}
