import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Login from '../pages/login';
import Vantagem from '../pages/vantagem';
import ProfessorDoar from '../pages/professor/Doar';
import Extrato from '../pages/transactions/Extrato';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/login" element={<Login />} />
        <Route path="/vantagens/:empresaId" element={<Vantagem />} />
        <Route path="/professor/:professorId/doar" element={<ProfessorDoar />} />
        <Route path="/extrato/:userId" element={<Extrato />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
