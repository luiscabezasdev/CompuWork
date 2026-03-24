# CompuWork

CompuWork es un MVP academico en Java + Swing para gestionar empleados, departamentos y reportes de desempeno. El proyecto aplica encapsulamiento, herencia, polimorfismo, manejo de excepciones, pruebas unitarias y una separacion simple por capas.

## Alcance implementado

- CRUD basico de empleados permanentes y temporales.
- CRUD basico de departamentos.
- Asignacion y desasignacion de empleados a departamentos.
- Generacion de reportes individuales y departamentales.
- Persistencia en memoria durante la ejecucion.
- Interfaz de escritorio en Swing.
- Pruebas unitarias con JUnit 5.

## Arquitectura

- **Dominio**: `Empleado`, `EmpleadoPermanente`, `EmpleadoTemporal`, `Departamento`, `ReporteDesempeno` y enums de apoyo.
- **Servicios**: `EmpleadoService`, `DepartamentoService`, `ReporteDesempenoService`.
- **UI**: `MainFrame` y paneles de empleados, departamentos y reportes.
- **Excepciones**: validaciones y errores de negocio manejados con excepciones personalizadas.

## Decisiones de diseno

- `Empleado` es abstracta y resuelve el pago por polimorfismo mediante `calcularPago()`.
- `Departamento` mantiene la relacion bidireccional con `Empleado`.
- `ReporteDesempeno` usa metricas `productividad`, `puntualidad` y `calidad` en rango de 0 a 100.
- El reporte departamental se calcula con el promedio de los ultimos reportes individuales disponibles por empleado del departamento.
- Los requerimientos empresariales amplios como seguridad avanzada, 24/7, compatibilidad web/movil y persistencia real quedan documentados como evolucion futura.

## Estructura principal

- `src/main/java/com/mycompany/mavenproject1/modelo`
- `src/main/java/com/mycompany/mavenproject1/servicio`
- `src/main/java/com/mycompany/mavenproject1/excepcion`
- `src/main/java/ui`
- `src/test/java/com/mycompany/mavenproject1/servicio`
- `docs/uml-clases.md`

## Ejecucion

```bash
mvn clean package
mvn exec:java
```

Al iniciar la app puedes elegir consola o interfaz grafica.

## Pruebas

```bash
mvn test
```

Las pruebas cubren:

- calculo polimorfico de pago,
- validacion de cedulas duplicadas,
- asignacion de departamentos,
- restriccion de eliminacion de departamentos con empleados,
- generacion de reportes individuales y departamentales.

## Documentacion UML

El diagrama de clases se encuentra en `docs/uml-clases.md` usando Mermaid para que puedas abrirlo en GitHub o adaptarlo a una imagen si la entrega lo exige.

## Requerimientos no funcionales tratados como futura evolucion

- **Seguridad**: autenticacion, autorizacion y cifrado no se implementan en este MVP.
- **Escalabilidad**: actualmente la persistencia es en memoria; una siguiente fase deberia incorporar repositorios y base de datos.
- **Disponibilidad**: al ser una app desktop local, no aplica un objetivo real de 24/7.
- **Compatibilidad web/movil**: se mantiene Swing para cumplir el MVP academico con el menor riesgo.
