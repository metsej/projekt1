package applikacja_dla_pracownikow;

import redis.clients.jedis.Jedis;

public class test_administrator {
	
	public static void main (String[] args) {
        // Prints "Hello, World" to the terminal window.
	
		Administrator.dodajPracownika("Grzegorz", "Klimek", "urukhai22");
		Administrator.dodajPracownika("Grzegorz", "Klimek", "urukhai22");
		Administrator.dodajPracownika("Grzegorz", "Klimek", "urukhai22");

		Jedis jedis = new Jedis("127.0.0.1");
		
		/*
		System.out.println(jedis.hget("GLIMEK", "name"));
		System.out.println(jedis.hget("GLIMEK", "last_name"));
		System.out.println(jedis.hget("GLIMEK", "pass"));
				
		System.out.println(jedis.hget("GLIMEK1", "name"));
		System.out.println(jedis.hget("GLIMEK1", "last_name"));
		System.out.println(jedis.hget("GLIMEK1", "pass"));
		
		System.out.println(jedis.hget("GLIMEK2", "name"));
		System.out.println(jedis.hget("GLIMEK2", "last_name"));
		System.out.println(jedis.hget("GLIMEK2", "pass"));
		*/
		
		jedis.flushAll();


		jedis.close();

    }

}
