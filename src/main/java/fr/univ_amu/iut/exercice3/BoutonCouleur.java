package fr.univ_amu.iut.exercice3;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;

public class BoutonCouleur extends Button {

  private IntegerProperty nbClics = new SimpleIntegerProperty(0);
  private String couleur;

  public BoutonCouleur(String texte, String couleur) {
    super(texte);
    this.couleur = couleur;

    // TODO: il manque le handler setOnAction qui incrémente nbClics
    this.setOnAction(e -> nbClics.set(nbClics.get() + 1));
  }

  public int getNbClics() {
    return nbClics.get();
  }

  public IntegerProperty nbClicsProperty() {
    return nbClics;
  }

  public String getCouleur() {
    return couleur;
  }
}
