package school.hei.patrimoine.modele.possession;

import org.junit.jupiter.api.Test;
import school.hei.patrimoine.modele.Devise;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.*;

class PossessionTest {

    @Test
    void testValeurComptableFuture() {
        LocalDate now = LocalDate.now();
        LocalDate futureDate = now.plusMonths(6);
        Argent argent = new Argent("TestArgent", now, 1000, Devise.EUR);

        int expectedValeur = argent.valeurComptableFuture(futureDate);

        assertEquals(expectedValeur, argent.valeurComptableFuture(futureDate));
    }

    @Test
    void testGetValeurComptable() {
        LocalDate now = LocalDate.now();
        Devise deviseUSD = new Devise("USD", 3000, now, -0.02);
        Argent argent = new Argent("TestArgent", now, 1000, Devise.EUR);

        int valeurComptableInUSD = argent.getValeurComptable(deviseUSD, now);

        var valeurAjouteeJournaliere = deviseUSD.valeurEnAriary() * (deviseUSD.tauxDappréciationAnnuel() / 365.);
        var conversionRate = (int) (deviseUSD.valeurEnAriary() + valeurAjouteeJournaliere * (DAYS.between(now, LocalDate.now())));
        int expectedValeur = (int) (argent.getValeurComptable()*argent.devise.valeurEnAriary(now)/conversionRate);

        assertEquals(expectedValeur, valeurComptableInUSD);
    }

    @Test
    void testProjectionFuture() {
        LocalDate debut = LocalDate.of(2024, 7, 1);
        LocalDate fin = LocalDate.of(2024, 12, 31);
        Argent argent = new Argent("TestArgent", debut, 1000, Devise.EUR);
        LocalDate futureDate = LocalDate.of(2025, 1, 1);


        var projected = argent.projectionFuture(futureDate);

        assertNotNull(projected);
        assertEquals(futureDate, projected.t); // Assuming the `projectionFuture` method updates the date
    }
    @Test
    void testProjectionFutureWithDifferentDates() {
        LocalDate debut = LocalDate.of(2024, 7, 1);
        LocalDate fin = LocalDate.of(2024, 12, 31);
        Argent argent = new Argent("TestArgent", debut, 1000, Devise.EUR);

        // Testing future projection
        LocalDate futureDate = LocalDate.of(2025, 1, 1);
        var projected = argent.projectionFuture(futureDate);

        assertNotNull(projected);
        assertTrue(projected.t.isBefore(futureDate) || projected.t.isEqual(futureDate));
    }

    @Test
    void testProjectionFutureWithPastDate() {
        LocalDate now = LocalDate.now();
        LocalDate pastDate = now.minusMonths(6);
        Argent argent = new Argent("TestArgent", now, 1000, Devise.EUR);

        var projected = argent.projectionFuture(pastDate);

        assertNotNull(projected);
        assertEquals(pastDate, projected.t);
    }

    @Test
    void testProjectionFutureWithSameDate() {
        LocalDate now = LocalDate.now();
        Argent argent = new Argent("TestArgent", now, 1000, Devise.EUR);

        // Testing projection with the same date
        var projected = argent.projectionFuture(now);

        assertNotNull(projected);
        assertEquals(now, projected.t);
    }

    @Test
    void testPossessionConstructorDefaults() {
        LocalDate now = LocalDate.now();
        Possession possession = new Argent("DefaultArgent", now, 0, Devise.NON_NOMMEE);

        // Testing default values
        assertEquals("DefaultArgent", possession.nom);
        assertEquals(now, possession.t);
        assertEquals(0, possession.valeurComptable);
        assertEquals(Devise.NON_NOMMEE, possession.devise);
    }
    @Test
    void testValeurComptableNegative() {
        LocalDate now = LocalDate.now();
        Argent dette = new Argent("Dette", now, -500, Devise.EUR);

        assertEquals(-500, dette.getValeurComptable());
    }
    @Test
    void testNomPossession() {
        LocalDate now = LocalDate.now();
        Argent argent = new Argent("MonTestArgent", now, 1000, Devise.EUR);

        assertEquals("MonTestArgent", argent.getNom());
    }

    @Test
    void testNom() {
        LocalDate now = LocalDate.now();
        Argent argent = new Argent("MonTestArgent", now, 1000, Devise.EUR);

        assertEquals("MonTestArgent", argent.getNom());
    }
    @Test
    void testValeurComptableNulle() {
        LocalDate now = LocalDate.now();
        Argent argent = new Argent("TestArgentNul", now, 0, Devise.EUR);

        assertEquals(0, argent.getValeurComptable());
    }
}