package applikacja_dla_pracownikow;


import redis.clients.jedis.Jedis;


public class test_pracownik {
	
	public static void main (String[] args) {
		
		Jedis jedis = new Jedis("127.0.0.1");
		System.out.print("Test na poprawnosc logowania = "); 
		System.out.println( Pracownik.czyPoprawneHasloiLogin("7cbflbskn72fqjOPQR9d06to", "bświerzyński") == true );
		
		// przy stworzeniu nowego pracownika  
		//zapisywany jest do bazy danych  czas zaczecia pracy
		Pracownik nowy_pracownik = new Pracownik ("gklimek");
		
		// uzyte obliczanie fibonnachiego w celu wywolaniu roznicy czasu pomiedzy zalogowaniem a wylogowaniem
		int a = fibonachi (47);
		
		// przy wylogowaniu zapisywany jest do bazy danych  czas koniec pracy
		nowy_pracownik.wyloguj();
		System.out.println(jedis.hget("gklimek", "start"));
		System.out.println(jedis.hget("gklimek", "stop"));
		jedis.close();
	}
	
	// fibonachi jest używany ponieważ jego obliczenie trwa trochę czasu, wiec można sprawdzić czy jest róznica pomiedzy czasem 
	// zaczecia pracy a czasem skonczenia pracy
	  static int fibonachi (int n) {
		 if ( n <= 1) {
			 return n;
		 } else {
			 return (fibonachi(n -1) + fibonachi(n - 2));
		 }
	 }

}
