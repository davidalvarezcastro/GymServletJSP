# GymServletsJSP

**Autor:** David Álvarez Castro

**Fecha:** 11/11/2021

**Asignatura:** ARQUITECTURA Y SERVICIOS DE INTERNET

---

Dynamic Web Application (Maven) para la gestión de actividades en un gimnasio desarrollado haciendo uso de Servlets y JSP.

El proyecto está formado por los siguientes elementos:

- ***GymServletsJPS:*** proyecto maven formado por los servlets y los jps.
- ***schema.sql:*** script SQL para crear la base de datos y las tablas que la forman así como los datos iniciales de la aplicación (usuario admin de la aplicación).
- ***video.txt:*** link del video (One Drive UBU).

## GymServletsJPS

Se explican brevemente algunos de los ficheros más destacados que forman la aplicación:

### **JSPs**

- ***login.jsp:*** formulario para insertar usuario y contraseña y permitir llamar al servlet de *login*
- ***home.jsp:*** visualización del listado de actividades y posibilidad de editar/eliminar o generar nuevas actividades.
- ***usuarios/registrar.jsp:*** formulario para insertar los nuevos datos de un usuarios.
- ***actividades/gestionar.jsp:*** formulario para crear/editar una actividad. Este jsp hace uso de otro jsp (*actividades/gestionar_ficheros.jsp:*), para permitir organizar un poco el código del formulario de actividades. En este último jsp se muestra el listado de ficheros asociados a la actividad, si se va a modificar, y la posibilidad de añadir nuevos ficheros adjuntos.
- ***css/:*** carpeta formada por algunos archivos css organizados, con los estilos de la aplicación (muy simples).
- ***js/:*** carpeta formada por un archivo javascript con dos funciones para visualizar/ocultar los ficheros de una actividad (véase web), o añadir otro input para subir un nuevo fichero (véase web).

### **JAVA**

En este apartado se describen y listan algunos de los archivos `java` utilizados:

#### **Controladores**

Listado de los controladores empleados en la aplicación.

- ***LoginController/LogoutController:*** servlets encargados de gestionar el login/logout de la aplicación a través del almacenamiento del usuario como un atributo de la sesión.
- ***UserController:*** servlet encargado del registro del nuevo usuario en la base de datos (*UserDAO*) a través de los datos del formulario.
- ***HandleActivityController:*** servlet encargado de gestionar la acción a llevar a cabo sobre una actividad (llamado desde el *home* para crear, editar o eliminar una actividad). A partir de la variable *method* enviada por el formulario se redirige a la vista encargada de crear/editar (*actividades/gestionar.jsp:*), o se lleva a cabo la eliminación de la actividad (*ActivityDAO*).
- ***ActivityController:*** servlet encargado de crear o modificar una actividad a partir de los datos recibidos desde el formulario (*MultipartForm*). Aparte de llamar a *ActivityDAO* para gestionar las actividades, gestionar y procesa los archivos adjuntos del formulario: obtener el directorio, revisar si ya existe un fichero con el mismo nombre (solo se actualiza el título en la base de datos), etc.

#### **DAO**

Clases encargadas de llevar a cabo el acceso a las tablas de la base de datos (servicios de acceso a las tablas).

- ***ActivityDAO:*** funciones de acceso a la tabla de actividades de la base de datos (crear/actualizar actividad, listar actividades, etc).
- ***FileDAO:*** funciones de acceso a la tabla de ficheros de la base de datos (crear fichero, lista de ficheros por actividad, etc).
- ***UserDAO:*** funciones de acceso a la tabla de usuarios de la base de datos (registrar nuevo usuario o verificar credenciales).

#### **Filters**

Se define un filtro de URL basado en la sesión del request para verificar si el usuario se encuentra o no logueado en la aplicación.

#### **Model**

- ***Activity:*** clase java formada por los atributos, getters y setter asociados a la tabla de actividades de la base de datos.
- ***File:*** clase java formada por los atributos, getters y setter asociados a la tabla de ficheros de la base de datos.
- ***User:*** clase java formada por los atributos, getters y setter asociados a la tabla de usuarios de la base de datos.

#### **Utils**

Listado con archivos de utilidades utilizados en la aplicación.

- ***FileSystem:*** funciones para gestionar el acceso al directorio de ficheros: añadir/eliminar fichero, eliminar directorio o comprobar la existencia de un fichero son algunas de las funciones implementadas.
- ***MultipartForm:*** funciones para gestionar la obtención de valores de los formularios (código de ejemplo del aula virtual).

## **Consideraciones**

Para llevar a cabo esta práctica se han tenido en cuenta una serie de consideraciones/decisiones. Se muestran algunas de ellas a continuación:

- se considera que es necesario impedir el acceso a las URLs de la app a usuarios no registrados,
- se permiten registrar nuevos usuarios,
- se permite eliminar una actividad (eso implica eliminar todos los ficheros de la base de datos y del directorio de ficheros),
- se permite subir más de un fichero al mismo tiempo en la vista de *Crear/Editar Actividad*,
- se permiten eliminar ficheros individuales de las actividades (esa funcionalidad se ha diseñado de una manera rápida y sencilla),
- no se ha priorizado el uso de estilos para la obtención de una aplicación final "bonita"

## **Deploy**

1. En primer lugar es necesario contar con una base de datos MySQL corriendo (en mi caso lo hice a través de un contenedor Docker).
2. El segundo paso es disponer de una instancia de Glassfish funcionando en el sistema.
3. El siguiente paso es configurar un servidor web de aplicaciones; se configura un servidor Glassfish siguiendo los pasos descritos en los guiones de prácticas anteriores y crear el pool de conexiones junto con el recurso JDBC (*jdbc/actividades*).
4. Obtención del archivo `.war`. En la raíz del proyecto se ejecuta `mvn clean` para limpiar los ficheros compilados/generados. A continuación se ejecuta `mvn package` para generar el paquete `.war`. En este punto hay dos opciones:
    - acceder a la web de administración de Glassfish y desplegar el `war` desde la ventana de `Applications`, o
    - ejecutar `mvn glassfish:deploy` (plugin en el `pom.xml`) para llevar el despliegue de forma automática.
5. Para finalizar, acceder a <http://localhost:8080/> actividades para entrar en la aplicación de gestión de actividades.

## **Demo**
Se describe brevemente el guión seguido:
- Acceder a una URL sin estar logueado
- Login en la página principal
- Visualizar *home* de la aplicación
  - listado de actividades
  - operaciones sobre la tabla de actividades
  - menú superior de la aplicación
- Registro de un nuevo usuario
- Logout de la aplicación
- Eliminar una actividad
- Crear una actividad con un fichero adjunto
- Editar una actividad añadiendo un nuevo fichero y sobreescribiendo el fichero anterior
- Crear una nueva actividad con una imagen de adjunto

Por falta de tiempo no se ha podido entrar muy en detalle en aspectos visuales o a más bajo nivel de la aplicación (funcionamiento de los diferentes servlets, explicación detallada de por qué se hizo de una manera determinada, ...). Otra de las funcionalidades que no se pudo apreciar en la demo era la opción de eliminar un fichero de una actividad.

Se adjunta una carpeta con *gifs* animados (denominada *gifs.zip*) mostrando algunas de las funcionalidades básicas del sistema, sin ninguna explicación adicional.

En cualquier caso, quedo a total disposición de la profesora para acordar una tutoría y explicar más en detalle ciertos aspectos de la aplicación si así fuese necesario.
