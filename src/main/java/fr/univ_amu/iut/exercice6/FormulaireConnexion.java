package fr.univ_amu.iut.exercice6;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Exercice 6 - Formulaire de connexion avec bindings de validation.
 *
 * <p>Cet exercice montre comment les bindings permettent de gérer l'état des contrôles (éditable,
 * disable) de manière déclarative. C'est un exemple concret d'<b>affordance</b> (concept CM2) : les
 * contrôles désactivés communiquent visuellement les exigences à l'utilisateur.
 *
 * <p>Règles de validation :
 *
 * <ul>
 *   <li>Le champ mot de passe n'est éditable que si l'identifiant contient au moins 6 caractères
 *   <li>Le bouton OK n'est actif que si le mot de passe est valide (>= 8 chars, 1 majuscule, 1
 *       chiffre)
 *   <li>Le bouton Annuler est désactivé si les deux champs sont vides
 * </ul>
 *
 * <p>Concepts :
 *
 * <ul>
 *   <li>{@code editableProperty().bind(...)}
 *   <li>{@code disableProperty().bind(...)}
 *   <li>Low-level {@link BooleanBinding} avec {@code computeValue()} personnalisé
 *   <li>Pattern {@code createBindings()}
 * </ul>
 */
public class FormulaireConnexion extends Application {

  private TextField userId;
  private PasswordField pwd;
  private Button okBtn;
  private Button cancelBtn;
  private Label message;

  @Override
  public void start(Stage primaryStage) {
    // 1. Créer un GridPane avec padding 20, hgap 10, vgap 10
    GridPane grille = new GridPane();
    grille.setPadding(new Insets(20));
    grille.setHgap(10);
    grille.setVgap(10);

    // 2. Initialisation et configuration des composants avec leurs IDs
    userId = new TextField();
    userId.setId("user-id");

    pwd = new PasswordField();
    pwd.setId("pwd");

    okBtn = new Button("OK");
    okBtn.setId("btn-ok");

    cancelBtn = new Button("Annuler");
    cancelBtn.setId("btn-cancel");

    message = new Label("");
    message.setId("message");

    // Placement dans la grille (colonne, ligne)
    grille.add(new Label("Identifiant :"), 0, 0);
    grille.add(userId, 1, 0);
    grille.add(new Label("Mot de passe :"), 0, 1);
    grille.add(pwd, 1, 1);
    grille.add(okBtn, 0, 2);
    grille.add(cancelBtn, 1, 2);
    grille.add(message, 0, 3, 2, 1); // colspan 2

    // 3. Appeler createBindings()
    createBindings();

    // 4. Ajouter les handlers d'événements
    okBtn.setOnAction(e -> okClicked());
    cancelBtn.setOnAction(e -> cancelClicked());

    // 5. Créer la Scene, l'attacher au Stage, afficher
    Scene scene = new Scene(grille);
    primaryStage.setTitle("Formulaire de Connexion");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /** Crée les bindings de validation. */
  void createBindings() {
    // 1. Le mot de passe n'est éditable que si userId >= 6 caractères
    pwd.editableProperty().bind(Bindings.greaterThanOrEqual(userId.textProperty().length(), 6));

    // 2. Le bouton Annuler est désactivé si les deux champs sont vides
    cancelBtn
        .disableProperty()
        .bind(
            Bindings.and(
                Bindings.equal(0, pwd.textProperty().length()),
                Bindings.equal(0, userId.textProperty().length())));

    // 3. Le bouton OK est désactivé par un BooleanBinding personnalisé de bas niveau
    BooleanBinding formValide =
        new BooleanBinding() {
          {
            super.bind(userId.textProperty(), pwd.textProperty());
          }

          @Override
          protected boolean computeValue() {
            String p = pwd.getText();
            return userId.getText().length() >= 6
                && p.length() >= 8
                && p.chars().anyMatch(Character::isUpperCase)
                && p.chars().anyMatch(Character::isDigit);
          }
        };

    // Le bouton OK est désactivé si le formulaire n'est PAS valide
    okBtn.disableProperty().bind(formValide.not());
  }

  void okClicked() {
    // Affiche l'identifiant et le mot de passe masqué par des étoiles (*)
    int pLength = pwd.getText().length();
    message.setText("Bienvenue " + userId.getText() + " (" + "*".repeat(pLength) + ")");
  }

  void cancelClicked() {
    // Vider les deux champs et effacer le label de statut
    userId.clear();
    pwd.clear();
    message.setText("");
  }

  public static void main(String[] args) {
    launch(args);
  }
}
