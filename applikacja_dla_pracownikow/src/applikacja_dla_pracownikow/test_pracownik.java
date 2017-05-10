package applikacja_dla_pracownikow;

import redis.clients.jedis.Jedis;

public class test_pracownik {
	
	public static void main (String[] args) {
		
		System.out.println( Pracownik.czyPoprawneHasloiLogin("1u964njelvKLMNonudad5jgs", "lciesielski"));
	}

}
