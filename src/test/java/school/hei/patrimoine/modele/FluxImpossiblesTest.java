package school.hei.patrimoine.modele;

import org.junit.jupiter.api.Test;
import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.FluxArgent;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FluxImpossiblesTest {

    @Test
    void testFluxImpossiblesConstructor() {
        LocalDate date = LocalDate.of(2024, 7, 28);
        String nomArgent = "EUR";
        int valeurArgent = 1000;
        Argent argent = new Argent("Argent1", LocalDate.now(), 500, Devise.EUR);
        Set<FluxArgent> flux = new HashSet<>();
        flux.add(new FluxArgent("Flux1", argent, LocalDate.now(), LocalDate.now(), 500, 15, Devise.EUR));

        FluxImpossibles fluxImpossibles = new FluxImpossibles(date, nomArgent, valeurArgent, flux);

        assertEquals(date, fluxImpossibles.date());
        assertEquals(nomArgent, fluxImpossibles.nomArgent());
        assertEquals(valeurArgent, fluxImpossibles.valeurArgent());
        assertEquals(flux, fluxImpossibles.flux());
    }

    @Test
    void testFluxImpossiblesEmptyFlux() {
        LocalDate date = LocalDate.of(2024, 7, 28);
        String nomArgent = "EUR";
        int valeurArgent = 1000;
        Set<FluxArgent> flux = new HashSet<>();

        FluxImpossibles fluxImpossibles = new FluxImpossibles(date, nomArgent, valeurArgent, flux);

        assertEquals(date, fluxImpossibles.date());
        assertEquals(nomArgent, fluxImpossibles.nomArgent());
        assertEquals(valeurArgent, fluxImpossibles.valeurArgent());
        assertTrue(fluxImpossibles.flux().isEmpty());
    }

    @Test
    void testFluxImpossiblesWithNullValues() {
        LocalDate date = LocalDate.of(2024, 7, 28);
        String nomArgent = null;
        int valeurArgent = 0;
        Set<FluxArgent> flux = null;

        FluxImpossibles fluxImpossibles = new FluxImpossibles(date, nomArgent, valeurArgent, flux);

        assertEquals(date, fluxImpossibles.date());
        assertNull(fluxImpossibles.nomArgent());
        assertEquals(0, fluxImpossibles.valeurArgent());
        assertNull(fluxImpossibles.flux());
    }

}