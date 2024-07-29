package school.hei.patrimoine.modele.possession;

import org.junit.jupiter.api.Test;
import school.hei.patrimoine.modele.Devise;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DetteTest {
    @Test
    void testConstructorWithValidValues() {
        LocalDate date = LocalDate.of(2024, 7, 1);
        int valeurComptable = -1000; // Dette should have a negative or zero value
        Devise devise = Devise.EUR;

        Dette dette = new Dette("Test Dette", date, valeurComptable, devise);

        assertNotNull(dette);
        assertEquals("Test Dette", dette.nom);
        assertEquals(date, dette.t);
        assertEquals(valeurComptable, dette.valeurComptable);
        assertEquals(devise, dette.devise);
    }

    @Test
    void testConstructorWithPositiveValue() {
        LocalDate date = LocalDate.of(2024, 7, 1);
        int positiveValeurComptable = 1000;

        Dette dette = new Dette("Test Dette", date, positiveValeurComptable);

        assertNotNull(dette);
        assertEquals("Test Dette", dette.nom);
        assertEquals(date, dette.t);
        assertEquals(-positiveValeurComptable, dette.valeurComptable); // Ensure it is negated
    }

    @Test
    void testConstructorWithDefaultDevise() {
        LocalDate date = LocalDate.of(2024, 7, 1);
        int valeurComptable = -1000;

        Dette dette = new Dette("Test Dette", date, valeurComptable);

        assertNotNull(dette);
        assertEquals("Test Dette", dette.nom);
        assertEquals(date, dette.t);
        assertEquals(valeurComptable, dette.valeurComptable);
        assertEquals(Devise.NON_NOMMEE, dette.devise);
    }

    @Test
    void testProjectionFuture() {
        LocalDate date = LocalDate.of(2024, 7, 1);
        int valeurComptable = -1000;
        Devise devise = Devise.EUR;
        Dette dette = new Dette("Test Dette", date, valeurComptable, devise);
        LocalDate futureDate = LocalDate.of(2025, 7, 1);

        Dette projected = dette.projectionFuture(futureDate);

        assertNotNull(projected);
        assertEquals("Test Dette", projected.nom);
        assertEquals(futureDate, projected.t);
        assertEquals(valeurComptable, projected.valeurComptable);
        assertEquals(devise, projected.devise);
    }

    @Test
    void testProjectionFutureWithSameDate() {
        LocalDate date = LocalDate.of(2024, 7, 1);
        int valeurComptable = -1000;
        Devise devise = Devise.EUR;
        Dette dette = new Dette("Test Dette", date, valeurComptable, devise);

        Dette projected = dette.projectionFuture(date);

        assertNotNull(projected);
        assertEquals(date, projected.t);
        assertEquals(valeurComptable, projected.valeurComptable);
        assertEquals(devise, projected.devise);
    }

}