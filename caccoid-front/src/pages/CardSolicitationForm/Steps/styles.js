import styled from "styled-components";


export const FormStep = styled.div`
  width: 100%;
  margin-bottom: 3rem;
  text-align: start;
  display: grid;
  grid-template-rows: 1fr;
`

export const Title = styled.h2`
  margin-bottom: 0;
  color: black;
  font-family: 'Bebas Neue', sans-serif;
  font-weight: 400;
  font-size: 1.8rem;

  @media (max-width: 768px) {
    font-size: 1.4rem;
    text-align: center;
  }
`

export const FormGrid = styled.div`
  width: 100%;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 2.5rem;

  @media (max-width: 768px) {
    grid-template-columns: 1fr;
    gap: 1rem;
  }
`

export const FormColumn = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
`

export const FormGroup = styled.div`
  margin-top: 1.5rem;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: flex-start;
  box-sizing: border-box;
`

export const FormInputLabel = styled.p`
  color: black;
  margin-bottom: 0.25rem;
  font-family: 'Roboto Mono', monospace;
  font-weight: bold;
  font-size: 1.2rem;

  & > span {
    color: #f04036;
  }

  @media (max-width: 768px) {
    font-size: 1rem;
  }
`
export const ErrorMessage = styled.span`
  color: #f04036;
  font-family: 'Bebas Neue', sans-serif;
  font-size: 1.2rem;

  @media (max-width: 768px) {
    font-size: 1rem;
  }
`

export const Checkbox = styled.div`
  display: inline-block;
  margin-bottom: 20px;
`
export const CheckboxLabel = styled.label`
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: black;
  font-family: 'Roboto Mono', monospace;
  font-weight: bold;
  font-size: 1.2rem;
  margin-right: 1rem;

  @media (max-width: 768px) {
    font-size: 1rem;
  }
`

export const DocumentInputContainer = styled.div`
  position: relative;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  font-family: 'Roboto Mono', monospace;
  font-size: 1rem;
  color: black;
  opacity: 0.5;
  background-color: white;
  border: 2px solid #d9d9d9;
  border-radius: 10px;
  width: 35vw;
  height: 3.2rem;
  padding-left: 0.8rem;
  padding-right: 0.8rem;
  margin-bottom: 0.3rem;

  @media (max-width: 768px) {
    width: 90%;
    height: 2.8rem;
  }
`

export const DocumentInput = styled.input`
  opacity: 0;
  inset: 0;
  position: absolute;
  height: 100%;
  width: 100%;
`
export const DocumentIcon = styled.img`
  width: 2rem;
  height: 2rem;

  @media (max-width: 768px) {
    width: 1.5rem;
    height: 1.5rem;
  }
`

export const LocationInput = styled.select`
  font-family: 'Roboto Mono', monospace;
  font-size: 1.2rem;
  color: black;
  background-color: white;
  border: 2px solid #d9d9d9;
  border-radius: 10px;
  width: 30vw;
  height: 3rem;
  padding-left: 0.8rem;

  &:hover {
    border: 2px solid #00aaff;
    outline: none;
  }

  @media (max-width: 768px) {
    width: 90%;
    font-size: 1rem;
    height: 2.8rem;
  }
`

export const UploadInputGrid = styled.div`
  display: grid;
  place-content: center;
`

export const UploadInputContainer = styled.div`
  display: flex;
  flex-direction: column;
  fill: white;
  cursor: pointer;
  position: relative;
  padding: 8rem;
  border: 2px dashed #00aaff;
  border-radius: 4px;
  color: black;
  font-family: 'Roboto Mono', monospace;
  font-weight: bold;
  font-size: 1rem;
  justify-content: center;
  align-items: center;

  @media (max-width: 768px) {
    padding: 4rem;
    font-size: 0.9rem;
  }
`

export const UploadIconContainer = styled.div`
  background-color: #00aaff;
  border-radius: 50%;
  width: 5rem;
  height: 5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;

  & > img { 
    width: 3rem;
    height: 3rem;
    object-fit: contain;

    @media (max-width: 768px) {
      width: 2rem;
      height: 2rem;
    }
  } 
`

export const UploadInput = styled.input`
  opacity: 0;
  inset: 0;
  position: absolute;
  height: 100%;
  width: 100%;
`

export const CropperWrapper = styled.div`
  width: 100%;
  max-width: 30px;
  aspect-ratio: 3 / 4;
  border-radius: 8px;
  overflow: hidden;
  margin: 0 auto;

  @media (max-width: 768px) {
    max-width: 20px;
  }
`

export const Button = styled.div`
  position: absolute;
  bottom: 30px;
  z-index: 10;
`
