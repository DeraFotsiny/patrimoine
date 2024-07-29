package school.hei.patrimoine.modele.possession;

import static java.time.Month.DECEMBER;
import static java.time.Month.JUNE;
import static java.time.Month.MAY;
import static java.time.Month.OCTOBER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class FluxArgentTest {
  @Test
  void train_de_vie_est_finance_par_compte_courant() {
    var au13mai24 = LocalDate.of(2024, MAY, 13);
    var compteCourant = new Argent("Compte courant", au13mai24, 600_000);

    var aLOuvertureDeITM = LocalDate.of(2021, OCTOBER, 26);
    var aLaDiplomation = LocalDate.of(2024, DECEMBER, 26);
    var vieEstudiantine =
        new FluxArgent(
            "Ma super(?) vie d'etudiant",
            compteCourant,
            aLOuvertureDeITM,
            aLaDiplomation,
            -500_000,
            1);
    var donsDePapaEtMamanAuDebut =
        new FluxArgent(
            "La générosité des parents au début",
            compteCourant,
            aLOuvertureDeITM,
            aLOuvertureDeITM.plusDays(100),
            400_000,
            30);
    var donsDePapaEtMamanALaFin =
        new FluxArgent(
            "La générosité des parents à la fin",
            compteCourant,
            aLaDiplomation,
            aLaDiplomation.minusDays(100),
            400_000,
            30);

    assertEquals(0, compteCourant.projectionFuture(au13mai24.minusDays(100)).valeurComptable);
    assertEquals(600_000, compteCourant.projectionFuture(au13mai24).valeurComptable);
    var au26juin24 = LocalDate.of(2024, JUNE, 26);
    assertEquals(100_000, compteCourant.projectionFuture(au26juin24).valeurComptable);
    assertEquals(-2_900_000, compteCourant.projectionFuture(aLaDiplomation).valeurComptable);
    assertEquals(
        -2_900_000, compteCourant.projectionFuture(aLaDiplomation.plusDays(100)).valeurComptable);
  }

  @Test
  void flux_positif_mensuel_impact_sur_compte_courant() {
    var au13mai24 = LocalDate.of(2024, MAY, 13);
    var compteCourant = new Argent("Compte courant", au13mai24, 200_000);

    var debutDepot = LocalDate.of(2024, MAY, 1);
    var finDepot = LocalDate.of(2025, MAY, 1);
    var depotMensuel =
        new FluxArgent("Dépot mensuel", compteCourant, debutDepot, finDepot, 50_000, 1);

    assertEquals(200_000, compteCourant.projectionFuture(au13mai24).valeurComptable);
    var au1mai25 = LocalDate.of(2025, MAY, 1);
    assertEquals(800_000, compteCourant.projectionFuture(au1mai25).valeurComptable);
  }

  @Test
  void flux_negatif_mensuel_impact_sur_compte_courant() {
    var au13mai24 = LocalDate.of(2024, MAY, 13);
    var compteCourant = new Argent("Compte courant", au13mai24, 500_000);

    var debutRetrait = LocalDate.of(2024, MAY, 1);
    var finRetrait = LocalDate.of(2024, DECEMBER, 1);
    var retraitMensuel =
        new FluxArgent("Retrait mensuel", compteCourant, debutRetrait, finRetrait, -100_000, 1);

    assertEquals(500_000, compteCourant.projectionFuture(au13mai24).valeurComptable);
    var au1dec24 = LocalDate.of(2024, DECEMBER, 1);
    assertEquals(-200_000, compteCourant.projectionFuture(au1dec24).valeurComptable);
  }

  @Test
  void flux_a_une_date_specifique() {
    var au13mai24 = LocalDate.of(2024, MAY, 13);
    var compteCourant = new Argent("Compte courant", au13mai24, 300_000);

    var fluxUnique = new FluxArgent("Versement unique", compteCourant, au13mai24, 100_000);

    assertEquals(400_000, compteCourant.projectionFuture(au13mai24).valeurComptable);
  }
}
