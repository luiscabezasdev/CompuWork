/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.modelo.Empleado;

import java.time.LocalDate;

/**
 *
 * @author USUARIO
 */
public class Mavenproject1 {

    public static void main(String[] args) {  
        Empleado myEmp = new Empleado("Marios","1983434",LocalDate.parse("2000-05-15"),12.000,"1898");
        System.out.println("El constructor es " + myEmp.getSalario());
    }
}
