export const solicitationStatusConverter = (statusCode) => {
    return statusMap.find(item => item.label == statusCode).value;
}

const statusMap = [
    { label: 'SOLICITADAS', value: 'EM_ANALISE'},
    { label: 'PENDENTES', value: 'PENDENTE' },
    { label: 'AUTORIZADAS', value: 'AUTORIZADA' },
    { label: 'EMITIDAS', value: 'EMITIDA' },
    { label: 'PRODUÇÃO', value: 'PRODUCAO' },
    { label: 'ENVIADAS', value: 'ENVIADA' },
    { label: 'ENTREGUES', value: 'ENTREGUE' },
    { label: 'EXCLUIDAS', value: 'EXCLUIDA' }
]