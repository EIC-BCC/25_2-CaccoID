import * as S from './styles';
import logo from '../../assets/cacco-logo.png';
import userIcon from '../../assets/user-icon.svg';
import topArrowIcon from '../../assets/simple-arrow-top-icon.svg';
import bottomArrowIcon from '../../assets/simple-arrow-bottom-icon.svg';
import { ActionButton } from '../ActionButton';
import { useAuth } from '../../contexts/AuthContext';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { useEffect, useRef, useState } from 'react';

const Navbar = () => {
  const { userName, logout } = useAuth();
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const navigate = useNavigate();
  const dropdownRef = useRef();
  const navbarRightRef = useRef();

  const handleLogout = () => {
    try {
      logout();
      setIsDropdownOpen(false);
      toast.success('Logout realizado com sucesso');
      navigate('/email-auth');
    } catch (error) {
      toast.error(error.message || 'Erro ao realizar logout');
    }
  };

  useEffect(() => {
    function handleClickOutside(event) {
      if (
        dropdownRef.current &&
        !dropdownRef.current.contains(event.target) &&
        navbarRightRef.current &&
        !navbarRightRef.current.contains(event.target)
      ) {
        setIsDropdownOpen(false);
      }
    }
    if (isDropdownOpen) {
      document.addEventListener('mousedown', handleClickOutside);
    }
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [isDropdownOpen]);

  useEffect(() => {
    setIsDropdownOpen(false);
  }, []);

  return (
    <S.NavbarContainer>
      <S.NavbarLeft>
        <S.HomeLink to="/">
          <S.Logo src={logo} alt="CACCO Logo" />
          <S.NavbarTitle>CACCO ID</S.NavbarTitle>
        </S.HomeLink>
      </S.NavbarLeft>
      <S.NavbarRight ref={navbarRightRef}>
        {userName && (
          <>
            <S.UserIcon src={userIcon} alt="Usuário" />
            <S.UserName>{userName}</S.UserName>
            <S.DropdownIcon
              src={isDropdownOpen ? topArrowIcon : bottomArrowIcon}
              onClick={() => setIsDropdownOpen(!isDropdownOpen)}
            />

            {isDropdownOpen && (
              <S.DropdownContainer ref={dropdownRef}>
                <ActionButton variant="danger" onClick={handleLogout}>
                  Logout
                </ActionButton>
              </S.DropdownContainer>
            )}
          </>
        )}
      </S.NavbarRight>
    </S.NavbarContainer>
  );
};

export default Navbar;
