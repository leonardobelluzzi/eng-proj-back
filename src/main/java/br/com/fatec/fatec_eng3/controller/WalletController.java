package br.com.fatec.fatec_eng3.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fatec.fatec_eng3.controller.dto.BuyCoinsDTO;
import br.com.fatec.fatec_eng3.model.Wallet;
import br.com.fatec.fatec_eng3.service.WalletService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/coins")
    public ResponseEntity<?> getWallet(@RequestParam Long idUser) {
      try {
        HashMap<String, Long> response = new HashMap<>();
        response.put("coins", walletService.findByUserId(idUser).getCoins());
        return ResponseEntity.ok(response);
      } catch (Exception e) {
        return ResponseEntity.unprocessableEntity().body(e.getMessage());
      }
    }

    @PostMapping("/coins/buy")
    public ResponseEntity<?> buyCoins(@RequestBody @Valid BuyCoinsDTO buyCoinsDTO) {
      try {
        Wallet wallet = walletService.buyCoins(buyCoinsDTO.getIdUser(), buyCoinsDTO.getCoins());
        HashMap<String, Long> response = new HashMap<>();
        response.put("coins", wallet.getCoins());
        return ResponseEntity.ok(response);
      } catch (Exception e) {
        return ResponseEntity.unprocessableEntity().body(e.getMessage());
      }
    }
}
