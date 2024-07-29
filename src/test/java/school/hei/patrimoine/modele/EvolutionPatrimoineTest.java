package school.hei.patrimoine.modele;

import static java.time.Month.MAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;
import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.Creance;
import school.hei.patrimoine.modele.possession.Dette;
import school.hei.patrimoine.modele.possession.FluxArgent;

class EvolutionPatrimoineTest {

    @Test
    void patrimoine_evolue() {
        var ilo = new Personne("Ilo");
        var au13mai24 = LocalDate.of(2024, MAY, 13);
        var financeur = new Argent("Espèces", au13mai24, 600_000);
        var trainDeVie =
                new FluxArgent(
                        "Vie courante",
                        financeur,
                        au13mai24.minusDays(100),
                        au13mai24.plusDays(100),
                        -100_000,
                        15);
        var patrimoineIloAu13mai24 =
                new Patrimoine("patrimoineIloAu13mai24", ilo, au13mai24, Set.of(financeur, trainDeVie));

        var evolutionPatrimoine =
                new EvolutionPatrimoine(
                        "Nom",
                        patrimoineIloAu13mai24,
                        LocalDate.of(2024, MAY, 12),
                        LocalDate.of(2024, MAY, 17));

        var evolutionJournaliere = evolutionPatrimoine.getEvolutionJournaliere();
        assertEquals(0, evolutionJournaliere.get(LocalDate.of(2024, MAY, 12)).getValeurComptable());
        assertEquals(
                600_000, evolutionJournaliere.get(LocalDate.of(2024, MAY, 13)).getValeurComptable());
        assertEquals(
                600_000, evolutionJournaliere.get(LocalDate.of(2024, MAY, 14)).getValeurComptable());
        assertEquals(
                500_000, evolutionJournaliere.get(LocalDate.of(2024, MAY, 15)).getValeurComptable());
        assertEquals(
                500_000, evolutionJournaliere.get(LocalDate.of(2024, MAY, 16)).getValeurComptable());
        assertEquals(
                500_000, evolutionJournaliere.get(LocalDate.of(2024, MAY, 17)).getValeurComptable());
    }

    @Test
    void patrimoine_avec_flux_impossible() {
        var ilo = new Personne("Ilo");
        var au13mai24 = LocalDate.of(2024, MAY, 13);
        var financeur = new Argent("Espèces", au13mai24, -600_000);
        var trainDeVie =
                new FluxArgent(
                        "Vie courante",
                        financeur,
                        au13mai24.minusDays(100),
                        au13mai24.plusDays(100),
                        -100_000,
                        15);
        var patrimoineIloAu13mai24 =
                new Patrimoine("patrimoineIloAu13mai24", ilo, au13mai24, Set.of(financeur, trainDeVie));

        var evolutionPatrimoine =
                new EvolutionPatrimoine(
                        "Nom",
                        patrimoineIloAu13mai24,
                        LocalDate.of(2024, MAY, 12),
                        LocalDate.of(2024, MAY, 17));

        var fluxImpossibles = evolutionPatrimoine.fluxImpossiblesStr();
        assertFalse(fluxImpossibles.isEmpty(), "Flux impossibles should not be empty.");
    }

    @Test
    void patrimoine_avec_plusieurs_possessions() {
        var ilo = new Personne("Ilo");
        var au13mai24 = LocalDate.of(2024, MAY, 13);
        var financeur = new Argent("Espèces", au13mai24, 600_000);
        var emprunt = new Dette("Emprunt", au13mai24, -300_000);
        var creance = new Creance("Créance", au13mai24, 400_000);
        var patrimoineIloAu13mai24 =
                new Patrimoine("patrimoineIloAu13mai24", ilo, au13mai24, Set.of(financeur, emprunt, creance));

        var evolutionPatrimoine =
                new EvolutionPatrimoine(
                        "Nom",
                        patrimoineIloAu13mai24,
                        LocalDate.of(2024, MAY, 12),
                        LocalDate.of(2024, MAY, 17));

        var evolutionJournaliere = evolutionPatrimoine.getEvolutionJournaliere();
        assertEquals(0, evolutionJournaliere.get(LocalDate.of(2024, MAY, 12)).getValeurComptable());
        assertEquals(
                700_000, evolutionJournaliere.get(LocalDate.of(2024, MAY, 13)).getValeurComptable());
        assertEquals(
                700_000, evolutionJournaliere.get(LocalDate.of(2024, MAY, 14)).getValeurComptable());
    }
}
