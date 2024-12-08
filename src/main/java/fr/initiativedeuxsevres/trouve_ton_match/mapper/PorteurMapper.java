package fr.initiativedeuxsevres.trouve_ton_match.mapper;

import fr.initiativedeuxsevres.trouve_ton_match.dto.PorteurDto;
import fr.initiativedeuxsevres.trouve_ton_match.dto.SecteurReseauDto;
import fr.initiativedeuxsevres.trouve_ton_match.dto.TypeAccompagnementDto;
import fr.initiativedeuxsevres.trouve_ton_match.entity.Parrain;
import fr.initiativedeuxsevres.trouve_ton_match.entity.Porteur;
import fr.initiativedeuxsevres.trouve_ton_match.mapper.UtilisateurMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PorteurMapper {

    private final UtilisateurMapper utilisateurMapper;

    // Injection du mapper Utilisateur
    public PorteurMapper(UtilisateurMapper utilisateurMapper) {
        this.utilisateurMapper = utilisateurMapper;
    }

    /**
     * Convertit une entité Porteur en DTO PorteurDto.
     *
     * @param entity L'entité Porteur à convertir.
     * @return Le DTO PorteurDto.
     */
    public PorteurDto toDto(Porteur entity) {
        if (entity == null) {
            return null;
        }

        // Conversion de l'entité Porteur en DTO PorteurDto
        PorteurDto dto = new PorteurDto();
        dto.setIdUtilisateur(entity.getIdUtilisateur());
        dto.setNomUtilisateur(entity.getNomUtilisateur());
        dto.setPrenomUtilisateur(entity.getPrenomUtilisateur());
        dto.setEntrepriseUtilisateur(entity.getEntrepriseUtilisateur());
        dto.setPlateformeUtilisateur(entity.getPlateformeUtilisateur());
        dto.setCodeUtilisateur(entity.getCodeUtilisateur());
        dto.setDateLancement(entity.getDateLancement());
        dto.setDomaine(entity.getDomaine());
        dto.setBesoins(entity.getBesoins());
        dto.setLieuActivite(entity.getLieuActivite());
        dto.setDisponibilites(entity.getDisponibilites());

        return dto;
    }

    /**
     * Convertit un DTO PorteurDto en entité Porteur.
     *
     * @param porteurDto Le DTO PorteurDto à convertir.
     * @return L'entité Porteur.
     */
    public Porteur toEntity(PorteurDto porteurDto, Porteur porteurExistant, List<TypeAccompagnementDto> accompagnements, List<SecteurReseauDto> secteursReseaux) {
        if (porteurDto == null) {
            return null;
        }

        // Convertir le DTO en entité
        Porteur porteur = porteurExistant != null ? porteurExistant : new Porteur();

        // Mapper les propriétés héritées de la classe Utilisateur
        // pas nécessaire : cause d'erreur

        // Mapper les propriétés spécifiques à Parrain
        porteur.setDateLancement(porteurDto.getDateLancement());
        porteur.setDomaine(porteurDto.getDomaine());
        porteur.setBesoins(porteurDto.getBesoins());
        porteur.setLieuActivite(porteurDto.getLieuActivite());
        porteur.setDisponibilites(porteurDto.getDisponibilites());

        return porteur;
    }
}