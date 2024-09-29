# DWES Ticket Logger Servlet

Este proyecto es una aplicación web basada en **JSP** y **Servlets**, que incluye funcionalidad CRUD (Crear, Leer, Actualizar, Eliminar) sobre una base de datos de **Comunidades Autónomas**. También está preparada para soportar **internacionalización** (i18n), permitiendo a los usuarios cambiar entre varios idiomas (español e inglés) a través de un **selector de idioma**.

---

## Estructura del Proyecto

### 1. Estructura General

- **`src/main/java/org/iesalixar/daw2/nombrealumno/`**: Código fuente Java dividido en:
    - **`dao`**: Acceso a datos.
    - **`entity`**: Clases modelo.
    - **`listeners`**: Listeners para la inicialización de datos.
    - **`servlets`**: Lógica de controladores.

- **`src/main/resources/`**: Contiene archivos de configuración y recursos.
    - **`application.properties`**: Configuración general de la aplicación.
    - **`data.sql`**: Script para inicializar la base de datos.
    - **`messages`**: Archivos de internacionalización (i18n) para diferentes idiomas.

- **`src/main/webapp/WEB-INF/`**: Archivos JSP para la vista.
    - **`footer.jsp`** y **`header.jsp`**: Componentes comunes de la interfaz.
    - **`region.jsp`**: Listado de Comunidades Autónomas.
    - **`region-form.jsp`**: Formulario para crear/editar una Comunidad Autónoma.
    - **`index.jsp`**: Página de inicio.
    - **`user.jsp`**: Gestión de usuarios.

- **Archivos raíz**:
    - **`pom.xml`**: Configuración de Maven.
    - **`.env`**: Variables de entorno.
    - **`docker-compose.yml`**: Configuración para Docker.

---

## Componentes Clave

### 1. DAO (Data Access Object)
- **`DatabaseConnectionManager`**: Clase que maneja las conexiones a la base de datos **MariaDB**.
- **`DataInitializer`**: Inicializa los datos básicos en la aplicación.
- **`RegionDAO` y `RegionDAOImpl`**: Implementan el acceso a datos para la entidad **Region** (Comunidad Autónoma), permitiendo realizar las operaciones CRUD.

### 2. Entidad
- **`Region`**: Representa la entidad Comunidad Autónoma en la base de datos.

### 3. Listeners
- **`AppContextListener`**: Inicializa ciertos parámetros al inicio de la aplicación y realiza tareas cuando la aplicación se detiene.

### 4. Servlets
- **`LanguageServlet`**: Controla el cambio de idioma y guarda el `Locale` en la sesión del usuario.
- **`RegionServlet`**: Implementa la lógica CRUD para las Comunidades Autónomas.
- **`UserServlet`**: Controla la gestión de usuarios.

---

## Funcionalidades

### CRUD de Comunidades Autónomas
- **Crear**: Añadir nuevas Comunidades Autónomas a la base de datos.
- **Leer**: Visualizar una lista de todas las Comunidades Autónomas.
- **Actualizar**: Editar la información de una Comunidad Autónoma.
- **Eliminar**: Eliminar una Comunidad Autónoma seleccionada.

### Selector de Idioma
- Los usuarios pueden cambiar entre **español** e **inglés** mediante un menú desplegable.
- El `LanguageServlet` gestiona el cambio de idioma y aplica el `Locale` correspondiente en la sesión del usuario.

---

## Instalación y Configuración

### Requisitos
- **Java 21** o superior.
- **Jetty 9.3** o superior.
- **Maven 3.9.9**.
- **MariaDB** o **MySQL** para la base de datos.

### Pasos para la instalación

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/tu-repositorio/dwese-ticket-logger-servlet.git
   cd dwese-ticket-logger-servlet
   ```

## Configurar la base de datos

1. **Crea la base de datos** en **MariaDB** o **MySQL**:
   ```sql
   CREATE DATABASE ticket_logger;
   ```

2. **Configura las credenciales de la base de datos** copiando el archivo `env.dist` a un archivo `.env` y actualizando las siguientes variables de entorno:

   ```bash
   DB_URL=jdbc:mariadb://localhost:3306/ticket-logger
   DB_ROOT_PASSWORD=root_password
   DB_DATABASE=ticket-logger
   DB_USER=myuser
   DB_PASSWORD=mypassword
   ``` 

3. **Compilar y desplegar la aplicación**:
    - Para compilar el proyecto con **Maven**, ejecuta el siguiente comando en la raíz del proyecto:

      ```bash
      mvn clean install
      ```

    - Una vez compilado, despliega el archivo `.war`  arrancamos el proyecto con **Jetty** usando el plugin de Maven, ejecuta el siguiente comando en la raíz del proyecto:

      ```bash
      mvn clean jetty:run
      ```

   - Esto ejecutará **Jetty** en modo embebido, y la aplicación estará disponible automáticamente sin necesidad de desplegar manualmente un archivo `.war`. Asegúrate de que todas las configuraciones estén correctamente establecidas en tu `pom.xml`.

4. **Accede a la aplicación**:
    - Una vez que hayas desplegado la aplicación en Jetty, puedes acceder a ella en el navegador utilizando la siguiente URL:

      ```
      http://localhost:8080/
      ```

    - Esta URL accede a un Jetty localmente en el puerto 8080. 

## Internacionalización (i18n)

La aplicación soporta varios idiomas (español e inglés) utilizando **JSTL**. Los archivos de propiedades que contienen las traducciones son:

- **`messages_es.properties`**: Mensajes en español.
- **`messages_en.properties`**: Mensajes en inglés.

La selección de idioma se maneja mediante el `LanguageServlet`, que almacena el `Locale` seleccionado en la sesión del usuario. Las etiquetas de JSTL se usan en los JSP para mostrar los mensajes internacionalizados. Ejemplo:

```jsp
<fmt:setBundle basename="messages" />
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:message key="msg.region-form.add" />
```
## Logs y Manejo de Errores

La aplicación utiliza **SLF4J** para el registro de logs, incluyendo:

- Entradas y salidas de los **servlets**.
- Conexiones a la base de datos.
- Cambios de idioma.
- Redirecciones.

Los errores se gestionan devolviendo códigos de error HTTP correspondientes, y se registran en los logs para depuración. Por ejemplo, si ocurre un error en la conexión a la base de datos o un error al procesar una solicitud HTTP, se generará un log con el nivel adecuado (INFO, ERROR, etc.).

---

## Docker

El proyecto incluye un archivo `docker-compose.yml` para configurar la base de datos y otros servicios necesarios para el despliegue. Esto permite una configuración rápida del entorno de desarrollo o producción usando contenedores Docker.

### Pasos para ejecutar con Docker:

1. Asegúrate de tener **Docker** y **Docker Compose** instalados.
2. Ejecuta el siguiente comando para levantar los servicios:

   ```bash
   docker-compose up
   ```
Esto iniciará todos los contenedores, incluido el de MariaDB. Asegúrate de configurar las credenciales de la base de datos correctamente en el archivo .env si es necesario.

