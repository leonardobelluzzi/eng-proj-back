package br.com.fatec.fatec_eng3.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fatec.fatec_eng3.model.Wallet;
import br.com.fatec.fatec_eng3.repository.WalletRepository;

@Service
public class WalletService {

   @Autowired
    private WalletRepository walletRepository; 

    public Wallet findByUserId(long id) {
        return walletRepository.findByUserId(id).get();
    }

    public Wallet buyCoins(long userId, long coins) {
        Optional<Wallet> walletOptional = walletRepository.findByUserId(userId);
        if (walletOptional.isEmpty()) {
            throw new RuntimeException("Wallet not found");
        }

        Wallet wallet = walletOptional.get();
        wallet.addCoins(coins);

        return walletRepository.save(wallet);
    }

    public Wallet spendCoins(long userId, long coins) {
        Optional<Wallet> walletOptional = walletRepository.findByUserId(userId);
        if (walletOptional.isEmpty()) {
            throw new RuntimeException("Wallet not found");
        }

        Wallet wallet = walletOptional.get();
        wallet.removeCoins(coins);

        return walletRepository.save(wallet);
    }

    public Wallet winCoins(long userId, long coins) {
        Optional<Wallet> walletOptional = walletRepository.findByUserId(userId);
        if (walletOptional.isEmpty()) {
            throw new RuntimeException("Wallet not found");
        }

        Wallet wallet = walletOptional.get();
        wallet.addCoins(coins);

        return walletRepository.save(wallet);
    }
}
