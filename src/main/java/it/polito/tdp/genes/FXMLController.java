package it.polito.tdp.genes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnStatistiche;

    @FXML
    private Button btnRicerca;

    @FXML
    private ComboBox<String> boxLocalizzazione;

    @FXML
    private TextArea txtResult;

    @FXML
    void doRicerca(ActionEvent event) {
    	if(!boxLocalizzazione.getValue().equals("")) {
    		txtResult.setText(model.getpercorsomax(boxLocalizzazione.getValue()).toString());
    		txtResult.appendText("\n"+model.getBest());
    	}
    }

    @FXML
    void doStatistiche(ActionEvent event) {
    	if(!boxLocalizzazione.getValue().equals("")) {
    		txtResult.setText("adiacenti a"+ boxLocalizzazione.getValue()+"\n"+model.fornisciStat(boxLocalizzazione.getValue()));
    	}
    }

    @FXML
    void initialize() {
        assert btnStatistiche != null : "fx:id=\"btnStatistiche\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxLocalizzazione != null : "fx:id=\"boxLocalizzazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        

    }

	public void setModel(Model model) {
		this.model = model;
		boxLocalizzazione.getItems().addAll(model.getLocalization());
		model.creagrafo();
    	txtResult.setText("numero di archi :"+model.getNarchi()+" numero di vertici:"+model.getNvertici());
	}
}
