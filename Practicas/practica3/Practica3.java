/**
 * Implementacion de un algoritmo de busqueda en grafos
 */

package practica3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Practica3 {
	
	public static ArrayList<Publicacion> publicaciones = new ArrayList<Publicacion>();
	public static ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	
	
	public static void main(String [] args) {
		Scanner in= new Scanner(System.in);
		Scanner in2 = new Scanner(System.in);
		//Nombre del fichero de entrada, cambiar la ruta
		String nombre = ("C://Users/Jorge/Desktop/entrada5.txt");
		File archivo = new File (nombre);
		LeerFichero(archivo);
		
		int eleccion=0;
		do {
			System.out.println("Escriba el numero de una pregunta:");
			System.out.println("1- Número de lecturas de una publicación");
			System.out.println("2- Publicaciones más leídas");
			System.out.println("3- Impacto de una publicación");
			System.out.println("4- Publicaciones que han impactado en todos los usuarios");
			eleccion = in.nextInt();
			if(eleccion != 1 && eleccion != 2 && eleccion != 3 && eleccion != 4)
				System.out.println("Pregunta erronea, repita por favor");
		}while(eleccion != 1 && eleccion != 2 && eleccion != 3 && eleccion != 4); 
		
		switch(eleccion) {
			case(1):
				System.out.println("Indique el numero de la publicacion:");
				String s=in2.nextLine();
				System.out.println("La publicacion #P"+s+" fue leida: "+numLecturas(s)+" veces");
				escribirFichero("Número de lecturas de la publicación #P"+s, "\n"+numLecturas(s));
				break;
			case(2):
				System.out.println("Las publicaciones mas leidas son:");
				ArrayList<Publicacion> pubs = pubMasLeidas();
				String s2 ="";
				for(int i=0; i<pubs.size(); i++) {
					System.out.println("#P"+pubs.get(i).nombre);
					s2+="#P"+pubs.get(i).nombre+",";
				}
				if(s2.length()>0)
					s2=s2.substring(0,s2.length()-1);
				else
					s2="Ninguna";
				escribirFichero("Las publicaciones más leídas son:\n", s2);
				break;
			case(3):
				System.out.println("Indique el numero de la publicacion:");
				s=in2.nextLine();
				System.out.println("La publicacion #P"+s+" tuvo un impacto de: "+impactoDe(s));
				escribirFichero("Impacto de la publicación #P"+s, "\n"+impactoDe(s));
				break;
			case(4):
				System.out.println("Las publicaciones que han impactado a todos son: ");
				ArrayList<Publicacion> publis = impactaATodos();
				s2 = "";
				for(int i=0; i<publis.size(); i++) {
					System.out.println("#P"+publis.get(i).nombre);
					s2+="#P"+publis.get(i).nombre+",";
				}
				if(s2.length()>0)
					s2=s2.substring(0,s2.length()-1);
				else
					s2="Ninguna";
				escribirFichero("Publicaciones que han impactado en todos los usuarios:\n", s2);
				break;
		}
		in.close();
		in2.close();
	}
		
	/**
	 * Busca que publicaciones han impactado a todos
	 * @return publicaciones que han impactado a todos
	 */
	public static ArrayList<Publicacion> impactaATodos(){
		ArrayList<Publicacion> aux = new ArrayList<Publicacion>();
		int cont = 0;
		for(int i=0; i<publicaciones.size(); i++) {
			cont = impactoDe(publicaciones.get(i).nombre);
			if(cont == usuarios.size()) {
				aux.add(publicaciones.get(i));
			}
		}
		return aux;
	}
	
	/**
	 * Calcula el numero de veces que se ha leido una publicacion
	 * @param nombre nombre de la publicacion
	 * @return cantidad de veces que ha sido leida
	 */
	public static int numLecturas(String nombre) {
		int cantidad=0;
		for(int i=0; i<publicaciones.size(); i++) {
			if(publicaciones.get(i).nombre.equals(nombre)) {
				cantidad = publicaciones.get(i).leidoPor.size();
				break;
			}
		}
		return cantidad;
	}
		
	/**
	 * Busca las publicaciones mas leidas
	 * @return publicaciones mas leidas
	 */
	public static ArrayList<Publicacion> pubMasLeidas() {
		ArrayList<Publicacion> aux = new ArrayList<Publicacion>();
		int[] veces = new int [publicaciones.size()];
		int mayor=0;
		for(int i=0; i< publicaciones.size(); i++) {
			veces[i]=publicaciones.get(i).leidoPor.size();
			if(veces[i]>mayor)
				mayor=veces[i];
		}
		for(int j=0; j<veces.length; j++)
			if(mayor==veces[j])
				aux.add(publicaciones.get(j));
		return aux;
	}
	
	/**
	 * Calcula el impacto que ha causado una publicacion
	 * @param nombre nonbre de la publicacion
	 * @return impacto de la publicacion
	 */
	public static int impactoDe(String nombre) {
		Publicacion p=null;
		for(int i=0; i<publicaciones.size(); i++) {
			if(publicaciones.get(i).nombre.equals(nombre)) {
				p = publicaciones.get(i);
			}
		}
		return calculaImpacto(p).size()+1; //Se suma uno por el autor
	}
	
	/**
	 * Busca los usuarios a los que ha llegado una publicacion de forma directa o indirecta
	 * @param p publicacion
	 * @return usuarios a los que impacto
	 */
	public static ArrayList<Usuario> calculaImpacto(Publicacion p) {
		ArrayList<Usuario> us = new ArrayList<Usuario>();
		if(p.citadoPor.size()!=0) {
			for(int i=0; i<p.citadoPor.size(); i++) {
				ArrayList<Usuario> aux = calculaImpacto(p.citadoPor.get(i));
				for(int j=0; j<aux.size(); j++)
					if(repetido(us, aux.get(j))==false)
						us.add(aux.get(j));
			}
			for(int k=0; k<p.leidoPor.size(); k++) {
				if(repetido(us, p.leidoPor.get(k))==false)
					us.add(p.leidoPor.get(k));
			}
			return us;
		}else {
			return p.leidoPor;
		}
	}
	
	/**
	 * Busca si hay usuarios repetidos 
	 * @param us usuarios totales
	 * @param u usuario a comparar
	 * @return si esta repetido o no
	 */
	public static boolean repetido(ArrayList<Usuario> us, Usuario u) {
		boolean b = false;
		for(int i=0; i<us.size(); i++) {
			if(us.get(i).nombre.equals(u.nombre)) {
				b = true;
				break;
			}
		}
		return b;
	}
 
	/**
	 * Transforma el fichero de entrada en el grafo
	 * @param archivo fichero de entrada
	 */
	public static void LeerFichero(File archivo){
		FileReader fr = null;
		BufferedReader br = null;
		ArrayList<String> lineas = new ArrayList<String>();
		int cont=0;
		try {
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			String linea;
	        while((linea=br.readLine())!=null) {
	            lineas.add(linea);
			}
		    fr.close();
			br.close();
			
			int j;
			int n;
			for(int i=0; i<lineas.size(); i++) {
				if(lineas.get(i).startsWith("#User")) {
					for(j=0; j<lineas.get(i).length(); j++){
						if(lineas.get(i).charAt(j)==32 || lineas.get(i).charAt(j)==9) {
							break;
						}else {
							cont++;
						}
					}
					String nombre = lineas.get(i).substring(0,cont);
					cont=0;
					Usuario u = new Usuario(nombre);
					boolean coincide=false;
					for(int k=0; k<usuarios.size(); k++) {
						if(usuarios.get(k).nombre.equals(u.nombre)) {
							coincide=true;
						}
					}
					if(coincide==false) {
						usuarios.add(u);
					}
					
					String accion="";
					for(n=j; n<lineas.get(i).length(); n++) {
						if(lineas.get(i).charAt(n)==101) {
							accion="escribe";
							break;
						}
						if(lineas.get(i).charAt(n)==108) {
							accion=("lee");
							break;
						}
					}
					String nombre2="";
					for(int p=n; p<lineas.get(i).length(); p++) {
						if(lineas.get(i).charAt(p)==80) {
							nombre2=lineas.get(i).substring(p+1,lineas.get(i).length());
						}
					}
					coincide=false;
					for(int m=0; m<publicaciones.size(); m++) {
						if(publicaciones.get(m).nombre.equals(nombre2)) {
							if(accion.equals("escribe")) {
								publicaciones.get(m).setAutor(u);
							}else {
								publicaciones.get(m).addLeidoPor(u);
							}
							coincide=true;
						}
					}
					if(coincide==false) {
						Publicacion pub = new Publicacion(nombre2);
						if(accion.equals("escribe")) {
							pub.setAutor(u);
						}else {
							pub.addLeidoPor(u);
						}
						
						publicaciones.add(pub);
					}
				}
				if(lineas.get(i).startsWith("#P")) {
					String linea2 = lineas.get(i);
					String citada = linea2.substring(linea2.length()-1, linea2.length());
					String cita = linea2.substring(2,3);
					for(int q=0; q<publicaciones.size(); q++) {
						if(publicaciones.get(q).nombre.equals(citada)) {
							for(int r=0; r<publicaciones.size(); r++) {
								if(publicaciones.get(r).nombre.equals(cita)) {
									publicaciones.get(q).setCita(publicaciones.get(r));
								}
							}
						}
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escribe las respuestas en un fichero externo
	 * @param s1 pregunta
	 * @param s2 respuesta
	 */
	public static void escribirFichero(String s1, String s2) {
		String name = "salida_p3_jorgsan_davcuna";
		PrintWriter pw;   
		try {   
		    pw= new PrintWriter(new FileWriter(name, true)); 
		    pw.println(s1+s2); 
		    pw.close();
		} catch (IOException e) {   
		       
		} 
	}
}