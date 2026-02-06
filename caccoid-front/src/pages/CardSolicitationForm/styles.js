import styled from 'styled-components';

export const FormWindow = styled.div`
  justify-content: center;
  background-color: white;
  border-radius: 20px;
  margin: 1rem 0 1rem 0;
  padding: 2rem;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80vw;
  
  @media (max-width: 768px) {
    width: 95vw;
    margin: 1rem 1rem;
  }
`
export const FormNavigationContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: ${({ firstPage }) =>
    firstPage ? 'flex-end' : 'space-between'};
  
  @media (max-width: 768px) {
    flex-direction: column-reverse;
    gap: 0.5rem;
    align-items: center;
  }
`

export const FormButton = styled.button`
  background-color: ${({ prevButton }) =>
    prevButton ? '#383838' : '#0c3561'
  };
  font-family: 'Roboto Mono', monospace;
  font-weight: bold;
  font-size: 1rem;
  display: inline-flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  color: white;
  height: 2.5rem;
  padding: 0 10px;
  height: 44px;
  border-radius: 5px;
  border: none;

  &:hover {
    opacity: 0.85;
  }
  
  & > img {
    transform: ${({ prevButton }) => prevButton ? 'rotate(180deg)' : 'none'};
    padding-left: 0.75rem;
    height: 1.35rem;
  }

  @media (max-width: 768px) {
    width: 100%;
    font-size: 0.9rem;

    & > img {
      height: 1.1rem;
      padding-left: 0.5rem;
    }
  }
`
