# 1. Repositorio.

 ## a.	Flujo de trabajo

Para este apartado se decido trabajar con el flujo GitFlow para mantener un flujo de trabajo organizado y estructurado en el proyecto, obteniendo el siguiente resultado.

![image](https://github.com/s-rojash/202210_BaseProject/assets/98681387/b0589801-f213-4fe0-93e2-a1f21ef35028)

![image](https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX/assets/98681387/d7ce9b88-d6a8-4657-9f25-801e9899466f)

## b.	Esquema del repositorio

Dentro de las carpetas src/main/java/com/prueba se encuentra la distribución de los componentes de la API, dividiendo el mismo en capas como buena práctica, teniendo los siguientes paquetes:

i.	controller: Paquete de los controladores que cuenta con los endpoint.

ii.	service: Paquete de los servicios donde se encuentra las funcionalidades y lógica.

iii.	model: Paquete de los modelos de representación de los datos.

iv.	respository: Paquete de repositorio el cual establece la comunicación con la base de datos.

v.	security: Paquete de seguridad que contiene la configuración de Authentication y Authoritation.

![image](https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX/assets/98681387/9eda9895-4afe-4070-bc79-1fcc48afb145)

Para los recursos de pruebas unitarias se podrá encontrar desde la siguiente ruta src/test/java/com/prueba allí se listarán las pruebas por cada controller existente, en nuestro caso 3 archivos.

![image](https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX/assets/98681387/330f3fc8-a91b-4af5-af97-d6a2b5e48599)

## c.	Actions GitHub - CI

En este apartado se resalta la implementación de un Action, con el fin de definir un flujo de trabajo dentro del repositorio, la implementación valida la ejecución de las pruebas unitarias y su coverage teniendo como mínimo el 80% de está, de lo contraria la ejecución fallará, esto es aplicado a la integración del codigo a la rama main.

![image](https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX/assets/98681387/48808025-1f7b-46f8-8f75-5633da84bbc3)

![image](https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX/assets/98681387/72812566-1098-48d9-9531-24b7bea5b858)

# 2.	Codigo

## a.	Repositorio

Según restricciones de la prueba se utiliza GitHub para el control de versiones de la prueba con el siguiente enlace:

https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX 

## b.	Endpoints

Se hace la implementación de endpoint según requerimiento solicitado, adicional se implementa un endpoint similar para la creación de productos, ya que en las restricciones del ejercicio se solicita 100.000 productos nuevos y teniendo presente el tiempo de respuesta prologados se implementa el mismo endpont similar, pero recibiendo un parámetro por url el cual nos indicara el número de productos a crear. 
Ejemplo: se desea crear 10 productos, la petición se realizaría de la siguiente manera {{server}}:{{port}}/Product/10, si desea realizar las 100.000 peticiones bastara con quitar el parámetro {{server}}/Product/.

Pare el tema de la paginación del entpoint de listar productos esta se maneja desde la url con la siguiente expresión ?page=0&size=10&sort=productId,desc.

Estas pruebas se pueden realizar con el collertor generado y que se mencionará más adelante en el documento.

i.	Crear categoría - POST {{server}}/Category/

ii.	Listar categoría - GET {{server}}/Categories/id

iii.	Crear producto - POST {{server}}/Product/ o {{server}}/Product/quantity

iv.	Listar producto - GET {{server}}/Products/?page=0&size=10&sort=productId,desc

v.	Buscar producto por ID - GET {{server}}/Products/id

## c.	Base de datos

Para este caso se utiliza como base de datos H2 que es una base de datos en memoria, esto para facilitar el despliegue en la nube.

## d.	Seguridad

Para este apartado se tomó la decisión de implementar Authentication y Authoritation, por lo anterior se crea un paquete con dicha configuración y adicional se implantan dos endpoints adicionales, los cuales son necesarios para ejecución.

i.	Crear usuario - POST {{server}}/User/

ii.	Login - POST {{server}}/Login/

Los endponts de crear usuario y login son los únicos que se encuentran expuestos teniendo como punto de entrada de la API, para consumir los demás servicios mencionados en el punto b Endpoints se requiere que el cuerpo de la petición se agregue el token (JWT) generado teniendo como cuerpo Authorization Bearer Token.

## e.	Postman collection

En este apartado entregamos la documentación de los endpoints con ejemplo de ejecución y sus excepciones los cuales podrán acceder desde el siguiente enlace:

https://documenter.getpostman.com/view/17084692/2s93m32NqW 

Adicional, se comparte collection de Postman para facilitar las pruebas de los entpoints, esta se puede encontrar en la raíz del repositorio del proyecto.

## f.	Pruebas unitarias

Para las pruebas unitarias se implementaron para los endpoints con Junit, la ejecución de pruebas se puede realizar ubicándose en la raíz del proyecto y ejecutando el siguiente comando. mvn test.

![image](https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX/assets/98681387/dd3ba2a9-23c0-4e06-96eb-7fdb5fec53e8)

![image](https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX/assets/98681387/f720aded-41b6-4815-98f7-68d6459637a3)

![image](https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX/assets/98681387/6351c6df-5bf5-496f-b142-478886c6e55e)

## g.	Coverage

En este aparatado se menciona la herramienta utilizada para el análisis de coverage del codigo jacoco, y que una ves ejecutado el comando mvn test podrá acceder al reporte que se muestra a continuación. Esta mimas es utilizada para evaluar el coverage en el Action de GitHub.

![image](https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX/assets/98681387/ad842598-3bf0-4729-aa14-34fa790752f4)

## h.	Evaluación de codigo

En este apartado se resalta el uso de la herramienta SonarQube para validar la calidad del código, en el tema del coverage este incluye a la configuración de seguridad por lo que el porcentaje es menor.

Además, se encuentra tres vulnerabilidades en cuento a la seguridad y esto se debe que en la configuración se guarda el secret del token y por buenas practicas este debe ir como variable de entorno.

Otro se debe a que se inactiva el CSRF, esto por cuestiones de configuración para facilitar esta implementación.

![image](https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX/assets/98681387/321d72b5-0766-48a9-8cf3-01f7bbb399e9)

![image](https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX/assets/98681387/e6d1bd01-24ff-4e44-8c82-5819a3e8a718)

# 3.	Ejecución

## a.	Ejecutar el .jar

Para ejecutar el jar usted debe posicionarse en la siguiente ruta /Prueba-Tecnica-INTCOMEX/target y ejecutar el comando java -jar Prueba-Tecnica-INTCOMEX-0.0.1-SNAPSHOT, para esto el equipo debe contar con el JDK 19 o el OpenJDK.

Si desea compilar nuevamente el jar usted se debe posicionarse en la raíz del proyecto y ejecutar el comando mvn install y este se alojará en la ruta mencionada anteriormente /Prueba-Tecnica-INTCOMEX/target
	
Puerto configurado es el 3000, por lo que la ruta sería la siguiente http:/localhost:3000/.

## b.	Ejecutar desde Docker

Para desplegar el contenedor usted debe posicionarse en la raíz del proyecto y ejecutar el comando docker-compose up, para esto el equipo debe contar con Docker.

Si desea recrear el jar usted se debe posicionarse en la raíz del proyecto y ejecutar el comando mvn install y este se alojará en la ruta mencionada anteriormente /Prueba-Tecnica-INTCOMEX/target y luego ejecutar el paso anterior para crear el contenedor.

Puerto configurado es el 3000, por lo que la ruta sería la siguiente http:/localhost:3000/.

# 4.	Arquitectura

La infraestructura utilizada es Azure, ya que actualmente las cuentas de Google Cloud y AWS los encuentro en uso por temas de la maestría, se utilizaron el siguiente elemento y configuración para su despliegue.

Se usa App Service con plan de precios F1 Gratis con 1 CPU y 1G RAM infraestructura compartida, este se configura con java 17 y el despliegue se hace a través de GitHub. Este plan gratis tiene restricciones, por lo que solo se puede usar 60 minutos por día.
 
![image](https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX/assets/98681387/cc448c1b-0907-4df2-aa10-d961a942d99d)

![image](https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX/assets/98681387/f27b7e31-cd2b-4407-8298-9fa8f0115308)

![image](https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX/assets/98681387/50118bbf-c9dc-4b95-bc3d-afccac2b828c)

![image](https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX/assets/98681387/81b7ee63-111f-48e8-9b65-2f57300d6b5c)

![image](https://github.com/s-rojash/Prueba-Tecnica-INTCOMEX/assets/98681387/e11843b7-7369-439d-972e-49c2a382a003)

.