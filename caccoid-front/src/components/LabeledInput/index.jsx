import { useState } from 'react';
import {
  Wrapper,
  Label,
  Input,
  ErrorMessage,
  ToggleButton,
  ToggleIcon,
  InputWrapper,
} from './styles';
import ShowIcon from '../../assets/show.svg';
import HideIcon from '../../assets/hide.svg';

export default function LabeledInput({
  title,
  placeholder,
  type = 'text',
  value: externalValue,
  onChange,
  confirmPassword = false,
  password = '',
}) {
  const [internalValue, setInternalValue] = useState('');
  const [error, setError] = useState('');
  const [showPassword, setShowPassword] = useState(false);

  const isPasswordField = type === 'password';
  const inputType = isPasswordField
    ? showPassword
      ? 'text'
      : 'password'
    : type;

  const value = externalValue !== undefined ? externalValue : internalValue;

  const validate = (val) => {
    if (type === 'email') {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return emailRegex.test(val) ? '' : 'E-mail inválido.';
    }

    if (type === 'password') {
      if (val.length < 6) return 'A senha deve ter no mínimo 6 caracteres.';
      if (confirmPassword && val !== password)
        return 'As senhas não coincidem.';
      return '';
    }

    return '';
  };

  const handleChange = (e) => {
    const val = e.target.value;
    if (externalValue === undefined) setInternalValue(val);

    const validationError = validate(val);
    setError(validationError);
    if (onChange) onChange(val, validationError);
  };

  return (
    <Wrapper>
      <Label>{title}</Label>
      <InputWrapper>
        <Input
          type={inputType}
          placeholder={placeholder}
          value={value}
          onChange={handleChange}
          hasError={!!error}
        />
        {isPasswordField && (
          <ToggleButton
            type="button"
            onClick={() => setShowPassword((prev) => !prev)}
            aria-label={showPassword ? 'Mostrar senha' : 'Ocultar senha'}
          >
            <ToggleIcon
              src={showPassword ? ShowIcon : HideIcon}
              alt={showPassword ? 'Mostrar senha' : 'Ocultar senha'}
            />
          </ToggleButton>
        )}
      </InputWrapper>
      {error && <ErrorMessage>{error}</ErrorMessage>}
    </Wrapper>
  );
}
