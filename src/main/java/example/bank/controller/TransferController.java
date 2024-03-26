package example.bank.controller;

import example.bank.dto.TransferDto;
import example.bank.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @GetMapping("/transfer")
    public String transfer(@ModelAttribute("transferDto")TransferDto transferDto) {
        return "transfer";
    }

    @PostMapping("/transfer")
    public String transferSend(@ModelAttribute("transferDto") TransferDto transferDto,
                               BindingResult bindingResult,
                               Model model) {
        return transferService.sendTransfer(transferDto, bindingResult, model);
    }

}
