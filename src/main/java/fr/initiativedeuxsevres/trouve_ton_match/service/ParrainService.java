package fr.initiativedeuxsevres.trouve_ton_match.service;

import fr.initiativedeuxsevres.trouve_ton_match.dto.ParrainDto;
import fr.initiativedeuxsevres.trouve_ton_match.dto.SecteurReseauDto;
import fr.initiativedeuxsevres.trouve_ton_match.dto.TypeAccompagnementDto;
import fr.initiativedeuxsevres.trouve_ton_match.dto.UtilisateurDto;
import fr.initiativedeuxsevres.trouve_ton_match.entity.Parrain;
import fr.initiativedeuxsevres.trouve_ton_match.entity.Porteur;
import fr.initiativedeuxsevres.trouve_ton_match.entity.Utilisateur;
import fr.initiativedeuxsevres.trouve_ton_match.mapper.ParrainMapper;
import fr.initiativedeuxsevres.trouve_ton_match.mapper.TypeAccompagnementMapper;
import fr.initiativedeuxsevres.trouve_ton_match.repository.ParrainRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ParrainService {

    private final TypeAccompagnementService typeAccompagnementService;
    private final SecteurReseauService secteurReseauService;
//    private final ParrainService parrainService;

    private final ParrainMapper parrainMapper;

    @Autowired
    private ParrainRepository parrainRepository;

//    @Autowired
//    private ParrainMapper parrainMapper;

    // Avant le Mapper
//    public Parrain createParrain (UtilisateurDto newParrainDto) {
//
//        // On transforme un Dto en entité
//        Parrain nouveauParrainEntity = Parrain.builder()
//                .nomUtilisateur(newParrainDto.getNomUtilisateur())
//                .prenomUtilisateur(newParrainDto.getPrenomUtilisateur())
//                .entrepriseUtilisateur(newParrainDto.getEntrepriseUtilisateur())
//                .plateformeUtilisateur(newParrainDto.getPlateformeUtilisateur())
//                .codeUtilisateur(newParrainDto.getCodeUtilisateur())
//                .build();
//
//        return parrainRepository.save(nouveauParrainEntity);
//    }

    public Parrain createParrain(UtilisateurDto newParrainDto) {
//        public Parrain createParrain(UtilisateurDto utilisateurDto) {
        System.out.println("Type d'utilisateur dans ParrainService: " + newParrainDto.getTypeUtilisateur());
        // Convertir un UtilisateurDto en ParrainDto
//        ParrainDto parrainDto = new ParrainDto();
//        parrainDto.setIdUtilisateur(utilisateurDto.getIdUtilisateur());
//        parrainDto.setNomUtilisateur(utilisateurDto.getNomUtilisateur());
//        parrainDto.setPrenomUtilisateur(utilisateurDto.getPrenomUtilisateur());
//        parrainDto.setEntrepriseUtilisateur(utilisateurDto.getEntrepriseUtilisateur());
//        parrainDto.setPlateformeUtilisateur(utilisateurDto.getPlateformeUtilisateur());
//        parrainDto.setCodeUtilisateur(utilisateurDto.getCodeUtilisateur());
//        parrainDto.setTypeUtilisateur(utilisateurDto.getTypeUtilisateur());
//
//        // Convertir le DTO en entité
//        Parrain nouveauParrainEntity = parrainMapper.toEntity(parrainDto);

//        // Sauvegarder l'entité dans la base de données
//        return parrainRepository.save(nouveauParrainEntity);

        // On transforme un Dto en entité
        Parrain nouveauParrainEntity = Parrain.builder()
                .nomUtilisateur(newParrainDto.getNomUtilisateur())
                .prenomUtilisateur(newParrainDto.getPrenomUtilisateur())
                .entrepriseUtilisateur(newParrainDto.getEntrepriseUtilisateur())
                .plateformeUtilisateur(newParrainDto.getPlateformeUtilisateur())
                .codeUtilisateur(newParrainDto.getCodeUtilisateur())
                .typeUtilisateur(newParrainDto.getTypeUtilisateur())
                .build();

        return parrainRepository.save(nouveauParrainEntity);
    }

    public Parrain completerCompteParrain(Long idUtilisateur, String presentationParcours, String branchesReseau, String domainesExpertise, String secteurGeographique, String disponibilites) {
        // Récupérer le parrain par son ID
        Parrain parrain = parrainRepository.findById(idUtilisateur)
                .orElseThrow(() -> new RuntimeException("Parrain non trouvé"));
        System.out.println("Parrain récupéré dans completerCompteParrain: " + parrain);

        // Mettre à jour les champs du parrain
        parrain.setPresentationParcours(presentationParcours);
        parrain.setBranchesReseau(branchesReseau);
        parrain.setDomainesExpertise(domainesExpertise);
        parrain.setSecteurGeographique(secteurGeographique);
        parrain.setDisponibilites(disponibilites);

        // Sauvegarder le parrain mis à jour
        return parrainRepository.save(parrain);
    }

    public Parrain findById(Long id) {
        Optional<Parrain> parrain = parrainRepository.findById(id);
        return parrain.isPresent() ? parrain.get() : null;
    }

    public Parrain save(Parrain parrain) {
        System.out.println("Sauvegarde en cours pour : " + parrain);
        System.out.println("Sauvegarde en cours (nom) : " + parrain.getNomUtilisateur());
        return parrainRepository.save(parrain);
    }

    public Parrain save(ParrainDto parrainDto) {

        Parrain parrainExistant = findById(parrainDto.getIdUtilisateur());
        System.out.println("Récupérer le parrain existant par son ID dans /completercompteparrain : " + parrainDto);
        System.out.println("Récupérer le NOM du parrain existant par son ID dans /completercompteparrain : " + parrainDto.getNomUtilisateur());

        // Vérifier si le parrain existe dans la base
        if (parrainExistant == null) {
            // Si le parrain n'existe pas, retourner un objet Parrain vide
            Parrain emptyParrain = new Parrain();
            emptyParrain.setIdUtilisateur(parrainDto.getIdUtilisateur());
            System.out.println("Parrain introuvable, renvoi d'un objet vide.");
            return emptyParrain;
        }

        List<TypeAccompagnementDto> accompagnements = typeAccompagnementService.findAll();
        List<SecteurReseauDto> secteursReseaux = secteurReseauService.findAll();
        Parrain updatedParrainEntity =  parrainMapper.toEntity(parrainDto, parrainExistant, accompagnements, secteursReseaux);
        System.out.println("Sauvegarde en cours pour : " + updatedParrainEntity);
        System.out.println("Sauvegarde en cours (nom) : " + updatedParrainEntity.getNomUtilisateur());
        return parrainRepository.save(updatedParrainEntity);
    }
}
