package model.data_structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

class RedBlackBSTTest {

	//---------------------------Constantes-----------------------------------//

	/**
	 * Constante que ayuda a generar las llaves
	 */
	private final static String[] a1 = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","w","v","x","y","z"};

	/**
	 * Constante que ayuda a generar las llaves
	 */
	private final static String[] a2 = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","w","v","x","y","z"};

	private final static int[] potencias2 = {0,1,2,4}; 

	//----------------------------Atributos-----------------------------------//

	/**
	 * Arbol que tiene como llaves numeros
	 */
	private RedBlackBST<Integer,String> arbolDeNumeros;

	/**
	 * Arbol que tiene como llaves strings
	 */
	private RedBlackBST<String, Integer> arbolDeString;

	/**
	 * arreglo dinamico donde se guardan las llaves
	 */
	private ORArray<String> llaves;

	/**
	 * arreglo dinamico donde se guardan los valores
	 */
	private ORArray<Integer> valores;

	//----------------------------Métodos-------------------------------------//


	/**
	 * 
	 */
	public void setUpEscenario0() {
		arbolDeNumeros = new RedBlackBST<Integer,String>();
		arbolDeString = new RedBlackBST<String, Integer>();
		llaves = new ORArray<String>(100);
		valores = new ORArray<Integer>(100);
	}
	/**
	 * 
	 */
	public void setUpEscenario1() {
		setUpEscenario0();
		for(int i = 0; i < a1.length; i++) {
			int x = (int)(Math.random()*((1000-0)+1))+0;
			for(int j = 0; j < a2.length; j++) {
				String key = a1[i]+a2[j] + "";
				arbolDeString.put(key, x);
				llaves.add(key);
				valores.add(x);
			}
		}
	}

	/**
	 * 
	 */
	public void setUpEscenario2() {
		setUpEscenario0();
		int length = 86686+74986+104219+107656 +108944+116722;
		for(int i = 0; i < length; i++) {
			arbolDeNumeros.put(i,"lel");
		}
	}

	public void setUpEscenario3() {
		setUpEscenario0();
		for(int i=0; i<10; i++) {
			arbolDeNumeros.put(i, "lol");
		}
	}

	/**
	 * Método con el que se rectifica que el arbol creado este balanceado
	 */
	/*@Test
	public void isBalanced0() {
		setUpEscenario0();
		Class c = arbolDeNumeros.getClass();
		Method privateMethod = null;
		boolean answer = true;
		try {
			privateMethod = c.getDeclaredMethod("isBalanced");
			System.out.println("pasa por aqui");
		}catch(Exception e) {
			e.printStackTrace();
		}
		privateMethod.setAccessible(true);
		try {
			answer = (boolean) privateMethod.invoke(arbolDeNumeros);
			System.out.println("También pasa por aquí");
		}catch(Exception e) {
			e.printStackTrace();
		}
		assertTrue("el arbol no esta bien balanceado", answer);
	}
	 */
	/**
	 * Método que rectifica que el arbol tenga la cantidad de elementos correctos
	 */
	@Test
	public void getSizeTest0() {
		setUpEscenario0();
		assertEquals("El arbol no deberia tener tamaño", arbolDeNumeros.size(), 0);
	}

	/**
	 * Método que rectifica que el arbol este vacio cuando se crea.
	 */
	@Test
	public void isEmptyTest0() {
		setUpEscenario0();
		assertTrue("El arbol no esta vacio",arbolDeNumeros.isEmpty());
	}

	/**
	 * Método que rectifica que se esten insertando elementos al arbol.
	 */
	@Test
	public void putTest0() {
		setUpEscenario0();
		arbolDeNumeros.put(1, "a");
		assertEquals("El tamaño del arbol debio haber aumentado", arbolDeNumeros.size(),1);
		arbolDeNumeros.put(2, "a");
		assertEquals("El tamaño del arbol debio haber aumentado", arbolDeNumeros.size(),2);
		arbolDeNumeros.put(2, "b");
		assertEquals("El tamaño del arbol debio haber aumentado", arbolDeNumeros.size(),2);
	}

	/**
	 * Método que prueba que se esten recuperando los valores del arbol de manera correcta
	 * según la llave
	 */
	@Test
	public void getTest0() {
		setUpEscenario0();
		arbolDeNumeros.put(1, "a");
		assertEquals("el elemento que se devolvio no es el correcto","a",arbolDeNumeros.get(1));
		arbolDeNumeros.put(2, "b");
		assertEquals("el elemento que se devolvio no es el correcto","b",arbolDeNumeros.get(2));
		arbolDeNumeros.put(2, "c");
		assertEquals("el elemento que se devolvio no es el correcto","c",arbolDeNumeros.get(2));
	}

	@Test
	public void isEmptyTest1() {
		setUpEscenario1();
		assertFalse("el arbol no deberia esta vacio",arbolDeString.isEmpty());
	}

	@Test
	public void getSizeTest1() {
		setUpEscenario1();
		int a = llaves.getSize();
		assertEquals("el tamaño del arbol no es el correcto", a, arbolDeString.size());
	}

