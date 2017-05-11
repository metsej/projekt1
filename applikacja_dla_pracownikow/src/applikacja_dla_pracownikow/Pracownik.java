package applikacja_dla_pracownikow;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import redis.clients.jedis.Jedis;

public class Pracownik {
	
	private String login;
	private String czasRozpoczecia;
	
	// w konstruktorze pracownika  
	//zapisywany jest do bazy danych  czas zaczecia pracy
	public Pracownik (String log) {
		Jedis jedis = new Jedis("127.0.0.1");
		
		login = log;
		/*
		haslo = jedis.hget(login, "pass");
		imie = jedis.hget(login, "name");
		nazwisko = jedis.hget(login, "last_name");
		*/
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		czasRozpoczecia = dtf.format(now);
		
		jedis.hset(log, "start", czasRozpoczecia);
		
		jedis.close();
	}
	
	//podczas wylogowania zapisany jest do bazy danych czas wyjscia pracownika
	public void wyloguj () {
		
		Jedis jedis = new Jedis("127.0.0.1");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String czasZakonczenia = dtf.format(now);
		
		jedis.hset(login, "stop", czasZakonczenia);
		
		jedis.close();
		
	}
	
	static public boolean czyPoprawneHasloiLogin (String Haslo, String login) {
		
		Jedis jedis = new Jedis("127.0.0.1");	
		boolean result =  jedis.exists(login)   &&  jedis.hget(login, "pass").equals(Haslo); 
		jedis.close();
		return result;
	}

}
