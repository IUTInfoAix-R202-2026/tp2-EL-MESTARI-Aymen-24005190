package fr.univ_amu.iut.exercice8;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * Exercice 8 (capstone) - Convertisseur de températures.
 *
 * <p>Cet exercice synthétise tous les types de bindings vus dans le TP :
 *
 * <ul>
 *   <li>Binding unidirectionnel (Labels de lecture)
 *   <li>Binding bidirectionnel (TextField ↔ Slider via {@link NumberStringConverter})
 *   <li>{@code ChangeListener} pour la conversion avec formule (C = (F-32)*5/9)
 *   <li>Sliders verticaux ({@code Orientation.VERTICAL})
 * </ul>
 *
 * <p>L'application affiche deux panneaux côte à côte : un pour Celsius, un pour Fahrenheit.
 * Modifier l'un met à jour l'autre automatiquement.
 */
public class ConvertisseurTemperatures extends Application {

  private boolean updating = false;

  @Override
  public void start(Stage primaryStage) {
    // 1. Créer le panneau Celsius (VBox)
    Label labelCelsius = new Label("°C");
    labelCelsius.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

    Slider sliderCelsius = new Slider(0, 100, 0);
    sliderCelsius.setOrientation(Orientation.VERTICAL);
    sliderCelsius.setId("slider-celsius");

    TextField tfCelsius = new TextField();
    tfCelsius.setId("tf-celsius");
    tfCelsius.setMaxWidth(50);

    VBox paneCelsius = new VBox(10, labelCelsius, sliderCelsius, tfCelsius);
    paneCelsius.setAlignment(Pos.CENTER);
    paneCelsius.setPadding(new Insets(15));

    // 2. Créer le panneau Fahrenheit (VBox)
    Label labelFahrenheit = new Label("°F");
    labelFahrenheit.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

    Slider sliderFahrenheit = new Slider(0, 212, 32);
    sliderFahrenheit.setOrientation(Orientation.VERTICAL);
    sliderFahrenheit.setId("slider-fahrenheit");

    TextField tfFahrenheit = new TextField();
    tfFahrenheit.setId("tf-fahrenheit");
    tfFahrenheit.setMaxWidth(50);

    VBox paneFahrenheit = new VBox(10, labelFahrenheit, sliderFahrenheit, tfFahrenheit);
    paneFahrenheit.setAlignment(Pos.CENTER);
    paneFahrenheit.setPadding(new Insets(15));

    // 3. Ajouter un ChangeListener sur le slider Celsius
    sliderCelsius
        .valueProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              if (!updating) {
                updating = true;
                // Formule : F = C * 9/5 + 32
                sliderFahrenheit.setValue(newVal.doubleValue() * 9.0 / 5.0 + 32.0);
                updating = false;
              }
            });

    // 4. Ajouter un ChangeListener sur le slider Fahrenheit
    sliderFahrenheit
        .valueProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              if (!updating) {
                updating = true;
                // Formule : C = (F - 32) * 5/9
                sliderCelsius.setValue((newVal.doubleValue() - 32.0) * 5.0 / 9.0);
                updating = false;
              }
            });

    // 5. Lier chaque TextField à son slider via un binding bidirectionnel
    Bindings.bindBidirectional(
        tfCelsius.textProperty(), sliderCelsius.valueProperty(), new NumberStringConverter());
    Bindings.bindBidirectional(
        tfFahrenheit.textProperty(), sliderFahrenheit.valueProperty(), new NumberStringConverter());

    // 6. Composer les panneaux dans un HBox, créer la Scene, afficher
    HBox root = new HBox(20, paneCelsius, paneFahrenheit);
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(20));

    Scene scene = new Scene(root, 250, 350);
    primaryStage.setTitle("Convertisseur Température");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
