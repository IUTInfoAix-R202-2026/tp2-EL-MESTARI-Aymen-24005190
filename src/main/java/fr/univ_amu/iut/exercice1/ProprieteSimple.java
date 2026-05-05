package fr.univ_amu.iut.exercice1;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;

/**
 * Exercice 1 - Découverte des propriétés JavaFX.
 *
 * <p>Les propriétés JavaFX sont un mécanisme de <b>liaison de données</b> : elles encapsulent une
 * valeur et permettent d'être notifié automatiquement quand cette valeur change. Ce mécanisme est
 * général et utile bien au-delà des interfaces graphiques.
 *
 * <p>Cet exercice explore trois concepts fondamentaux :
 *
 * <ul>
 *   <li>{@link IntegerProperty} : une propriété qui encapsule un entier
 *   <li>{@link InvalidationListener} : notifie quand la propriété est invalidée (lazy)
 *   <li>{@link ChangeListener} : notifie avec l'ancienne et la nouvelle valeur
 * </ul>
 *
 * <p>Pour vous concentrer sur le mécanisme d'observation, le scénario d'affichage est entièrement
 * fourni. Votre travail se limite à <b>ajouter et retirer les listeners</b> aux endroits indiqués.
 *
 * @see <a
 *     href="https://openjfx.io/javadoc/25/javafx.base/javafx/beans/property/IntegerProperty.html">IntegerProperty</a>
 */
public class ProprieteSimple {

  private IntegerProperty anIntProperty;
  private InvalidationListener invalidationListener;
  private ChangeListener<Number> changeListener;

  /**
   * Crée la propriété (si elle n'existe pas déjà) et affiche ses informations.
   *
   * <p>Méthode entièrement fournie. Les tests 3-5 doivent passer sans modification.
   */
  void creerPropriete() {
    if (anIntProperty == null) {
      anIntProperty = new SimpleIntegerProperty();
    }
    System.out.println();
    System.out.println("anIntProperty = " + anIntProperty);
    System.out.println("anIntProperty.get() = " + anIntProperty.get());
    System.out.println("anIntProperty.getValue() = " + anIntProperty.getValue());
  }

  /**
   * Ajoute un {@link InvalidationListener}, modifie la propriete, puis le retire.
   *
   * <p>L'InvalidationListener est "paresseux" : il n'est déclenché qu'une fois après un changement,
   * et ne se redéclenchera pas tant que la valeur n'a pas été lue (via get()).
   *
   * <p>Sortie attendue :
   *
   * <pre>
   * Add invalidation listener.
   * setValue() with 1024.
   * set() with 2105.
   * The observable has been invalidated.
   * setValue() with 5012.
   * Remove invalidation listener.
   * set() with 1024.
   * </pre>
   *
   * <p>Remarque : setValue(1024) ne déclenche rien car la valeur ne change pas. set(2105) déclenche
   * le listener. setValue(5012) ne le redéclenche PAS car la valeur n'a pas été lue entre-temps
   * (comportement paresseux).
   */
  void ajouterEtRetirerInvalidationListener() {
    System.out.println();

    // TODO exercice 1 (3 lignes à écrire) :
    // 1. Afficher "Add invalidation listener."
    // 2. Créer un InvalidationListener qui affiche "The observable has been
    //    invalidated." et le stocker dans this.invalidationListener.
    // 3. L'abonner à anIntProperty via addListener(invalidationListener).

    System.out.println("setValue() with 1024.");
    anIntProperty.setValue(1024);

    System.out.println("set() with 2105.");
    anIntProperty.set(2105);

    System.out.println("setValue() with 5012.");
    anIntProperty.setValue(5012);

    // TODO exercice 1 (2 lignes à écrire) :
    // 1. Afficher "Remove invalidation listener."
    // 2. Retirer le listener via anIntProperty.removeListener(invalidationListener).

    System.out.println("set() with 1024.");
    anIntProperty.set(1024);
  }

  /**
   * Ajoute un {@link ChangeListener}, modifie la propriété, puis le retire.
   *
   * <p>Contrairement à l'InvalidationListener, le ChangeListener est déclenché à chaque changement
   * de valeur et reçoit l'ancienne ET la nouvelle valeur.
   *
   * <p>Sortie attendue :
   *
   * <pre>
   * Add change listener.
   * setValue() with 1024.
   * set() with 2105.
   * The observableValue has changed: oldValue = 1024, newValue = 2105
   * setValue() with 5012.
   * The observableValue has changed: oldValue = 2105, newValue = 5012
   * Remove change listener.
   * set() with 1024.
   * </pre>
   */
  void ajouterEtRetirerChangeListener() {
    System.out.println();

    // TODO exercice 1 (3 lignes à écrire) :
    // 1. Afficher "Add change listener."
    // 2. Créer un ChangeListener<Number> qui affiche
    //    "The observableValue has changed: oldValue = X, newValue = Y" et le
    //    stocker dans this.changeListener.
    // 3. L'abonner à anIntProperty via addListener(changeListener).

    System.out.println("setValue() with 1024.");
    anIntProperty.setValue(1024);

    System.out.println("set() with 2105.");
    anIntProperty.set(2105);

    System.out.println("setValue() with 5012.");
    anIntProperty.setValue(5012);

    // TODO exercice 1 (2 lignes à écrire) :
    // 1. Afficher "Remove change listener."
    // 2. Retirer le listener via anIntProperty.removeListener(changeListener).

    System.out.println("set() with 1024.");
    anIntProperty.set(1024);
  }

  public int getAnInt() {
    return anIntProperty.get();
  }

  public void setAnInt(int value) {
    if (anIntProperty == null) {
      anIntProperty = new SimpleIntegerProperty();
    }
    anIntProperty.set(value);
  }

  public IntegerProperty anIntProperty() {
    return anIntProperty;
  }

  public static void main(String[] args) {
    ProprieteSimple exemple = new ProprieteSimple();
    exemple.setAnInt(1024);
    exemple.creerPropriete();
    exemple.ajouterEtRetirerInvalidationListener();
    exemple.ajouterEtRetirerChangeListener();
  }
}
