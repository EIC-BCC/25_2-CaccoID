import { useState } from 'react';
import whiteTrashIcon from '../../assets/trash-icon.svg';
import darkGreyTrashIcon from '../../assets/darkgrey-trash-icon.svg';

import * as S from './styles';

export const Tabs = ({ tabs, selectedTab, setSelectedTab }) => {
  const [hoveredTab, setHoveredTab] = useState(null);

  return (
    <S.ContainerTabs>
      {tabs?.map((tab, index) => 
        tab === 'EXCLUIDAS' ? (
        <S.TabItemIconContainer
            key={index}
            onClick={() => setSelectedTab(tab)}
            isSelected={selectedTab === tab}
            onMouseEnter={() => setHoveredTab(tab)}
            onMouseLeave={() => setHoveredTab(null)}
        >
          <S.TabItemIcon
            src={
                selectedTab === tab || hoveredTab === tab
                  ? whiteTrashIcon
                  : darkGreyTrashIcon
              }
            key={index}
          >
          </S.TabItemIcon>
        </S.TabItemIconContainer>  
        ) : (
        <S.TabItem
          key={index}
          onClick={() => setSelectedTab(tab)}
          isSelected={selectedTab === tab}
        >
          {tab}
        </S.TabItem>
      ))}
    </S.ContainerTabs>
  );
};
