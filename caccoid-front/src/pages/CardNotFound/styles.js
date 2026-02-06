import styled from 'styled-components';

export const Container = styled.div`
  max-width: 450px;
  width: 85%;
  background: var(--white);
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  border-radius: 24px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  .header {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 12px;
    margin-bottom: 16px;
    padding: 24px;
  }

  .header h1 {
    text-align: center;
    padding-bottom: 24px;
  }

  .header img {
    width: 30%;
    object-fit: contain;
    padding-bottom: 24px;
    padding-top: 24px;
  }

  .verified {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 20px;
    background: var(--dark-blue);
    color: white;
    border-radius: 0 0 8px 8px;
  }

  .verified img {
    width: 36px;
    height: 36px;
  }
`;
