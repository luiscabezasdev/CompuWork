package com.mycompany.mavenproject1.modelo;

import com.mycompany.mavenproject1.excepcion.ReporteInvalidoException;
import java.time.LocalDateTime;

public class ReporteDesempeno {
    private final String idReporte;
    private final LocalDateTime fechaGeneracion;
    private final TipoObjetivoReporte tipoObjetivo;
    private final double productividad;
    private final double puntualidad;
    private final double calidad;
    private final double calificacionFinal;
    private final String observaciones;
    private final Empleado empleadoEvaluado;
    private final Departamento departamentoEvaluado;

    public ReporteDesempeno(
            String idReporte,
            TipoObjetivoReporte tipoObjetivo,
            double productividad,
            double puntualidad,
            double calidad,
            String observaciones,
            Empleado empleadoEvaluado,
            Departamento departamentoEvaluado) {
        if (idReporte == null || idReporte.trim().isEmpty()) {
            throw new ReporteInvalidoException("El id del reporte es obligatorio.");
        }
        if (tipoObjetivo == null) {
            throw new ReporteInvalidoException("El tipo de objetivo del reporte es obligatorio.");
        }
        validarMetrica(productividad, "La productividad debe estar entre 0 y 100.");
        validarMetrica(puntualidad, "La puntualidad debe estar entre 0 y 100.");
        validarMetrica(calidad, "La calidad debe estar entre 0 y 100.");
        validarObjetivo(tipoObjetivo, empleadoEvaluado, departamentoEvaluado);

        this.idReporte = idReporte;
        this.fechaGeneracion = LocalDateTime.now();
        this.tipoObjetivo = tipoObjetivo;
        this.productividad = productividad;
        this.puntualidad = puntualidad;
        this.calidad = calidad;
        this.calificacionFinal = (productividad + puntualidad + calidad) / 3.0;
        this.observaciones = observaciones == null ? "" : observaciones.trim();
        this.empleadoEvaluado = empleadoEvaluado;
        this.departamentoEvaluado = departamentoEvaluado;
    }

    private void validarMetrica(double valor, String message) {
        if (valor < 0 || valor > 100) {
            throw new ReporteInvalidoException(message);
        }
    }

    private void validarObjetivo(
            TipoObjetivoReporte tipoObjetivo,
            Empleado empleadoEvaluado,
            Departamento departamentoEvaluado) {
        if (tipoObjetivo == TipoObjetivoReporte.INDIVIDUAL && empleadoEvaluado == null) {
            throw new ReporteInvalidoException("El reporte individual requiere un empleado.");
        }
        if (tipoObjetivo == TipoObjetivoReporte.DEPARTAMENTO && departamentoEvaluado == null) {
            throw new ReporteInvalidoException("El reporte departamental requiere un departamento.");
        }
    }

    public String getIdReporte() {
        return idReporte;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public TipoObjetivoReporte getTipoObjetivo() {
        return tipoObjetivo;
    }

    public double getProductividad() {
        return productividad;
    }

    public double getPuntualidad() {
        return puntualidad;
    }

    public double getCalidad() {
        return calidad;
    }

    public double getCalificacionFinal() {
        return calificacionFinal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Empleado getEmpleadoEvaluado() {
        return empleadoEvaluado;
    }

    public Departamento getDepartamentoEvaluado() {
        return departamentoEvaluado;
    }

    public String getNombreObjetivo() {
        if (tipoObjetivo == TipoObjetivoReporte.INDIVIDUAL) {
            return empleadoEvaluado.getNombre();
        }
        return departamentoEvaluado.getNombre();
    }
}
