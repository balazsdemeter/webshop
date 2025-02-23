package hu.webuni.user.sercurity;

import hu.webuni.user.model.ShopUser;
import hu.webuni.user.repository.ShopUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {
    private final ShopUserRepository shopUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ShopUser shopUser = shopUserRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));

        return createUserDetails(shopUser);
    }

    public static UserDetails createUserDetails(ShopUser shopUser) {
        return new User(
                shopUser.getUsername(),
                shopUser.getPassword(),
                shopUser.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }
}