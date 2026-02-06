package br.com.cefet.caccoId.mappers;

import br.com.cefet.caccoId.dtos.ApiResponseDTO;
import br.com.cefet.caccoId.models.ReviewDocuments;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewDocumentsMapper {

    public List<String> toFieldNamesList(List<String> fieldNames) {
        return fieldNames;
    }

    public ApiResponseDTO<List<String>> toApiResponse(List<ReviewDocuments> corrections) {
        List<String> fieldNames = corrections.stream()
                .map(ReviewDocuments::getFieldName)
                .collect(Collectors.toList());
        return new ApiResponseDTO<>(true, "Campos revisados", fieldNames);
    }
}
