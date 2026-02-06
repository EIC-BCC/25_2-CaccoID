import { useState } from 'react';
import * as S from './styles';
import LabeledInput from '../LabeledInput';
import { ActionButton } from '../ActionButton';
import deleteIcon from '../../assets/white-delete-icon.svg';
import openIcon from '../../assets/open-icon.svg';
import { dateFormatter } from '../../utils/dateFormatter';
import { toast } from 'react-toastify';
import { useAPI } from '../../hooks/useAPI';

const StudentCardDetailsModal = ({ data, onClose, status, onDelete }) => {
  const [activeTab, setActiveTab] = useState('dados');
  const [formData, setFormData] = useState({ ...data });
  const [changeRequests, setChangeRequests] = useState({});
  const [isEditing, setIsEditing] = useState(false);
  const api = useAPI();

  if (!data) return null;

  const handleChange = (field, value) => {
    setFormData((prev) => ({ ...prev, [field]: value }));
  };

  const handleCheckboxChange = (field) => {
    setChangeRequests((prev) => ({ ...prev, [field]: !prev[field] }));
  };

  const handleStartChangeRequest = () => {
    setIsEditing(true);
  };

  const handleCancelChangeRequest = () => {
    setIsEditing(false);
    setChangeRequests({});
  };

  const handleSendChanges = () => {
    const camposSolicitados = Object.keys(changeRequests).filter(
      (key) => changeRequests[key]
    );
    console.log('Campos solicitados para mudança:', camposSolicitados);
    setFormData((prev) => ({ ...prev, status: 'PENDENTE' }));
    toast.info(
      `Status da carteirinha atualizado para pendente. Campos solicitados: ${camposSolicitados.join(
        ', '
      )}`
    );
    setIsEditing(false);
    onClose();
  };

  const handleSolicitationAuthorization = async (e) => {
    e.preventDefault();
    try {
      const response = await api.put(
        '/solicitation/authorize/' + formData.id,
        {}
      );
      toast.success(response.data?.message);
      onClose();
    } catch (error) {
      toast.error(
        error.response?.data?.message || 'Erro ao autorizar solicitação'
      );
    }
  };

  const renderInputWithCheckbox = (title, value, field, type) => (
    <S.InputCheckboxWrapper>
      {isEditing && (
        <S.CheckBox
          checked={!!changeRequests[field]}
          onChange={() => handleCheckboxChange(field)}
        />
      )}
      <LabeledInput
        title={title}
        value={value}
        onChange={(v) => handleChange(field, v)}
        type={type}
      />
    </S.InputCheckboxWrapper>
  );

  return (
    <S.Overlay>
      <S.ModalContainer>
        <S.CloseButton onClick={onClose}>×</S.CloseButton>
        <S.ModalContent>
          <S.InputCheckboxWrapper>
            {isEditing && (
              <S.CheckBox
                checked={!!changeRequests.studentPhoto}
                onChange={() => handleCheckboxChange('studentPhoto')}
              />
            )}
            <S.Photo src={formData.studentPhoto} alt={formData.nome} />
          </S.InputCheckboxWrapper>
          <S.RightContent>
            <S.Tabs>
              <S.Tab
                active={activeTab === 'dados'}
                onClick={() => setActiveTab('dados')}
              >
                DADOS
              </S.Tab>
              <S.Tab
                active={activeTab === 'entrega'}
                onClick={() => setActiveTab('entrega')}
              >
                ENTREGA
              </S.Tab>
              <S.Tab
                active={activeTab === 'documentos'}
                onClick={() => setActiveTab('documentos')}
              >
                DOCUMENTOS
              </S.Tab>
            </S.Tabs>

            {activeTab === 'dados' && (
              <>
                <S.GridTwoThirdsOneThird>
                  {renderInputWithCheckbox(
                    'Nome',
                    formData.student.name,
                    'nome'
                  )}
                  {renderInputWithCheckbox(
                    'Data do pedido',
                    dateFormatter(formData.requestDate),
                    'data'
                  )}
                </S.GridTwoThirdsOneThird>

                <S.GridThreeEqual>
                  {renderInputWithCheckbox('RG', formData.student.rg, 'rg')}
                  {renderInputWithCheckbox('CPF', formData.student.cpf, 'cpf')}
                  {renderInputWithCheckbox(
                    'Matrícula',
                    formData.student.enrollmentNumber,
                    'matricula'
                  )}
                </S.GridThreeEqual>

                <S.GridTwoEqual>
                  {renderInputWithCheckbox(
                    'Curso',
                    formData.student.program,
                    'curso'
                  )}
                  {renderInputWithCheckbox(
                    'Instituição',
                    formData.student.institution,
                    'instituicao'
                  )}
                </S.GridTwoEqual>

                <S.GridTwoThirdsOneThird>
                  {renderInputWithCheckbox(
                    'E-mail',
                    formData.student.email,
                    'email',
                    'email'
                  )}
                  {renderInputWithCheckbox(
                    'Telefone',
                    formData.student.telephone,
                    'telefone'
                  )}
                  {renderInputWithCheckbox(
                    'Id do Estudante',
                    formData.student.id,
                    'id'
                  )}
                </S.GridTwoThirdsOneThird>
              </>
            )}

            {activeTab === 'entrega' && (
              <>
                <S.GridSingleColumn>
                  {renderInputWithCheckbox(
                    'Modalidade',
                    formData.modalidade,
                    'modalidade'
                  )}
                </S.GridSingleColumn>

                <S.GridTwoThirdsOneThird>
                  {renderInputWithCheckbox(
                    'Lougradouro',
                    formData.lougradouro,
                    'lougradouro'
                  )}
                  {renderInputWithCheckbox('Número', formData.numero, 'numero')}
                </S.GridTwoThirdsOneThird>

                <S.GridTwoThirdsOneThird>
                  {renderInputWithCheckbox(
                    'Complemento',
                    formData.complemento,
                    'complemento'
                  )}
                  {renderInputWithCheckbox('CEP', formData.cep, 'cep')}
                </S.GridTwoThirdsOneThird>

                <S.GridThreeEqual>
                  {renderInputWithCheckbox('Bairro', formData.bairro, 'bairro')}
                  {renderInputWithCheckbox('Estado', formData.estado, 'estado')}
                  {renderInputWithCheckbox('Cidade', formData.cidade, 'cidade')}
                </S.GridThreeEqual>
              </>
            )}

            {activeTab === 'documentos' && (
              <>
                {[
                  'enrollmentProof',
                  'paymentProof',
                  'identityDocumentFront',
                  'identityDocumentBack',
                ].map((docField) => (
                  <S.DocumentDiv key={docField}>
                    {isEditing && (
                      <S.CheckBox
                        checked={!!changeRequests[docField]}
                        onChange={() => handleCheckboxChange(docField)}
                      />
                    )}
                    {(() => {
                      switch (docField) {
                        case 'enrollmentProof':
                          return 'Comprovante de Matrícula';
                        case 'paymentProof':
                          return 'Comprovante de Pagamento';
                        case 'identityDocumentFront':
                          return 'Documento de Identificação - Frente';
                        case 'identityDocumentBack':
                          return 'Documento de Identificação - Verso';
                      }
                    })()}
                    <S.OpenButtonIcon>
                      <a
                        href={formData[docField]}
                        target="_blank"
                        rel="noopener noreferrer"
                      >
                        <img src={openIcon} style={{ cursor: 'pointer' }} />
                      </a>
                    </S.OpenButtonIcon>
                  </S.DocumentDiv>
                ))}
              </>
            )}
          </S.RightContent>
        </S.ModalContent>

        {(status === 'SOLICITADAS' || status === 'PENDENTES') && (
          <S.Actions>
            <S.DeleteButtonIcon onClick={() => onDelete(data.id)}>
              <img src={deleteIcon} />
            </S.DeleteButtonIcon>

            {!isEditing ? (
              <ActionButton
                variant="quaternary"
                onClick={handleStartChangeRequest}
              >
                SOLICITAR MUDANÇA
              </ActionButton>
            ) : (
              <>
                <ActionButton
                  variant="quaternary"
                  onClick={handleCancelChangeRequest}
                >
                  VOLTAR
                </ActionButton>
                <ActionButton variant="primary" onClick={handleSendChanges}>
                  ENVIAR
                </ActionButton>
              </>
            )}

            {!isEditing && (
              <ActionButton
                variant="primary"
                onClick={handleSolicitationAuthorization}
              >
                AUTORIZAR
              </ActionButton>
            )}
          </S.Actions>
        )}
      </S.ModalContainer>
    </S.Overlay>
  );
};

export default StudentCardDetailsModal;
