/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.modelo;

/**
 *
 * @author USUARIO
 */
package com.mycompany.mavenproject1.modelo;

public class Reporte {

    private int idReporte;
    private String tipo;
    private String formato;

    public Reporte() {
    }

    public Reporte(int idReporte, String tipo, String formato) {
        this.idReporte = idReporte;
        this.tipo = tipo;
        this.formato = formato;
    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public void generarReporteGeneral() {
        System.out.println("Generando reporte general de tipo: " + tipo);
    }

    public void exportar(String formato) {
        System.out.println("Exportando reporte en formato: " + formato);
    }
}
