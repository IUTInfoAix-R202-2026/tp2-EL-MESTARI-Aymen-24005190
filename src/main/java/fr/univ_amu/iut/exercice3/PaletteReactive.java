package fr.univ_amu.iut.exercice3;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.binding.StringExpression;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PaletteReactive extends Application {

  @Override
  public void start(Stage primaryStage) {
    // TODO: construire le BorderPane, les trois BoutonCouleur, le Pane zone et le Label compteurs
    BorderPane root = new BorderPane();

    BoutonCouleur btnRouge = new BoutonCouleur("Rouge", "red");
    btnRouge.setId("btn-rouge");

    BoutonCouleur btnVert = new BoutonCouleur("Vert", "green");
    btnVert.setId("btn-vert");

    BoutonCouleur btnBleu = new BoutonCouleur("Bleu", "blue");
    btnBleu.setId("btn-bleu");

    HBox hbox = new HBox(btnRouge, btnVert, btnBleu);
    root.setTop(hbox);

    Pane zone = new Pane();
    zone.setId("zone");
    zone.setMinSize(300, 200);
    root.setCenter(zone);

    Label labelCompteurs = new Label();
    labelCompteurs.setId("compteurs");
    root.setBottom(labelCompteurs);

    createBindings(btnRouge, btnVert, btnBleu, zone, labelCompteurs);

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void createBindings(
      BoutonCouleur btnRouge,
      BoutonCouleur btnVert,
      BoutonCouleur btnBleu,
      Pane zone,
      Label labelCompteurs) {
    // TODO: connecter les boutons à la zone et au label via des ChangeListener et des Bindings

    // ChangeListener sur la propriété nbClics pour changer la couleur de la zone
    btnRouge
        .nbClicsProperty()
        .addListener(
            (obs, oldVal, newVal) ->
                zone.setStyle("-fx-background-color: " + btnRouge.getCouleur() + ";"));
    btnVert
        .nbClicsProperty()
        .addListener(
            (obs, oldVal, newVal) ->
                zone.setStyle("-fx-background-color: " + btnVert.getCouleur() + ";"));
    btnBleu
        .nbClicsProperty()
        .addListener(
            (obs, oldVal, newVal) ->
                zone.setStyle("-fx-background-color: " + btnBleu.getCouleur() + ";"));

    // StringExpression pour le texte des compteurs
    StringExpression texteCompteurs =
        Bindings.concat(
            "Rouge: ", btnRouge.nbClicsProperty().asString(),
            " Vert: ", btnVert.nbClicsProperty().asString(),
            " Bleu: ", btnBleu.nbClicsProperty().asString());

    // Somme des trois propriétés pour vérifier si le total est à 0
    NumberBinding total =
        btnRouge.nbClicsProperty().add(btnVert.nbClicsProperty()).add(btnBleu.nbClicsProperty());

    // Expression conditionnelle
    StringBinding texteAvecBienvenue =
        Bindings.when(total.isEqualTo(0)).then("Bienvenue !").otherwise(texteCompteurs);

    // Liaison (bind) entre le label et l'expression conditionnelle
    labelCompteurs.textProperty().bind(texteAvecBienvenue);
  }
}
