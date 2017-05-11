package applikacja_dla_pracownikow;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import redis.clients.jedis.Jedis;

public class Administrator {
		
	Administrator()  { System.out.println("Administrator is created");}
	
	public static void logowanie (String login, String password ) {System.out.println("administrtor i logged");}
	
	public static boolean czyAdmin (String login, String password) {
		return (login == "admin" && password == "admin");
	}
	
	public static void dodajPracownika (String imie, String nazwisko) {
		String login = imie.substring(0, 1) + nazwisko.substring(0, nazwisko.length()) ;
		login = login.toLowerCase();
		
		Jedis jedis = new Jedis("127.0.0.1");
		String new_login = zwrocDostepnyLogin (login);
		String haslo = generujHaslo ();
		
		
		
		
		jedis.hset(new_login, "name", imie);
		jedis.hset(new_login, "last_name", nazwisko);
		jedis.hset(new_login, "pass", haslo);
		
		jedis.close();		
			
		}
	
	
	public static String generujHaslo () {
		int randomNum = ThreadLocalRandom.current().nextInt(0, 15 );
		String alfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String insert = alfabet.substring(randomNum, (randomNum + 4));
		
		SecureRandom random = new SecureRandom();
		 String random_pass =  new BigInteger(  100 , random).toString(32);
		 String potencjalneHaslo = (random_pass.substring(0, randomNum) + insert + random_pass.substring(randomNum, random_pass.length()) );
		 
		 while (!czyHasloPoprawne(potencjalneHaslo)) {
			 potencjalneHaslo = generujHaslo ();
			}
		 
		 return potencjalneHaslo;
		
	} 
	
	public static void zmienHaslo (String login) {
		
		Jedis jedis = new Jedis("127.0.0.1");
		
		String noweHaslo = generujHaslo();
		
		jedis.hset(login, "pass", noweHaslo);
		
		jedis.close();
	}
	
	public static  Set <String> zwrocPracownikow () {
		 
		Jedis jedis = new Jedis("127.0.0.1");
		
		Set <String> result = jedis.keys("*");
		
		jedis.close();
		
		return result;
		
		
	}
	
	private static String zwrocDostepnyLogin (String login ) {
		String potLogin = login;
		
		Jedis jedis = new Jedis("127.0.0.1");
		
		while (jedis.hexists(potLogin, "name")) {
			
			if (potLogin.length() > login.length() ) {
				String str_num = potLogin.substring(login.length(), potLogin.length());
				int num = Integer.valueOf(str_num).intValue(); 
				num = num + 1;
				str_num = Integer.toString(num);
				potLogin = login.toLowerCase() + str_num;
			} else {
				potLogin = login.toLowerCase() + "1";
				continue;
			}
		}
		jedis.close();
		
		
		
		return potLogin;	
	}
	
	private static boolean czyHasloPoprawne (String haslo) {
		boolean czyMaMalaLitere = false;
		boolean czyMaDuzaLitere = false;
		boolean czyMaLiczbe = false;
		
		for (int i = 0; i < haslo.length(); i++ ) {
			char c = haslo.charAt(i);
			
			if (Character.isUpperCase(c) ) {
				czyMaDuzaLitere = true;
			}
			
			if (Character.isLowerCase(c) ) {
				czyMaMalaLitere = true;
			}
			
			if (Character.isDigit(c) ) {
				czyMaLiczbe = true;
			}
			
			if (czyMaMalaLitere && czyMaDuzaLitere &&  czyMaLiczbe) {
				return true;
			}
			
		}
			return false;
	}
}
