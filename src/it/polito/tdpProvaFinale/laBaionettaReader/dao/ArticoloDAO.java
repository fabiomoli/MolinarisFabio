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

	public Set<Articolo> getAllArticoli() {

		final String sql = "SELECT titolo, mostrina, penna, link, data FROM articolo";

		Set<Articolo> articoli = new HashSet<>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Articolo a = new Articolo(rs.getString("titolo"), new Mostrina(rs.getString("mostrina")),
						new Penna(rs.getString("penna")), rs.getString("link"), (rs.getDate("data").toLocalDate()));
				articoli.add(a);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return articoli;
	}

	public Set<Penna> getAllPenne() {

		final String sql = "SELECT nome FROM penna";

		Set<Penna> penne = new HashSet<>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Penna p = new Penna(rs.getString("nome"));
				penne.add(p);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return penne;
	}

	public Set<Mostrina> getAllMostrine() {

		final String sql = "SELECT nome FROM mostrina";

		Set<Mostrina> mostrine = new HashSet<>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Mostrina m = new Mostrina(rs.getString("nome"));
				mostrine.add(m);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return mostrine;
	}

	public Set<ParolaChiave> getParoleChiave(Articolo a) {

		final String sql = "SELECT parola, peso FROM parolaChiave where link = ?;";

		Set<ParolaChiave> parole = new HashSet<>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, a.getLink());

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				ParolaChiave p = new ParolaChiave(rs.getString("parola"), a.getLink(), rs.getInt("peso"));
				parole.add(p);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return parole;
	}
}