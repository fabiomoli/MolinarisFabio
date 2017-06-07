package it.polito.tdpProvaFinale.laBaionettaReader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import it.polito.tdpProvaFinale.laBaionettaReader.beans.Articolo;
import it.polito.tdpProvaFinale.laBaionettaReader.beans.Mostrina;
import it.polito.tdpProvaFinale.laBaionettaReader.beans.Penna;
import it.polito.tdpProvaFinale.laBaionettaReader.browser.Browser;
import it.polito.tdpProvaFinale.laBaionettaReader.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class laBaionettaController {

	Model model;

	private int N = 0;

	private List<Articolo> articoli;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label lblRicerca;

	@FXML
	private Label lblTitolo0;

	@FXML
	private Button btnLeggi0;

	@FXML
	private Label lblTitolo1;

	@FXML
	private Button btnLeggi1;

	@FXML
	private Label lblTitolo2;

	@FXML
	private Button btnLeggi2;

	@FXML
	private Label lblTitolo3;

	@FXML
	private Button btnLeggi3;

	@FXML
	private Label lblTitolo4;

	@FXML
	private Button btnLeggi4;

	@FXML
	private TextField txtTitolo;

	@FXML
	private ComboBox<Penna> cbxAutore;

	@FXML
	private ComboBox<Mostrina> cbxMostrina;

	@FXML
	private DatePicker cbxData;

	@FXML
	private Label lblPagine;

	@FXML
	private Button btnRecenti;

	@FXML
	private Button btnVecchi;

	@FXML
	private Button btnReset;

	@FXML
	void doCerca(ActionEvent event) {
		articoli.clear();
		List<Articolo> risultatoRicerca = new ArrayList<>(
				model.ricerca(txtTitolo.getText(), cbxAutore.getValue(), cbxMostrina.getValue(), cbxData.getValue()));
		articoli.addAll(risultatoRicerca);
		set();
		btnReset.setDisable(false);
	}

	@FXML
	void doLeggi0(ActionEvent event) {
		doLeggi(4);
		lblTitolo0.setStyle("-fx-font-weight: normal");
	}

	@FXML
	void doLeggi1(ActionEvent event) {
		doLeggi(3);
		lblTitolo1.setStyle("-fx-font-weight: normal");
	}

	@FXML
	void doLeggi2(ActionEvent event) {
		doLeggi(2);
		lblTitolo2.setStyle("-fx-font-weight: normal");
	}

	@FXML
	void doLeggi3(ActionEvent event) {
		doLeggi(1);
		lblTitolo3.setStyle("-fx-font-weight: normal");
	}

	@FXML
	void doLeggi4(ActionEvent event) {
		doLeggi(0);
		lblTitolo4.setStyle("-fx-font-weight: normal");
	}

	@FXML
	void doRecenti(ActionEvent event) {
		N -= 9;
		if (N <= 0) {
			N = 0;
			btnRecenti.setDisable(true);
		}
		lblTitolo0.setDisable(false);
		btnLeggi0.setDisable(false);
		lblTitolo0.setText(articoli.get(N).toString());
		if (!model.isLetto(articoli.get(N).getLink()))
			lblTitolo0.setStyle("-fx-font-weight: bold");
		if (model.isLetto(articoli.get(N).getLink()))
			lblTitolo0.setStyle("-fx-font-weight: normal");
		N++;
		lblTitolo1.setDisable(false);
		btnLeggi1.setDisable(false);
		lblTitolo1.setText(articoli.get(N).toString());
		if (!model.isLetto(articoli.get(N).getLink()))
			lblTitolo1.setStyle("-fx-font-weight: bold");
		if (model.isLetto(articoli.get(N).getLink()))
			lblTitolo1.setStyle("-fx-font-weight: normal");
		N++;
		lblTitolo2.setDisable(false);
		btnLeggi2.setDisable(false);
		lblTitolo2.setText(articoli.get(N).toString());
		if (!model.isLetto(articoli.get(N).getLink()))
			lblTitolo2.setStyle("-fx-font-weight: bold");
		if (model.isLetto(articoli.get(N).getLink()))
			lblTitolo2.setStyle("-fx-font-weight: normal");
		N++;
		lblTitolo3.setDisable(false);
		btnLeggi3.setDisable(false);
		lblTitolo3.setText(articoli.get(N).toString());
		if (!model.isLetto(articoli.get(N).getLink()))
			lblTitolo3.setStyle("-fx-font-weight: bold");
		if (model.isLetto(articoli.get(N).getLink()))
			lblTitolo3.setStyle("-fx-font-weight: normal");
		N++;
		lblTitolo4.setDisable(false);
		btnLeggi4.setDisable(false);
		lblTitolo4.setText(articoli.get(N).toString());
		if (!model.isLetto(articoli.get(N).getLink()))
			lblTitolo4.setStyle("-fx-font-weight: bold");
		if (model.isLetto(articoli.get(N).getLink()))
			lblTitolo4.setStyle("-fx-font-weight: normal");

		if (N <= articoli.size())
			btnVecchi.setDisable(false);
		if (N >= articoli.size())
			btnVecchi.setDisable(true);

		lblPagine.setText("pagina " + (N / 5 + 1) + " di " + (articoli.size() / 5 + 1));

	}

	@FXML
	void doReset(ActionEvent event) {
		reSet();
	}


    @FXML
    void doSegnaTuttoComeLetto(ActionEvent event) {
    	for(Articolo a : articoli)
			try {
				model.segnaComeLetto(a);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	reSet();
    }

	@FXML
	void doVecchi(ActionEvent event) {
		N++;
		if (N < articoli.size()) {
			lblTitolo0.setText(articoli.get(N).toString());
			if (!model.isLetto(articoli.get(N).getLink()))
				lblTitolo0.setStyle("-fx-font-weight: bold");
			if (model.isLetto(articoli.get(N).getLink()))
				lblTitolo0.setStyle("-fx-font-weight: normal");
		} else {
			lblTitolo0.setText("");
			lblTitolo0.setDisable(true);
			btnLeggi0.setDisable(true);
		}
		N++;
		if (N < articoli.size()) {
			lblTitolo1.setText(articoli.get(N).toString());
			if (!model.isLetto(articoli.get(N).getLink()))
				lblTitolo1.setStyle("-fx-font-weight: bold");
			if (model.isLetto(articoli.get(N).getLink()))
				lblTitolo1.setStyle("-fx-font-weight: normal");
		} else {
			lblTitolo1.setText("");
			lblTitolo1.setDisable(true);
			btnLeggi1.setDisable(true);
		}
		N++;
		if (N < articoli.size()) {
			lblTitolo2.setText(articoli.get(N).toString());
			if (!model.isLetto(articoli.get(N).getLink()))
				lblTitolo2.setStyle("-fx-font-weight: bold");
			if (model.isLetto(articoli.get(N).getLink()))
				lblTitolo2.setStyle("-fx-font-weight: normal");
		} else {
			lblTitolo2.setText("");
			lblTitolo2.setDisable(true);
			btnLeggi2.setDisable(true);
		}
		N++;
		if (N < articoli.size()) {
			lblTitolo3.setText(articoli.get(N).toString());
			if (!model.isLetto(articoli.get(N).getLink()))
				lblTitolo3.setStyle("-fx-font-weight: bold");
			if (model.isLetto(articoli.get(N).getLink()))
				lblTitolo3.setStyle("-fx-font-weight: normal");
		} else {
			lblTitolo3.setText("");
			lblTitolo3.setDisable(true);
			btnLeggi3.setDisable(true);
		}
		N++;
		if (N < articoli.size()) {
			lblTitolo4.setText(articoli.get(N).toString());
			if (!model.isLetto(articoli.get(N).getLink()))
				lblTitolo4.setStyle("-fx-font-weight: bold");
			if (model.isLetto(articoli.get(N).getLink()))
				lblTitolo4.setStyle("-fx-font-weight: normal");
		} else {
			lblTitolo4.setText("");
			lblTitolo4.setDisable(true);
			btnLeggi4.setDisable(true);
		}

		if (N >= 5)
			btnRecenti.setDisable(false);
		if (N >= articoli.size())
			btnVecchi.setDisable(true);
		lblPagine.setText("pagina " + (N / 5 + 1) + " di " + (articoli.size() / 5 + 1));
	}

	@FXML
	void initialize() {
		assert lblRicerca != null : "fx:id=\"lblRicerca\" was not injected: check your FXML file 'laBaioApp.fxml'.";
		assert lblTitolo0 != null : "fx:id=\"lblTitolo0\" was not injected: check your FXML file 'laBaioApp.fxml'.";
		assert lblTitolo1 != null : "fx:id=\"lblTitolo1\" was not injected: check your FXML file 'laBaioApp.fxml'.";
		assert lblTitolo2 != null : "fx:id=\"lblTitolo2\" was not injected: check your FXML file 'laBaioApp.fxml'.";
		assert lblTitolo3 != null : "fx:id=\"lblTitolo3\" was not injected: check your FXML file 'laBaioApp.fxml'.";
		assert lblTitolo4 != null : "fx:id=\"lblTitolo4\" was not injected: check your FXML file 'laBaioApp.fxml'.";
		assert txtTitolo != null : "fx:id=\"txtTitolo\" was not injected: check your FXML file 'laBaioApp.fxml'.";
		assert cbxAutore != null : "fx:id=\"cbxAutore\" was not injected: check your FXML file 'laBaioApp.fxml'.";
		assert cbxMostrina != null : "fx:id=\"cbxMostrina\" was not injected: check your FXML file 'laBaioApp.fxml'.";
		assert cbxData != null : "fx:id=\"cbxData\" was not injected: check your FXML file 'laBaioApp.fxml'.";
		assert btnRecenti != null : "fx:id=\"btnRecenti\" was not injected: check your FXML file 'laBaioApp.fxml'.";
		assert btnVecchi != null : "fx:id=\"btnVecchi\" was not injected: check your FXML file 'laBaioApp.fxml'.";
		assert lblPagine != null : "fx:id=\"lblPagine\" was not injected: check your FXML file 'laBaioApp.fxml'.";
		assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'laBaioApp.fxml'.";
	}

	public void setModel(Model model) {

		Locale.setDefault(Locale.ITALY);

		this.model = model;
		model.initialize();

		cbxAutore.getItems().addAll(model.getAllPenne());
		cbxMostrina.getItems().addAll(model.getAllMostrine());

		reSet();
	}

	private void reSet() {

		articoli = new ArrayList<>(model.getAllArticoliOrderByDate());

		set();

		btnVecchi.setDisable(false);
		btnReset.setDisable(true);
		txtTitolo.clear();
		cbxAutore.getSelectionModel().clearSelection();
		cbxMostrina.getSelectionModel().clearSelection();
		cbxData.setValue(null);

	}

	private void set() {

		int nuovi = 0;
		try {
			nuovi = model.getNumArtAggiornati();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		lblRicerca.setText("Caricati " + articoli.size() + " articoli (" + nuovi + " nuovi)");

		N = 0;
		if (N < articoli.size()) {
			lblTitolo0.setDisable(false);
			btnLeggi0.setDisable(false);
			lblTitolo0.setText(articoli.get(N).toString());
			if (!model.isLetto(articoli.get(N).getLink()))
				lblTitolo0.setStyle("-fx-font-weight: bold");
			if (model.isLetto(articoli.get(N).getLink()))
				lblTitolo0.setStyle("-fx-font-weight: normal");
		} else {
			lblTitolo0.setText("");
			lblTitolo0.setDisable(true);
			btnLeggi0.setDisable(true);
		}
		N = 1;
		if (N < articoli.size()) {
			lblTitolo1.setDisable(false);
			btnLeggi1.setDisable(false);
			lblTitolo1.setText(articoli.get(N).toString());
			if (!model.isLetto(articoli.get(N).getLink()))
				lblTitolo1.setStyle("-fx-font-weight: bold");
			if (model.isLetto(articoli.get(N).getLink()))
				lblTitolo1.setStyle("-fx-font-weight: normal");
		} else {
			lblTitolo1.setText("");
			lblTitolo1.setDisable(true);
			btnLeggi1.setDisable(true);
		}
		N = 2;
		if (N < articoli.size()) {
			lblTitolo2.setDisable(false);
			btnLeggi2.setDisable(false);
			lblTitolo2.setText(articoli.get(N).toString());
			if (!model.isLetto(articoli.get(N).getLink()))
				lblTitolo2.setStyle("-fx-font-weight: bold");
			if (model.isLetto(articoli.get(N).getLink()))
				lblTitolo2.setStyle("-fx-font-weight: normal");
		} else {
			lblTitolo2.setText("");
			lblTitolo2.setDisable(true);
			btnLeggi2.setDisable(true);
		}
		N = 3;
		if (N < articoli.size()) {
			lblTitolo3.setDisable(false);
			btnLeggi3.setDisable(false);
			lblTitolo3.setText(articoli.get(N).toString());
			if (!model.isLetto(articoli.get(N).getLink()))
				lblTitolo3.setStyle("-fx-font-weight: bold");
			if (model.isLetto(articoli.get(N).getLink()))
				lblTitolo3.setStyle("-fx-font-weight: normal");
		} else {
			lblTitolo3.setText("");
			lblTitolo3.setDisable(true);
			btnLeggi3.setDisable(true);
		}
		N = 4;
		if (N < articoli.size()) {
			lblTitolo4.setDisable(false);
			btnLeggi4.setDisable(false);
			lblTitolo4.setText(articoli.get(N).toString());
			if (!model.isLetto(articoli.get(N).getLink()))
				lblTitolo4.setStyle("-fx-font-weight: bold");
			if (model.isLetto(articoli.get(N).getLink()))
				lblTitolo4.setStyle("-fx-font-weight: normal");
		} else {
			lblTitolo4.setText("");
			lblTitolo4.setDisable(true);
			btnLeggi4.setDisable(true);
		}

		if (N+1 <= articoli.size())
			btnVecchi.setDisable(false);
		if (N+1 >= articoli.size())
			btnVecchi.setDisable(true);

		lblPagine.setText("pagina " + (N / 5 + 1) + " di " + (articoli.size() / 5 + 1));
	}

	private void doLeggi(int i) {
		Browser b = new Browser();
		b.setModel(model);
		b.setUrl(articoli.get(N - i).getLink() + "?m=1");
		b.setArticolo(articoli.get(N - i));
		b.start(new Stage());
		try {
			model.segnaComeLetto(articoli.get(N - i));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}