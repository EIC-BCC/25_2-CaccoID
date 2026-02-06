import styled from 'styled-components';

export const Container = styled.div`
  width: 100%;
  max-width: 1100px;
  margin: 10px auto 10px auto;
  padding: 0 32px 20px 32px;
  background: var(--white);
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
`;

export const ContainerInfo = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  padding-top: 20px;
`;

export const NoContentTitle = styled.h2`
  font-family: 'Roboto Condensed', sans-serif;
  color: var(--dark-grey);
  font-size: 18px;
  font-weight: 400;
`;

export const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  background: var(--white);
  overflow: hidden;
`;

export const TableHeader = styled.thead`
  border-bottom: 1px solid var(--lightest-grey);
`;

export const TableRow = styled.tr`
  border-bottom: 1px solid var(--lightest-grey);
`;

export const TableDataCell = styled.td`
  padding: 10px;
  font-family: var(--font-regular);
  font-size: 12px;
  color: var(--dark-grey);
  vertical-align: middle;
  max-width: 160px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

export const TableHeaderCell = styled.th`
  padding: 14px 10px;
  font-family: 'Roboto Condensed', sans-serif;
  font-size: 16px;
  color: var(--black);
  font-weight: semibold;
  text-align: left;
  max-width: 160px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;
export const TableBody = styled.tbody``;

export const PhotoMini = styled.img`
  width: 35px;
  height: 35px;
  border-radius: 50%;
  object-fit: cover;
`;

export const ButtonIcon = styled.button`
  background: none;
  border: none;
  cursor: pointer;

  img {
    width: 16px;
    height: 16px;
  }

  &:hover {
    opacity: 0.8;
  }
`;

export const CheckBox = styled.input.attrs({ type: 'checkbox' })`
  appearance: none;
  -webkit-appearance: none;
  background-color: var(--white);
  border: 2px solid var(--light-grey);
  border-radius: 4px;
  width: 20px;
  height: 20px;
  cursor: pointer;
  margin-right: 1rem;
  transition: border-color 0.2s, box-shadow 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;

  &:checked {
    border-color: var(--dark-blue);
    background-color: var(--dark-blue);
  }

  &:checked::after {
    content: '';
    position: absolute;
    left: 4px;   
    bottom: 3px;    
    width: 6px;
    height: 12px;
    border: solid var(--white);
    border-width: 0 2px 2px 0;
    transform: rotate(45deg);
    display: block;
  }
`;

