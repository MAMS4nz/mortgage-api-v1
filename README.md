
- Empezar con un breve resumen de: arquitectura y tecnologías empleadas.
- Perfiles de Spring Boot para aplicar las configuraciones por entorno. Variable "SPRING_PROFILES_ACTIVE".
- Asumimos que la intención sería, a medio plazo, emplear una base de datos MS SQL SERVER, dado que la previsión es que la aplicación siga creciendo y evolucionando. Para ello trabajaremos con H2 en modo de compatibilidad con MS SQL SERVER, para simplificar al máximo la migración llegado el momento.
- Al trabajar con H2 en modo de compatibilidad con MS SQL SERVER carecemos de un tipo de datos en la BD que nos permita almacenar información de zona horaria. Por otro lado, por simplicidad no emplearemos ninguna columna para especificar la zona horario de los instantes almacenados, sino que operaremos implícitamente en UTC en toda la aplicación y todos los valores temporales estarán implícitamente referidos a este estándar.
- Inicializaremos y alimentaremos la base de datos H2 con scripts situados en la carpeta "resources". No emplearemos la autogeneración de tablas de Hibernate.
- Respecto a la licencia del proyecto, lo típico para un servicio como éste, en caso de no ser de acceso libre, sería una licencia de tipo SaaS, por suscripción, pero careciendo de más datos nos hemos inclinado por una licencia Apache License 2.0 que dentro de las licencias de código abierto quizás sea de las más empleadas en proyectos comerciales.
- Tipos de interés: como es habitual en los préstamos hipotecarios consideramos que a mayor plazo de devolución habrá más riesgo para la entidad financiera y, por tanto, ésta aplicará un tipo de interés más alto. Inversamente, a plazos más cortos menor incertidumbre y tipos más bajos para el comprador.
Por simplicidad consideraremos que este será el único factor que influirá en el tipo de interés a aplicar al préstamo. La relación directa entre duración y tipo de interés queda plasmada implícitamente en la tabla 'mortgage_rates', en la que la columna 'maturity_period_months' está sometida a una restricción de clave única.
Por otro lado, siguiendo la práctica usual consideramos también que el plazo mínimo de devolución será de diez años, con cuarenta años como plazo máximo admisible en ciertos casos (jóvenes, hipotecas con aval, etc.)
- Explicar modelo de ramas empleado: GitHub Flow
- Explicar cambios en las URLes respecto al enunciado del ejercicio.
- Explicar que, idealmente, querríamos exponer los "beans" de gestión y monitorización en tiempo real, vía JMX, de HikariCP. El acceso por parte de los clientes debería estar securizado mediante credenciales, intercambio de certificados y restricciones de puerto, red y cortafuegos. En un ejercicio como éste, la sobrecarga de configurar esta infraestructura carece de sentido.
- Explicar Swagger: http://localhost:8080/swagger-ui/index.html & http://localhost:8080/v3/api-docs
- Explicar Spring Security
- Explicar Actuator y su configuración de seguridad.