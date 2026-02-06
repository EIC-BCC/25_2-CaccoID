import { useState, useCallback } from 'react';
import { Controller, useFormContext } from 'react-hook-form';
import Cropper from 'react-easy-crop';
import uploadIcon from '../../../assets/upload-icon.svg';
import getCroppedImg from '../../../utils/cropImage';
import * as S from './styles';
import { ActionButton } from '../../../components/ActionButton';

export const PhotoStep = ({ goToNextStep }) => {
  const { control, formState, watch } = useFormContext();
  const formData = watch();

  const [imageSrc, setImageSrc] = useState(null);
  const [crop, setCrop] = useState({ x: 0, y: 0 });
  const [zoom, setZoom] = useState(1);
  const [croppedAreaPixels, setCroppedAreaPixels] = useState(null);

  const onCropComplete = useCallback((_, croppedAreaPixels) => {
    setCroppedAreaPixels(croppedAreaPixels);
  }, []);

  const handleFileChange = (file) => {
    if (!file) return;
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => setImageSrc(reader.result);
  };

  const handleCropConfirm = async (field) => {
    if (!croppedAreaPixels || !imageSrc) return;
    const croppedBlob = await getCroppedImg(imageSrc, croppedAreaPixels);
    const croppedFile = new File([croppedBlob], 'studentPhoto.jpg', {
      type: 'image/jpeg',
    });
    field.onChange(croppedFile);
    setImageSrc(null);
  };

  return (
    <S.FormStep>
      <S.Title>Envie sua Foto 3x4 a ser usada na carteirinha</S.Title>
      <div>
        <ul>
          <li>Foto recente 3x4 com fundo claro e uniforme.</li>
          <li>Rosto centralizado, visível e sem cortes.</li>
          <li>
            Sem óculos escuros, chapéus ou acessórios que escondam o rosto.
          </li>
          <li>Boa iluminação, sem sombras fortes.</li>
        </ul>
      </div>

      <S.UploadInputGrid>
        <S.UploadInputContainer>
          <S.UploadIconContainer>
            <img src={uploadIcon} />
          </S.UploadIconContainer>

          <Controller
            name="studentPhoto"
            control={control}
            render={({ field }) => (
              <>
                {!imageSrc && (
                  <>
                    <S.UploadInput
                      type="file"
                      accept=".jpg,.jpeg,.png"
                      onChange={(e) => handleFileChange(e.target.files[0])}
                      key={formData.studentPhoto?.name}
                    />
                    {formData.studentPhoto?.name ? (
                      <p>Arquivo selecionado: {formData.studentPhoto.name}</p>
                    ) : (
                      <p>Faça o upload do arquivo desejado</p>
                    )}
                  </>
                )}

                {imageSrc && (
                  <>
                    <S.CropperWrapper>
                      <Cropper
                        image={imageSrc}
                        crop={crop}
                        zoom={zoom}
                        aspect={3 / 4}
                        onCropChange={setCrop}
                        onZoomChange={setZoom}
                        onCropComplete={onCropComplete}
                      />
                    </S.CropperWrapper>
                    <S.Button>
                      <ActionButton
                        onClick={async () => {
                          await handleCropConfirm(field);
                          goToNextStep();
                        }}
                      >
                        Confirmar
                      </ActionButton>
                    </S.Button>
                  </>
                )}

                {formState.errors?.studentPhoto && (
                  <S.ErrorMessage>
                    {formState.errors.studentPhoto.message}
                  </S.ErrorMessage>
                )}
              </>
            )}
          />
        </S.UploadInputContainer>
      </S.UploadInputGrid>
    </S.FormStep>
  );
};
