package applikacja_dla_pracownikow;

import redis.clients.jedis.Jedis;

public class Administrator {
		
	Administrator()  { System.out.println("Administrator is created");}
	
	public static void logowanie (String login, String password ) {System.out.println("administrtor i logged");}	
	
	public static void dodajPracownika (String imie, String nazwisko, String haslo ) {
		String login = imie.substring(0, 1) + nazwisko.substring(1, nazwisko.length()) ;
		login = login.toLowerCase();
		
		Jedis jedis = new Jedis("127.0.0.1");
		String new_login = zwrocDostepnyLogin (login);
		System.out.println("login for " + imie + " " + nazwisko + " = " + login);
		System.out.println("new login for " + imie + " " + nazwisko + " = " + new_login);
		
		
		jedis.hset(new_login, "name", imie);
		jedis.hset(new_login, "last_name", nazwisko);
		jedis.hset(new_login, "pass", haslo);
		
		jedis.close();		
			
		}
	
	
	public static String generujHaslo () {
		
		
		return "haslo";
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
		

}
