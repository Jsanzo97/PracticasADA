/**
 * Implementacion del problema de la busqueda de subcadenas comunes a dos cadenas dadas
 * Algoritmo de programacion dinámica
 */

package practica2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Practica2 {
		
	public static void main(String[] args) {
		ArrayList<Character> cad1 = new ArrayList<Character>();
		ArrayList<Character> cad2 = new ArrayList<Character>();
		ArrayList<Character> fin = new ArrayList<Character>();
		ArrayList<Character> inicio = new ArrayList<Character>();
		ArrayList<ArrayList<Character>> medio = new ArrayList<ArrayList<Character>>();
		
		File archivo = new File ("entrada.txt");
		LeerFichero(archivo,cad1,cad2);
		
		System.out.print("Cadena1: ");
		for(int i=0; i<cad1.size(); i++)
			System.out.print(cad1.get(i));
		System.out.print("\nCadena2: ");
		for(int i=0; i<cad2.size(); i++)
			System.out.print(cad2.get(i));
		System.out.println();
		
		if(cad1.equals(cad2))
			System.out.println("Las cadenas son iguales");
		else{
			//Buscamos las coincidencias en el inicio de las subcadenas
			IgualesInicio(cad1,cad2,inicio);
			
			//Buscamos las coincidencias en el final de las subcadenas
			IgualesFinal(cad1,cad2,fin);
			
			//Imprimimos los elementos iguales al inicio y al final
			System.out.println();
			System.out.print("Elementos iguales al inicio: ");
			for(int i=0; i<inicio.size(); i++)
				System.out.print(inicio.get(i));
			System.out.print("\nElementos iguales al final: ");
			for(int j=fin.size()-1; j>=0; j--)
				System.out.print(fin.get(j));
			System.out.println();
		
			char[][] b = new char[cad1.size()+1][cad2.size()+1];
			int[][] c = new int[cad1.size()+1][cad2.size()+1];
			
			//Creamos la tabla
			TablaSubsecuencias(cad1,cad2,b,c);
			
			System.out.println();
			System.out.println("La matriz de coincidencias es: ");
			System.out.println("(Quitando comunes al principio y final)");
			ImprimirTabla(cad2,cad1,c,b);
			
			//Buscamos todas las soluciones
			BuscaTodas(cad1,cad1.size(),cad2.size(),b,c,medio);
			
			//Imprimimos los resultados obtenidos de la matriz de subsecuencias
			System.out.println();
			System.out.println("Soluciones de la matriz:");
			for(int i=0; i<medio.size(); i++) {
				for(int j=0; j<medio.get(i).size(); j++) {
					System.out.print(medio.get(i).get(j));
				}
				System.out.println();
			}
			
			//Escribimos las subcadenas completas formadas por los elementos comundes del inicio
			//los obtenidos de la matriz y los elementos comunes del final
			System.out.println();
			if(medio.size()!=0) {
				System.out.println("Las subsecuencias son:");
				for(int i=0; i<medio.size(); i++) {
					System.out.print((i+1)+"- ");
					for(int j=0; j<inicio.size(); j++) {
						System.out.print(inicio.get(j));
					}
					for(int k=0; k<medio.get(i).size(); k++) {
						System.out.print(medio.get(i).get(k));
					}
					for(int l=fin.size()-1; l>=0; l--) {
						System.out.print(fin.get(l));
					}
				System.out.println();
				}
			}else {
				if(inicio.size()==0 && fin.size()==0) {
					System.out.println("No hay subsecuencias comunes.");
				}else {
					System.out.println("La subsecuencia es:");
					for(int j=0; j<inicio.size(); j++) {
						System.out.print(inicio.get(j));
					}
					for(int l=fin.size()-1; l>=0; l--) {
						System.out.print(fin.get(l));
					}
				}
			}
			
			//Guardamos los datos en el fichero
			archivo = new File("salida_p2_davcuna_jorgsan.txt");
			EscribirFichero(archivo, inicio, medio, fin);
		}
	}
	
	/**
	 * Busqueda de elementos comunes al final de las cadenas
	 * @param cad1 primera cadena dada
	 * @param cad2 segunda cadena dada
	 * @param fin elementos coincidentes 
	 */
	public static void IgualesFinal(ArrayList<Character> cad1, ArrayList<Character> cad2, ArrayList<Character> fin) {
		boolean coincide=true;
		int index1=cad1.size()-1;
		int index2=cad2.size()-1;
		while(coincide==true) {
			if(index1<0 || index2<0)
				coincide=false;
			else {
				if(cad1.get(index1)==cad2.get(index2)) {
					fin.add(cad2.get(index2));
					cad1.remove(index1);
					cad2.remove(index2);
					index1--;
					index2--;
				}else
					coincide=false;
			}
		}
	}
	
	/**
	 * Busqueda de elementos comunes al principio de las cadenas
	 * @param cad1 primera cadena dada
	 * @param cad2 segunda cadena dada
	 * @param inicio elementos coincidentes
	 */
	public static void IgualesInicio(ArrayList<Character> cad1, ArrayList<Character> cad2, ArrayList<Character> inicio) {
		boolean coincide=true;
		int index1=0;
		int index2=0;
		while(coincide==true && (cad2.size()!=0 && cad1.size()!=0)) {
			if(cad1.get(index1)==cad2.get(index2)) {
				inicio.add(cad2.get(index2));
				cad1.remove(index1);
				cad2.remove(index2);
			}else
				coincide=false;
		}
		
	}
	
	/**
	 * Impresion de la matriz de soluciones parciales
	 * @param y primera cadena dada
	 * @param x segunda cadena dada
	 * @param c longitud de la solucion parcial mas larga
	 * @param b caracter de control para recuperar la solucion
	 */
	public static void ImprimirTabla(ArrayList<Character> y, ArrayList<Character> x, int[][]c, char[][]b) {
		System.out.print("   Yj");
		for(int i=0; i<y.size(); i++)
			System.out.print("   "+y.get(i));
		
		System.out.println();
		for(int i=0; i<c.length; i++) {
			if(i>0)
				System.out.print(x.get(i-1)+"  ");
			else
				System.out.print("Xi ");
			for(int j=0; j<c[i].length; j++) {
				System.out.print(b[i][j]);
				System.out.print(c[i][j]);
				System.out.print("  ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Creacion de la matriz de soluciones parciales
	 * @param x primera cadena dada
	 * @param y segunda cadena dada
	 * @param c longitud de la solucion parcial mas larga
	 * @param b caracter de control para recuperar la solucion
	 */
	public static void TablaSubsecuencias(ArrayList<Character> x, ArrayList<Character> y, char[][]b, int[][]c){
	   
		//Primera fila y primera columna con ceros
	    for(int i=0; i<x.size(); i++)
	        c[i][0]=0;
	    for(int j=0; j<y.size(); j++)
	        c[0][j]=0;
	    
	    for(int i=1; i<x.size()+1; i++){
	    	for(int j=1; j<y.size()+1; j++){
	    		//Si los elementos coinciden sumamos 1 a lo que habia en la diagonal anterior
	    		//Indicamos con una "/" al reves la casilla padre (char)92
	    		if (x.get(i-1).equals(y.get(j-1))){
	    			b[i][j]=(char)92;
	    			c[i][j]=c[i-1][j-1]+1;	    			
	    		}else{
	    			//Si los elementos no son iguales pero el numero de la casilla de la fila anterior
	    			//es mayor o igual que el de la columna anterior la casilla padre es la casilla
	    			//superior de la actual, lo indicamos con "|"
	    			if (c[i-1][j]>=c[i][j-1]){
	    				b[i][j]='|';
	    				c[i][j]=c[i-1][j];	    				 
		            }else{
		            	//Si no es mayor indicamos que el padre es el de la izquierda y lo indicamos
		            	//con "-"
		            	b[i][j]='-';
		            	c[i][j]=c[i][j-1];
	            	}
	    		}
	    	} 
	    }
	}
	
	/**
	 * Obtener de forma recursiva una solucion de la matriz de soluciones parciales
	 * @param x primera cadena dada
	 * @param i indice de la fila
	 * @param j indice de la columna
	 * @param b caracteres de control
	 * @param c longitud de la solucion parcial
	 * @param solucionN solucion numero N
	 */
	public static void BuscaSecuencia(ArrayList<Character> x, int i, int j, char[][] b, int[][]c, ArrayList<Character> solucionN) { // Imprime cada Ocurrencia del Vector B en A		
		if (i==0 || j==0) {
	        return;
	    }
	    if (b[i][j]==(char)92){
	        BuscaSecuencia(x,i-1,j-1,b,c,solucionN);
	      	solucionN.add(x.get(i-1));
	    }else
	        if (b[i][j]=='|')
	             BuscaSecuencia(x,i-1,j,b,c,solucionN);
	        else // "-"
	            BuscaSecuencia(x,i,j-1,b,c,solucionN);
	}
	
	/**
	 * Busqueda de todas las posibles soluciones en la matriz de soluciones parciales
	 * @param x primera cadena dada
	 * @param i indice de la fila
	 * @param j indice de la columna
	 * @param b caracteres de control
	 * @param c longitud de la solucion parcial 
	 * @param medios elementos coincidentes
	 */
	public static void BuscaTodas(ArrayList<Character> x, int i, int j, char[][] b, int[][]c, ArrayList<ArrayList<Character>>medios) {
		ArrayList<Character> solucionN = new ArrayList<Character>();
		int longMax=c[c.length-1][c[i].length-1];
		for(int k=0; k<c.length; k++) {
			for(int l=0; l<c[k].length; l++) {
				if(c[k][l] == longMax && b[k][l]==(char)92) {
					BuscaSecuencia(x,k,l,b,c,solucionN);
				}
			}
		}
		
		ArrayList<Character> aux=null;
		
		int index=0;
		int index2=0;
		if(longMax==0) {
			longMax=1;
		}
		for(int k=0; k<solucionN.size()/longMax; k++) {
			aux = new ArrayList<Character>();
			for(int l=0; l<longMax; l++) {
				aux.add(solucionN.get(index));
				index++;
			}
			if(medios.contains(aux)==false) {
				medios.add(new ArrayList<Character>());
				for(int n=0; n<aux.size(); n++) {
					medios.get(index2).add(aux.get(n));
				}
				index2++;
			}
		}
	}
	
	/**
	 * Obtiene las dos cadenas de un fichero
	 * @param archivo fichero que contiene las cadenas
	 * @param cad1 primera cadena 
	 * @param cad2 segunda cadena
	 */
	public static void LeerFichero(File archivo, ArrayList<Character> cad1, ArrayList<Character> cad2){
		FileReader fr = null;
		BufferedReader br = null;
		int caracter = 0;
		try {
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			while(caracter != 10) {
				caracter=br.read();
				if(caracter != 10 && caracter !=13)
					cad1.add((char)caracter);
			}
			caracter=0;
			while(caracter != -1) {
				caracter=br.read();
				if(caracter!=-1)
					cad2.add((char)caracter);
			}
		    fr.close();
			br.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Copia los resultados obtenidos en un fichero externo
	 * @param archivo fichero de resultados
	 * @param inicio elementos coincidentes al inicio
	 * @param medios elementos coincidentes intermedios
	 * @param fin elementos coincidentes al final
	 */
	public static void EscribirFichero(File archivo, ArrayList<Character> inicio, ArrayList<ArrayList<Character>> medios, ArrayList<Character> fin) {
		FileWriter fw = null;
        PrintWriter pw = null;
        try
        {
            fw = new FileWriter(archivo);
            pw = new PrintWriter(fw);
            
            if(medios.size()!=0) {
				pw.println("Las subsecuencias son:");
				for(int i=0; i<medios.size(); i++) {
					pw.print((i+1)+"- ");
					for(int j=0; j<inicio.size(); j++) {
						pw.print(inicio.get(j));
					}
					for(int k=0; k<medios.get(i).size(); k++) {
						pw.print(medios.get(i).get(k));
					}
					for(int l=fin.size()-1; l>=0; l--) {
						pw.print(fin.get(l));
					}
					pw.println();
				}
			}else {
				if(inicio.size()==0 && fin.size()==0) {
					pw.println("No hay subsecuencias comunes.");
				}else {
					pw.println("La subsecuencia es:");
					for(int j=0; j<inicio.size(); j++) {
						pw.print(inicio.get(j));
					}
					for(int l=fin.size()-1; l>=0; l--) {
						pw.print(fin.get(l));
					}
				}
			}
            pw.close();
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}