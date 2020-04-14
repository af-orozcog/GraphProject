package model.data_structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

class HashTableSCTest {

	/**
	 * Constante que ayuda a generar las llaves
	 */
	private final static String[] a1 = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","w","v","x","y","z"};

	/**
	 * Constante que ayuda a generar las llaves
	 */
	private final static String[] a2 = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","w","v","x","y","z"};

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
	private HashTableSC<String,Integer> datos;

	/**
	 * Escenario en el que simplemente se inicializan los tama�os las diferentes estrucuturas 
	 */
	public void setUpEscenario0() {

		datos = new HashTableSC<String, Integer>(20);
		llaves = new ORArray<String>(100);
		valores = new ORArray<Integer>(100);
	}

	/**
	 * Escenario en el que se crean valores al a zar que se guardan en el hastable y
	 * en los arreglos dinamicos
	 */
	public void setUpEscenario1() {
		setUpEscenario0();
		for(int i = 0; i < a1.length; i++) {
			int x = (int)(Math.random()*((1000-0)+1))+0;
			for(int j = 0; j < a2.length; j++) {
				System.out.println((a1[i] + a2[i]) + "");
				datos.put(a1[i]+a2[j] + "", x);
				llaves.add(a1[i]+a2[j] + "");
				valores.add(x);
			}
		}
	}

	/**
	 * M�todo que prueba que el m�todo isEmpty este funcionando bien
	 */
	@Test
	public void IsEmptyTest0() {
		setUpEscenario0();
		assertTrue("el HashTable deber�a estar vacio", datos.isEmpty());
	}

	/**
	 * M�todo que prueba que el m�todo getSize este devolviendo valores correctos
	 */
	@Test
	public void getSizeTest0() {
		setUpEscenario0();
		assertEquals("el numero de elementos deber�a ser igual a cero", 0, datos.getSize());
	}

	/**
	 * m�todo que rectifica que los elementos si se esten agregando al hashTable
	 */
	@Test
	public void putTest0() {
		setUpEscenario0();
		datos.put("a", 0);
		assertEquals("el numero de elementos deber�a ser igual a 1", 1, datos.getSize());
		datos.put("a", 2);
		assertEquals("el numero de elementos deber�a ser igual a 1", 1, datos.getSize());
		datos.put("b", 3);
		assertEquals("el numero de elementos deber�a ser igual a 2", 2, datos.getSize());
	}

	/**
	 * M�todo que rectifica que se esten devolviendo los valores correctos en el hashTable seg�n su llave.
	 */
	@Test
	public void getTest0() {
		setUpEscenario0();
		datos.put("a", 0);
		Integer compare = 0;
		assertEquals("el m�todo no regreso el valor correcto para la llave", datos.get("a"), compare);
		datos.put("a", 2);
		compare = 2;
		assertEquals("el m�todo no regreso el valor correcto para la llave", datos.get("a"), compare);
		datos.put("b", 3);
		compare = 3;
		assertEquals("el m�todo no regreso el valor correcto para la llave", datos.get("b"), compare);
	}

	/**
	 * M�todo que rectifica que se borren los pares llave valor y que se devuelve de manera correcta
	 * el elemento eliminado seg�n la llave
	 */
	@Test
	public void deleteTest0() {
		setUpEscenario0();
		datos.put("a", 0);
		datos.put("b", 3);
		Integer compare = 0;
		assertEquals("el elemento que se borro no es el correcto", datos.delete("a"),compare);
		compare = 3;
		assertEquals("el elemento que se borro no es el correcto", datos.delete("b"),compare);
	}

