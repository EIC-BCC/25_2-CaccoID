package br.com.cefet.caccoId.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDocumentsDTO {

    @NotNull(message = "Lista de campos não pode ser nula")
    @NotEmpty(message = "Lista de campos não pode estar vazia")
    private List<String> fieldNames;
    public List<String> getFieldNames() {
        return fieldNames;
    }
}