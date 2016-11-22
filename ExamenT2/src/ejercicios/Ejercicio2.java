package ejercicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import beans.Alumno;
import dao.Dao;

public class Ejercicio2 {

	public static void main(String[] args) {
		Connection conn = null;
		try {

			conn = Dao.getMysqlConnection();

			String dni;
			try {
				// leer los datos
				System.out.println("Escribe el DNI del alumno que deseas modificar:");
				dni = leerString();
				Alumno alum = Dao.getAlumnoPorDni(conn, dni);
				// si no se introduce un dni o es incorrecto
				if (alum != null) {
					System.out.println(alum.getApenom());
					System.out.println("Escribe el nuevo nombre para el alumno:");
					String nuevoNombre = "";
					nuevoNombre = leerString();
					// si ha introducido algún valor
					if (!nuevoNombre.equals("") && nuevoNombre != null) {
						alum.setApenom(nuevoNombre);
						if (Dao.cambiarNombreAlumno(conn, alum)) {
							System.out.println("Alumno modificado correctamente");
						} else {
							System.out.println("No se ha modificado el nombre del alumno");
						}
					} else {
						System.out.println("No se ha modificado el nombre del alumno");
					}
				} else {
					System.out.println("DNI incorrecto");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {
			// al final cerramos la conexión aunque haya habido errores en el programa
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Fin del programa");
	}

	private static String leerString() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String str = String.valueOf(in.readLine());
		return str;
	}

}
