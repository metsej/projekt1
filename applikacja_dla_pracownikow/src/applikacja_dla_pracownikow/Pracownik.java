package applikacja_dla_pracownikow;

import redis.clients.jedis.Jedis;

public class Pracownik {
	
	static public boolean czyPoprawneHasloiLogin (String Haslo, String login) {
		
		Jedis jedis = new Jedis("127.0.0.1");
		
		boolean result = jedis.exists(login) && (jedis.hget(login, "pass") == Haslo); 
		jedis.close();
		return result;
	}

}
