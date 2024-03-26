package example.bank.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferDto {
    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String title;
}
