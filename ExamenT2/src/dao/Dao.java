package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import beans.Alumno;
import beans.Nota;
import beans.Asignatura;

public class Dao {

	public static Connection getMysqlConnection() {

		Connection conn = null;

		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("ex2");
		dataSource.setPassword("examen2");
		dataSource.setServerName("172.20.105.120");
		dataSource.setDatabaseName("examen2");

		try {
			conn = (Connection) dataSource.getConnection();
		} catch (SQLException e) {
			System.err.println("ERROR - al conectar a la base de datos MySQL");
		}
		return conn;
	}

	public static ArrayList<Alumno> todosLosAlumnosDescendente(Connection conn) {
		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();

		String sql = "SELECT dni, apenom, pobla, telef FROM alumnos ORDER BY apenom DESC";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Alumno a = new Alumno(rs.getString("dni"), rs.getString("apenom"), rs.getString("pobla"), rs.getString("telef"));
				alumnos.add(a);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return alumnos;
	}

	// El arrayList es una lista ORDENADA
	public static ArrayList<Nota> notasDelAlumno(Connection conn, String dni) {
		ArrayList<Nota> notas = new ArrayList<Nota>();

		String sql = "SELECT abreviatura, nota FROM notas, asignaturas WHERE notas.cod = asignaturas.cod AND dni = ?";

		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dni);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Nota n = new Nota(rs.getString("abreviatura"), rs.getInt("nota"));
				notas.add(n);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.err.println("ERROR - al hacer la consulta sql: " + sql);
			e.printStackTrace();
		}
		return notas;
	}

	public static Alumno getAlumnoPorDni(Connection conn, String dni) {
		Alumno a = null;
		PreparedStatement pstmt = null;

		String sql = "SELECT dni, apenom, pobla, telef FROM alumnos WHERE dni = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dni);

			ResultSet rs = pstmt.executeQuery();
			// if porque es la clave primaria y sólo recoge uno
			if (rs.next()) {
				a = new Alumno(rs.getString("dni"), rs.getString("apenom"), rs.getString("pobla"), rs.getString("telef"));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.err.println("ERROR - al hacer la consulta sql: " + sql);
			e.printStackTrace();
		}
		return a;
	}

	public static boolean cambiarNombreAlumno(Connection conn, Alumno alum) {
		// recibe el alumno con el nombre ya cambiado
		PreparedStatement pstmt = null;

		String sql = "UPDATE alumnos SET apenom = ? WHERE dni = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, alum.getApenom());
			pstmt.setString(2, alum.getDni());

			int num = pstmt.executeUpdate();
			// si no se ha modificado ninguno
			if (num == 0)
				return false;
			pstmt.close();
		} catch (SQLException e) {
			System.err.println("ERROR - al hacer la consulta sql: " + sql);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static ArrayList<Asignatura> todasLasAsignaturas(Connection conn) {
		ArrayList<Asignatura> asignaturas = new ArrayList<Asignatura>();

		String sql = "SELECT cod,nombre,abreviatura FROM asignaturas";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Asignatura a = new Asignatura(rs.getInt("cod"), rs.getString("nombre"), rs.getString("abreviatura"));
				asignaturas.add(a);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return asignaturas;
	}

	public static boolean existeNotaAsignatura(Connection conn, String dni, Asignatura asignatura) {
		boolean existe = false;
		PreparedStatement pstmt = null;

		// saco el código porque es clave, pero cualquier campo vale, con que exista uno devuelve true
		String sql = "SELECT cod FROM notas where dni = ? AND cod = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dni);
			pstmt.setInt(2, asignatura.getCod());

			ResultSet rs = pstmt.executeQuery();
			// si no se ha modificado ninguno
			if (rs.next()) {
				existe = true;
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.err.println("ERROR - al hacer la consulta sql: " + sql);
			e.printStackTrace();
		}
		return existe;
	}

	public static boolean actualizarNota(Connection conn, String dni, Asignatura asignatura, int nota) {
		PreparedStatement pstmt = null;
		boolean actualizada = false;

		String sql = "UPDATE notas SET nota = ? WHERE cod = ? AND dni = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, nota);
			pstmt.setInt(2, asignatura.getCod());
			pstmt.setString(3, dni);

			int num = pstmt.executeUpdate();
			if (num != 0) {
				actualizada = true;
			}
			pstmt.close();
		} catch (SQLException e) {
			System.err.println("ERROR - al hacer la consulta sql: " + sql);
			e.printStackTrace();
		}
		return actualizada;
	}

	public static boolean insertarNuevaNota(Connection conn, String dni, Asignatura asignatura, int nota) {
		boolean insertada = false;
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO notas (dni,cod,nota) VALUES (?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dni);
			pstmt.setInt(2, asignatura.getCod());
			pstmt.setInt(3, nota);

			int num = pstmt.executeUpdate();
			if (num != 0) {
				insertada = true;
			}
			pstmt.close();
		} catch (SQLException e) {
			System.err.println("ERROR - al hacer la consulta sql: " + sql);
			e.printStackTrace();
		}
		return insertada;
	}

	public static String procedimientoNota(Connection conn, String dni, Asignatura asignatura, int nota) {
		String str = "";

		String sql = "{? = call insertar_nota(?, ?, ?)}";
		try {
			CallableStatement cs = conn.prepareCall(sql);
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.setInt(2, asignatura.getCod());
			cs.setString(3, dni);
			cs.setInt(4, nota);

			cs.executeUpdate(); // ejecutar el procedimiento

			str = cs.getString(1);

			cs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return str;
	}
}
