/**
 * Resuelve el famoso problema de las torres de Hanoi de forma recursiva
 */

package hanoi;

import java.util.Scanner;

public class Hanoi {
	
	public static int x=0;
	
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n;
        System.out.println("Numero de discos: ");
        n = sc.nextInt();
        double start = System.currentTimeMillis();
        Hanoi(n,1,2,3);  //1:origen  2:auxiliar 3:destino
        double end = System.currentTimeMillis();
        System.out.println(x+" "+(double)(end-start)/1000+" s");
    }


/**
 * Muestra los pasos a seguir para resolver el problema de hanoi con n discos
 * @param n numero de discos del problema
 * @param origen torre de origen
 * @param auxiliar torre intermedia
 * @param destino torre de destino
 */
public static void Hanoi(int n, int origen,  int auxiliar, int destino){
		if(n==1) {
			System.out.println("mover disco de " + origen + " a " + destino);
			x++;
		}else{
			Hanoi(n-1, origen, destino, auxiliar);
			System.out.println("mover disco de "+ origen + " a " + destino);
			x++;
			Hanoi(n-1, auxiliar, origen, destino);
		}
 	}
}