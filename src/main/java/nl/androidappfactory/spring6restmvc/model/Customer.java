package nl.androidappfactory.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Customer {
    private int id;
    private String name;
    private String version;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
