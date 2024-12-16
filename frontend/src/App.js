// import './App.css';
import { Routes, Route } from 'react-router-dom';
import Accueil from './pages/Accueil/accueil';
import MonCompteParrain from './pages/MonCompteParrain/monCompteParrain';
import MonComptePorteur from './pages/MonComptePorteur/monComptePorteur';
import Profils from './pages/Profils/profils';
import Matchs from './pages/Matchs/matchs';
import Messages from './pages/Messages/messages';
import Ressources from './pages/Ressources/ressources';
import Indicateurs from './pages/Indicateurs/indicateurs';
import Connexion from './pages/Connexion/connexion';
import CreationCompte from './pages/CreationCompte/creationCompte';
import Filtres from './pages/Filtres/filtres';
import { AuthProvider } from './AuthContext';

function App() {
  return (
    <AuthProvider>
      <Routes>
        <Route path='/' element={<Accueil/>}></Route>
        <Route path='/monCompteParrain' element={<MonCompteParrain/>}></Route>
        <Route path='/monComptePorteur' element={<MonComptePorteur/>}></Route>
        <Route path='/profils' element={<Profils/>}></Route>
        <Route path='/matchs' element={<Matchs/>}></Route>
        <Route path='/messages' element={<Messages/>}></Route>
        <Route path='/ressources' element={<Ressources/>}></Route>
        <Route path='/indicateurs' element={<Indicateurs/>}></Route>
        <Route path='/connexion' element={<Connexion/>}></Route>
        <Route path='/creationCompte' element={<CreationCompte/>}></Route>
        <Route path='/filtres' element={<Filtres/>}></Route>
      </Routes>
    </AuthProvider>
  );
}

export default App;

