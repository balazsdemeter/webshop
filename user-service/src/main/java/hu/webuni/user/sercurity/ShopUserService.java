package hu.webuni.user.sercurity;

import hu.webuni.user.dto.RegisterDto;
import hu.webuni.user.model.ShopUser;
import hu.webuni.user.repository.ShopUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShopUserService {
    private final ShopUserRepository shopUserRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterDto registerDto) {
        Optional<ShopUser> optional = shopUserRepository.findByUsername(registerDto.getUsername());
        if (optional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        ShopUser shopUser = new ShopUser();
        shopUser.setUsername(registerDto.getUsername());
        shopUser.setEmail(registerDto.getEmail());
        shopUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        shopUser.setRoles(Set.of("customer"));
        shopUserRepository.save(shopUser);
    }
}