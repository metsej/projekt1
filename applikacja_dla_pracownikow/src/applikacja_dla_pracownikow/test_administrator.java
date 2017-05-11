package applikacja_dla_pracownikow;

import redis.clients.jedis.Jedis;

public class test_administrator {
	
	public static void main (String[] args) {
		/*
		Administrator.dodajPracownika("Grzegorz", "Klimek");
		Administrator.dodajPracownika("Grzegorz", "Klimek");
		Administrator.dodajPracownika("Edwin", "Langaj");
		Administrator.dodajPracownika("Lukasz", "Ciesielski");
		Administrator.dodajPracownika("Bartłomiej ", "Świerzyński");
		*/
		
		Jedis jedis = new Jedis("127.0.0.1");
		
		
		for (String s : Administrator.zwrocPracownikow()) {
			System.out.println("login: " + s);
			System.out.println("imie: " + jedis.hget(s, "name"));
		    System.out.println("naziwsko: " + jedis.hget(s, "last_name"));
		    System.out.println("haslo: " + jedis.hget(s, "pass"));
		    System.out.println(" ");
		}
	
		//jedis.flushAll();

		jedis.close();

    }

}
