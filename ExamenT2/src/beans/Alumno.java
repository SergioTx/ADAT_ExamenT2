package beans;

public class Alumno {
	String dni, apenom, pobla, telef;

	public Alumno(String dni, String apenom, String pobla, String telef) {
		super();
		this.dni = dni;
		this.apenom = apenom;
		this.pobla = pobla;
		this.telef = telef;
	}

	public Alumno() {
		super();
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getApenom() {
		return apenom;
	}

	public void setApenom(String apenom) {
		this.apenom = apenom;
	}

	public String getPobla() {
		return pobla;
	}

	public void setPobla(String pobla) {
		this.pobla = pobla;
	}

	public String getTelef() {
		return telef;
	}

	public void setTelef(String telef) {
		this.telef = telef;
	}
	
	
}
