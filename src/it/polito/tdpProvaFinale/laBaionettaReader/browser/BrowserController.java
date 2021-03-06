package it.polito.tdpProvaFinale.laBaionettaReader.browser;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.URL;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdpProvaFinale.laBaionettaReader.beans.Articolo;
import it.polito.tdpProvaFinale.laBaionettaReader.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

public class BrowserController {

	private Model model;
	List<Articolo> articoli;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private WebView web;

	@FXML
	private Label lblTitolo;

	@FXML
	private Label lblData;

	@FXML
	private Label lblPenna;

	@FXML
	private Label lblSimile0;

	@FXML
	private Label lblSimile1;

	@FXML
	private Label lblSimile2;

	@FXML
	private Label lblSimile3;

	@FXML
	private Label lblSimile4;

	@FXML
	void doLeggi0(ActionEvent event) {
		doLeggi(0);
	}

	@FXML
	void doLeggi1(ActionEvent event) {
		doLeggi(1);
	}

	@FXML
	void doLeggi2(ActionEvent event) {
		doLeggi(2);
	}

	@FXML
	void doLeggi3(ActionEvent event) {
		doLeggi(3);
	}

	@FXML
	void doLeggi4(ActionEvent event) {
		doLeggi(4);
	}

	@FXML
	void initialize() {
		assert web != null : "fx:id=\"web\" was not injected: check your FXML file 'Browser.fxml'.";
		assert lblTitolo != null : "fx:id=\"lblTitolo\" was not injected: check your FXML file 'Browser.fxml'.";
		assert lblData != null : "fx:id=\"lblData\" was not injected: check your FXML file 'Browser.fxml'.";
		assert lblPenna != null : "fx:id=\"lblPenna\" was not injected: check your FXML file 'Browser.fxml'.";
		assert lblSimile0 != null : "fx:id=\"lblSimile0\" was not injected: check your FXML file 'Browser.fxml'.";
		assert lblSimile1 != null : "fx:id=\"lblSimile1\" was not injected: check your FXML file 'Browser.fxml'.";
		assert lblSimile2 != null : "fx:id=\"lblSimile2\" was not injected: check your FXML file 'Browser.fxml'.";
		assert lblSimile3 != null : "fx:id=\"lblSimile3\" was not injected: check your FXML file 'Browser.fxml'.";
		assert lblSimile4 != null : "fx:id=\"lblSimile4\" was not injected: check your FXML file 'Browser.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void setUrl(String url) {
		URI uri = URI.create(url);
		Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
		headers.put("Set-Cookie", Arrays.asList("name=value"));
		try {
			CookieHandler.getDefault().put(uri, headers);
			CookieManager manager = new CookieManager();
			manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		web.getEngine().load(url);
	}

	private void read(String url){
		web.getEngine().load(url);
	}

	public void setArticolo(Articolo a) {

		lblTitolo.setText(a.getTitolo());

		lblData.setText(a.getData().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALY) + " "
				+ a.getData().getDayOfMonth() + " - "
				+ a.getData().getMonth().getDisplayName(TextStyle.FULL, Locale.ITALY) + " - " + a.getData().getYear());

		lblPenna.setText(a.getPenna().getNome());

		articoli = new ArrayList<>(model.getArticoliSimili(a));

		lblSimile0.setText(articoli.get(0).getTitolo());
		lblSimile1.setText(articoli.get(1).getTitolo());
		lblSimile2.setText(articoli.get(2).getTitolo());
		lblSimile3.setText(articoli.get(3).getTitolo());
		lblSimile4.setText(articoli.get(4).getTitolo());
	}

	private void doLeggi(int i) {
		read(articoli.get(i).getLink() + "?m=1");
		setArticolo(articoli.get(i));
		try {
			model.segnaComeLetto(articoli.get(i));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
