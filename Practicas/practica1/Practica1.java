/**
 * Implementacion del algoritmo de ordenacion QuickSort que nos permite elegir 
 * distintos pivotes, el primer elemento, el ultimo, uno aleatorio y el del centro
 * El programa ordena varios vectores de distintos tamaños para comparar el tiempo que se tarda
 * en funcion del pivote elegido
 */

package practica1;

import java.io.*;
import java.util.*;

public class Practica1 {

	public static int tam = 10;
	public static int comparaciones = 0;
	public static int asignaciones = 0;
	
	/**
	 * Desordena de forma aleatoria
	 * @param vector vector de numeros a desordenar
	 */
	public static void Desordenar(int[] vector) {
		int x,y,aux;
		for(int i=0; i<vector.length/10; i++) {
			x=(int)(Math.random()*tam);
			y=(int)(Math.random()*tam);
			aux = vector[x];
			vector[x] = vector[y];
			vector[y] = aux;
		}
	}
	
	/**
	 * Devuelve el elemento mediana del vector A
	 * @param A vector del que se obtiene la mediana
	 * @param izq limite izquierdo
	 * @param der limite derecho
	 * @return elemento mediana de A
	 */
	public static int Mediana(int A[], int izq, int der) {
		int mediana = 0;
		mediana = (A[izq]+A[der]+A[(izq+der)/2])/3;
		return mediana;
	}
	
	/**
	 * Ordena mediante quicksort con pivote en el primer elemento
	 * @param A vector a ordenar
	 * @param izq limite izquierdo
	 * @param der limite derecho
	 */
	public static void QuicksortPivoteElPrimero(int A[], int izq, int der) {

		int pivote=A[izq]; // tomamos primer elemento como pivote
		asignaciones++;
		int i=izq; // i realiza la busqueda de izquierda a derecha
		int j=der; // j realiza la busqueda de derecha a izquierda
		int aux;
	 
		while(i<j){            	// mientras no se crucen las busquedas
			while(A[i]<=pivote && i<j) {
				comparaciones++;
				i++; 			// busca elemento mayor que pivote
			}
			while(A[j]>pivote) {
				comparaciones++;
				j--;            // busca elemento menor que pivote
			}
			if (i<j) {          // si no se han cruzado   
				asignaciones++;
				aux= A[i];      // los intercambia
				asignaciones++;
				A[i]=A[j];
				asignaciones++;
				A[j]=aux;
			}
		}
		
		asignaciones++;
		A[izq]=A[j]; // se coloca el pivote en su lugar de forma que tendremos
		asignaciones++;
		A[j]=pivote; // los menores a su izquierda y los mayores a su derecha
		
		if(izq<j-1)
			QuicksortPivoteElPrimero(A,izq,j-1); // ordenamos subarray izquierdo
		if(j+1 <der)
			QuicksortPivoteElPrimero(A,j+1,der); // ordenamos subarray derecho
	}
	
	/**
	 * Ordena mediante quicksort con pivote en el ultimo elemento
	 * @param A vector a ordenar
	 * @param izq limite izquierdo
	 * @param der limite derecho
	 */
	public static void QuicksortPivoteElUltimo(int A[], int izq, int der) {

		int pivote=A[der]; // tomamos ultimo elemento como pivote
		asignaciones++;
		int i=izq; // i realiza la busqueda de izquierda a derecha
		int j=der; // j realiza la busqueda de derecha a izquierda
		int aux;
		
		while(i<j){            	// mientras no se crucen las busquedas
			
			while(A[i]<=pivote && i<j) {
				comparaciones++;
				i++; 			// busca elemento mayor que pivote
			}
			
			while(A[j]>pivote) {
				comparaciones++;
				j--;            // busca elemento menor que pivote
			}
			if (i<j) {          // si no se han cruzado  
				asignaciones++;
				aux= A[i];      // los intercambia
				asignaciones++;
				A[i]=A[j];
				asignaciones++;
				A[j]=aux;
			}
		}
		
		comparaciones++;
		if(A[izq]>A[j]) {
			asignaciones++;
			aux = A[izq];
			asignaciones++;
			A[izq]=A[j]; // se coloca el pivote en su lugar de forma que tendremos
			asignaciones++;
			A[j]=aux; // los menores a su izquierda y los mayores a su derecha
		}
		
		if(izq<j-1)
			QuicksortPivoteElUltimo(A,izq,i-1); // ordenamos subarray izquierdo
		if(j+1 <der)
			QuicksortPivoteElUltimo(A,i+1,der); // ordenamos subarray derecho
	}
	
