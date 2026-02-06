import styled from "styled-components";

export const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  gap: 10px;
  padding: 20px;
  margin-top: 25px;

  & > p {
    font-size: 14px;
    color: var(--dark-grey);
    align-self: right;
  }
`;

export const RightAlign = styled.div`
  width: 100%;
  max-width: 1100px;
  margin: 0 auto;
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
`;

export const AuthTabButton = styled.button`
  padding: 10px 20px;
  background-color: ${(props) =>
    props.isEmitButton ? 'var(--dark-blue)' : 'var(--dark-grey)'};
  color: var(--white);
  border: none;
  cursor: pointer;
  font-size: 20px;
  font-family: 'Bebas Neue', sans-serif;
`;