package ejercicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Alumno;
import beans.Asignatura;
import dao.Dao;

public class Ejercicio4 {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = Dao.getMysqlConnection();

			System.out.println("Escribe el DNI del alumno que deseas modificar:");
			String dni = leerString();
			Alumno alum = Dao.getAlumnoPorDni(conn, dni);
			// si no se introduce un dni o es incorrecto
			if (alum != null) {
				ArrayList<Asignatura> asignaturas = Dao.todasLasAsignaturas(conn);

				System.out.println("Asignaturas disponibles");
				System.out.println("------------------------------------");
				int i = 1;
				for (Asignatura asignatura : asignaturas) {
					System.out.println(i++ + ".- " + asignatura.getNombre() + " (" + asignatura.getAbreviatura() + ")");
				}
				// elegir asignatura
				int elegida = -1;
				do {
					System.out.println("\nEscribe el código de la asignatura a calificar:");
					String str = leerString();
					try {
						elegida = Integer.parseInt(str);
					} catch (NumberFormatException e) {
						System.out.println("Introduzca un número correcto");
					}
				} while (elegida < 0 || elegida > asignaturas.size());
				elegida--; // restar uno para que sea el índice del ArrayList

				// elegir nota
				int nota = -1;
				do {
					System.out.println("Escribe la calificación:");
					String str = leerString();
					try {
						nota = Integer.parseInt(str);
					} catch (NumberFormatException e) {
						System.out.println("Introduzca un número correcto");
					}
				} while (nota < 0 || nota > 10);

				System.out.println(Dao.procedimientoNota(conn, alum.getDni(), asignaturas.get(elegida), nota));

			} else {
				System.out.println("DNI incorrecto");
			}
		} catch (IOException e) {
			e.printStackTrace();
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
