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
    * Link Gitlab: https://oscll@gitlab.com/oscll/MovieLife.git
    * Implementacion search API(OMDB)
    * Autentificacion con firebase , twitter y google
    * CRUD Firebase Estrenos
    * Guardar las busquedas en SQLite

* Implementaciones Conjuntas:
    * Union favoritos

# FUNCIONALIDAD DEL PROYECTO
------------------------------

Nuestro proyecto es una aplicación de cine. Ofrece información sobre películas y series extraída desde la API de OMDB, así cómo de cines próximos a nuestra localización, servidos ya sea en un listado, o geolocalizados en un mapa de Google Maps.
Nuestra aplicación es totalmente configurable por el usuario, y podrá ser utilizada por un usuario administrador, un usuario registrado mediante su email, twitter o google+, o también por un usuario corriente sin ningún tipo de registro.
Todos ellos con sus respectivos privilegios y accesos, que explicaremos en su apartado correspondiente.

Menú
====

El menú principal nos ofrece diferentes opciones desde donde podemos movernos dentro de la aplicación

<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/menu12.jpg?alt=media&token=3105c042-2785-4551-9403-51b2b5c6f12a" height="680px" width="380px">
<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/menu22.jpg?alt=media&token=45f50f5d-a70e-49c7-99d7-f7b4f8343126" height="680px" width="380px">


Registrarse
====
Desde la entrada de menú 'Registrarse' podemos acceder a nuestra cuenta (si estamos registrados previamente), desde google+, email o twitter.

<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/logining.jpg?alt=media&token=b29170ea-fd98-4d6d-8c2a-e00aeeef7dfb" height="680px" width="380px">


Estrenos
====
Desde la entrada Estrenos (main) , nos mostrará las películas en estreno que hemos agregado nosotros al main cómo administrador (mediante un CRUD)

<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/main.jpg?alt=media&token=e5b3cb2c-5012-47ea-b1af-5e52aa104d8d" height="680px" width="380px">


CRUD (Firebase)
====
Aqui el unico que tiene acceso a este crud es la cuenta de movielife loggeado a traves de google . Una vez loggeado puede crear , eliminar y modificar los estrenos . Aqui se verfican algunos campos como pueden ser el imdbID , web url , img url ... 

>CREATE. Pulsando el floatting button amarillo podemos crear una entrada desde 0
</BR>

<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/crudcreate.jpg?alt=media&token=c3e18726-1327-4e6b-9122-2733af9c6c4d" height="680px" width="380px">
<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/crudcreate2.jpg?alt=media&token=a9152b7c-4788-4f75-8b52-fea069958743" height="680px" width="380px">
<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/crudcreate3.jpg?alt=media&token=c5a88538-0f44-466a-8590-93147d9ab10b" height="680px" width="380px">

>UPDATE. Clickando sobre cualquier item de la lista del crud, podremos editar su contenido.

<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/crudupdate1.jpg?alt=media&token=4460a5d7-b454-4e4d-ba4c-0789d491b879" height="680px" width="380px">
<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/crudupdate2.jpg?alt=media&token=62afe344-5811-4d21-8bdf-e3663743c2de" height="680px" width="380px">


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
En favoritos podemos guardar peliculas de la api , estrenos y cines que se subiran a firebase y se podran observar en diferentes "tabs"(peliculas , series y cines) 

>Todos ellos se podrán borrar con un LongClick sobre el item deseado

<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/misfavspelis.jpg?alt=media&token=5dfde8c2-abf6-4dbf-acb5-4d1948ac2978" height="680px" width="380px">
<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/misfavsseries.jpg?alt=media&token=b49d3d43-356b-432b-b5e8-e4a5de8a5a56" height="680px" width="380px">
<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/misfavcines.jpg?alt=media&token=4f554ed1-abab-4107-b2cf-c4b0890a2ff2" height="680px" width="380px">

Settings / Configuración
====


Desde el apartado de configuración, podremos modificar , cómo hemos señalado en el apartado de búsqueda, las páginas que queremos cómo resultado de la misma.
Para ello haremos uso de un seekbar que adoptará valores entre 1 y 100 páginas.


<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/settings.jpg?alt=media&token=de51cb92-4687-4dce-920e-76c8167a47b6" height="680px" width="380px">

Mas Info.
=========

En esta entrada de menú se abrirá una ventana emergente donde dará información extra sobre nuestro proyecto. En este caso, nuestros nombres, para que módulo hemos programado la app y de qué centro somos.


<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/about.jpg?alt=media&token=f04bcddf-0af4-4785-bdb8-105a22624b3c" height="680px" width="380px">


Lang
====


Finalmente, el apartado de Lang de nuestro menú principal, que consta de dos idiomas, Español e Inglés, cambiará en su totalidad el idioma de nuestra app para que el usuario pueda utilizarla en su idioma. Cuando seleccionemos el idioma deseado, se relanzará la app en dicho idioma.




<img src="https://firebasestorage.googleapis.com/v0/b/cineapp-b4786.appspot.com/o/menulang.jpg?alt=media&token=d83de7fb-593b-4c4f-987c-ac67a3c718d3" height="680px" width="380px">

