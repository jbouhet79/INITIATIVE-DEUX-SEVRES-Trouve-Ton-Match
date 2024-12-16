import Wrapper from '../../Wrapper/index'
import { useEffect, useState, useRef } from "react";
import { useLocation } from 'react-router-dom';
import { ChampSaisie } from './champSaisie';
import './creationCompte.css';
import Container from 'react-bootstrap/esm/Container';
import send from '../../media/images/logos/send_blanc.png';

const otherRegex = /^[a-zA-ZÀ-ÿ\- ]{2,}$/; // minimum 2 caractères pour les autres champs
const nomRegex = /^[A-ZÀ-ÿ\- ]{2,}$/; // NOM en MAJUSCULES
const codeRegex = /^[a-zA-ZÀ-ÿ\- ]{1}\d{3}$/; // code admis :  1 lettre suivie de 3 chiffres

const CreationCompte = () => {

    const nomUtilisateurRef = useRef(null);


    // Initialisation des états des valeurs de utilisateurDto
    const [utilisateurDto, setUtilisateurDto] = useState({
        idUtilisateur: '',
        nomUtilisateur: '',
        prenomUtilisateur: '',
        entrepriseUtilisateur: '',
        plateformeUtilisateur: '',
        codeUtilisateur: '',
        typeUtilisateur: ''
    });

    const [isSubmitted, setIsSubmitted] = useState(false);
    const location = useLocation(); // Ce hook permet d’accéder à l’objet location qui représente l’URL actuelle de l’application 

    // Réinitialisation des états des valeurs de utilisateurDto
    // lorsque le composant est monté (c’est-à-dire lorsque la page est chargée ou actualisée).
    useEffect(() => {
        setUtilisateurDto({
            idUtilisateur: '',
            nomUtilisateur: '',
            prenomUtilisateur: '',
            entrepriseUtilisateur: '',
            plateformeUtilisateur: '',
            codeUtilisateur: '',
            typeUtilisateur: ''
        });

        setIsSubmitted(false);

        // Focaliser sur le champ "nomUtilisateur"
        if (nomUtilisateurRef.current) {
            nomUtilisateurRef.current.focus();
        }

    }, [location]); // A chaque fois que l’URL change (info connue grâce à l'objet location), le useEffect est déclenché pour réinitialiser la page.

    const [errors, setErrors] = useState({});

    const validate = () => {
        const newErrors = {};

        if (!utilisateurDto.nomUtilisateur) newErrors.nomUtilisateur = 'Le nom est requis';
        if (!utilisateurDto.prenomUtilisateur) newErrors.prenomUtilisateur = 'Le prénom est requis';
        if (!utilisateurDto.entrepriseUtilisateur) newErrors.entrepriseUtilisateur = 'L\'entreprise est requise';
        if (!utilisateurDto.plateformeUtilisateur) newErrors.plateformeUtilisateur = 'La plateforme est requise';
        if (!utilisateurDto.codeUtilisateur) newErrors.codeUtilisateur = 'Le code d\'accès est requis';
        if (!utilisateurDto.typeUtilisateur) newErrors.type = 'Le type de profil est requis';

        return newErrors;
    };


    // Met à jour dynamiquement les propriétés de utilisateurDto à chaque changer de valeur
    const handleChange = (name, value) => {
        setUtilisateurDto({
            ...utilisateurDto, // L’opérateur de décomposition (...utilisateurDto) est utilisé pour copier toutes les propriétés existantes de utilisateurDto.
            [name]: value
        });

        // Validation des champs
        const newErrors = { ...errors };
        if (value.trim() === '') {
            newErrors[name] = 'Ce champ est requis';
        } else {
            delete newErrors[name];
        }
        setErrors(newErrors);
    };

    // Envoie des données de l’utilisateur au serveur, reception de la réponse contenant l’ID de l’utilisateur créé, et mise à jour l’état local avec cet ID. 
    // Cela permet de garder l’interface utilisateur synchronisée avec les données du serveur.
    const handleSubmit = (e) => {
        e.preventDefault();

        const validationErrors = validate();
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }

        console.log("JSON.stringify(utilisateurDto):" + JSON.stringify(utilisateurDto))

        fetch('http://localhost:8080/creationCompte/createutilisateur', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(utilisateurDto)
        })
        .then(response => {
            console.log("Réponse du serveur:", response);  // reponse du serveur après la requête
            // console.log("response.json:",response.json());
            if (!response.ok) {
                return response.json().then(err => { throw new Error(err.message || 'Erreur inconnue'); });
            }
            return response.json();
        })
        .then(data => {
            console.log('Utilisateur créé:', data);

            setUtilisateurDto(prevState => ({
                ...prevState,
                idUtilisateur: data.idUtilisateur // Met à jour idUtilisateur tout en conservant les autres propriétés
            }));

            console.log('id:', utilisateurDto);
            console.log('Type de idUtilisateur:', typeof data.idUtilisateur);
            console.log('id:', data.idUtilisateur); // Ok
            localStorage.setItem('idUtilisateur', data.idUtilisateur); // Stockage de l'id
            console.log('id:', utilisateurDto.idUtilisateur);
            console.log('nom:', utilisateurDto.nomUtilisateur);
            console.log('type:', utilisateurDto.typeUtilisateur);
    
            setIsSubmitted(true); // Masquer le bouton après l'envoi
        })

        .catch(error => {
            console.error('Erreur lors de la soumission du formulaire!', error);
        });
    }

    return (
        <Wrapper>
            <div className='titre'>
                <h1 className='text'>creation compte</h1>
            </div>
            <form onSubmit={handleSubmit} className='col-6 mx-auto my-3'>

                {errors.nomUtilisateur && <div className="error">{errors.nomUtilisateur}</div>}
                <ChampSaisie
                    ref={nomUtilisateurRef}
                    setValue={(value) => handleChange('nomUtilisateur', value)}
                    label="Nom :"
                    name="nomUtilisateur"
                    value={utilisateurDto.nomUtilisateur}
                    regex={nomRegex}
                />

                {errors.prenomUtilisateur && <div className="error">{errors.prenomUtilisateur}</div>}
                <ChampSaisie setValue={(value) => handleChange('prenomUtilisateur', value)} label="Prenom :" name="prenomUtilisateur" value={utilisateurDto.prenomUtilisateur} regex={otherRegex} ></ChampSaisie>

                {errors.entrepriseUtilisateur && <div className="error">{errors.entrepriseUtilisateur}</div>}
                <ChampSaisie setValue={(value) => handleChange('entrepriseUtilisateur', value)} value={utilisateurDto.entrepriseUtilisateur} label="Entreprise (entreprise représentée en tant que membre d’Initiative Deux-Sèvres) :" name="entrepriseUtilisateur" regex={otherRegex}  ></ChampSaisie>

                {errors.plateformeUtilisateur && <div className="error">{errors.plateformeUtilisateur}</div>}
                <ChampSaisie setValue={(value) => handleChange('plateformeUtilisateur', value)} value={utilisateurDto.plateformeUtilisateur} label="Plateforme Initiative :" name="plateformeUtilisateur" regex={otherRegex}  ></ChampSaisie>

                {errors.codeUtilisateur && <div className="error">{errors.codeUtilisateur}</div>}
                <ChampSaisie setValue={(value) => handleChange('codeUtilisateur', value)} label="Code d'accès :" value={utilisateurDto.codeUtilisateur} name="codeUtilisateur" regex={codeRegex}  ></ChampSaisie>

                {errors.type && <div className="error">{errors.type}</div>}
                <div class="form-label-type">
                    <label class="radio-label">
                        <input
                            type="radio"
                            name="type"
                            value="parrain"
                            checked={utilisateurDto.typeUtilisateur === 'parrain'}
                            onChange={(e) => handleChange('typeUtilisateur', e.target.value)}
                            class="radio-input"
                        />
                        Parrain
                    </label>
                    <label class="radio-label">
                        <input
                            type="radio"
                            name="type"
                            value="porteur"
                            checked={utilisateurDto.typeUtilisateur === 'porteur'}
                            onChange={(e) => handleChange('typeUtilisateur', e.target.value)}
                            class="radio-input"
                        />
                        Porteur
                    </label>
                </div>

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
                                            value={utilisateurDto.idUtilisateur}
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

export default CreationCompte;
