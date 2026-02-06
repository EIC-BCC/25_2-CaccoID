import { useParams } from 'react-router-dom';
import { useStudentCard } from '../../hooks/useStudentCard';
import CardNotFound from '../CardNotFound';
import * as S from './styles';
import GreenCheckCircle from '../../assets/green-check-circle.svg';
import { QRCodeSVG } from 'qrcode.react';
import CaccoLogo from '../../assets/cacco-logo.png';

const formatDate = (dateString) => {
  if (!dateString) return 'Não informado';
  const [year, month, day] = dateString.split('-');
  return `${day}/${month}/${year}`;
};

export const StudentCard = () => {
  const { cardId } = useParams();
  const { card, loading, error } = useStudentCard(cardId);

  if (loading) return <p>Carregando...</p>;
  if (error || !card) return <CardNotFound />;

  const qrValue = `${window.location.origin}/carteirinha/${cardId}`;

  return (
    <S.Container>
      <div className="info">
        <div className="header">
          <img src={GreenCheckCircle} alt="Check" />
          <h1>
            Carteirinha Válida até <br />
            {formatDate(card.validity)}
          </h1>
        </div>

        <h4>
          <strong>Nome:</strong> {card.name}
        </h4>
        <h4>
          <strong>Data de Nascimento:</strong> {formatDate(card.dateOfBirth)}
        </h4>
        <h4>
          <strong>Nível de Educação:</strong> {card.educationLevel}
        </h4>
        <h4>
          <strong>Instituição:</strong> {card.institution}
        </h4>
        <h4>
          <strong>Curso:</strong> {card.program}
        </h4>
        <h4>
          <strong>Matrícula:</strong> {card.enrollmentNumber}
        </h4>
        <h4>
          <strong>CPF:</strong> {card.cpf}
        </h4>
        <h4>
          <strong>RG:</strong> {card.rg}
        </h4>
        <h4>
          <strong>Validade:</strong> {formatDate(card.validity)}
        </h4>

        <div className="qr-photo">
          <img
            src={`data:image/png;base64,${card.studentPhotoBase64}`}
            alt="Foto do aluno"
          />
          <QRCodeSVG value={qrValue} size={100} />
        </div>
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

export default StudentCard;
