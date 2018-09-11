package practica3;

import java.util.ArrayList;

public class Publicacion {
	String nombre;
	ArrayList<Usuario> leidoPor;
	Usuario escritoPor;
	ArrayList<Publicacion> citadoPor;
	
	public Publicacion(String nombre) {
		leidoPor = new ArrayList<Usuario>();
		citadoPor = new ArrayList<Publicacion>();
		this.nombre=nombre;
	}
	
	public void addLeidoPor(Usuario u) {
		leidoPor.add(u);
	}
	
	public void setAutor(Usuario u) {
		escritoPor=u;
	}
	
	public void setCita(Publicacion p) {
		citadoPor.add(p);
	}
	
	public String toString() {
		String s = "";
		s = "Publicacion "+nombre+" escrita por: "+escritoPor.nombre+" leida por";
		for(int i=0; i<leidoPor.size(); i++) {
			s+=" "+leidoPor.get(i).nombre;
		}
		if(leidoPor.size()==0)
			s+=" nadie";
		s+=" citada por";
		for(int i=0; i<citadoPor.size(); i++) {
			s+=" "+citadoPor.get(i).nombre;
		}
		if(citadoPor.size()==0)
			s+=" nadie";
		return s;
	}
}
