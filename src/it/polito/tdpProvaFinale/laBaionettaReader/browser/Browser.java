package it.polito.tdpProvaFinale.laBaionettaReader.browser;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import it.polito.tdpProvaFinale.laBaionettaReader.beans.Articolo;
import it.polito.tdpProvaFinale.laBaionettaReader.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Browser extends Application {

	private Model model;
	private String url;
	private Articolo articolo;

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Browser.fxml"));
			BorderPane root = (BorderPane) loader.load();

			BrowserController controller = loader.getController();
			
			controller.setModel(model);
			controller.setUrl(url);
			controller.setArticolo(articolo);

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("La Baionetta Browser");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}
}