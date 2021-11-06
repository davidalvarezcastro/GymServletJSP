package es.ubu.asi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ubu.asi.dao.ActivityDAO;
import es.ubu.asi.dao.FileDAO;
import es.ubu.asi.model.Activity;
import es.ubu.asi.model.File;
import es.ubu.asi.utils.FileSystem;
import es.ubu.asi.utils.MultipartForm;

/**
 * @author david {dac1005@alu.ubu.es}
 *
 * Servlet implementation class AddActivityController
 */
@WebServlet(name = "activity", urlPatterns = {"/activity"})
@MultipartConfig(
        fileSizeThreshold   = 1024 * 1024 * 1,  // 1 MB
        maxFileSize         = 1024 * 1024 * 10, // 10 MB
        maxRequestSize      = 1024 * 1024 * 15 // 15 MB
)
public class ActivityController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivityController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("handleActivity").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long idActivity = 0;
		Activity a;
		String id = null, id_file= null, msg = null, error = null;
		boolean deleteFile = false;

		try {
			// listado con la información de los ficheros
			HashMap<String, Part> files = new HashMap<>();
			// variables del formulario
			String title = null, description = null, suggestions = null, teachers = null, 
				   days = null, timeStart = null, timeEnd = null, dateStart = null,
				   dateEnd = null, fileTitle = null;
			
			// Procesar las partes que forman el formulario
			for (Part part : request.getParts())
			{
				// campos del formulario
				if (part.getName().equals("id")) {
					id = MultipartForm.obtenerValor(part);
				} else if (part.getName().equals("title_file")) {
					fileTitle = MultipartForm.obtenerValor(part);
				} else if (part.getName().equals("file")) {
					files.put(fileTitle, part);
				} else if (part.getName().equals("title")) {
					title = MultipartForm.obtenerValor(part);
				} else if (part.getName().equals("description")) {
					description = MultipartForm.obtenerValor(part);
				} else if (part.getName().equals("suggestions")) {
					suggestions = MultipartForm.obtenerValor(part);
				} else if (part.getName().equals("teachers")) {
					teachers = MultipartForm.obtenerValor(part);
				} else if (part.getName().equals("days")) {
					days = MultipartForm.obtenerValor(part);
				} else if (part.getName().equals("timeStart")) {
					timeStart = MultipartForm.obtenerValor(part);
				} else if (part.getName().equals("timeEnd")) {
					timeEnd = MultipartForm.obtenerValor(part);
				} else if (part.getName().equals("dateStart")) {
					dateStart = MultipartForm.obtenerValor(part);
				} else if (part.getName().equals("dateEnd")) {
					dateEnd = MultipartForm.obtenerValor(part);
				} else if (part.getName().equals("delete_file")) {
					deleteFile = true; // el usuario ha pulsado el botón de eliminar fichero
					id_file = MultipartForm.obtenerValor(part);
				}
			}

			if (deleteFile) {
				// redirigimos al servlet apropiado y terminamos la ejecución
				request.setAttribute("method", "delete");
				request.setAttribute("file", id_file);
	            request.getRequestDispatcher("handleFile").forward(request, response);
	            return;
			}

            String schedule = String.format("%s-%s", timeStart, timeEnd);

            // se utiliza el id de la actividad como referencia para saber si estamos modificando o añadiendo
            if (id == null) {
            	// se crea la actividad en la base de datos y se obtiene su id para almacenar los ficheros
            	a = new Activity(title, description, suggestions, teachers, days, schedule, format.parse(dateStart), format.parse(dateEnd));
                idActivity = ActivityDAO.add(a);
                msg = "Actividad creada correctamente!";
            } else {
            	idActivity = Long.parseLong(id);
            	a = new Activity(idActivity, title, description, suggestions, teachers, days, schedule, format.parse(dateStart), format.parse(dateEnd));
            	// se actualiza la actividad en la base de datos
                ActivityDAO.update(a);
                msg = "Actividad modificada correctamente!";
            }

            // se recorre el haspmap con los datos de los ficheros adjuntos
            for(String auxTitle : files.keySet())
            {
            	Part part = files.get(auxTitle);
				InputStream is = request.getPart(part.getName()).getInputStream();
				String filename = MultipartForm.obtenerNombreFichero(part);
				String path = FileSystem.generatePathFiles(this.getServletContext().getRealPath(""), idActivity);
				boolean updateFile = false; // por defecto, creamos los ficheros en la base de datos

				// en caso de actualizar ficheros, se comprueba si existe el fichero en el directorio (actualizar información)
				if (id != null) {
					updateFile = FileSystem.existsFile(path + "/" + filename);
				}
				
				// en caso de subir el fichero correctamente, lo añadimos a la BD (si ya existía, lo reemplaza)
				if (FileSystem.addFile(path, filename, is)) {
					if (updateFile) { // solo será posible cuando se actualicen actividades
						File fileUpdate = null;
						// si hay algún fallo al actualizar un fichero en la base de datos, no es necesario eliminar toda la actividad, solo ese fichero
						try {
							String newRoute = (path + "/" + filename).replaceAll(this.getServletContext().getRealPath(""), "");
							fileUpdate = FileDAO.getFileByRoute(newRoute);
							fileUpdate.setRoute(newRoute);
							fileUpdate.setTitle(auxTitle);
							FileDAO.update(fileUpdate);
						} catch (Exception e) {
							logger.error(e.getMessage());
							// en caso de error, se elimina el fichero para que se vuelva a subir otra vez
							try {
								if (fileUpdate != null) {
									FileDAO.deleteFile(fileUpdate.getId());
									FileSystem.removeFile(path + "/" + filename);
								}
							} catch (Exception e2) {}

							throw new Exception("Error updating file " + filename, e);
						}
					} else {
						try {
							FileDAO.add(new File(auxTitle, (path + "/" + filename).replaceAll(this.getServletContext().getRealPath(""), ""), idActivity));
						} catch (Exception e) {
							logger.error(e.getMessage());
							// en caso de error, se elimina la actividad de la base de datos y se limpia el directorio de ficheros
							try {
								ActivityDAO.deleteActivity(String.valueOf(idActivity));
								FileSystem.removePath(path);
							} catch (Exception e2) {}

							throw new Exception("Error uploading file " + filename, e);
						}
					}
				}
            }

            // cuando se modifica una actividad, se envían datos adicionales al formulario
            if (id != null) {
            	List<File> filesActivity = FileDAO.getFiles(a.getId());
            	request.setAttribute("activity", a); // se vuelve a mostrar la información de la actividad
            	request.setAttribute("files", filesActivity);
            }
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("actividades/gestionar.jsp").forward(request, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			if (id == null) {
                error = "Error añadiendo la nueva actividad en el sistema";
            } else {
                error = "Error modificando la actividad en el sistema";
            }
			request.setAttribute("error", error);
			request.getRequestDispatcher("actividades/gestionar.jsp").forward(request, response);
		}
	}
}
