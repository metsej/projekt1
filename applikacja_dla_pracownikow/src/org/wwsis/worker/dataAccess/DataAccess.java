package org.wwsis.worker.dataAccess;

import java.util.List;

import org.wwsis.worker.data.Pracownik;

public interface DataAccess {

	public boolean czyPracownikIstnieje(Pracownik p);

	public Pracownik wczytajPracownika(Pracownik p);

	public void zapiszPracownika(Pracownik p);

	public List<Pracownik> listaPracownikow();
	
	public void close();
	
	public void erase();

}
