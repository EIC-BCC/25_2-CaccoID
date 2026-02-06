import styled from 'styled-components';

export const Container = styled.div`
  max-width: 500px;
  width: 85%;
  background: var(--white);
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  border-radius: 24px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  h4 {
    margin: 4px 0;
    line-height: 1.2;
  }

  .info {
    padding: 48px;
    padding-bottom: 0;
  }

  .header {
    display: flex;
    align-items: center;
    gap: 12px;
    margin-bottom: 16px;
  }

  .header h1 {
    text-align: center;
    justify-content: flex-end;
    display: flex;
    align-items: center;
  }

  .header img {
    display: flex;
    align-items: center;
    width: 30%;
    margin: 0 15px 0 0;
  }

  .qr-photo {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    margin: 16px 0;
    padding-bottom: 12px;
  }

  .qr-photo img {
    width: 96px;
    height: 128px;
    object-fit: cover;
    border-radius: 8px;
  }

  .verified {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 20px;
    background: var(--dark-blue);
    color: white;
    padding: 8px 12px;
    border-radius: 0 0 8px 8px;
    margin: 0 0;
  }

  .verified img {
    width: 36px;
    height: 36px;
  }
`;
