package school.hei.patrimoine.modele;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.*;

class DeviseTest {
    @Test
    void testMGAConstant() {
        Devise devise = Devise.MGA;
        assertEquals("ARIARY", devise.nom());
        assertEquals(1, devise.valeurEnAriary());
        assertEquals(LocalDate.MIN, devise.t());
        assertEquals(0.0, devise.tauxDappréciationAnnuel());
    }

    @Test
    void testNonNommeeConstant() {
        Devise devise = Devise.NON_NOMMEE;
        assertEquals("non-nommee", devise.nom());
        assertEquals(1, devise.valeurEnAriary());
        assertEquals(LocalDate.MIN, devise.t());
        assertEquals(0.0, devise.tauxDappréciationAnnuel());
    }

    @Test
    void testEURConstant() {
        Devise devise = Devise.EUR;
        assertEquals("EURO", devise.nom());
        assertEquals(4821, devise.valeurEnAriary());
        assertEquals(LocalDate.of(2024, 7, 3), devise.t());
        assertEquals(-0.1, devise.tauxDappréciationAnnuel());
    }

    @Test
    void testCADConstant() {
        Devise devise = Devise.CAD;
        assertEquals("CAD", devise.nom());
        assertEquals(3286, devise.valeurEnAriary());
        assertEquals(LocalDate.of(2024, 7, 8), devise.t());
        assertEquals(-0.1, devise.tauxDappréciationAnnuel());
    }

    @Test
    void testValeurEnAriaryForNonNommee() {
        Devise devise = Devise.NON_NOMMEE;
        assertEquals(1, devise.valeurEnAriary(LocalDate.now()));
    }

    @Test
    void testValeurEnAriaryForPastDate() {
        Devise devise = new Devise("TestDevise", 1000, LocalDate.now(), 0.05);
        LocalDate pastDate = LocalDate.now().minusDays(10);
        var expectedValue = (int)( devise.valeurEnAriary() + devise.valeurEnAriary() * (devise.tauxDappréciationAnnuel() / 365) * DAYS.between(pastDate, LocalDate.now()));
        assertEquals(expectedValue, devise.valeurEnAriary(pastDate), 0.01);
    }

    @Test
    void testValeurEnAriaryForFuturDate() {
        Devise devise = new Devise("TestDevise", 1000, LocalDate.now(), 0.05);
        LocalDate futurDate = LocalDate.now().plusDays(10);
        var valeurAjouteeJournaliere = devise.valeurEnAriary() * (devise.tauxDappréciationAnnuel() / 365.);
        var expectedValue = (int) (devise.valeurEnAriary() + valeurAjouteeJournaliere * (DAYS.between(futurDate, LocalDate.now())));
        assertEquals(expectedValue, devise.valeurEnAriary(futurDate), 0.01);
    }

    @Test
    void testValeurEnAriaryWithZeroAppreciation() {
        Devise devise = new Devise("TestDevise", 1000, LocalDate.now(), 0.0);
        assertEquals(1000, devise.valeurEnAriary(LocalDate.now()));
    }

}