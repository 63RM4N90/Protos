-------- packages -------------------


---attachment---
paquete creado con la idea de manejar los attachments que son algo que se le adjunta al canal de cada socket con la info
del request/response, la jerarquia que plantie yo fue una clase attachment de la cual extienden requestattachment y
response attachment. Hable con el agulo y me dijo que el solo uso un attachment que hacia las veces de ambos, me dijo
que lo que plantie yo se puede hacer pero que es mas jodido de programar porque tengo que saber cuando es un request
y cuando un response.

--handler--
paquete creado con la idea de manejar la interaccion de los sockets, tiene un TCPRequesthandler y un TCPresponsehandler
que se encargarian de hacer el accept/read/write de cada request/response. ambos implementan la interfaz TCPProtocol que 
los obliga a implementar los handlers de lo antes mencionado. tambien hay un enum state que es lo que usarian los handlers
para poder recorrer el request/response y delegar las funciones en las clases request y response del paquete model.
creo que esto tambien podia generarnos problemas me habia dicho el agulo. habria que preguntarle bien porque no tenia
el codigo para mostrarle.

--model--
este paquete tiene las clases request y response que ambas heredan de HttpPacket y la idea es que estas guarden los
elementos del request/response y se les consulte a estas por ciertas cosas, tambien esta la calase data para pasar cachos
de bytes.

--proxy--
 tiene la calase proxyserver que es el main de la aplicacion, aca se plantea por parametro en que puero escucha nuestro
 proxy y instancia un request handler ya que lo que el user manda son requests entonces habria que handelear requests.
 
 ------- ciclo de vida del programa en la cabeza de gabo ----------------------
 
 primero se prende el proxy, el user manda un reques, el requesthandler hace un handle read donde crearia un request attachment
 y llama al state para que recorra sus estados y genere un request. dentro del state en cada estado se llamaria a un metodo
 del request que tiene el attachment para que parsee lo que acaba de identificar el state y si esta todo piola te lo guarde en
 el request de ese attachment. una vez que tenemos el request bien parseado habria que analizarlo ver que cosas se agregan y que
 cosas se omiten y finalemente mandarselo al server... (el resto no lo pense)
 
 ------ que estaria copado que traten de hacer -------------
 hablar con el agulo para checkear lo que me dijo a mi de las complicaciones y ver que les parece mejor a ustedes, si pueden
 fixeen el enum State para que recorra cada state y llame al parser de request/response segun corresponda y una vez que se forma
 bien el request imprimanlo para ver que esa parte anda bien.
 
 ------ si estan muy manijas ------
 
 si quieren seguir avanzando traten de ver la parte del analizador del request para ver que le mandamos al server y traten de
 armar una coneccion con este usando el header host
 
 ------- IMPORTANTE! cosa que se me acaba de ocurrir escribiendo esto ---------------
 lo que se deberia guardar en el attachment no es un req o un response sino una variable de su superclase (HttpPacket) y cuando se instancia
 instaciarlo con su subclase request/response y que en el state se agarre la variable packet y haga packet.parsegilada y cada
 subclase sabe parsearse.
 
 cualquier duda me llaman ;)
