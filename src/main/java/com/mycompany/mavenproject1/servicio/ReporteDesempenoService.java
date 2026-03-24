package com.mycompany.mavenproject1.servicio;

import com.mycompany.mavenproject1.excepcion.ReporteInvalidoException;
import com.mycompany.mavenproject1.modelo.Departamento;
import com.mycompany.mavenproject1.modelo.Empleado;
import com.mycompany.mavenproject1.modelo.ReporteDesempeno;
import com.mycompany.mavenproject1.modelo.TipoObjetivoReporte;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ReporteDesempenoService {
    private final Map<String, ReporteDesempeno> reportesPorId;
    private final AtomicInteger secuencia;
    private final EmpleadoService empleadoService;
    private final DepartamentoService departamentoService;

    public ReporteDesempenoService(
            EmpleadoService empleadoService,
            DepartamentoService departamentoService) {
        this.reportesPorId = new LinkedHashMap<String, ReporteDesempeno>();
        this.secuencia = new AtomicInteger(0);
        this.empleadoService = empleadoService;
        this.departamentoService = departamentoService;
    }

    public ReporteDesempeno generarReporteIndividual(
            String idEmpleado,
            double productividad,
            double puntualidad,
            double calidad,
            String observaciones) {
        Empleado empleado = empleadoService.obtenerPorId(idEmpleado);
        ReporteDesempeno reporte = new ReporteDesempeno(
                generarId(),
                TipoObjetivoReporte.INDIVIDUAL,
                productividad,
                puntualidad,
                calidad,
                observaciones,
                empleado,
                null);
        reportesPorId.put(reporte.getIdReporte(), reporte);
        return reporte;
    }

    public ReporteDesempeno generarReporteDepartamento(String idDepartamento, String observaciones) {
        Departamento departamento = departamentoService.obtenerPorId(idDepartamento);
        if (departamento.getEmpleados().isEmpty()) {
            throw new ReporteInvalidoException(
                    "El departamento no tiene empleados para generar el reporte.");
        }

        List<ReporteDesempeno> reportesIndividuales = new ArrayList<ReporteDesempeno>();
        for (Empleado empleado : departamento.getEmpleados()) {
            ReporteDesempeno ultimoReporte = obtenerUltimoReporteIndividual(empleado.getIdEmpleado());
            if (ultimoReporte != null) {
                reportesIndividuales.add(ultimoReporte);
            }
        }

        if (reportesIndividuales.isEmpty()) {
            throw new ReporteInvalidoException(
                    "El departamento necesita al menos un reporte individual previo.");
        }

        double productividad = promedioProductividad(reportesIndividuales);
        double puntualidad = promedioPuntualidad(reportesIndividuales);
        double calidad = promedioCalidad(reportesIndividuales);

        ReporteDesempeno reporte = new ReporteDesempeno(
                generarId(),
                TipoObjetivoReporte.DEPARTAMENTO,
                productividad,
                puntualidad,
                calidad,
                observaciones,
                null,
                departamento);
        reportesPorId.put(reporte.getIdReporte(), reporte);
        return reporte;
    }

    public List<ReporteDesempeno> obtenerTodos() {
        return new ArrayList<ReporteDesempeno>(reportesPorId.values());
    }

    private String generarId() {
        return String.format("REP-%03d", secuencia.incrementAndGet());
    }

    private ReporteDesempeno obtenerUltimoReporteIndividual(String idEmpleado) {
        ReporteDesempeno ultimoReporte = null;
        for (ReporteDesempeno reporte : reportesPorId.values()) {
            if (reporte.getTipoObjetivo() == TipoObjetivoReporte.INDIVIDUAL
                    && reporte.getEmpleadoEvaluado().getIdEmpleado().equals(idEmpleado)) {
                ultimoReporte = reporte;
            }
        }
        return ultimoReporte;
    }

    private double promedioProductividad(List<ReporteDesempeno> reportes) {
        double suma = 0;
        for (ReporteDesempeno reporte : reportes) {
            suma += reporte.getProductividad();
        }
        return suma / reportes.size();
    }

    private double promedioPuntualidad(List<ReporteDesempeno> reportes) {
        double suma = 0;
        for (ReporteDesempeno reporte : reportes) {
            suma += reporte.getPuntualidad();
        }
        return suma / reportes.size();
    }

    private double promedioCalidad(List<ReporteDesempeno> reportes) {
        double suma = 0;
        for (ReporteDesempeno reporte : reportes) {
            suma += reporte.getCalidad();
        }
        return suma / reportes.size();
    }
}
