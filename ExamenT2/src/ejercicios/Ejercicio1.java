package ejercicios;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Alumno;
import beans.Nota;
import dao.Dao;

public class Ejercicio1 {

	public static void main(String[] args) {
		Connection conn = null;
		try {

			conn = Dao.getMysqlConnection();
			ArrayList<Alumno> alumnos = null;

			// ha conectado correctamente
			if (conn != null) {
				alumnos = Dao.todosLosAlumnosDescendente(conn);
				for (Alumno alumno : alumnos) {
					ArrayList<Nota> notas = Dao.notasDelAlumno(conn, alumno.getDni());

					System.out.println(alumno.getApenom());
					System.out.println("-----------------------------------------");
					for (Nota nota : notas) {
						System.out.println(nota.getCodigo() + "\t" + nota.getNota());
					}
					System.out.println();
				}
			}
		} finally {
			// al final cerramos la conexión aunque haya habido errores en el programa
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
