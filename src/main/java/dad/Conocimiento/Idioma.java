package dad.Conocimiento;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Idioma {
	
	private StringProperty certificacion = new SimpleStringProperty();
	
	public Idioma() {
	}
	public Idioma(String certificacion) {
		setCertificacion(certificacion);
	}

	public final StringProperty certificacionProperty() {
		return this.certificacion;
	}
	

	public final String getCertificacion() {
		return this.certificacionProperty().get();
	}
	

	public final void setCertificacion(final String certificacion) {
		this.certificacionProperty().set(certificacion);
	}
	
	
	

}
