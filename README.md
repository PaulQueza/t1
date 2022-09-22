## Datos importantes:
    Solo se puede agregar una letra por cada estado, por ejemplo un mal ejemplo de entrada:
    ab.c , El programa no lo correra de forma adecuada, debido a que solo se permite una letra/simbolo por cada transicion
    No editar, mover o eliminar los archivos TXT.
## Problemas:
    Complejidad al ordenar el txt para que se vea decentes
## Parches: 
    Hasta ahora no hay ningun parche
## Ejemplos de usos:
    Agregar una expresion regular ejemplo --> a.b, --> (a.b)|(c.d*), etc. (por ahora solo se admiten estas entradas, en un futuro cercano lo haremos con TXT's)
## Como funciona: 
    Package AFD: Transforma de afnd a afd
    Package Main: Se ejecutan todo de manera secuencial ER->AFND->AFD
    Package AFND: Tendra 3 clases:
        -datos: Muestra todos los datos del AFND creado.
        -elavorar_transiciones: Crea el automata, segun el ER ejecutara las transiciones correspondiestes.
        -ERtoAFND: Es la clase encargada de ejecutar todo y mostrar los datos en orden.
    Package Archivos: Seran los archivos en donde se guardaran los automatas.
    Package V1: Se guardan todos los archivos que puedan servir pero que son obsoletos.

## Logros a completar:
    No funciona en su completitud el desglosar el ER, hay problemas con el parseo(Lamentable).
    Funciona la transicion '.'
    Funciona la transicion '|'
    Funciona la transicion '*'
    Funciona la creacion de las tablas (Epsilon, de transiciones y la tabla final)
    Funciona AFND->AFD
## Link para estudio:
	 (AFND->AFD) https://www.youtube.com/watch?v=km-yS9BUDFA&ab_channel=MauricioQuirogaLipe
	 [PDF AUTOMATAS](ProcedimientoCompleto.pdf)
## DEBUG: 
    Usualmente muere con los parentesis, por lo demas funciona de maravilla.
    Un buen ejemplo para comprobar es usar (a.b)|(c.d*) entre sus distintas combinaciones
