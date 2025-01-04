package nl.androidappfactory.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CustomerDTO {
    private UUID id;
    private String name;
    private String version;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}