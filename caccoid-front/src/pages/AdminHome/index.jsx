import { useState } from 'react';
import * as S from './styles';
import { useListOrders } from '../../hooks/useListOrders';
import { ListOrders } from '../ListOrders';
import { Tabs } from '../Tabs';
import { toast } from 'react-toastify';
import { useAPI } from '../../hooks/useAPI';

export default function AdminHome() {
  const tabsAdmin = [
    'SOLICITADAS',
    'PENDENTES',
    'AUTORIZADAS',
    'EMITIDAS',
    'PRODUÇÃO',
    'ENVIADAS',
    'ENTREGUES',
    'EXCLUIDAS',
  ];

  const api = useAPI();

  const [selectedTab, setSelectedTab] = useState(tabsAdmin[0]);
  const [selectedOrders, setSelectedOrders] = useState([]);
  const { data, loading, error } = useListOrders({ status: selectedTab });

  const handleSolicitationAuthorization = async (e) => {
    e.preventDefault();
    try {
      for (const order of selectedOrders) {
        await api.put(`/solicitation/authorize/${order.id}`, {});
      }
      toast.success('Solicitações autorizadas com sucesso');
    } catch (error) {
      toast.error(
        error.response?.data?.message || 'Erro ao autorizar solicitação'
      );
    }
  };

  const handleCreateStudentCard = async (e) => {
    e.preventDefault();
    try {
      for (const order of selectedOrders) {
        await api.post(`/student-card/create/${order.id}`, {});
      }
      toast.success('Carteirinha criada com sucesso');
    } catch (error) {
      toast.error(error.response?.data?.message || 'Erro ao criar carteirinha');
    }
  };

  const handleAuthorizationRevert = async (e) => {
    e.preventDefault();
    try {
      const selectedOrdersIds = selectedOrders.map((order) => order.id);
      const response = await api.put('/solicitation/revert', selectedOrdersIds);
      toast.success(response.data?.message);
    } catch (error) {
      toast.error(
        error.response?.data?.message || 'Erro ao reverter solicitações'
      );
    }
  };

  return (
    <S.Container>
      <Tabs
        tabs={tabsAdmin}
        selectedTab={selectedTab}
        setSelectedTab={setSelectedTab}
      />
      <ListOrders
        status={selectedTab}
        data={data}
        loading={loading}
        error={error}
        selectedOrders={selectedOrders}
        setSelectedOrders={setSelectedOrders}
      />
      <S.RightAlign>
        {selectedTab === 'SOLICITADAS' && (
          <S.AuthTabButton
            onClick={handleSolicitationAuthorization}
            isEmitButton={true}
          >
            Autorizar
          </S.AuthTabButton>
        )}
        {(selectedTab === 'AUTORIZADAS' || selectedTab === 'PENDENTES') && (
          <S.AuthTabButton onClick={handleAuthorizationRevert}>
            Reverter
          </S.AuthTabButton>
        )}
        {selectedTab === 'AUTORIZADAS' && (
          <S.AuthTabButton
            onClick={handleCreateStudentCard}
            isEmitButton={true}
          >
            Enviar Virtual
          </S.AuthTabButton>
        )}
        {selectedTab === 'EXCLUIDAS' && (
          <p>Todas as carteirinhas na lixeira são excluídas em 1 semana</p>
        )}
      </S.RightAlign>
    </S.Container>
  );
}
