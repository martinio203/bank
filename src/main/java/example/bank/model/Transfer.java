package example.bank.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transfer_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User payer_id;

    @Column(nullable = false)
    private Long recipient_id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Date date;
}
