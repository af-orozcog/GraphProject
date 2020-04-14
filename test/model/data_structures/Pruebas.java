package model.data_structures;

import java.lang.reflect.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Iterator;
import java.util.Random;

import org.junit.jupiter.api.Test;

class Pruebas {

	/**
	 * Constante que ayuda a generar las llaves
	 */
	private final static String[] a1 = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

	/**
	 * arreglo dinamico donde se guardan las llaves
	 */
	private ORArray<String> llaves;

	/**
	 * arreglo dinamico donde se guardan los valores
	 */
	private ORArray<Integer> valores;

	/**
	 * Hashtable que se va a probar
	 */
	private HashTableLP<String,Integer> tablaLP;

	/**
	 * Hashtable que se va a probar
	 */
	private HashTableSC<String,Integer> tablaSC;

	/**
	 * Imprime la tabla que realiza las pruebas 
	 */
	@Test
	public void printResultadosPruebas() {

		int tamañoInicial = 97;

		tablaLP = new HashTableLP<String, Integer>(tamañoInicial);
		tablaSC = new HashTableSC<String, Integer>(tamañoInicial);
		llaves = new ORArray<String>(17576);
		valores = new ORArray<Integer>(17576);

		for(int i = 0; i < a1.length; i++) {
			int val = (int)(Math.random()*((1000-0)+1))+0;
			for(int j = 0; j < a1.length; j++) {
				for(int k = 0; k < a1.length; k++) {
					for(int l = 0; l < a1.length; l++) {
						String key = a1[i]+a1[j]+a1[k]+a1[l];
						tablaLP.put(key, val);
						tablaSC.put(key, val);
						llaves.add(key);
						valores.add(val);
					}
				}
			}
		}

		System.out.println("Información\tLinearProbing\tSeparateChaining");

		double N1 = tablaLP.getSize(); double N2 = tablaSC.getSize();
		System.out.println("Numero de duplas                     \t" + N1 +"\t" + N2);

		System.out.println("Tamaño inicial del arreglo           \t" + tamañoInicial + "\t" + tamañoInicial);

		double M1 = tablaLP.getM(); double M2 = tablaSC.getM();
		System.out.println("Tamaño final del arreglo             \t" + tablaLP.getM() + "\t" + tablaSC.getM() );

		double carga1 = N1/M1;
		double carga2 = N2/M2;
		System.out.println("Factor de carga final                \t" + carga1 + "\t" + carga2 );

		System.out.println("Número de rehashes                   \t"+ tablaLP.getRehash() + "\t" + tablaSC.getRehash());

		double[] promedios = tiempoPromedioGet();
		System.out.println("Tiempo promedio de consultas get(...)\t" + promedios[0] + "\t" + promedios[1]);
	}

	public double[] tiempoPromedioGet() {		
		double time1 = 0; double time2 = 0;
		double startTime = 0; double endTime = 0;
		double tot1 = 0; double tot2 = 0;

		double[] promedios = new double[2];

		for (int i = 0; i<10000; i++) {
			Random aleatorio = new Random(System.currentTimeMillis());
			int intAletorio = aleatorio.nextInt(llaves.getSize());
			String key = llaves.deleteAtK(intAletorio);

			startTime = System.currentTimeMillis();
			tablaLP.get(key);
			endTime = System.currentTimeMillis();
			time1 = (endTime - startTime);
			tot1+=time1;

			startTime = System.currentTimeMillis();
			tablaSC.get(key);
			endTime = System.currentTimeMillis();
			time2 = (endTime - startTime);
			tot2+=time2;
		}

		promedios[0] = tot1/10000;
		promedios[1] = tot2/10000;

		return promedios;
	}

}
