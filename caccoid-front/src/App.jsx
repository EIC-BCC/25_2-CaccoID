import GlobalStyles from './globalStyles';
import { store } from './store';
import { Provider as ReduxProvider } from 'react-redux';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { CardSolicitationForm } from './pages/CardSolicitationForm';
import EmailAuth from './pages/EmailAuth';
import MicrosoftAuth from './pages/MicrosoftAuth';
import CardNotFound from './pages/CardNotFound';
import StudentCard from './pages/StudentCard';
import Navbar from './components/Navbar/index';
import Forbidden from './pages/Forbidden';
import { ThemeProvider } from 'styled-components';
import theme from './theme';
import UserHome from './pages/UserHome';
import { AuthProvider } from './contexts/AuthContext';
import { ToastContainer } from 'react-toastify';
import AdminHome from './pages/AdminHome';
import { ProtectedRoute } from './guards/ProtectedRoutes';

function App() {
  return (
    <>
      <ToastContainer
        position="bottom-right"
        autoClose={3000}
        hideProgressBar={false}
      />
      <AuthProvider>
        <ThemeProvider theme={theme}>
          <GlobalStyles />
          <ReduxProvider store={store}>
            <BrowserRouter>
              <Navbar />
              <div style={{ paddingTop: '90px', minHeight: '100vh' }}>
                <Routes>
                  <Route path="/" element={<EmailAuth />} />
                  <Route path="/microsoft-auth" element={<MicrosoftAuth />} />
                  <Route path="/email-auth" element={<EmailAuth />} />
                  <Route path="/forbidden" element={<Forbidden />} />
                  <Route
                    path="/carteirinha-invalida"
                    element={<CardNotFound />}
                  />
                  <Route
                    path="/carteirinha/:cardId"
                    element={<StudentCard />}
                  />
                  <Route
                    path="/form"
                    element={
                      <ProtectedRoute
                        element={<CardSolicitationForm />}
                        allowedRoles={['User']}
                      />
                    }
                  />
                  <Route
                    path="/home"
                    element={
                      <ProtectedRoute
                        element={<UserHome />}
                        allowedRoles={['User']}
                      />
                    }
                  />
                  <Route
                    path="/admin-home"
                    element={
                      <ProtectedRoute
                        element={<AdminHome />}
                        allowedRoles={['Admin']}
                      />
                    }
                  />
                </Routes>
              </div>
            </BrowserRouter>
          </ReduxProvider>
        </ThemeProvider>
      </AuthProvider>
    </>
  );
}

export default App;
