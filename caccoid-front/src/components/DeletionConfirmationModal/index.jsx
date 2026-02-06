import alertIcon from '../../assets/alert-red-icon.svg';
import * as S from './styles';
import { ActionButton } from '../ActionButton';
import { useAPI } from '../../hooks/useAPI';
import { toast } from 'react-toastify';


export const DeletionConfirmationModal = ({ solicitationId, onClose }) => {
  const api = useAPI();

  const handleSolicitationDeletion = async (e) => {
    e.preventDefault();
    try {
      const response = await api.delete('/solicitation/reject/' + solicitationId);
      toast.success(response.data?.message);
      onClose();
      
    } catch (error) {
      toast.error(error.response?.data?.message || 'Erro ao apagar solicitação');
    }
  };

  return (
    <S.Overlay>
      <S.ModalContainer>
        <S.CloseButton onClick={onClose}>×</S.CloseButton>
        <S.Icon>
          <img src={alertIcon} alt="alert-icon" />
        </S.Icon>
        <S.Text>Tem certeza que deseja apagar essa solicitação? </S.Text>
        <S.Warning>Essa ação não pode ser desfeita.</S.Warning>
        <S.Actions>
          <ActionButton variant="danger" onClick={handleSolicitationDeletion}>Apagar</ActionButton>
          <ActionButton variant="quaternary" onClick={onClose}>
            Manter Solicitação
          </ActionButton>
        </S.Actions>
      </S.ModalContainer>
    </S.Overlay>
  );
};

export default DeletionConfirmationModal;
