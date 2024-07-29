package school.hei.patrimoine.modele.possession;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import school.hei.patrimoine.modele.Devise;

class AchatMaterielAuComptantTest {
  @Test
  void testConstructorWithDefaultDevise() {
    LocalDate dateAchat = LocalDate.of(2024, 7, 1);
    int valeurComptable = 5000;
    double tauxAppreciation = 0.05;
    Argent financeur = new Argent("Financeur", dateAchat, 5000, Devise.EUR);
    AchatMaterielAuComptant achat =
        new AchatMaterielAuComptant(
            "Achat Test", dateAchat, valeurComptable, tauxAppreciation, financeur);

    assertNotNull(achat);
    assertEquals("Achat Test", achat.nom);
    assertEquals(dateAchat, achat.t);
    assertEquals(valeurComptable, achat.valeurComptable);
    assertEquals(Devise.NON_NOMMEE, achat.devise);

    // Check if GroupePossession contains correct components
    var groupe = achat.getAchatCommeGroupe();
    assertNotNull(groupe);
    assertEquals(dateAchat, groupe.t);

    // Further validation for internal components if needed
  }

  @Test
  void testConstructorWithCustomDevise() {
    LocalDate dateAchat = LocalDate.of(2024, 7, 1);
    int valeurComptable = 5000;
    double tauxAppreciation = 0.05;
    Argent financeur = new Argent("Financeur", dateAchat, 5000, Devise.EUR);
    Devise devise = new Devise("USD", 3000, dateAchat, -0.02);
    AchatMaterielAuComptant achat =
        new AchatMaterielAuComptant(
            "Achat Test", dateAchat, valeurComptable, tauxAppreciation, financeur, devise);

    assertNotNull(achat);
    assertEquals("Achat Test", achat.nom);
    assertEquals(dateAchat, achat.t);
    assertEquals(valeurComptable, achat.valeurComptable);
    assertEquals(devise, achat.devise);
  }

  @Test
  void testProjectionFuture() {
    LocalDate dateAchat = LocalDate.of(2024, 7, 1);
    int valeurComptable = 5000;
    double tauxAppreciation = 0.05;
    Argent financeur = new Argent("Financeur", dateAchat, 5000, Devise.EUR);
    AchatMaterielAuComptant achat =
        new AchatMaterielAuComptant(
            "Achat Test", dateAchat, valeurComptable, tauxAppreciation, financeur);

    LocalDate futureDate = LocalDate.of(2025, 7, 1);
    var projected = achat.projectionFuture(futureDate);

    assertNotNull(projected);
    assertTrue(projected instanceof GroupePossession);
    GroupePossession groupe = (GroupePossession) projected;

    // Validate that the projected GroupePossession has correct future projections
    assertEquals(futureDate, groupe.t);
    // You can add more specific assertions based on expected projections
  }

  @Test
  void testProjectionFutureWithPastDate() {
    LocalDate dateAchat = LocalDate.of(2024, 7, 1);
    int valeurComptable = 5000;
    double tauxAppreciation = 0.05;
    Argent financeur = new Argent("Financeur", dateAchat, valeurComptable, Devise.EUR);
    AchatMaterielAuComptant achat =
        new AchatMaterielAuComptant(
            "Achat Test", dateAchat, valeurComptable, tauxAppreciation, financeur);

    LocalDate pastDate = LocalDate.of(2023, 7, 1);
    var projected = achat.projectionFuture(pastDate);

    assertNotNull(projected);
    assertTrue(projected instanceof GroupePossession);
    GroupePossession groupe = (GroupePossession) projected;

    assertEquals(pastDate, groupe.t);
  }

  @Test
  void testProjectionFutureWithSameDate() {
    LocalDate dateAchat = LocalDate.of(2024, 7, 1);
    int valeurComptable = 5000;
    double tauxAppreciation = 0.05;
    Argent financeur = new Argent("Financeur", dateAchat, 5000, Devise.EUR);
    AchatMaterielAuComptant achat =
        new AchatMaterielAuComptant(
            "Achat Test", dateAchat, valeurComptable, tauxAppreciation, financeur);

    var projected = achat.projectionFuture(dateAchat);

    assertNotNull(projected);
    assertTrue(projected instanceof GroupePossession);
    GroupePossession groupe = (GroupePossession) projected;

    assertEquals(dateAchat, groupe.t);
    // Add more specific assertions if needed
  }
}
