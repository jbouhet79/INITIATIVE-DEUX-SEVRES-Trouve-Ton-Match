package fr.initiativedeuxsevres.trouve_ton_match.dto;

import fr.initiativedeuxsevres.trouve_ton_match.entity.TypeAccompagnement;
import fr.initiativedeuxsevres.trouve_ton_match.enums.TypeUtilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurDto {

    /**
     * Propriétés protected pour que les classes héritants de UtilisateurDto
     * puissent les utiliser
     */
    protected Long idUtilisateur;
    protected String nomUtilisateur;
    protected String prenomUtilisateur;
    protected String entrepriseUtilisateur;
    protected String plateformeUtilisateur;
    protected String codeUtilisateur;
    protected String typeUtilisateur; // TODO a vérifier l'utilité.
//    protected TypeUtilisateur type;  // Enum pour type d'utilisateur
//    protected List<Long> accompagnementTypeList;
//    protected List<Long> secteurReseauList;
    protected List<TypeAccompagnementDto> accompagnementTypeList;  // Liste de DTO pour les types d'accompagnement
    protected List<SecteurReseauDto> secteurReseauList;  // Liste de DTO pour les secteurs de réseau

}
