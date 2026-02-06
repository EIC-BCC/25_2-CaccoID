import * as S from './styles';
import RedErrorCircle from '../../assets/red-error-circle.svg';
import CaccoLogo from '../../assets/cacco-logo.png';

const CardNotFound = () => {
  return (
    <S.Container>
      <div className="header">
        <img src={RedErrorCircle} alt="Erro" />
        <h1>Carteirinha Inválida ou Expirada</h1>
      </div>
      <div className="verified">
        <h4>
          <strong>Verificado por:</strong> CaccoID
        </h4>
        <img src={CaccoLogo} alt="CaccoID Logo" />
      </div>
    </S.Container>
  );
};

export default CardNotFound;
