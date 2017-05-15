package it.polito.tdpProvaFinale.baionettaUpdater.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import it.polito.tdpProvaFinale.BaionettaUpdater.model.Articolo;
import it.polito.tdpProvaFinale.BaionettaUpdater.model.Mostrina;
import it.polito.tdpProvaFinale.BaionettaUpdater.model.ParolaChiave;
import it.polito.tdpProvaFinale.BaionettaUpdater.model.Penna;

public class ArticoloDAO {

	public Set<Articolo> getAllArticoli() {
		final String sql = "SELECT titolo, mostrina, penna, link, data FROM articolo";
		Set<Articolo> articoli = new HashSet<Articolo>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			System.out.println("leggo dal database");

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

	public void addArticolo(Articolo a) {
		final String sql = "INSERT INTO articolo (titolo, mostrina, penna, link, data)" + " VALUES (?, ?, ?, ?, ?)";
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			System.out.println(a.getData().format(DateTimeFormatter.BASIC_ISO_DATE));

			st.setString(1, a.getTitolo());
			st.setString(2, a.getMostrina().getMostrina());
			st.setString(3, a.getPenna().getNome());
			st.setString(4, a.getLink());
			String data = "" + a.getData().getYear() + "-" + a.getData().getMonthValue() + "-"
					+ a.getData().getDayOfMonth();
			st.setDate(5, Date.valueOf(data));

			st.executeUpdate();

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
	}

	public void addParoleChiave(ParolaChiave pc) {
		final String sql = "INSERT INTO parolaChiave (parola, link, peso)" + " VALUES (?, ?, ?)";
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, pc.getParola());
			st.setString(2, pc.getLink());
			st.setInt(3, pc.getPeso());

			st.executeUpdate();

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
	}

	public Set<ParolaChiave> getAllParoleChiave(Articolo a) {

		final String sql = "SELECT parola, link, peso FROM parolaChiave WHERE link=?";

		Set<ParolaChiave> paroleChiave = new HashSet<ParolaChiave>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, a.getLink());

			ResultSet rs = st.executeQuery();

			System.out.println("leggo dal database le parole chiave");

			while (rs.next()) {
				ParolaChiave pc = new ParolaChiave(rs.getString("parola"), rs.getString("link"), rs.getInt("peso"));
				paroleChiave.add(pc);
				System.out.println(pc.getParola());
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return paroleChiave;

	}
}
