package beans;

public class Nota {
	private String codigo;
	private int nota;
	
	public Nota(String codigo, int nota) {
		super();
		this.codigo = codigo;
		this.nota = nota;
	}
	public Nota() {
		super();
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public double getNota() {
		return nota;
	}
	public void setNota(int nota) {
		this.nota = nota;
	}
	
	
}
