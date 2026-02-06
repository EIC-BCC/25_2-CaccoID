import styled from 'styled-components';

export const Overlay = styled.div`
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
`;

export const ModalContainer = styled.div`
  background: var(--white);
  border-radius: 8px;
  width: 35%;
  height: 35%;
  position: relative;
  padding: 2rem;
  font-family: var(--font-regular);
  display: flex;
  flex-direction: column;
`;

export const CloseButton = styled.button`
  position: absolute;
  top: 1rem;
  right: 1rem;
  background: none;
  border: none;
  font-size: 1.5rem;
  color: var(--dark-grey);
  cursor: pointer;
`;

export const ModalContent = styled.div`
  display: flex;
  gap: 2rem;
  flex: 1;
  overflow: auto;
`;

export const Actions = styled.div`
  display: flex;
  justify-content: center;
  padding: 2rem 0;
  gap: 2rem;
  Button{
  width: 100%;
  }
`;

export const Icon = styled.div`
  width: 50px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 1rem;
  img {
    width: 100%;
    height: 100%;
  }
`;

export const Text = styled.p`
  margin-top: 1rem;
  font-size: 26px;
  font-weight: 800;
  margin: 0.2;
`;

export const Warning = styled.p`
  font-size: 16px;
  color: var(--dark-red);
  margin: 0;
`;