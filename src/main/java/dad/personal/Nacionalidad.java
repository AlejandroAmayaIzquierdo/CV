package dad.personal;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Nacionalidad {
	
	private StringProperty denominacion = new SimpleStringProperty();
	
	public Nacionalidad(String text) {
		setDenominacion(text);
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
	
	@Override
	public String toString() {
		return getDenominacion();
	}
	
	@Override
	public boolean equals(Object obj) {
		Nacionalidad a = (Nacionalidad) obj;
		if(a != null) {
			return this.getDenominacion() == a.getDenominacion();			
		}
		return super.equals(obj);
	}
	

}
