# Examen Final: Automatización de Pruebas (CI/CD con GitHub Actions)

Este repositorio contiene la solución completa a las Actividades 1, 2 y 3 del examen final de Automatización de Pruebas. El objetivo principal es implementar un pipeline robusto de Integración Continua (CI) y Despliegue Continuo (CD) usando Maven y GitHub Actions, incluyendo una estrategia de pruebas por niveles y un mecanismo de Rollback.

---

## 1. Estrategia de Flujo de Ramas (Actividad 1)

Se implementó la estrategia de **Trunk-Based Development (TBD)**.

* **Flujo:** Se utiliza una única rama principal (`main`) como fuente de verdad (el *Trunk*).
* **Desarrollo:** Todas las *features* se desarrollan en ramas de muy corta duración que se fusionan a `main` varias veces al día a través de Pull Requests.
* **Justificación:** TBD es el flujo más simple y moderno, ideal para la Integración Continua y la alta frecuencia de despliegues, minimizando los conflictos de fusión.

---

## 2. Configuración y Estructura de Pruebas (Actividades 1 & 2)

El proyecto está configurado con **Maven** para manejar las dependencias y el ciclo de vida del build. Se implementó una **Estrategia de Pruebas por Niveles** (Test Pyramid) para asegurar la calidad del código en cada etapa del pipeline.

### Dependencias Clave (`pom.xml`):

* **JUnit 5 (Jupiter):** Framework principal para escribir pruebas.
* **Selenium:** Biblioteca utilizada para la automatización de pruebas a nivel de integración/aceptación.
* **Maven Surefire Plugin (v3.2.5):** Ejecuta las pruebas unitarias.
* **Maven Failsafe Plugin (v3.2.5):** Ejecuta las pruebas de integración y aceptación.

### Niveles de Pruebas Implementados:

| Nivel | Herramienta | Stage del Pipeline | Objetivo |
| :--- | :--- | :--- | :--- |
| **Unitarias** | JUnit 5 | `test_unit` | Verificar la lógica de componentes individuales (ej. `CalculadoraTest.java`). |
| **Integración** | JUnit 5 | `test_integration` | Verificar la interacción de módulos (ej. `IntegracionWebTest.java`). |
| **Aceptación** | JUnit 5 / Selenium | `acceptance_tests` | Verificar que la solución cumpla con los requisitos del negocio (usa el profile **`acceptance`** en Maven). |

---

## 3. Pipeline de CI/CD (Actividades 2 & 3)

El pipeline de entrega continua está definido en el archivo `.github/workflows/ci-maven.yml` y se ejecuta automáticamente en cada *push* a la rama `main`.

### Etapas del Pipeline

El pipeline consta de 6 *jobs* encadenados que cubren CI y CD:

1.  **`build`:** Compila el proyecto Maven.
2.  **`test_unit`:** Ejecuta Pruebas Unitarias.
3.  **`test_integration`:** Ejecuta Pruebas de Integración.
4.  **`acceptance_tests`:** Ejecuta Pruebas de Aceptación (usando el profile `-Pacceptance`).
5.  **`deploy_staging`:** Simula el despliegue en un ambiente de pruebas. **(Fallo intencional para demostración)**
6.  **`rollback_deployment`:** Mecanismo de emergencia que se activa solo si el *job* anterior (`deploy_staging`) falla.

### Evidencia de Rollback (Mecanismo de CD Avanzado)

Se implementó un mecanismo de **Rollback** en el job `rollback_deployment`, que utiliza la condición `if: always() && failure()` de GitHub Actions para garantizar que se ejecute si la etapa de despliegue (`deploy_staging`) falla.

En la ejecución final, se utilizó el comando `exit 1` en la etapa de despliegue para forzar el fallo y demostrar el correcto funcionamiento del Rollback.

* [**Insertar aquí la Captura de Pantalla** que muestre la secuencia de la columna de Jobs: `deploy_staging` en rojo (Fallo) y `rollback_deployment` en verde (Éxito)]. 

---

## 4. Ejecución de Pruebas

Para ejecutar las pruebas y el pipeline:

1.  **Ejecución Local:** Se utiliza el comando Maven estándar.
    * Pruebas Unitarias: `mvn test`
    * Pruebas de Integración/Aceptación: `mvn verify`
2.  **Ejecución Automática (Pipeline):** Al hacer *push* a la rama `main`, el pipeline de **GitHub Actions** se dispara automáticamente, ejecutando todas las etapas secuencialmente.