	/**
	 * Ordena mediante quicksort con pivote aleatorio
	 * @param A vector a ordenar
	 * @param izq limite izquierdo
	 * @param der limite derecho
	 */
	public static void QuicksortPivoteRandom(int A[], int izq, int der) {
		
		int rand = (int)(Math.random()*(der-izq+1)+izq);
		asignaciones++;
		int pivote=A[rand]; // tomamos elemento aleatorio como pivote
		int i=izq; // i realiza la busqueda de izquierda a derecha
		int j=der; // j realiza la busqueda de derecha a izquierda
		int aux;
		
		while(i<j){            	// mientras no se crucen las busquedas
			
			while(A[i]<=pivote && i<j) {
				comparaciones++;
				i++; 			// busca elemento mayor que pivote
			}
			
			while(A[j]>pivote) {
				comparaciones++;
				j--;            // busca elemento menor que pivote
			}
			if (i<j) {          // si no se han cruzado  
				asignaciones++;
				aux= A[i];      // los intercambia
				asignaciones++;
				A[i]=A[j];
				asignaciones++;
				A[j]=aux;
			}
		}
		
		comparaciones++;
		if(A[izq]>A[j]) {
			asignaciones++;
			aux = A[izq];
			asignaciones++;
			A[izq]=A[j]; // se coloca el pivote en su lugar de forma que tendremos
			asignaciones++;
			A[j]=aux; // los menores a su izquierda y los mayores a su derecha
		}
		
		if(izq<j-1){
			QuicksortPivoteRandom(A,izq,j-1); // ordenamos subarray izquierdo
		}
		if(j<der){
			QuicksortPivoteRandom(A,j+1,der); // ordenamos subarray derecho
		}
	}

	/**
	 * Ordena mediante quicksort con pivote en el elemento mediana
	 * @param A vector a ordenar
	 * @param izq limite izquierdo
	 * @param der limite derecho
	 */
	public static void QuicksortPivoteMediana(int A[], int izq, int der) {
		
		int pivote=Mediana(A,izq,der); // tomamos elemento aleatorio como pivote
		asignaciones++;
		int i=izq; // i realiza la busqueda de izquierda a derecha
		int j=der; // j realiza la busqueda de derecha a izquierda
		int aux;
		
		while(i<j){            	// mientras no se crucen las busquedas
			
			while(A[i]<=pivote && i<j) {
				comparaciones++;
				i++; 			// busca elemento mayor que pivote
			}
			
			while(A[j]>pivote) {
				comparaciones++;
				j--;            // busca elemento menor que pivote
			}
			if (i<j) {          // si no se han cruzado  
				asignaciones++;
				aux= A[i];      // los intercambia
				asignaciones++;
				A[i]=A[j];
				asignaciones++;
				A[j]=aux;
			}
		}
		
		comparaciones++;
		if(A[izq]>A[j]) {
			asignaciones++;
			aux = A[izq];
			asignaciones++;
			A[izq]=A[j]; // se coloca el pivote en su lugar de forma que tendremos
			asignaciones++;
			A[j]=aux; // los menores a su izquierda y los mayores a su derecha
		}
		
		if(izq<j-1)
			QuicksortPivoteMediana(A,izq,j-1); // ordenamos subarray izquierdo
		if(j <der)
			QuicksortPivoteMediana(A,j+1,der); // ordenamos subarray derecho
	}

