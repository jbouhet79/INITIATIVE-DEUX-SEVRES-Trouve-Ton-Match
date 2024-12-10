package fr.initiativedeuxsevres.trouve_ton_match.entity;

//import fr.initiativedeuxsevres.trouve_ton_match.enums.TypeUtilisateur;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
//@NoArgsConstructor
@SuperBuilder
@Table(name= "porteur")
public class Porteur extends Utilisateur  {

    private String dateLancement;
    private String domaine;
    private String descriptifActivite;
    private String besoins;
    private String lieuActivite;
    private String disponibilites;

//    @Builder.Default
//    private String type = Porteur.class.getSimpleName();
//    private TypeUtilisateur type;

    public Porteur(){
        super();
    }
    /**
     * Constructeur avec tous les paramètres de la classe mère
     * @param idUtilisateur
     * @param nomUtilisateur
     * @param prenomUtilisateur
     * @param entrepriseUtilisateur
     * @param plateformeUtilisateur
     * @param codeUtilisateur
     * @param dateLancement
     * @param domaine
     * @param descriptifActivite
     * @param besoins
     * @param lieuActivite
     * @param disponibilites
     */
    public Porteur(Long idUtilisateur, String nomUtilisateur, String prenomUtilisateur, String entrepriseUtilisateur,
                   String plateformeUtilisateur, String codeUtilisateur, String dateLancement, String domaine, String descriptifActivite,
                   String besoins, String lieuActivite, String disponibilites) {

        // Appel au constructeur de la classe mère
        super(idUtilisateur, nomUtilisateur, prenomUtilisateur, entrepriseUtilisateur, plateformeUtilisateur, codeUtilisateur, "porteur");

        this.dateLancement = dateLancement;
        this.domaine = domaine;
        this.descriptifActivite = descriptifActivite;
        this.besoins = besoins;
        this.lieuActivite = lieuActivite;
        this.disponibilites = disponibilites;

        // Méthode pour obtenir le type en tant que String
//        public String getTypeAsString() {
//            return getType() != null ? getType().name() : null;
//        }
    }

}
