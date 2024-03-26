package example.bank.service;

import example.bank.dto.TransferDto;
import example.bank.model.Transfer;
import example.bank.model.User;
import org.springframework.ui.Model;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import java.math.BigDecimal;


@Service
public interface TransferService {

    void addBalance(String recipientName, BigDecimal amount);

    void substractPayerBalance(User payer, BigDecimal amount);
    void createTransfer(TransferDto transferDto);

    void saveTransfer(Transfer transfer);

    String sendTransfer(TransferDto transferDto, BindingResult bindingResult, Model model);
}