	public static void main (String [] args) {
		
		long media;
		long operacionesTotales = 0;
		int repeticiones = 20;
		double mediaTiempo;
		double end = 0, start = 0;
		double tiempoTotal = 0;
		Scanner in = new Scanner(System.in);
		String alg;
	
		do{
			System.out.println("Introduzca el tipo de pivote deseado (p(primero), u(ultimo), r(random), m(mediana): ");
			alg = in.nextLine();
			alg = alg.toUpperCase();
		}while(alg.equals("P")==false && alg.equals("U")==false && alg.equals("R")==false && alg.equals("M")==false);
		in.close();
		
		String ruta = "datos"+alg+".txt";
		File archivo = new File(ruta);
		
		if(archivo.exists()){ archivo.delete();}
		
		for(; tam<=10000000; tam = tam*10){
			System.out.println("				----------- TAMAÑO: " + tam + " -----------");
			int [] vector = new int[tam];
			for (int k=1; k<=repeticiones; k++) {
				System.out.print("Iteracion: "+k);
				for(int i=0; i<vector.length; i++)
					vector[i]=i+1;
			
				Desordenar(vector);

				switch(alg){
				case "P":
					start = System.currentTimeMillis();
					QuicksortPivoteElPrimero(vector, 0, tam-1);
					end = System.currentTimeMillis();
					break;
				case "U":
					start = System.currentTimeMillis();
					QuicksortPivoteElUltimo(vector, 0, tam-1);
					end = System.currentTimeMillis();
					break;
				case "R":
					start = System.currentTimeMillis();
					QuicksortPivoteRandom(vector, 0, tam-1);
					end = System.currentTimeMillis();
					break;
				case "M":
					start = System.currentTimeMillis();
					QuicksortPivoteMediana(vector, 0, tam-1);
					end = System.currentTimeMillis();
					break;				
				}
			System.out.print("	Tiempo empleado en ordenar: "+(end-start)+" ms");
			System.out.print("	Asignaciones: " + asignaciones);
			System.out.print("	Comparaciones: " + comparaciones);
			tiempoTotal = tiempoTotal + (end-start);
			operacionesTotales = operacionesTotales + comparaciones + asignaciones;
		
			try{
            BufferedWriter datos = new BufferedWriter(new FileWriter(archivo, true));

            if(k==1 && tam==10){      	
            	switch(alg){
				case "P":
					datos.write("			****PIVOTE ELEGIDO: PRIMER ELEMENTO****\n");
					break;
				case "U":
					datos.write("			****PIVOTE ELEGIDO: ULTIMO ELEMENTO****\n");
					break;
				case "R":
					datos.write("			****PIVOTE ELEGIDO: ELEMENTO ALEATORIO****\n");
					break;
				case "M":
					datos.write("			****PIVOTE ELEGIDO: MEDIANA****\n");
					break;				
				}            	
            }
            if(k==1){
            	datos.write("				----------- TAMAÑO: " + tam + " -----------\n");
            }
            datos.write("Iteracion: " + k + "	Tiempo empleado en ordenar: "+(end-start) + " ms" +
            		"	Asignaciones: " + asignaciones + "	Comparaciones: " + comparaciones + "\n");
            if(k==repeticiones){
            	datos.newLine();
            	datos.write("Operaciones totales: " + operacionesTotales + "				Tiempo total: "+tiempoTotal+" ms");
            	datos.newLine();
            	datos.newLine();
            }
            datos.close();
			}
			catch (IOException err){
				System.out.println(
                "ERROR: " +
                err.getMessage() );
			}
		

			asignaciones = 0;
			comparaciones = 0;
			System.out.print("\n");
			}
	        
			media = operacionesTotales/repeticiones;
			System.out.print("\nOperaciones totales: " + operacionesTotales);
			System.out.println("		Media operaciones/ejecucion: "+media);
			
			mediaTiempo = tiempoTotal/repeticiones;
			System.out.print("Tiempo total: "+tiempoTotal+" ms");
			System.out.print("			Media tiempo/ejecucion: "+mediaTiempo+" ms");
			System.out.println("\n\n");
			operacionesTotales = 0;
			tiempoTotal = 0;
			System.out.println("\n");
			
			try
	        {
	            BufferedWriter prom = new BufferedWriter(new FileWriter(new File("datosPromedio.txt"), true));
				if(tam==10){
		            prom.write("\n			****PIVOTE ELEGIDO: ");
		            switch(alg){
					case "P":
						prom.write("PRIMER ELEMENTO****\n");
						break;
					case "U":
						prom.write("ULTIMO ELEMENTO****\n");
						break;
					case "R":
						prom.write("ELEMENTO ALEATORIO****\n");
						break;
					case "M":
						prom.write("MEDIANA****\n");
						break;				
					}
				}
	            prom.write("				----------- Tamaño: " + tam + " -----------");
	            prom.newLine();
	            prom.write("Media operaciones/ejecucion: "+ media + "		Media tiempo/ejecucion: "+mediaTiempo+" ms\n");
	            prom.newLine();
	            prom.close();
	        }
	        catch (IOException err)
	        {
	            System.out.println(
	                "ERROR: " + err.getMessage() );
	        }
		}
	}
}