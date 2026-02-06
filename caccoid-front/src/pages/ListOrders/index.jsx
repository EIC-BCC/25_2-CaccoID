import { useState } from 'react';
import StudentCardDetailsModal from '../../components/StudentCardDetailsModal';
import * as S from './styles';
import Loading from '../../components/Loading';
import openIcon from '../../assets/open-icon.svg';
import userDefault from '../../assets/user-default.jpg';
import deleteIcon from '../../assets/red-delete-icon.svg';
import { dateFormatter } from '../../utils/dateFormatter';
import { DeletionConfirmationModal } from '../../components/DeletionConfirmationModal';

export const ListOrders = ({
  status,
  data,
  loading,
  error,
  selectedOrders,
  setSelectedOrders,
}) => {
  const [studentCardDetailsModal, setCardDetailsModal] = useState(null);
  const [deletionConfirmationModal, setDeletionConfirmationModal] = useState(0);

  const handleSelectAll = (e) => {
    if (e.target.checked) {
      setSelectedOrders(data);
    } else {
      setSelectedOrders([]);
    }
  };

  const handleSelectOne = (order) => {
    if (selectedOrders.some((o) => o.id === order.id)) {
      setSelectedOrders(selectedOrders.filter((o) => o.id !== order.id));
    } else {
      setSelectedOrders([...selectedOrders, order]);
    }
  };

  const allSelected = data.length > 0 && selectedOrders.length === data.length;

  if (loading) return <Loading />;

  if (error || (data.length === 0 && !loading)) {
    return (
      <S.Container>
        <S.ContainerInfo>
          <S.NoContentTitle>
            Não há solicitações para o status selecionado.
          </S.NoContentTitle>
        </S.ContainerInfo>
      </S.Container>
    );
  }

  return (
    <>
      <S.Container>
        <S.Table>
          <S.TableHeader>
            <S.TableRow>
              {status === 'EMITIDAS' || status === 'ENTREGUES' ? (
                <S.TableHeaderCell />
              ) : (
                <S.TableHeaderCell>
                  <div style={{ display: 'flex', alignItems: 'center' }}>
                    <S.CheckBox
                      checked={allSelected}
                      onChange={handleSelectAll}
                    />
                    <span>Todos</span>
                  </div>
                </S.TableHeaderCell>
              )}
              <S.TableHeaderCell>Foto</S.TableHeaderCell>
              <S.TableHeaderCell>Nome</S.TableHeaderCell>
              <S.TableHeaderCell>CPF</S.TableHeaderCell>
              <S.TableHeaderCell>Email</S.TableHeaderCell>
              <S.TableHeaderCell>Matrícula</S.TableHeaderCell>
              <S.TableHeaderCell>Data</S.TableHeaderCell>
              <S.TableHeaderCell />
            </S.TableRow>
          </S.TableHeader>

          <S.TableBody>
            {data?.map((order, index) => (
              <S.TableRow key={index}>
                <S.TableDataCell>
                  {status === 'EMITIDAS' || status === 'ENTREGUES' ? (
                    <S.TableHeaderCell />
                  ) : (
                    <S.TableHeaderCell>
                      <S.CheckBox
                        checked={selectedOrders.some((o) => o.id === order.id)}
                        onChange={() => handleSelectOne(order)}
                      />
                    </S.TableHeaderCell>
                  )}
                </S.TableDataCell>
                <S.TableDataCell>
                  <S.PhotoMini src={order.studentPhoto ?? userDefault} />
                </S.TableDataCell>
                <S.TableDataCell>{order.student.name}</S.TableDataCell>
                <S.TableDataCell>{order.student.cpf}</S.TableDataCell>
                <S.TableDataCell>{order.student.email}</S.TableDataCell>
                <S.TableDataCell>
                  {order.student.enrollmentNumber}
                </S.TableDataCell>
                <S.TableDataCell>
                  {dateFormatter(order.requestDate)}
                </S.TableDataCell>
                <S.TableDataCell>
                  <S.ButtonIcon onClick={() => setCardDetailsModal(order)}>
                    <img src={openIcon} />
                  </S.ButtonIcon>
                  {status !== 'AUTORIZADAS' && (
                    <S.ButtonIcon
                      onClick={() => setDeletionConfirmationModal(order.id)}
                    >
                      <img src={deleteIcon} />
                    </S.ButtonIcon>
                  )}
                </S.TableDataCell>
              </S.TableRow>
            ))}
          </S.TableBody>
        </S.Table>
      </S.Container>

      {studentCardDetailsModal && (
        <StudentCardDetailsModal
          data={studentCardDetailsModal}
          onClose={() => setCardDetailsModal(null)}
          status={status}
          onDelete={(id) => {
            setDeletionConfirmationModal(id);
          }}
        />
      )}

      {!!deletionConfirmationModal && (
        <DeletionConfirmationModal
          solicitationId={deletionConfirmationModal}
          onClose={() => setDeletionConfirmationModal(0)}
        />
      )}
    </>
  );
};
