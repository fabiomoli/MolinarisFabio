package it.polito.tdpProvaFinale.BaionettaUpdater.model;

import java.util.HashSet;
import java.util.Set;

public class Mostrina {

	private String mostrina;
	private Set<Articolo> articoli = new HashSet<>();

	public Set<Articolo> getAllArticoli() {
		return articoli;
	}

	public void addArticoli(Articolo articoli) {
		this.articoli.add(articoli);
	}

	public String getMostrina() {
		return mostrina;
	}

	public void setMostrina(String mostrina) {
		this.mostrina = mostrina;
	}

	public Mostrina(String mostrina) {
		super();
		this.mostrina = mostrina;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mostrina == null) ? 0 : mostrina.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mostrina other = (Mostrina) obj;
		if (mostrina == null) {
			if (other.mostrina != null)
				return false;
		} else if (!mostrina.equals(other.mostrina))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.mostrina;
	}

	public void setArticolo(Articolo a) {
		articoli.add(a);
	}

}