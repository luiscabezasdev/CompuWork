/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.modelo.Empleado;
import com.mycompany.mavenproject1.servicio.EmpleadoService;
import java.util.Scanner;

/**
 *
 * @author USUARIO
 */
public class Mavenproject1 {
    
    
    public static void main(String[] args) { 
        
        
        System.out.println("=== CompuWork ===");
        System.out.println("1. Usar Consola");
        System.out.println("2. Usar Interfaz Gráfica");
        System.out.print("Elige: ");
        
        try (Scanner sc = new Scanner(System.in)) {
            int opcion = sc.nextInt();
            
            if (opcion == 1) {
                
                usarConsola(sc);
            } else {
                
                usarGUI();
            }
        }
    }
    
    private static void usarConsola(Scanner sc) {
        EmpleadoService service = new EmpleadoService();
        int elegirTipodeEmpleado;
        
        do {   
            System.out.print("Elige el tipo de empleado \n" +
                           "El número 1 para Empleado Permanente \n" +
                           "El número 2 para empleado Temporal \n" +
                           "Ingresa aquí: ");
            elegirTipodeEmpleado = sc.nextInt();    
        } while ((elegirTipodeEmpleado != 1) && (elegirTipodeEmpleado != 2));
        
        System.out.println("Has introducido el número " + elegirTipodeEmpleado);
       
        Empleado tipodeEmpleado;
        
        if (elegirTipodeEmpleado == 1) {
            tipodeEmpleado = service.crearEmpleado(
                "Pedro", "198923", 1, 12800, 0, 0
            );
        } else {
            tipodeEmpleado = service.crearEmpleado(
                "Juan", "678243", 2, 0, 14, 300
            );
        }

        System.out.println("Has introducido el número " + 
                          elegirTipodeEmpleado + " " + 
                          tipodeEmpleado.getCedula());
        System.out.println("Total empleados: " + service.getCantidad());
    }
    
    private static void usarGUI() {
        ui.MainFrame.main(new String[]{});
    }
        
        
    }
 
      
