/**
 * Algoritmo basado en backtracking para resolver sudokus de 9x9
 * Soluciona el sudoku correspondiente e imprime el tiempo que ha tardado en hacerlo
 * El fichero que contiene el sudoku debe tener extension .txt
 * Cuando se pide el nombre del fichero a resolver no se escribe la extension
 * Ejemplo:
 *  -Fichero = Sudoku1.txt
 *  -Nombre = Sudoku1
 *  
 * Ficheros deprueba:
 * 	-Sudoku1.txt
 * 	-Sudoku2.txt
 */

package algoritmosClaseTeoria;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Sudoku {
	
	public static final int tam=9;
	static boolean resuelto=false;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		char[][] tableroImprimir = new char[tam][tam];
		int [][] tableroResolver = new int[tam][tam];
		System.out.println("Introduzca el nombre del sudoku:");
		String nombre = in.nextLine()+".txt";
		LeerFichero(nombre,tableroImprimir);
		Imprimir(tableroImprimir);
		
		for(int i=0; i<tam; i++) 
			for(int j=0; j<tam; j++) 
				tableroResolver[i][j]= (((int)(tableroImprimir[i][j]))-48);
		
		double start=System.currentTimeMillis();
		Resolver(0,tableroResolver);
		double end=System.currentTimeMillis();
		
		for(int i=0; i<tam; i++) 
			for(int j=0; j<tam; j++) 
				tableroImprimir[i][j]= (char)(tableroResolver[i][j]+48);
		
		System.out.println("Sudoku resuelto en: "+(end-start)/1000+" s");
		Imprimir(tableroImprimir);
		
	}
	
	/**
	 * Evalua si un resultado es correcto o no para una celda del sudoku
	 * @param i valor que queremos evaluar
	 * @param f	fila donde esta el valor a evaluar
	 * @param c columna donde esta el valor a evaluar
	 * @param tablero sudoku actual
	 * @return si el reultado es valido o no
	 */
	public static boolean Aceptable(int i, int f, int c, int[][]tablero) {
		for(int k=0; k<tam; k++){
			if(tablero[f][k]==i){
				return false;
			}
		}
		
		for(int m=0; m<tam; m++){
			if(tablero[m][c]==i){
				return false;
				}	
		}
		
		if(f<3 && c<3) { //1C
			for(int n=0; n<3; n++)
				for(int j=0; j<3; j++)
					if(tablero[n][j]==i)
						return false;
		}else {
			if(f<3 && c>2 && c<6) { //2C
				for(int n=0; n<3; n++)
					for(int j=3; j<6; j++)
						if(tablero[n][j]==i)
							return false;
			}else {
				if(f<3 && c>6) { //3C
					for(int n=0; n<3; n++)
						for(int j=6; j<9; j++)
							if(tablero[n][j]==i)
								return false;
				}
			}
		}
		if(f>2 && f<6 && c<3) {//4C
			for(int n=3; n<6; n++)
				for(int j=0; j<3; j++)
					if(tablero[n][j]==i)
						return false;
		}else {
			if(f>2 && f<6 && c>2 && c<6) { //5C
				for(int n=3; n<6; n++)
					for(int j=3; j<6; j++)
						if(tablero[n][j]==i)
							return false;
			}else {
				if(f>2 && f<6 && c>6) { //6C
					for(int n=3; n<6; n++)
						for(int j=6; j<9; j++)
							if(tablero[n][j]==i)
								return false;
				}
			}
		}
		if(f>6 && c<3) { //7C
			for(int n=6; n<9; n++)
				for(int j=0; j<3; j++)
					if(tablero[n][j]==i)
						return false;
		}else {
			if(f>6 && c>2 && c<6) { //8C
				for(int n=6; n<9; n++)
					for(int j=3; j<6; j++)
						if(tablero[n][j]==i)
							return false;
			}else {
				if(f>6 && c>6) { //9C
					for(int n=6; n<9; n++)
						for(int j=6; j<9; j++)
							if(tablero[n][j]==i)
								return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Intenta colocar el valor correcto en la casilla n
	 * @param n casilla que estamos evaluando
	 * @param tablero sudoku actual que estamos resolviendo
	 */
	public static void Resolver(int n, int[][]tablero) {
		int f=((n)/tam);
		int c=((n)%tam);		
		
		if(tablero[f][c]==0) {
			for(int i=1; i<10; i++) {
				if(Aceptable(i,f,c,tablero)){
					tablero[f][c]=i;
					if(n==80) {
						resuelto=true;
					}else {
						Resolver((n+1),tablero);
					}
					if(resuelto==false) {
						tablero[f][c]=0;
					}
				}
			}
		}else {
			if(n==80) {
				resuelto=true;
			}
			else
				Resolver(n+1,tablero);
		}
	}
	
	/**
	 * Lee un fichero y carga el correspondiente sudoku
	 * @param nombre nombre del fichero que queremos leer
	 * @param tablero lugar donde se guarda el sudoku
	 */
	public static void LeerFichero(String nombre, char[][]tablero) {
		File archivo = new File (nombre);
		FileReader fr = null;
		BufferedReader br = null;
		int caracter=0;
		
		try {
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
		    
	    	for(int i=0; i<tam; i++) {
	    		for(int j=0; j<tam; j++) {
	    			caracter=br.read();
	    			if(caracter!=10 && caracter!= 32 && caracter!= 13 && caracter!=-1) {
	    				tablero[i][j]=(char)caracter;
	    			}else {
	    				j--;
	    			}
	    		}
	    	}
		    
		    fr.close();
			br.close();
			System.out.println("Fichero leido: "+nombre);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Imprime el sudoku que recibe como parametro dandole formato de celdas
	 * @param tablero sudoku que queremos imprimir
	 */
	public static void Imprimir(char[][]tablero) {
		System.out.println("+------+-------+-------+");
		for(int i=0; i<tam; i++) {
			System.out.print("|");
			for(int j=0; j<tam; j++) {
				if((j-2)%3==0)
					if(tablero[i][j]=='0')
						System.out.print("  | ");
					else
						System.out.print(tablero[i][j]+" | ");
				else
					if(tablero[i][j]=='0')
						System.out.print("  ");
					else
						System.out.print(tablero[i][j]+" ");
			}
			
			if((i-2)%3==0) {
				System.out.println();
				System.out.println("+------+-------+-------+");
			}else
				System.out.println();
		}
	}
}