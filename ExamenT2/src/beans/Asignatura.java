package beans;

public class Asignatura {
	int cod;
	String nombre, abreviatura;
	public Asignatura(int cod, String nombre, String abreviatura) {
		super();
		this.cod = cod;
		this.nombre = nombre;
		this.abreviatura = abreviatura;
	}
	public Asignatura() {
		super();
	}
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
}
