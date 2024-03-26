package example.bank.service.impl;

import example.bank.dto.TransferDto;
import example.bank.model.Transfer;
import example.bank.model.User;
import example.bank.repository.TransferRepository;
import example.bank.repository.UserRepository;
import example.bank.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final UserRepository userRepository;
    private final TransferRepository transferRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void addBalance(String recipientName, BigDecimal amount) {
        Optional<User> recipient = Optional.ofNullable(findByUsername(recipientName));
        recipient.ifPresent(user -> {
            user.setBalance(user.getBalance().add(amount));
            userRepository.save(user);
        });
    }

    @Override
    public void substractPayerBalance(User payer, BigDecimal amount) {
        payer.setBalance(payer.getBalance().subtract(amount));
        userRepository.save(payer);
    }

    @Override
    public void createTransfer(TransferDto transferDto) {
        Transfer transfer = new Transfer();
        User loggedUser = getPayerId();
        Long recipientId = findByUsername(transferDto.getRecipient()).getUser_id();
        Date currentDate = java.sql.Date.valueOf(LocalDate.now());
//        Date currentDate = java.util.Date.from(LocalDate.now().
//                atStartOfDay().
//                atZone(ZoneId.systemDefault()).toInstant()
//        );

        addBalance(transferDto.getRecipient(), transferDto.getAmount());

        transfer.setPayer_id(getPayerId());
        transfer.setRecipient_id(recipientId);
        transfer.setAmount(transferDto.getAmount());
        transfer.setTitle(transferDto.getTitle());
        transfer.setDate(currentDate);
        saveTransfer(transfer);
        substractPayerBalance(loggedUser, transferDto.getAmount());
    }

    @Override
    public void saveTransfer(Transfer transfer) {
        transferRepository.save(transfer);
    }

    @Override
    public String sendTransfer(TransferDto transferDto, BindingResult bindingResult, Model model) {
        User payer = getPayerId();

        if (findByUsername(transferDto.getRecipient()) == null) {
            bindingResult.rejectValue("recipient", "Recipient does not exist");
            model.addAttribute("recipientError", "Recipient does not exist");
        }

        if (transferDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            bindingResult.rejectValue("amountNotMoreThanZero", "You can send only more than 0");
            model.addAttribute("amountNotMoreThanZeroError", "You can send only more than 0");
        }

        if (transferDto.getAmount().compareTo(payer.getBalance()) >= 0) {
            bindingResult.rejectValue("insufficientAmount", "Insufficient amount");
            model.addAttribute("insufficientAmountError", "Insufficient amount");
        }

        if (transferDto.getTitle() == null){
            bindingResult.rejectValue("title", "Title cannot be empty");
            model.addAttribute("titleError", "Title cannot be empty");
        }

        if (bindingResult.hasErrors()){
            return "transfer";
        }

        createTransfer(transferDto);
        return "redirect:/home";
    }


    private User getPayerId() {
        String loggedUsername;
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserDetailsImpl) {
            loggedUsername = ((UserDetailsImpl) principal).getUsername();
        } else {
            loggedUsername = principal.toString();
        }

        return findByUsername(loggedUsername);
    }



}
