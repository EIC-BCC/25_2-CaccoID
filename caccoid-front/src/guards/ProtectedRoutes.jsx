import { toast } from 'react-toastify';
import { useAuth } from '../contexts/AuthContext';
import { Navigate } from 'react-router-dom';

export function ProtectedRoute({ element, allowedRoles }) {
  const { isAuthenticated, role, loading } = useAuth();

  if (loading) return null;

  if (!isAuthenticated) {
    return <Navigate to="/microsoft-auth" replace />;
  }

  if (allowedRoles && !allowedRoles.includes(role)) {
    toast.error('Você não tem permissão para acessar esta página.');
    return <Navigate to="/forbidden" replace />;
  }

  return element;
}
