package applikacja_dla_pracownikow;

public class test_pracownik {
	
	public static void main (String[] args) {
		
		System.out.print("Test na poprawnosc logowania = "); 
		System.out.println( Pracownik.czyPoprawneHasloiLogin("7cbflbskn72fqjOPQR9d06to", "bświerzyński") == true );
	}

}