	/**
	 * Método con el que se rectifica que el arbol creado este balanceado
	 */
	@Test
	public void isBalanced1() {
		setUpEscenario1();
		Class c = arbolDeString.getClass();
		Method privateMethod = null;
		boolean answer = true;
		try {
			privateMethod = c.getDeclaredMethod("isBalanced");
			System.out.println("pasa por aqui");
		}catch(Exception e) {
			e.printStackTrace();
		}
		privateMethod.setAccessible(true);
		try {
			 answer = (boolean) privateMethod.invoke(arbolDeString);
			 System.out.println("También pasa por aquí");
		}catch(Exception e) {
			e.printStackTrace();
		}
		assertTrue("el arbol no esta bien balanceado", answer);
	}
	 

	/**
	 * Método que rectifica que los valores que se devuelven con el get corresponden a los insertados
	 */
	@Test
	public void getTest1() {
		setUpEscenario1();
		for(int i = 0; i < llaves.getSize(); i++) {
			assertEquals("el valor de la llave no corresponde con el valor con el que se creo", valores.getElement(i),arbolDeString.get(llaves.getElement(i)));
		}
	}

	@Test
	public void getHeight() {
		setUpEscenario3();
		assertEquals("El valor de la altura es erróneo", 2,arbolDeNumeros.getHeight(0));
		assertEquals("El valor de la altura es erróneo", 1,arbolDeNumeros.getHeight(1));
		assertEquals("El valor de la altura es erróneo", 2,arbolDeNumeros.getHeight(2));
		assertEquals("El valor de la altura es erróneo", 0,arbolDeNumeros.getHeight(3));
		assertEquals("El valor de la altura es erróneo", 2,arbolDeNumeros.getHeight(4));
		assertEquals("El valor de la altura es erróneo", 1,arbolDeNumeros.getHeight(5));
		assertEquals("El valor de la altura es erróneo", 2,arbolDeNumeros.getHeight(6));
		assertEquals("El valor de la altura es erróneo", 1,arbolDeNumeros.getHeight(7));
		assertEquals("El valor de la altura es erróneo", 2,arbolDeNumeros.getHeight(8));
		assertEquals("El valor de la altura es erróneo", 2,arbolDeNumeros.getHeight(9));

	}

	@Test
	public void testContains() {
		setUpEscenario3();
		for(int i= 0; i<10; i++)
			assertEquals("Debería contener el objeto",true,arbolDeNumeros.contains(i));

		for(int i= 10; i<15; i++)
			assertEquals("No debería contener el objeto",false,arbolDeNumeros.contains(i));
	}
	@Test
	public void testHeight0() {
		setUpEscenario0();
		assertEquals("La altura es errónena", 0, arbolDeString.height());
	}

	@Test
	public void testHeight1() {
		setUpEscenario1();
		assertEquals("La altura es errónena", 7, arbolDeString.height());
	}

	@Test
	public void testHeight2() {
		setUpEscenario3();
		assertEquals("La altura es errónena", 2, arbolDeNumeros.height());
	}

	@Test
	public void check() {
		
	}
	
	@Test
	public void testKeys() {
		setUpEscenario2();
		Iterator<String> it = arbolDeString.keys();
		while(it.hasNext()) {
			String key = it.next();
			assertEquals("El elemento debería exisitir y ser una llave",key,llaves.deleteElm(key));
		}
		assertEquals("Deberían estar todas las llaves en el iterador",true,llaves.getSize() == 0);
	}
	
	@Test
	public void keysInRangeTest0() {
		setUpEscenario2();
		int length = 86686+74986+104219+107656 +108944+116722;
		long start_time = System.currentTimeMillis();
		Iterator<Integer> temp = arbolDeNumeros.keysInRange(0, length);
		long end_time = System.currentTimeMillis();
		long difference = end_time-start_time;
		System.out.println(difference);
		int comp1 = 0;
		while(temp.hasNext()) {
			int comp2 = temp.next();
			assertEquals("El elemento regresado por el rango no es el correcto " + comp1 + " " +comp1 + " " + comp2,comp1,comp2);
			comp1++;
		}
	}

	@Test
	public void keysInRangeTest1() {
		setUpEscenario2();
		int length = 86686+74986+104219+107656 +108944+116722;
		long start_time = System.currentTimeMillis();
		Iterator<Integer> temp = arbolDeNumeros.keysInRange(0, length);
		long end_time = System.currentTimeMillis();
		long difference = end_time-start_time;
		System.out.println(difference);
		int comp1 = length-1;
		while(temp.hasNext()) {
			int comp2 = temp.next();
			assertEquals("El elemento regresado por el rango no es el correcto",comp1,comp2);
			comp1--;
		}
	}

	@Test
	public void valuesInRangeTest0() {
		setUpEscenario2();
		int length = 86686+74986+104219+107656 +108944+116722;
		long start_time = System.currentTimeMillis();
		Iterator<String> temp = arbolDeNumeros.valuesInRange(0, length);
		long end_time = System.currentTimeMillis();
		long difference = end_time-start_time;
		System.out.println("diferencia1 "+difference);
		int comp1 = length-1;
		long start_time1 = System.currentTimeMillis();
		ORArray<String> temp1 = arbolDeNumeros.ValuesInRangeInt(0, length);
		long end_time1 = System.currentTimeMillis();
		long difference1 = end_time1-start_time1;
		System.out.println("diferencia2 " + difference1);
		System.out.println(temp1.getSize());
		int e = 0;
		while(temp.hasNext()) {
			assertEquals("El elemento regresado por el rango no es el correcto",temp.next(),temp1.getElement(e));
			comp1--;
			e++;
		}
	}
}
