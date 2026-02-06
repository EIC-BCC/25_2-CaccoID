import styled from 'styled-components';

const getColor = (variant) => {
  switch (variant) {
    case 'PENDENTE':
      return '#F04036';
    case 'EM_ANALISE':
      return 'var(--medium-yellow)';
    case 'AUTORIZADA':
      return 'var(--light-blue)';
    case 'EMITIDA':
      return 'var(--medium-green)';
    default:
      return 'var(--black)';
  }
};

export const CardContainer = styled.div`
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  background-color: var(--white);
  padding: 20px 50px;
  width: 80%;
  min-width: 800px;
  height: auto;
  border-radius: 10px;

  @media (max-width: 768px) {
    flex-direction: column;
    width: 80%;
    min-width: auto;
    padding: 15px;
    height: auto;
    gap: 15px;
    align-items: center;
    justify-content: center;
  }
`;

export const Content = styled.div`
  display: flex;
  flex-direction: row;
  gap: 40px;

  @media (max-width: 768px) {
    gap: 10px;
    flex-direction: column;
  }
`;

export const Image = styled.img`
  width: 100%;
  max-width: 150px;
  aspect-ratio: 3 / 4;
  object-fit: cover;
  border-radius: 6px;

  @media (max-width: 768px) {
    max-width: 180px;
    margin: 0 auto;
  }
`;

export const InfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  gap: 20px;

  @media (max-width: 768px) {
    gap: 10px;
    width: 100%;
    justify-content: center;
    align-items: center;
  }
`;

export const InfoText = styled.span`
  font-family: var(--font-regular);
  font-size: 16px;

  @media (max-width: 768px) {
    font-size: 14px;
  }
`;

export const InfoLabel = styled.span`
  font-family: var(--font-regular);
  font-size: 16px;
  color: var(--light-grey);

  @media (max-width: 768px) {
    font-size: 14px;
  }
`;

export const InfoItem = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5px;
  
  @media (max-width: 768px) {
    justify-content: center;
    align-items: center;
  }
`;

export const Status = styled.div`
  display: flex;
  font-family: var(--font-regular);
  font-size: 14px;
  background-color: #ededed;
  border-radius: 10px;
  padding: 5px;
  margin-bottom: 5px;
  min-width: 200px;
  max-height: 20px;
  align-items: center;

  span {
    display: inline-block;
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background-color: ${({ status }) => getColor(status)};
    margin-right: 5px;
  }

  @media (max-width: 768px) {
    font-size: 12px;
    min-width: auto;
    max-height: 18px;

    span {
      width: 8px;
      height: 8px;
    }
  }
`;

export const AlertContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: flex-start;
  gap: 10px;

  @media (max-width: 768px) {
    justify-content: flex-start;
    gap: 5px;
    flex-wrap: wrap;
  }
`;

export const CardActions = styled.div`
  display: flex;
  flex-direction: column;
  gap: 90px;
  align-items: flex-end;

  button {
    max-width: 200px;
    width: 200px;
  }

  @media (max-width: 768px) {
    gap: 20px;
    align-items: flex-start;

    button {
      width: 100%;
      max-width: none;
    }
  }
`;
