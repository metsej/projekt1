package org.wwsis.worker.dataAccess.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.wwsis.worker.data.Pracownik;
import org.wwsis.worker.dataAccess.DataAccess;

import redis.clients.jedis.Jedis;

public class JadisDataAccess implements DataAccess {

	public String hostName;

	private Jedis connection;

	public JadisDataAccess(String hostName) {
		this.hostName = hostName;
		connection = new Jedis(hostName);

	}

	@Override
	public boolean czyPracownikIstnieje(Pracownik p) {

		return connection.exists(p.getLogin());
	}

	@Override
	public Pracownik wczytajPracownika(Pracownik p) {
		String klucz = p.getLogin();

		p.setImie(connection.hget(klucz, "name"));
		p.setNazwisko(connection.hget(klucz, "last_name"));
		p.setHaslo(connection.hget(klucz, "pass"));

		String czyZalogowany = connection.hget(klucz, "czyZalogowany");
		if (czyZalogowany != null) {
			p.setZalogowany(czyZalogowany.equals("true"));
		}

		String strCzasRozpoczecia = connection.hget(klucz, "start");
		if (strCzasRozpoczecia != null) {
			p.setCzasRozpoczecia(strCzasRozpoczecia);
		}
		String strCzasZakonczenia = connection.hget(klucz, "stop");
		if (strCzasZakonczenia != null) {
			
			p.setCzasZakonczenia(strCzasZakonczenia);
		}
		
		return p;
	}

	@Override
	public void zapiszPracownika(Pracownik p) {
		String klucz = p.getLogin();
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/ddHH:mm:ss");

		if (p.getImie() != null) {
			connection.hset(klucz, "name", p.getImie());
		}
		if (p.getNazwisko() != null) {
			connection.hset(klucz, "last_name", p.getNazwisko());
		}
		if (p.getHaslo() != null) {
			connection.hset(klucz, "pass", p.getHaslo());
		}
		if (p.getZalogowany() != null) {
			connection.hset(klucz, "czyZalogowany", Boolean.toString(p.getZalogowany()));
		}
		if (p.getCzasRozpoczecia() != null) {
			connection.hset(klucz, "start", p.getCzasRozpoczecia());
		}
		if (p.getCzasZakonczenia() != null) {
			connection.hset(klucz, "stop", p.getCzasZakonczenia());
		}
	}

	@Override
	public List<Pracownik> listaPracownikow() {
		Set<String> Loginy = connection.keys("*");
		List<Pracownik> wynik = new ArrayList<Pracownik>();
		for (String s : Loginy) {
			wynik.add(wczytajPracownika(Pracownik.zLoginem(s)));
		}
		return wynik;
	}
	
	@Override
	public void close() {
		connection.close();

	}
	
	@Override
	public void erase() {
		connection.flushAll();
	}

}
