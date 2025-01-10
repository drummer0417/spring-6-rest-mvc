package nl.androidappfactory.spring6restmvc.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CustomerDTO {
    private UUID id;

    @NotNull
    @NotEmpty
    private String name;
    private String version;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
