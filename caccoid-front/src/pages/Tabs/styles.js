import styled from 'styled-components';

export const ContainerTabs = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  align-items: center;
  gap: 20px;
  padding: 10px 0;
`;

export const TabItem = styled.button`
  padding: 10px 20px;
  background-color: ${(props) =>
    props.isSelected ? 'var(--dark-grey)' : 'var(--white)'};
  color: ${(props) => (props.isSelected ? 'var(--white)' : 'var(--dark-grey)')};
  border: none;
  cursor: pointer;
  font-size: 20px;
  font-family: 'Bebas Neue', sans-serif;

  &:hover {
    background: var(--dark-blue);
    color: var(--white);
  }
`;

export const TabItemIconContainer = styled.div`
  background-color: ${(props) =>
    props.isSelected ? 'var(--dark-grey)' : 'var(--white)'};
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  padding: 10px 20px;
  
  &:hover {
    background: var(--dark-blue);
    color: var(--white);
  }

`

export const TabItemIcon = styled.img`
  color: ${(props) => (props.isSelected ? 'var(--white)' : 'var(--dark-grey)')};
  width: 1.5rem;
  height: 1.5rem;
`;
