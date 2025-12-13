# Lab Reservas API (Spring Boot)

Proyecto de ejemplo para la **Segunda parte** del avance: API REST en Java (Spring Boot) con PostgreSQL, Swagger/OpenAPI, envío de correo y generación de PDF.

## ¿Qué incluye?
- Entidades JPA: Rol, Usuario, Laboratorio, Equipo, Reserva.
- Repositorios Spring Data JPA.
- Servicios y controladores con endpoints CRUD.
- Ejemplo de envío de correo cuando se crea una reserva.
- Endpoint que genera un PDF (Apache PDFBox) con la información de la reserva.
- Configuración para conexión a PostgreSQL (Supabase u otra).
- Swagger UI (springdoc-openapi) disponible en `/swagger-ui/index.html`.

## Cómo usar (desarrollo local con Docker Compose)
1. Copia `.env.example` a `.env` y edita las variables.
2. Levanta Postgres local:
   ```bash
   docker compose up -d
   ```
3. Compila y corre:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## Conexión a Supabase / instancia remota
- Si usas Supabase: desde el dashboard copia la URL de conexión (JDBC) y las credenciales y pégalas en `src/main/resources/application.yml` (se provee plantilla).

## Deploy
- Puedes desplegar en Render, Railway o Heroku (si aún disponible). Configura variables de entorno para la conexión a la DB y credenciales SMTP.

