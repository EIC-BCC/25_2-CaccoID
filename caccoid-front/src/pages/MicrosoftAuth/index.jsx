import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import * as S from './styles';
import MicrosoftLogo from '../../assets/microsoft-logo.svg';
import Container from '../../components/Container';
import UnderlinedTitle from '../../components/UnderlinedTitle';
import { ActionButton } from '../../components/ActionButton';

export default function MicrosoftAuth() {
  const navigate = useNavigate();
  const { isAuthenticated, role, loading } = useAuth();

  useEffect(() => {
    if (!loading && isAuthenticated) {
      if (role === 'User') {
        navigate('/home', { replace: true });
      } else if (role === 'Admin') {
        navigate('/admin-home', { replace: true });
      }
    }
  }, [isAuthenticated, role, loading, navigate]);

  if (loading) return null;

  return (
    <Container variant="centered">
      <UnderlinedTitle color="var(--medium-blue)">
        Entrar com conta institucional CEFET-RJ
      </UnderlinedTitle>
      <ActionButton variant="tertiary">
        <S.MicrosoftIcon src={MicrosoftLogo} />
        MICROSOFT LOGIN
      </ActionButton>
      <Container color="var(--off-white)">
        <S.StyledH3>
          Olá, Calouro! Ainda não possui e-mail institucional?
        </S.StyledH3>
        <S.StyledH3>
          <S.StyledLink to="/email-auth">
            Crie uma conta ou faça login
          </S.StyledLink>{' '}
          com seu e-mail pessoal.
        </S.StyledH3>
      </Container>
    </Container>
  );
}
