import Wrapper from '../../Wrapper/index'
import { useEffect, useState } from "react";
import { useLocation } from 'react-router-dom';
import { ChampSaisie } from '../CreationCompte/champSaisie';
import './filtres.css';
import Container from 'react-bootstrap/esm/Container';
import send from '../../media/images/logos/send_blanc.png';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
// import Container from 'react-bootstrap/Container';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../AuthContext';

const Filtres = () => {

  //  Initialisation des états des valeurs de utilisateurDto
  const [utilisateurDto, setUtilisateurDto] = useState({
    idUtilisateur: '',
    secteurReseauList: [],
    accompagnementTypeList: [],
    typeUtilisateur: ''
  });

  const location = useLocation(); // Ce hook permet d’accéder à l’objet location qui représente l’URL actuelle de l’application 
  const idUtilisateur = localStorage.getItem('idUtilisateur');
  const typeUtilisateur = localStorage.getItem('typeUtilisateur');
  console.log('recupération de idUtilisateur - page - filtres :', idUtilisateur);
  console.log('recupération de typeUtilisateur - page - filtres :', typeUtilisateur);

  
  const [secteurs, setSecteurs] = useState([]); // Liste des secteurs récupérés de la BDD
  const [secteurReseau, setSecteurReseau] = useState([]); // Liste des secteurs sélectionnés
  const [typesAccompagnement, setTypesAccompagnement] = useState([]); // Liste des types d'accompagnement récupérés
  const [accompagnements, setAccompagnements] = useState([]); // Liste des types sélectionnés
  const [isSubmitted, setIsSubmitted] = useState(false);
  
  // // Réinitialisation des états des valeurs de utilisateurDto
  // // lorsque le composant est monté (c’est-à-dire lorsque la page est chargée ou actualisée).
  // useEffect(() => {
  //   setUtilisateurDto({
  //     idUtilisateur: idUtilisateur,
  //     typeUtilisateur: typeUtilisateur
  //   });

  //   setIsSubmitted(false);
  // }, [location]); // A chaque fois que l’URL change (info connue grâce à l'objet location), le useEffect est déclenché pour réinitialiser la page.

  // Avant recupération des secteurs en bbd - suppression de l'initialisation de l'objet
  // const [secteurReseau, setSecteurReseau] = useState({
  // secteur1: false,
  // secteur2: false,
  // secteur3: false,
  // secteur4: false,
  // secteur5: false,
  // })

  // récupération des secteurs depuis la bdd en utlisant l'API Fetch
  useEffect(() => {
    fetch('http://localhost:8080/secteurReseau/listeSecteurReseau')
      .then(response => response.json())
      .then(data => {
        console.log("ListeSecteurReseau -> Secteurs:", data)
        setSecteurs(data);
      })
      .catch(error => {
        console.error('Erreur lors de la récupération des secteurs:', error);
      });
  }, []);

  // Avant recupération des secteurs en bbd - suppression de l'initialisation de l'objet
  // const [accompagnements, setAccompagnements] = useState({
  //   typeAccompagnement1: false,
  //   typeAccompagnement2: false,
  //   typeAccompagnement3: false,
  //   typeAccompagnement4: false,
  //   typeAccompagnement5: false,
  // })

  // récupération des types d'accompagnement depuis la bdd en utlisant l'API Fetch
  useEffect(() => {
    fetch('http://localhost:8080/accompagnement/listeAccompagnement')
      .then(response => response.json())
      .then(data => {
        setTypesAccompagnement(data);
        console.log('listeAccompagnement -> TypesAccompagnement:', data);
      })
      .catch(error => {
        console.error('Erreur lors de la récupération des secteurs:', error);
      });
  }, []);


  // Initialisation de l'objet utilisateurDto
  useEffect(() => {
    setUtilisateurDto({
      idUtilisateur: idUtilisateur,
      secteurReseauList: [],
      accompagnementTypeList: [],
      typeUtilisateur: typeUtilisateur
    })
  }, [location, idUtilisateur, typeUtilisateur]);

  // const handleChange = (event) => {

  //   const { name, checked } = event.target;

  //   // Différencier les cases cochées
  //   if (name.startsWith("secteur")) {
  //     setSecteurReseau((prevState) => ({
  //       ...prevState,
  //       [name]: checked,
  //     }));
  //   } else if (name.startsWith("typeAccompagnement")) {
  //     setAccompagnements((prevState) => ({
  //       ...prevState,
  //       [name]: checked
  //     }));
  //   }

  //   setUtilisateurDto((prevState) => ({
  //     ...prevState,
  //     [name]: checked,
  //   }));

  //================================ début version modifiée=====================

  const handleChange = (event, item, isSecteur) => {
    const { checked } = event.target;
  
    if (isSecteur) {
      // Gestion des secteurs
      setSecteurReseau((prevState) => {
        if (checked) {
          // Ajoutez l'objet secteur
          return [...prevState, { id: item.id, label: item.label }];
        } else {
          // Retirez l'objet secteur
          return prevState.filter((secteur) => secteur.id !== item.id);
        }
      });
    } else {
      // Gestion des types d'accompagnement
      setAccompagnements((prevState) => {
        if (checked) {
          // Ajoutez l'objet accompagnement
          return [...prevState, { id: item.id, label: item.label }];
        } else {
          // Retirez l'objet accompagnement
          return prevState.filter((type) => type.id !== item.id);
        }
      });
    }

  };


  // Soumission du formulaire
  const handleSubmit = (event) => {
    event.preventDefault();

    const utilisateurDtoToSend = {
      idUtilisateur: idUtilisateur,
      secteurReseauList: secteurReseau,
      accompagnementTypeList: accompagnements,
      typeUtilisateur: typeUtilisateur
    };

    console.log('Payload envoyé:', utilisateurDtoToSend);

    fetch('http://localhost:8080/creationCompte/filtres', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(utilisateurDtoToSend),
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('La réponse du réseau n\'était pas correcte');
        }
        return response.json();
      })
      .then(data => {
        console.log('Données reçues:', data);
        setIsSubmitted(true);
      })
      .catch(error => console.error('Erreur lors de l\'opération fetch:', error));
  };


  return (
    <Wrapper>
      <Container fluid>
        <form onSubmit={handleSubmit}>
          <Row className="align-items-start">
            <Col sm={1}></Col>
            <Col sm={5} className="d-flex justify-content-start flex-column">
              <div className='titre'>
                <h1 className='text'>Secteurs/Reseaux</h1>
              </div>
              {/* {secteurs.map((secteur, index) => (
                <label key={index} className="checkbox-label">
                  {secteur.label} */}
              {secteurs.map((secteur) => (
                <label key={secteur.id} className="checkbox-label">
                  {secteur.label}
                  <input
                    type="checkbox"
                    // ====================================== Code avec envoi du type : secteur5 = true=========
                    // name={`secteur${index + 1}`}
                    // checked={secteurReseau[`secteur${index + 1}`] || false}
                    // onChange={handleChange}
                    // ================================= Code pour envoyer : { "id": 16, "label": "Secteur 16" } ====================
                    name={`secteur${secteur.id}`}
                    checked={secteurReseau.some((item) => item.id === secteur.id)}
                    onChange={(event) => handleChange(event, secteur, true)}
                  />
                </label>
              ))}
              <br />
            </Col>
            {/* <Col sm={1}></Col> */}
            <Col sm={5} className="d-flex justify-content-start flex-column">
              <div className='titre'>
                <h1 className='text'>Types d’accompagnement (demandés / proposés)</h1>
              </div>
              {/* <form onSubmit={handleSubmit}>  */}
              <br />
              {/* {typesAccompagnement.map((typeAccompagnement, index) => (
                <label key={index} className="checkbox-label colonne-droite">
                  {typeAccompagnement.label} */}
              {typesAccompagnement.map((typeAccompagnement) => (
                <label key={typeAccompagnement.id} className="checkbox-label">
                  {typeAccompagnement.label}
                  <input
                    type="checkbox"
                    // name={`typeAccompagnement${index + 1}`}
                    // checked={accompagnements[`typeAccompagnement${index + 1}`] || false}
                    // onChange={handleChange}
                    name={`typeAccompagnement${typeAccompagnement.id}`}
                    checked={accompagnements.some((item) => item.id === typeAccompagnement.id)}
                    onChange={(event) => handleChange(event, typeAccompagnement, false)}
                  />
                </label>
              ))}
            </Col>
            <Col sm={1}></Col>
          </Row>
          {/* </Container>
          <Container fluid> */}
          <div className="position-bouton">
            <button
              type="submit"
              className="bouton-envoyer mt-3"
            >
              Enregistrer
            </button>
          </div>
        </form>
      </Container>
    </Wrapper>
  )
};

