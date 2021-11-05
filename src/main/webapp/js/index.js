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

/**
 * Función para duplicar un grupo de DOM elements (inputs ficheros)
 *
 * @returns
 */
function anhadir_fichero () {
	let ficheros_dom = document.getElementById('new_files')
	let domElement = document.getElementsByClassName('add-file')[0]
	
	let clon = domElement.cloneNode("add-file")
	let inputs = clon.getElementsByTagName('input')
	for (var i=0, max=inputs.length; i < max; i++) {
		inputs[i].value = "";
	}

	ficheros_dom.appendChild(clon);
}

