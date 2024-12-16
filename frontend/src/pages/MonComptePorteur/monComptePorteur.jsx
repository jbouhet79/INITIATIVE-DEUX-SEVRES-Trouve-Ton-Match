import Wrapper from '../../Wrapper/index'
import { useEffect, useState, useRef } from "react";
import { useLocation } from 'react-router-dom';
import { ChampSaisie } from '../CreationCompte/champSaisie';
import './monComptePorteur.css';
import Container from 'react-bootstrap/esm/Container';
import send from '../../media/images/logos/send_blanc.png';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../AuthContext';



const otherRegex = /^[a-zA-ZÀ-ÿ\- ]{1,}$/; // minimum 2 caractères pour les autres champs
// const nomRegex = /^[A-ZÀ-ÿ\- ]{2,}$/; // NOM en MAJUSCULES
// const codeRegex = /^[a-zA-ZÀ-ÿ\- ]{1}\d{3}$/; // code admis :  1 lettre suivie de 3 chiffres

const MonComptePorteur = () => {
  const dateLancementRef = useRef(null);

  // Initialisation des états des valeurs de utilisateurDto
  // const [utilisateurDto, setUtilisateurDto] = useState({
  //   idUtilisateur: '',
  //   presentationParcours: '',
  //   branchesReseau: '',
  //   domainesExpertise: '',
  //   secteurGeographique: '',
  //   disponibilites: '',
  // });

  const [porteurDto, setPorteurDto] = useState({
    idUtilisateur: '',
    dateLancement: '',
    domaine: '',
    descriptifActivite: '',
    besoins: '',
    lieuActivite: '',
    disponibilites: '',
  });

  const [isSubmitted, setIsSubmitted] = useState(false);
  const location = useLocation(); // Ce hook permet d’accéder à l’objet location qui représente l’URL actuelle de l’application 
  const navigate = useNavigate();

  const idUtilisateur = localStorage.getItem('idUtilisateur');
  console.log('recupération de idUtilisateur:', idUtilisateur);

  // Réinitialisation des états des valeurs de porteurDto
  // lorsque le composant est monté (c’est-à-dire lorsque la page est chargée ou actualisée).
  useEffect(() => {
    setPorteurDto({
      idUtilisateur: idUtilisateur,
      dateLancement: '',
      domaine: '',
      descriptifActivite: '',
      besoins: '',
      lieuActivite: '',
      disponibilites: '',
    });

    setIsSubmitted(false);

    // Focaliser sur le champ "dateLancement"
    if (dateLancementRef.current) {
      dateLancementRef.current.focus();
    }

  }, [location]); // A chaque fois que l’URL change (info connue grâce à l'objet location), le useEffect est déclenché pour réinitialiser la page.

  const [errors, setErrors] = useState({});

  const validate = () => {
    const newErrors = {};

    if (!porteurDto.dateLancement || porteurDto.dateLancement.trim() === '') newErrors.dateLancement = 'La date de lancement est requise';
    if (!porteurDto.domaine || porteurDto.domaine.trim() === '') newErrors.domaine = 'Le domaine d\'activite est requis';
    if (!porteurDto.descriptifActivite || porteurDto.descriptifActivite.trim() === '') newErrors.descriptifActivite = 'Le descriptif de l\'activite est requis';
    if (!porteurDto.besoins || porteurDto.besoins.trim() === '') newErrors.besoins = 'Les besoins potentiels sont requis';
    if (!porteurDto.lieuActivite || porteurDto.lieuActivite.trim() === '') newErrors.lieuActivite = 'Le lieu d\'activité est requis';
    if (!porteurDto.disponibilites || porteurDto.disponibilites.trim() === '') newErrors.disponibilites = 'Les disponibilités sont requises';

    return newErrors;
  };


  // Met à jour dynamiquement les propriétés de porteurDto à chaque changer de valeur
  const handleChange = (name, value) => {
    setPorteurDto({
      ...porteurDto,
      // L’opérateur de décomposition (...porteurDto) est utilisé pour copier toutes les propriétés existantes de porteurDto.
      [name]: value
    });
    console.log("Valeurs mises à jour:", { ...porteurDto, [name]: value }); // Log des valeurs mises à jour

    // Validation des champs
    const newErrors = { ...errors };
    if (value.trim() === '') {
      newErrors[name] = 'Ce champ est requis';
    } else {
      delete newErrors[name];
    }
    setErrors(newErrors);
  };


  // Envoie des données de l’utilisateur au serveur, reception de la réponse contenant l’ID de l’utilisateur créé, 
  // et mise à jour l’état local avec cet ID. 
  // Cela permet de garder l’interface utilisateur synchronisée avec les données du serveur.
  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Formulaire soumis"); // Formulaire soumis

    const validationErrors = validate();
    if (Object.keys(validationErrors).length > 0) {
      console.log("Erreurs de validation:", validationErrors); // Tous les champs sont-ils remplis ?
      setErrors(validationErrors);
      return;
    }

    console.log("Données envoyées:", porteurDto);
    console.log("Données envoyées (JSON.stringify):", JSON.stringify(porteurDto));
    fetch('http://localhost:8080/creationCompte/completercompteporteur', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(porteurDto)
    })
    .then(response => {
      console.log("Réponse du serveur:", response);  // reponse du serveur après la requête
      if (!response.ok) {
        return response.json().then(err => { throw new Error(err.message || 'Erreur inconnue'); });
      }
      return response.json();
    })
    .then(data => {
      console.log("Données reçues du serveur:", data);
      if (data && data.idUtilisateur) {
        console.log('Utilisateur trouvé, id:', data.idUtilisateur);
        console.log('dateLancement:', data.dateLancement);

        // Mettez à jour l'état ici après avoir reçu la réponse
        setPorteurDto({
          idUtilisateur: data.idUtilisateur, // Assurez-vous que l'ID est récupéré correctement
          dateLancement: data.dateLancement || '',
          domaine: data.domaine || '',
          descriptifActivite: data.descriptifActivite || '',
          besoins: data.besoins || '',
          lieuActivite: data.lieuActivite || '',
          disponibilites: data.disponibilites || '',
          type: data.type || '', // Ajoutez le type si nécessaire
        });

        // Vérifiez si le type de l'utilisateur est "porteur" avant de naviguer
        navigate('/filtres'); // Rediriger vers la page : filtres -> "Secteurs/Réseaux et Type d'accompagnement"
      } else {
        console.log("else data.exists")
        // setUserNotFound(true); // Afficher le message "Utilisateur inconnu"
      }
    })
    .catch(error => {
      console.error('Erreur lors de la soumission du formulaire!', error);
    });
  }

  return (
    <Wrapper>
      <div className='titre'>
        <h1 className='text'>compte Porteur</h1>
      </div>
      <form onSubmit={handleSubmit} className='col-6 mx-auto my-3'>

        {errors.dateLancement && <div className="error">{errors.dateLancement}</div>}
        <ChampSaisie
          ref={dateLancementRef}
          setValue={(value) => handleChange('dateLancement', value)}
          label="Date de lancement (reprise de l’activité) :"
          name="dateLancement"
          value={porteurDto.dateLancement}
          regex={otherRegex}
        />

        {errors.domaine && <div className="error">{errors.domaine}</div>}
        <ChampSaisie setValue={(value) => handleChange('domaine', value)} label="Domaine d’activité :" name="domaine" value={porteurDto.branchesReseau} regex={otherRegex} ></ChampSaisie>

        {errors.descriptifActivite && <div className="error">{errors.descriptifActivite}</div>}
        <ChampSaisie setValue={(value) => handleChange('descriptifActivite', value)} value={porteurDto.descriptifActivite} label="Descriptif rapide de l’activité :" name="descriptifActivite" regex={otherRegex}  ></ChampSaisie>

        {errors.besoins && <div className="error">{errors.besoins}</div>}
        <ChampSaisie setValue={(value) => handleChange('besoins', value)} value={porteurDto.besoins} label="Besoins potentiels :" name="besoins" regex={otherRegex}  ></ChampSaisie>

        {errors.lieuActivite && <div className="error">{errors.lieuActivite}</div>}
        <ChampSaisie setValue={(value) => handleChange('lieuActivite', value)} value={porteurDto.lieuActivite} label="Lieu de l’activité :" name="lieuActivite" regex={otherRegex}  ></ChampSaisie>
        
        {errors.disponibilites && <div className="error">{errors.disponibilites}</div>}
        <ChampSaisie setValue={(value) => handleChange('disponibilites', value)} label="Disponibilités :" value={porteurDto.disponibilites} name="disponibilites" regex={otherRegex}  ></ChampSaisie>

        {/* {errors.type && <div className="error">{errors.type}</div>} */}


        <Container fluid>
          <div className="position-bouton">
            {!isSubmitted ? (
              <button
                type="submit"
                className="bouton-envoyer"
              >
                Envoyer
                <img src={send} className="img-send" alt="send" />
              </button>
            ) : (
              <div className='row g-2 mb-3'>
                <div className="col-md d-flex align-items-center">
                  <label htmlFor="idUtilisateur" className="form-label me-2">Identifiant : </label>
                  <div className="custom-container">
                    <input
                      className="form-control custom-id"
                      disabled
                      name="idUtilisateur"
                      id="idUtilisateur"
                      value={porteurDto.idUtilisateur}
                    />
                  </div>
                </div>
              </div>
            )}
          </div>
        </Container>
      </form>
    </Wrapper>
  )
};

export default MonComptePorteur;