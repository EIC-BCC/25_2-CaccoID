import styled from 'styled-components';

export const ProgressBarContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 0 1rem;
  overflow-x: auto;

  @media screen and (max-width: 600px) {
    flex-direction: row;
    justify-content: space-between;
    padding: 0 0.5rem;
  }
`;

export const ProgressStep = styled.div`
  background-color: ${({ completed }) =>
    completed === 'completed' ? '#00aaff' : 'var(--lightest-grey)'};
  border: ${({ step }) => (step === 'active' ? '2px solid #00aaff' : 'none')};
  border-radius: 50%;
  width: 3.5rem;
  height: 3.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  z-index: 2;
  flex-shrink: 0;

  @media screen and (max-width: 600px) {
    width: 2.5rem;
    height: 2.5rem;
  }
`;

export const StepIcon = styled.img`
  width: 2rem;
  height: 2rem;
  object-fit: contain;

  @media screen and (max-width: 600px) {
    width: 1.5rem;
    height: 1.5rem;
  }
`;

export const ProgressLine = styled.div`
  height: 4px;
  background-color: ${({ completed }) =>
    completed === 'completed' ? '#00aaff' : '#e0e0e0'};
  flex-grow: 1;
  align-self: center;

  @media screen and (max-width: 600px) {
    height: 3px;
  }
`;
