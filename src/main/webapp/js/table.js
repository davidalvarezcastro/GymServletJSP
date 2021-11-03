/**
 * @author david {dac1005@alu.ubu.es}
 */
const HIDE_ELEMENT_CLASS = 'hide-element';

/**
 * Función para gestionar la visualización del listado de ficheros de una actividad
 *
 * @param actividad 		identificador de la actividad
 * @returns
 */
function gestionar_visualizar_ficheros (actividad) {
	let ficheros_dom = document.getElementById(`ficheros-actividad-${actividad}`)

	if (ficheros_dom.className.includes(HIDE_ELEMENT_CLASS)) {
		ficheros_dom.classList.remove(HIDE_ELEMENT_CLASS)
	} else {
		ficheros_dom.classList.add(HIDE_ELEMENT_CLASS)
	}
}
