package it.polito.tdpProvaFinale.laBaionettaReader.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import it.polito.tdpProvaFinale.laBaionettaReader.beans.Articolo;
import it.polito.tdpProvaFinale.laBaionettaReader.beans.Mostrina;
import it.polito.tdpProvaFinale.laBaionettaReader.beans.ParolaChiave;
import it.polito.tdpProvaFinale.laBaionettaReader.beans.Penna;

public class ArticoloDAO {

	private Set<Articolo> articoli = new HashSet<>();
	private Set<Penna> penne = new HashSet<>();
	private Set<Mostrina> mostrine = new HashSet<>();

	public Set<Articolo> getAll() {

		final String sql = "SELECT titolo, mostrina, penna, articolo.link, parolaChiave.parola, data, peso FROM articolo, parolaChiave where parolaChiave.link = articolo.link";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Mostrina m = new Mostrina(rs.getString("mostrina"));
				Penna p = new Penna(rs.getString("penna"));
				ParolaChiave pc = new ParolaChiave(rs.getString("parola"), rs.getString("articolo.link"), rs.getInt("peso"));

				Articolo a = new Articolo(rs.getString("titolo"), m, p, rs.getString("link"), (rs.getDate("data").toLocalDate()));

				if (articoli.contains(a)) {
					a.setMostrina(m);
					a.setPenna(p);
					a.addParolaChiave(pc);
				}
				if (!articoli.contains(a)) {
					articoli.add(a);
					a.setMostrina(m);
					a.setPenna(p);
					a.addParolaChiave(pc);
				}

				penne.add(p);
				mostrine.add(m);

			}
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return articoli;
	}

	public Set<Articolo> getAllArticoli() {

		if(!articoli.isEmpty())
			return articoli;
		else{
			getAll();
			return getAllArticoli();
		}
	}

	public Set<Penna> getAllPenne() {

		if(!penne.isEmpty())
			return penne;
		else{
			getAll();
			return getAllPenne();
		}
	}

	public Set<Mostrina> getAllMostrine() {

		if(!mostrine.isEmpty())
			return mostrine;
		else{
			getAll();
			return getAllMostrine();
		}
	}

}