	/**
	 * M�todo que rectifica que se esten devolviendo los valores correctos en el hashTable seg�n su llave.
	 */
	@Test
	public void getSizeTest1() {
		setUpEscenario0();
		datos.put("a", 0);
		datos.put("b", 3);
		datos.put("c",4);
		datos.put("d", 5);
		datos.put("h", 7);
		assertEquals("el tama�o del HashTable no es el correcto", 5, datos.getSize());
		datos.delete("a");
		assertEquals("el tama�o del HashTable no es el correcto", 4, datos.getSize());
		datos.delete("b");
		assertEquals("el tama�o del HashTable no es el correcto", 3, datos.getSize());
		datos.delete("c");
		assertEquals("el tama�o del HashTable no es el correcto", 2, datos.getSize());
		datos.delete("d");
		assertEquals("el tama�o del HashTable no es el correcto", 1, datos.getSize());
		datos.delete("h");
		assertEquals("el tama�o del HashTable no es el correcto", 0, datos.getSize());
	}

	/**
	 * M�todo que prueba que el m�todo isEmpty este funcionando bien
	 */
	@Test
	public void isEmptyTest1() {
		setUpEscenario1();
		assertFalse("el hashTable no deber�a estar vacio",datos.isEmpty());
	}

	/**
	 * M�todo que prueba que el m�todo getSize este devolviendo valores correctos
	 */
	@Test
	public void getSizeTest2() {
		setUpEscenario1();
		int a = llaves.getSize();
		assertEquals("el tama�o deber�a ser igual a la cantidad de llaves", a,datos.getSize());
	}

	/**
	 * M�todo que rectifica que se esten devolviendo los valores correctos en el hashTable seg�n su llave.
	 */
	@Test
	public void getTest1() {
		setUpEscenario1();
		for(int i = 0; i < llaves.getSize(); i++) {
			assertEquals("el valor de la llave no corresponde con el valor con el que se creo", valores.getElement(i),datos.get(llaves.getElement(i)));
		}
	}

	/**
	 * M�todo que rectifica que se borren los pares llave valor y que se devuelve de manera correcta
	 * el elemento eliminado seg�n la llave
	 */
	@Test
	public void deleteTest1() {
		setUpEscenario1();
		for(int i = 0; i < llaves.getSize();i++) {
			int erased = datos.delete(llaves.getElement(i));
			int elementAtpos = valores.getElement(i);
			assertEquals("el valor borrado no es el correcto",erased,elementAtpos);
		}
	}

	/**
	 * M�todo que prueba que las llaves se esten devolviendo con el m�todo que devuelve un iterador de las 
	 * llaves.
	 */
	@Test
	public void IteratorTest0() {
		setUpEscenario1();
		Iterator<String> itera = datos.keys();
		while(itera.hasNext()) {
			String llave = itera.next();
			boolean encontro = false;
			for(int i = 0; i < llaves.getSize(); i++) {
				if(llave.equals(llaves.getElement(i))) {
					encontro = true;
					break;
				}
			}
			assertTrue("la llave que se devolvio no esta entre las llaves que se introdujeron",encontro);
		}
	}
	/**
	 * M�todo que rectifica que el m�todo rehash este funcionando correctamente y mantenga la estructura
	 * del HashTable
	 */
	@Test
	public void rehashTest0() {
		setUpEscenario1();
		Class c = datos.getClass();
		Method privateMethod = null;
		try {
			privateMethod = c.getDeclaredMethod("rehash", int.class);
			System.out.println("pasa por aqui");
		}catch(Exception e) {
			e.printStackTrace();
		}
		privateMethod.setAccessible(true);
		try {
			privateMethod.invoke(datos, datos.getSize()*2);
		}catch(Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i < llaves.getSize(); i++) {
			assertEquals("el valor de la llave no corresponde con el valor con el que se creo", valores.getElement(i),datos.get(llaves.getElement(i)));
		}
		int a = llaves.getSize();
		assertEquals("el tama�o deber�a ser igual a la cantidad de llaves", a,datos.getSize());
		a = llaves.getSize();
		assertEquals("el tama�o deber�a ser igual a la cantidad de llaves", a,datos.getSize());
	}
}
