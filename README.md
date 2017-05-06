![Logo App](https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/movielife.png?alt=media&token=8a746ce5-4a89-4dcf-a8e7-cfc7a010f0fc "De 300 x 120 píxeles")


**Proyecto Final Android Studio del Módulo de Programacioón de Oscar Llorenc y Carlos Axel Castex**


# DETALLES DEL PROYECTO
------------------------

**APIS utilizadas en la app:**
1. API GMAPS (https://developers.google.com/maps/?hl=es-419) -> Geolocalización de Cines
2. OMDB (www.omdbapi.com) -> API utilizada para la búsqueda de Películas, Series ...ect


# IMPLEMENTACIONES DEL PROYECTO
------------------------------

* Implementaciones Carlos:
    * Link GitLab: https://CAxGu@gitlab.com/CAxGu/MovieLifeCarlos.git
    * Acoplar distintos módulos a la aplicación base 'RicoPaRico':
        * Geolocate GoogleMaps Apidemo
        * CRUD Firebase inicial
        * CRUD SQLITE inicial
        * BookSearch

    * Transformar apariencia de 'RicoPaRico' a 'MovieLife'. Adaptación de la app para que los datos ofrecidos sean de Cines, Series, etc...
    * Implementación de Home con datos en dummys antes de que funcionase desde API con sus respectivos detalles al pinchar en una.
    * Icono y Baner del menú
    * Guardado y borrado Peliculas,Series de favoritos en Firebase
    * Entrada de menú 'Cerca de ti' desde la que puedes ver cines cercanos en dummys
    * Details cines con Navigation, Streetview , guardado en favoritos, vista en googlemaps, enlace con un click web del cine y teléfono.
    * Seetings con Seekbar

* Implementaciones Oscar:

* Implementaciones Conjuntas:

# FUNCIONALIDAD DEL PROYECTO
------------------------------

Nuestro proyecto es una aplicación de cine. Ofrece información sobre películas y series extraída desde la API de OMDB, así cómo de cines próximos a nuestra localización, servidos ya sea en un listado, o geolocalizados en un mapa de Google Maps.
Nuestra aplicación es totalmente configurable por el usuario, y podrá ser utilizada por un usuario administrador, un usuario registrado mediante su email, twitter o google+, o también por un usuario corriente sin ningún tipo de registro.
Todos ellos con sus respectivos privilegios y accesos, que explicaremos en su apartado correspondiente.

Menú
====

El menú principal nos ofrece diferentes opciones desde donde podemos movernos dentro de la aplicación

<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/menu1.jpg?alt=media&token=a52af498-68fb-4376-9cfb-061ae4f99316" height="680px" width="380px">
<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/menu2.jpg?alt=media&token=78c8ecc8-8f3e-4a5d-9c8a-9b4976001fd0" height="680px" width="380px">


Registrarse
====
Desde la entrada de menú 'Registrarse' podemos acceder a nuestra cuenta (si estamos registrados previamente), desde google+, email o twitter.

<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/logining.jpg?alt=media&token=b29170ea-fd98-4d6d-8c2a-e00aeeef7dfb" height="680px" width="380px">


Estrenos
====
Desde la entrada Estrenos (main) , nos mostrará las películas en estreno que hemos agregado nosotros al main cómo administrador (mediante un CRUD)

<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/main.jpg?alt=media&token=e5b3cb2c-5012-47ea-b1af-5e52aa104d8d" height="680px" width="380px">


CRUD (SQLITE)
====
**(explicacion de que podemos hacer dependiendo de que user este logeado)**


Cerca de ti
====


Mediante la entrada de menú 'Sitúate' accederemos a un fragment de GMAps donde estarán pintados los cines disponibles (insertados mediante dummys) geoloalizados y en los cuales mediante un onClickInfo se nos abrirá el details de cada cine.


<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/maps.jpg?alt=media&token=d57fe612-d211-4691-b309-2d0fa80e567b" height="680px" width="380px">
<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/cinesdetails.jpg?alt=media&token=5bf73340-ad48-4c20-9fea-3d8890a450b5" height="680px" width="380px">




Desde estos details del cine podremos acceder a una vista de gmaps pinchando sobre su dirección, abrir la página web del cine seleccionado en nuestro navegador dandole click en la url, establecer una ruta gps hasta dicho cine mediante google navigation haciendo click en "cómo llegar" o, finalmente, realizar una llamada telefónica al mismo mediante un click en su número de teléfono. 


Sitúate
====
Los cines que estarán pintados en la vista de Gmaps a la que accedemos mediante la entrada de menú 'Sitúate', estarán listados dentro de la sección que abriremos al pinchar en la entrada de menú 'Cerca de ti'
Estos cines estarán almacenados cómo dummys en firebase, en una rama sólo accesible por el administrador de la misma.


<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/cinesfavs.jpg?alt=media&token=ba5a7927-f5bb-44b6-8066-8c24bb9acf58" height="680px" width="380px">


Pinchando en uno de ellos, accederemos a los details de los mismos, al igual que cuando pinchamos en el InfoView de google maps.

Buscar
====

Cómo hemos comentado antes, en nuestro Main, tendremos los estrenos presentados por el administrador, sin embargo, si la película que queremos ver no se encuentrase entre ellas, podremos buscarla desde la entrada de menú 'Buscar' o desde el icono de la lupa de nuestro Toolbar, accesible desde cualquier otra vista asociada al toolbar principal.
 > **Por defecto cada página de búsqueda nos mostrará 10 resultados (modificables desde la config.)**


<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/seek.jpg?alt=media&token=3608099d-9ff7-4c87-9bf4-dd96780e9628" height="680px" width="380px">


MIS FAVORITOS
====
**(explicacion de que podemos guardar en favoritos y donde se guarda)**



Settings / Configuración
====


Desde el apartado de configuración, podremos modificar , cómo hemos señalado en el apartado de búsqueda, las páginas que queremos cómo resultado de la misma.
Para ello haremos uso de un seekbar que adoptará valores entre 1 y 100 páginas.


<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/settings.jpg?alt=media&token=de51cb92-4687-4dce-920e-76c8167a47b6" height="680px" width="380px">



Lang
====


Finalmente, el apartado de Lang de nuestro menú principal, que consta de dos idiomas, Español e Inglés, cambiará en su totalidad el idioma de nuestra app para que el usuario pueda utilizarla en su idioma. Cuando seleccionemos el idioma deseado, se relanzará la app en dicho idioma.




<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/lang.jpg?alt=media&token=ffa21179-50d0-4405-86e9-f85dc0f28879" height="680px" width="380px">

