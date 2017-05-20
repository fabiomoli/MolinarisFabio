package it.polito.tdpProvaFinale.laBaionettaReader.beans;

public class ParolaChiave {

	private String parola;
	private String link;
	private int peso;

	public ParolaChiave(String parola, String link, int peso) {
		super();
		this.parola = parola;
		this.link = link;
		this.peso = peso;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getParola() {
		return parola;
	}

	public void setParola(String parola) {
		this.parola = parola;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((parola == null) ? 0 : parola.hashCode());
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
		ParolaChiave other = (ParolaChiave) obj;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (parola == null) {
			if (other.parola != null)
				return false;
		} else if (!parola.equals(other.parola))
			return false;
		return true;
	}

}