export default Filtres;

// =============================================== Secteurs/Reseaux en dur

{/* <label className="checkbox-label">
  Activités de services administratifs et de soutien
  <input
    type="checkbox"
    name="secteur1"
    checked={secteurReseau.secteur1}
    onChange={handleChange}
  />
</label>
<label className="checkbox-label">
  Activités spécialisées, scientifiques et techniques
  <input
    type="checkbox"
    name="secteur2"
    checked={secteurReseau.secteur2}
    onChange={handleChange}
  />
</label>
<label className="checkbox-label">
  Agriculture, sylviculture, pêche
  <input
    type="checkbox"
    name="secteur3"
    checked={secteurReseau.secteur3}
    onChange={handleChange}
  />
</label>
<label className="checkbox-label">
  Arts, spectacles et activités récréatives
  <input
    type="checkbox"
    name="secteur4"
    checked={secteurReseau.secteur4}
    onChange={handleChange}
  />
</label>
<label className="checkbox-label">
  Commerce et réparation
  <input
    type="checkbox"
    name="secteur5"
    checked={secteurReseau.secteur5}
    onChange={handleChange}
  />
</label>
<label className="checkbox-label">
  Commerce et réparation
  <input
    type="checkbox"
    name="secteur6"
    checked={secteurReseau.secteur6}
    onChange={handleChange}
  />
</label><br /> */}
{/* </form> */ }



// ================================ TypesAccompagnement en dur
{/* <label className="checkbox-label">
  Ressources humaines
  <input
    type="checkbox"
    name="typeAccompagnement1"
    checked={accompagnements.typeAccompagnement1}
    onChange={handleChange}
  />
</label><br/>
<label className="checkbox-label">
  Finance / Comptabilité
  <input
    type="checkbox"
    name="typeAccompagnement2"
    checked={accompagnements.typeAccompagnement2}
    onChange={handleChange}
  />
</label><br />
<label className="checkbox-label">
  Juridique
  <input
    type="checkbox"
    name="typeAccompagnement3"
    checked={accompagnements.typeAccompagnement3}
    onChange={handleChange}
  />
</label><br />
<label className="checkbox-label">
  Informatique
  <input
    type="checkbox"
    name="typeAccompagnement4"
    checked={accompagnements.typeAccompagnement4}
    onChange={handleChange}
  />
</label><br />
<label className="checkbox-label">
  Commercial / Communication
  <input
    type="checkbox"
    name="typeAccompagnement5"
    checked={accompagnements.typeAccompagnement5}
    onChange={handleChange}
  />
</label><br /> */}