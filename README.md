# Sonar Solidity Plugin

## Descripción

**Sonar Solidity Plugin** es un plugin en desarrollo para SonarQube que permite analizar código fuente escrito en Solidity, el lenguaje de programación utilizado para contratos inteligentes en Ethereum. Este plugin tiene como objetivo identificar problemas de seguridad (como riesgos de reentrancia), optimizaciones de gas (como operaciones matemáticas sin `unchecked`) y problemas de calidad de código (como visibilidad de funciones no especificada) en contratos inteligentes.

El plugin integra reglas personalizadas y planea soportar herramientas externas como Slither para un análisis más completo. Actualmente incluye métricas personalizadas como el número de contratos, funciones, complejidad promedio y estimación de uso de gas.

## Estado Actual

⚠️ **Advertencia**: Este proyecto está en fase de desarrollo y pruebas. Aún no está listo para su uso en producción. Algunas funcionalidades pueden estar incompletas, y el plugin podría no funcionar como se espera en todos los casos. Estamos trabajando activamente para estabilizarlo y mejorar su funcionalidad.

## Contribuciones

¡Cualquier aportación es bienvenida! Si deseas contribuir al desarrollo de este plugin, puedes:

- Reportar errores o sugerir mejoras abriendo un *issue* en este repositorio.
- Enviar *pull requests* con correcciones, nuevas reglas, tests o documentación adicional.
- Probar el plugin en tus propios proyectos y compartir retroalimentación.

Para comenzar, clona el repositorio, revisa el código y sigue las instrucciones de compilación en la sección siguiente.

## Instalación y Uso (Experimental)

1. **Compilación**:
   - Requiere Java 11 y Maven.
   - Ejecuta `mvn clean package -DskipTests` para generar el archivo `.jar` en `target/`.
   - El archivo generado será `sonar-solidity-plugin-2.0.0-SNAPSHOT.jar`.

2. **Instalación en SonarQube**:
   - Copia el `.jar` a la carpeta `extensions/plugins/` de tu instalación de SonarQube.
   - Reinicia SonarQube.

3. **Análisis**:
   - Configura tu proyecto en SonarQube y ejecuta un análisis sobre archivos `.sol`.
   - Las métricas y reglas aparecerán en el dashboard (si funcionan correctamente en esta fase experimental).

**Nota**: Los tests están desactivados actualmente (`-DskipTests`) debido a dependencias pendientes de resolución. Usa bajo tu propio riesgo.

## Descargo de Responsabilidad

Este plugin se proporciona "tal cual", sin garantías de ningún tipo, ya sea explícitas o implícitas, incluyendo, entre otras, las garantías de comerciabilidad, idoneidad para un propósito particular o no infracción. Los autores no serán responsables de ningún daño directo, indirecto, incidental, especial, ejemplar o consecuente (incluyendo, pero no limitado a, la pérdida de datos, beneficios o interrupción del negocio) que surja del uso o la incapacidad de usar este plugin, incluso si se ha advertido de la posibilidad de tales daños.

Si decides usar este plugin en su estado actual, lo haces bajo tu propio riesgo. Recomendamos encarecidamente esperar a una versión estable antes de emplearlo en entornos críticos.

## Licencia

Por definir. Actualmente, este proyecto no tiene una licencia oficial asignada.

## Contacto

Para preguntas o comentarios, abre un *issue* en este repositorio o contacta al equipo de desarrollo a través de GitHub.

---

¡Gracias por tu interés en Sonar Solidity Plugin!

```
sonar-solidity-updated/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── antlr4/
│   │   │   └── org/sonarsource/solidity/Solidity.g4
│   │   └── java/
│   │       └── org/sonarsource/solidity/
│   │           ├── SolidityPlugin.java
│   │           ├── SoliditySensor.java
│   │           ├── SolidityRulesDefinition.java
│   │           ├── SolidityLanguage.java
│   │           ├── SolidityMetrics.java
│   │           └── checks/
│   │               ├── UncheckedMathRule.java
│   │               ├── ReentrancyRule.java
│   │               └── VisibilityRule.java
│   ├── test/
│   │   └── java/
│   │       └── org/sonarsource/solidity/checks/
│   │           ├── UncheckedMathRuleTest.java
│   │           ├── ReentrancyRuleTest.java
│   │           └── VisibilityRuleTest.java
```
