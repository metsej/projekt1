package applikacja_dla_pracownikow;

import redis.clients.jedis.Jedis;

public class main_class {
	
	public static void main(String[] args) {
        // Prints "Hello, World" to the terminal window.
		Administrator admin = new Administrator();
		admin.dodajPracownika("Grzegorz", "Klimek", "urukhai22");
		admin.dodajPracownika("Grzegorz", "Klimek", "urukhai22");
		admin.dodajPracownika("Grzegorz", "Klimek", "urukhai22");

		Jedis jedis = new Jedis("127.0.0.1");
		System.out.println(jedis.hget("Glimek", "name"));
		System.out.println(jedis.hget("Glimek", "last_name"));
		System.out.println(jedis.hget("Glimek", "pass"));
				
		System.out.println(jedis.hget("Glimek1", "name"));
		System.out.println(jedis.hget("Glimek1", "last_name"));
		System.out.println(jedis.hget("Glimek1", "pass"));
		
		System.out.println(jedis.hget("Glimek2", "name"));
		System.out.println(jedis.hget("Glimek2", "last_name"));
		System.out.println(jedis.hget("Glimek2", "pass"));


		jedis.close();

    }

}
