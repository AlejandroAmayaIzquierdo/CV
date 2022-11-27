package dad.Conocimiento;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Conocimiento extends Idioma {
	
	
	private StringProperty denominacion = new SimpleStringProperty();
	private ObjectProperty<Nivel> nivel = new SimpleObjectProperty<Nivel>();
	
	public Conocimiento() {
	}
	
	public Conocimiento(String denominacion,Nivel nivel) {
		setDenominacion(denominacion);
		setNivel(nivel);
	}
	
	public Conocimiento(String denominacion,String nivel) {
		setDenominacion(denominacion);
		setNivel(Nivel.valueOf(nivel));
	}
	
	public Conocimiento(String denominacion,Nivel nivel,String certificacion) {
		super(certificacion);
		setDenominacion(denominacion);
		setNivel(nivel);
	}

	public final StringProperty denominacionProperty() {
		return this.denominacion;
	}
	

	public final String getDenominacion() {
		return this.denominacionProperty().get();
	}
	

	public final void setDenominacion(final String denominacion) {
		this.denominacionProperty().set(denominacion);
	}
	

	public final ObjectProperty<Nivel> nivelProperty() {
		return this.nivel;
	}
	

	public final Nivel getNivel() {
		return this.nivelProperty().get();
	}
	

	public final void setNivel(final Nivel nivel) {
		this.nivelProperty().set(nivel);
	}
	
	
	

}
