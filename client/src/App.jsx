import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import CreatePaste from './pages/CreatePaste';
import Dashboard from './pages/Dashboard';
import ViewPaste from './pages/ViewPaste';

export default function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/create" element={<CreatePaste />} />
        <Route path="/view/:id" element={<ViewPaste />} />
      </Routes>
    </BrowserRouter>
  );
}
