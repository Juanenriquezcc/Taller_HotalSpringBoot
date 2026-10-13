# Taller de pruebas unitarias — HotelSpringBoot

## Propósito del proyecto

Este repositorio contiene una copia organizada del proyecto universitario
`HotelSpringBoot`, junto con cinco pruebas unitarias nuevas para evaluar parte
del dominio y del servicio de reservas.

## Descripción del sistema

El proyecto original implementa un sistema de reservas hoteleras con clientes,
habitaciones, rangos de fechas y reservas. Está construido como una aplicación
Spring Boot con una arquitectura que separa dominio, servicios, repositorios,
mapeadores y controladores.

## Objetivo del taller

El taller busca aplicar pruebas unitarias con JUnit 5 y Mockito, combinando:

- Pruebas directas sobre objetos de dominio.
- Pruebas aisladas de un servicio mediante dependencias simuladas (*mocks*).

## Tecnologías utilizadas

Las tecnologías se contrastaron con `pom.xml`:

- Java 17.
- Spring Boot 3.3.4.
- Maven y Maven Wrapper.
- JUnit 5, incluido mediante `spring-boot-starter-test`.
- Mockito, incluido mediante `spring-boot-starter-test`.
- Spring Data JPA.
- PostgreSQL como base de datos de ejecución de la aplicación.

## Pruebas implementadas

### Pruebas normales

Archivo: `src/test/java/com/hotel/Hotel/domain/HotelDomainTest.java`

1. **Creación de un cliente válido**
   - Construye un `Cliente` con datos válidos.
   - Comprueba que tenga un identificador.
   - Comprueba que esté activo inicialmente.
   - Comprueba que tenga cero penalizaciones.

2. **Rechazo de nombres inválidos**
   - Comprueba que un nombre vacío lance `IllegalArgumentException`.
   - Comprueba que un nombre formado únicamente por espacios lance
     `IllegalArgumentException`.

3. **Tres penalizaciones**
   - Registra tres penalizaciones sobre un cliente.
   - Comprueba que el contador sea `3`.
   - Comprueba que el cliente quede inactivo.
   - Comprueba que no pueda realizar reservas.

### Pruebas con Mockito

Archivo: `src/test/java/com/hotel/Hotel/service/ReservaServiceUnitTest.java`

4. **Consulta de una reserva existente**
   - Simula `ReservaRepository.findById(UUID)` para devolver una reserva.
   - Simula `ReservaMapper.toResponse(Reserva)` para devolver la respuesta
     esperada.
   - Comprueba que `ReservaService.obtenerPorId(UUID)` devuelva esa respuesta.
   - Verifica la consulta al repositorio y el uso del mapper.

5. **Confirmación de una reserva inexistente**
   - Configura `ReservaRepository.findById(UUID)` para devolver
     `Optional.empty()`.
   - Comprueba que `ReservaService.confirmar(UUID)` lance
     `IllegalArgumentException`.
   - Verifica que no se guarde ninguna reserva y que no se invoque el mapper.

## Diferencia entre los tipos de pruebas

Las tres pruebas de dominio son pruebas normales: crean objetos reales y
comprueban directamente su estado y comportamiento, sin Spring, base de datos
ni dependencias simuladas.

Las dos pruebas del servicio utilizan Mockito para aislar `ReservaService`.
`ReservaRepository`, `ClienteRepository`, `HabitacionRepository` y
`ReservaMapper` se reemplazan por objetos simulados. De esta forma se prueba la
lógica del servicio y sus interacciones sin conectarse a PostgreSQL.

## Estructura relevante

```text
HotelSpringBoot/
├── .mvn/
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/hotel/Hotel/
│   │   │   ├── controller/
│   │   │   ├── domain/
│   │   │   ├── mapper/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   └── resources/
│   └── test/
│       └── java/com/hotel/Hotel/
│           ├── domain/
│           │   ├── HotelDomainTest.java
│           │   └── ReservaTest.java
│           └── service/
│               ├── ReservaServiceTest.java
│               └── ReservaServiceUnitTest.java
└── README.md
```

## Requisitos

- JDK 17.
- PowerShell en Windows.
- Acceso a Internet la primera vez que se descarguen las dependencias de
  Maven.

Las pruebas unitarias nuevas no requieren una conexión real a PostgreSQL.
Para ejecutar la aplicación completa sí debe estar disponible una instancia
local de PostgreSQL configurada de acuerdo con el entorno local del proyecto.

## Ejecutar las pruebas en Windows PowerShell

Desde la carpeta que contiene `pom.xml`, ejecutar las cinco pruebas nuevas:

```powershell
.\mvnw.cmd "-Dtest=HotelDomainTest,ReservaServiceUnitTest" test
```

Para ejecutar las pruebas nuevas junto con las dos clases de pruebas existentes:

```powershell
.\mvnw.cmd "-Dtest=HotelDomainTest,ReservaServiceUnitTest,ReservaTest,ReservaServiceTest" test
```

## Resultados verificados

Se verificó la primera ejecución con el siguiente resultado:

```text
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

También se verificó la ejecución conjunta con las pruebas existentes:

```text
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Estos resultados corresponden únicamente a las clases indicadas en los
comandos anteriores.

La suite completa del proyecto incluye una prueba de carga de contexto
(`HotelApplicationTests`) que anteriormente falló al no poder configurar la
conexión JPA/PostgreSQL. Por ese motivo, este README no afirma que la suite
completa pase; los resultados documentados se limitan a las ejecuciones
seleccionadas y verificadas.

## Abrir y ejecutar el proyecto

1. Clonar o descargar el repositorio.
2. Abrir la carpeta raíz en IntelliJ IDEA, Eclipse o Visual Studio Code.
3. Verificar que el proyecto utilice un JDK 17.
4. Para ejecutar las pruebas, usar los comandos de Maven Wrapper de la sección
   anterior.
5. Para ejecutar la aplicación Spring Boot, configurar primero una instancia
   local de PostgreSQL y los valores de conexión del entorno local. Después,
   ejecutar:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

## Conclusión

El proyecto contiene cinco pruebas unitarias nuevas: tres pruebas directas de
dominio y dos pruebas aisladas con Mockito para `ReservaService`. Las
ejecuciones seleccionadas fueron verificadas satisfactoriamente sin modificar
las clases de producción ni requerir una conexión real a PostgreSQL.
