package com.multipolar.bootcamp.product.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "product")
public class Product {

    @Id
    private String id;

    @NotEmpty(message = "Product Name must be filled.")
    private String productName;

    private ProductType productType;

    @Min(value = 0, message = "Interest Rate is at least 0.")
    @Max(value = 100, message = "Interest Rate is at most 100.")
    private Double interestRate;

    @Min(value = 0, message = "Minimum Balance must be non-negative.")
    private Double minimumBalance;

    @Min(value = 0, message = "Maximum Loan Amount must be non-negative.")
    private Double maximumLoanAmount;

    private String termsAndConditions;
    private LocalDateTime dateOfCreation = LocalDateTime.now();
}
