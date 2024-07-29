package school.hei.patrimoine.modele.possession;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import school.hei.patrimoine.modele.Devise;

class TransfertArgentTest {

  @Test
  void testTransfertArgentConstructorWithDevise() {
    LocalDate debut = LocalDate.of(2024, 7, 1);
    LocalDate fin = LocalDate.of(2024, 12, 31);
    Argent depuisArgent = new Argent("ArgentDepuis", LocalDate.now(), 1000, Devise.EUR);
    Argent versArgent = new Argent("ArgentVers", LocalDate.now(), 2000, Devise.EUR);
    var fluxMensuel = 100;
    var dateOperation = 15;

    TransfertArgent transfertArgent =
        new TransfertArgent(
            "Transfert1",
            depuisArgent,
            versArgent,
            debut,
            fin,
            fluxMensuel,
            dateOperation,
            Devise.EUR);

    // Check if the instance is created correctly
    assertNotNull(transfertArgent);
    assertEquals("Transfert1", transfertArgent.getNom());
    assertEquals(debut, transfertArgent.getT());
    assertEquals(0, transfertArgent.getValeurComptable());
    assertEquals(Devise.EUR, transfertArgent.getDevise());
    assertNotNull(transfertArgent.getTransfertCommeGroupe());
  }

  @Test
  void testTransfertArgentConstructorWithoutDevise() {
    LocalDate debut = LocalDate.of(2024, 7, 1);
    LocalDate fin = LocalDate.of(2024, 12, 31);
    Argent depuisArgent = new Argent("ArgentDepuis", LocalDate.now(), 1000, Devise.EUR);
    Argent versArgent = new Argent("ArgentVers", LocalDate.now(), 2000, Devise.EUR);
    var fluxMensuel = 100;
    var dateOperation = 15;

    TransfertArgent transfertArgent =
        new TransfertArgent(
            "Transfert1", depuisArgent, versArgent, debut, fin, fluxMensuel, dateOperation);

    // Check if the instance is created correctly
    assertNotNull(transfertArgent);
    assertEquals("Transfert1", transfertArgent.getNom());
    assertEquals(debut, transfertArgent.t);
    assertEquals(0, transfertArgent.getValeurComptable());
    assertEquals(Devise.NON_NOMMEE.nom(), transfertArgent.getDevise().nom());
    assertNotNull(transfertArgent.getTransfertCommeGroupe());
  }

  @Test
  void testProjectionFuture() {
    LocalDate debut = LocalDate.of(2024, 7, 1);
    LocalDate fin = LocalDate.of(2024, 12, 31);
    Argent depuisArgent = new Argent("ArgentDepuis", LocalDate.now(), 1000, Devise.EUR);
    Argent versArgent = new Argent("ArgentVers", LocalDate.now(), 2000, Devise.EUR);
    var fluxMensuel = 100;
    var dateOperation = 15;

    TransfertArgent transfertArgent =
        new TransfertArgent(
            "Transfert1", depuisArgent, versArgent, debut, fin, fluxMensuel, dateOperation);

    LocalDate futureDate = LocalDate.of(2025, 1, 1);
    var result = transfertArgent.projectionFuture(futureDate);

    assertNotNull(result);
    assertTrue(result instanceof GroupePossession);
  }
}
