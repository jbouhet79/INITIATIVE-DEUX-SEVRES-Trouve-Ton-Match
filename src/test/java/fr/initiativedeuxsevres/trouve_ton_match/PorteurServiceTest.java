package fr.initiativedeuxsevres.trouve_ton_match;

import fr.initiativedeuxsevres.trouve_ton_match.entity.Porteur;
import fr.initiativedeuxsevres.trouve_ton_match.repository.PorteurRepository;
import fr.initiativedeuxsevres.trouve_ton_match.service.PorteurService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PorteurServiceTest {

    @Mock
    private PorteurRepository porteurRepository;

    @InjectMocks
    private PorteurService porteurService;

    @Test
    public void completerComptePorteur() {

        // mock
        Long idUtilisateur = 1L;

        String dateLancement = "2024-11-06";
        String domaine = "TP";
        String descriptifActivite = "activité";
        String besoins = "comptabilité";
        String lieuActivite = "NIORT";
        String disponibilites = "lundi";

//        Porteur mockPorteur = new Porteur(
//                dateLancement,
//                domaine,
//                besoins,
//                lieuActivite,
//                disponibilites);

        Porteur mockPorteur = Porteur.builder()
                .dateLancement(dateLancement)
                .domaine(domaine)
                .descriptifActivite(descriptifActivite)
                .besoins(besoins)
                .lieuActivite(lieuActivite)
                .disponibilites(disponibilites)
                .build();

        // when
        when(porteurRepository.findById(idUtilisateur)).thenReturn(Optional.of(mockPorteur));
        when(porteurRepository.save(any(Porteur.class))).thenReturn(mockPorteur);

        // then
        Porteur porteurTest = porteurService.completerComptePorteur(
                idUtilisateur,
                dateLancement,
                domaine,
                descriptifActivite,
                besoins,
                lieuActivite,
                disponibilites);

        assertEquals(dateLancement, porteurTest.getDateLancement());
        assertEquals(domaine, porteurTest.getDomaine());
        assertEquals(descriptifActivite, porteurTest.getDescriptifActivite());
        assertEquals(besoins, porteurTest.getBesoins());
        assertEquals(lieuActivite, porteurTest.getLieuActivite());
        assertEquals(disponibilites, porteurTest.getDisponibilites());
    }
}
