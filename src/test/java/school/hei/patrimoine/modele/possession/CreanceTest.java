package school.hei.patrimoine.modele.possession;

import org.junit.jupiter.api.Test;
import school.hei.patrimoine.modele.Devise;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreanceTest {

    @Test
    void testConstructorWithPositiveValue() {
        LocalDate date = LocalDate.of(2024, 7, 1);
        var positiveValeurComptable = 1000; // This should be negated to -1000
        var devise = Devise.EUR;

        Creance creance = new Creance("Test Creance", date, positiveValeurComptable, devise);

        assertNotNull(creance);
        assertEquals("Test Creance", creance.nom);
        assertEquals(date, creance.t);
        assertEquals(positiveValeurComptable, creance.valeurComptable); // Ensure it is negated
        assertEquals(devise, creance.devise);
    }

    @Test
    void testConstructorWithNegativeValue() {
        LocalDate date = LocalDate.of(2024, 7, 1);
        var negativeValeurComptable = -1000;
        var devise = Devise.EUR;

        Creance creance = new Creance("Test Creance", date, negativeValeurComptable, devise);

        assertNotNull(creance);
        assertEquals("Test Creance", creance.nom);
        assertEquals(date, creance.t);
        assertEquals(-negativeValeurComptable, creance.valeurComptable); // Should remain unchanged
        assertEquals(devise, creance.devise);
    }

    @Test
    void testConstructorWithDefaultDevise() {
        LocalDate date = LocalDate.of(2024, 7, 1);
        var positiveValeurComptable = 1000; // This should be negated to -1000

        Creance creance = new Creance("Test Creance", date, positiveValeurComptable);

        assertNotNull(creance);
        assertEquals("Test Creance", creance.nom);
        assertEquals(date, creance.t);
        assertEquals(positiveValeurComptable, creance.valeurComptable); // Ensure it is negated
        assertEquals(Devise.NON_NOMMEE, creance.devise);
    }

    @Test
    void testProjectionFuture() {
        LocalDate date = LocalDate.of(2024, 7, 1);
        var valeurComptable = 1000;
        var devise = Devise.EUR;
        Creance creance = new Creance("Test Creance", date, valeurComptable, devise);
        LocalDate futureDate = LocalDate.of(2025, 7, 1);

        Creance projected = creance.projectionFuture(futureDate);

        assertNotNull(projected);
        assertEquals("Test Creance", projected.nom);
        assertEquals(futureDate, projected.t);
        assertEquals(valeurComptable, projected.valeurComptable); // Value should remain the same
        assertEquals(devise, projected.devise);
    }

    @Test
    void testProjectionFutureWithSameDate() {
        var date = LocalDate.of(2024, 7, 1);
        var valeurComptable = 1000;
        var devise = Devise.EUR;
        var creance = new Creance("Test Creance", date, valeurComptable, devise);

        Creance projected = creance.projectionFuture(date);

        assertNotNull(projected);
        assertEquals(date, projected.t);
        assertEquals(valeurComptable, projected.valeurComptable); // Value should remain the same
        assertEquals(devise, projected.devise);
    }

}