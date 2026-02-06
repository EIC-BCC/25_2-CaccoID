import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import * as S from './styles';
import Container from '../../components/Container';
import UnderlinedTitle from '../../components/UnderlinedTitle';
import { ActionButton } from '../../components/ActionButton';
import MicrosoftLogo from '../../assets/microsoft-logo.svg';
import LabeledInput from '../../components/LabeledInput';
import { useAPI } from '../../hooks/useAPI';
import { useAuth } from '../../contexts/AuthContext';
import { toast } from 'react-toastify';
import alertIcon from '../../assets/alert-red-icon.svg';

export default function EmailAuth() {
  const [loginEmail, setLoginEmail] = useState('');
  const [loginPassword, setLoginPassword] = useState('');
  const [registerEmail, setRegisterEmail] = useState('');
  const [registerPassword, setRegisterPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const api = useAPI();
  const navigate = useNavigate();
  const { login } = useAuth();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      navigate('/home');
    }
  }, [navigate]);

  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post('/auth/login', {
        email: loginEmail,
        password: loginPassword,
      });

      const result = response.data.result;
      login(result.token, result.role == 0 ? 'Admin' : 'User');
      toast.success('Login realizado com sucesso');

      if (result.role === 0) {
        navigate('/admin-home');
      } else {
        navigate('/home');
      }
    } catch (error) {
      toast.error(error.response?.data?.message || 'Erro ao fazer login');
    }
  };

  const handleRegisterSubmit = async (e) => {
    e.preventDefault();
    if (registerPassword !== confirmPassword) return;

    try {
      await api.post('/auth/register', {
        email: registerEmail,
        password: registerPassword,
      });

      toast.success('Cadastro realizado com sucesso');
      setRegisterEmail('');
      setRegisterPassword('');
      setConfirmPassword('');
    } catch (error) {
      if (error.response?.status === 409) {
        toast.error(error.response?.data?.message);
      } else {
        toast.error(error.response?.data?.message || 'Erro ao registrar');
      }
    }
  };

  return (
    <>
      <S.BlueContainerDiv>
        <Container variant="blueRow">
          <S.StyledH3>
            <img src={alertIcon} />
            <strong>ATENÇÃO: ESTE É UM SITE DE TESTE</strong>
            <br />
            As carteirinhas exibidas aqui são apenas para demonstração e não
            possuem validade oficial.
          </S.StyledH3>
          {/*
          <S.StyledDiv>
            <ActionButton variant="quaternary">
              <S.MicrosoftIcon src={MicrosoftLogo} />
              FAÇA LOGIN COM CONTA MICROSOFT
            </ActionButton>
          </S.StyledDiv>
          */}
        </Container>
      </S.BlueContainerDiv>

      <S.BlueContainerDiv>
        <Container variant="blueRow">
          <S.StyledH3>
            Seus dados pessoais são armazenados de forma segura, com
            criptografia, em conformidade com a Lei Geral de Proteção de Dados
            (LGPD). Informamos que nenhuma informação é compartilhada com
            terceiros.
          </S.StyledH3>
        </Container>
      </S.BlueContainerDiv>

      <S.ContainerWrapper>
        <Container>
          <UnderlinedTitle color="var(--light-grey)">
            Faça login com seu e-mail pessoal
          </UnderlinedTitle>
          <S.form onSubmit={handleLoginSubmit}>
            <LabeledInput
              title="E-mail"
              placeholder="📧 Digite seu e-mail"
              type="email"
              value={loginEmail}
              onChange={(val) => setLoginEmail(val)}
            />
            <LabeledInput
              title="Senha"
              placeholder="🔒 Digite sua senha"
              type="password"
              value={loginPassword}
              onChange={(val) => setLoginPassword(val)}
            />
            <ActionButton variant="tertiary" type="submit">
              LOGIN
            </ActionButton>
          </S.form>
        </Container>

        <Container>
          <UnderlinedTitle color="var(--light-grey)">
            Crie uma conta com seu e-mail pessoal
          </UnderlinedTitle>
          <S.form onSubmit={handleRegisterSubmit}>
            <LabeledInput
              title="E-mail"
              placeholder="📧 Digite seu e-mail"
              type="email"
              value={registerEmail}
              onChange={(val) => setRegisterEmail(val)}
            />
            <LabeledInput
              title="Senha"
              placeholder="🔒 Digite sua senha"
              type="password"
              value={registerPassword}
              onChange={(val) => setRegisterPassword(val)}
            />
            <LabeledInput
              title="Confirme sua senha"
              placeholder="🔒 Confirme sua senha"
              type="password"
              value={confirmPassword}
              onChange={(val) => setConfirmPassword(val)}
              confirmPassword
              password={registerPassword}
            />
            <ActionButton variant="tertiary" type="submit">
              REGISTRE-SE
            </ActionButton>
          </S.form>
        </Container>
      </S.ContainerWrapper>
    </>
  );
}
