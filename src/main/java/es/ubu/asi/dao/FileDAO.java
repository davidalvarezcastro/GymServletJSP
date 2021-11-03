/**
 * 
 */
package es.ubu.asi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import es.ubu.asi.database.Database;
import es.ubu.asi.model.File;

/**
 * @author david {dac1005@alu.ubu.es}
 */
public class FileDAO {
	private static Database db;
	
	static {
		db = Database.getInstance();
	}

	// methods
	/**
	 * Permite insertar un fichero en la base de datos
	 *
	 * @param e	datos del fichero
	 * @throws Exception
	 */
	public static void add(File f) throws Exception {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn = db.getConnection();
			String query = "INSERT INTO ficheros"
					+ "(titulo, ruta, idActividad) VALUES"
					+ "(?,?,?)";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, f.getTitle());
			preparedStatement.setString(2, f.getRoute());
			preparedStatement.setLong(3, f.getActivity());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			try {
				conn.rollback();
			} catch (SQLException ex2) {
				throw new Exception("Rooling back query error", ex2);
			}
			throw new Exception("General SQL error", ex);
		} finally {
			close(preparedStatement);
			close(conn);
		}
	}

	/**
	 * Permite conectarse a la db y obtener el listado de ficheros almacenados
	 * para una actividad concreta
	 *
	 * @param actividad asociada a los ficheros
	 * @return listado de los ficheros
	 * @throws Exception
	 */
	public static List<File> getFiles(long actividad) throws Exception {
		String query = "SELECT * FROM ficheros"
				+ " WHERE idActividad = ?";
		List<File> list = new ArrayList<File>();
		
		try (Connection conn = db.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
			preparedStatement.setLong(1, actividad);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("titulo");
                String route = resultSet.getString("ruta");
                list.add(new File(id, title, route, actividad));
            }
        
            return list;
        } catch (SQLException e) {
        	throw new Exception("General SQL error", e);
        } catch (Exception e) {
        	throw new Exception(e);
        }
	}

	// handle connections
	private static void close(Statement st) throws Exception {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException ex) {
				throw new Exception("", ex);
			}
		}
	}

	private static void close(Connection con) throws Exception {
		if (con != null) {
			try {
				db.close(con);
			} catch (SQLException ex) {
				throw new Exception("", ex);
			}
		}
	}
}
