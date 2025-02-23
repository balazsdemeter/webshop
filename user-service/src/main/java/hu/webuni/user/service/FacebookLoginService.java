package hu.webuni.user.service;

import hu.webuni.user.dto.FacebookData;
import hu.webuni.user.dto.RegisterDto;
import hu.webuni.user.model.ShopUser;
import hu.webuni.user.repository.ShopUserRepository;
import hu.webuni.user.sercurity.ShopUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FacebookLoginService {

    @Value("${facebook.graph.api.base.url}")
    private String GRAPH_API_BASE_URL;

    private final ShopUserRepository shopUserRepository;

    @Transactional
    public UserDetails getUserDetailsForToken(String facebookToken, boolean createUser) {
        FacebookData facebookData = getEmailOfFacebookUser(facebookToken);
        ShopUser shopUser = findOrCreateUser(facebookData, createUser);
        return ShopUserDetailsService.createUserDetails(shopUser);
    }

    private FacebookData getEmailOfFacebookUser(String facebookToken) {
        return WebClient.create(GRAPH_API_BASE_URL)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/me")
                        .queryParam("fields", "email,name")
                        .build())
                .headers(headers -> headers.setBearerAuth(facebookToken))
                .retrieve()
                .bodyToMono(FacebookData.class)
                .block();
    }

    private ShopUser findOrCreateUser(FacebookData facebookData, boolean createUser) {
        if (facebookData == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        String facebookId = String.valueOf(facebookData.getId());
        Optional<ShopUser> optExistingUser = shopUserRepository.findByFacebookId(facebookId);
        if (optExistingUser.isPresent()) {
            return optExistingUser.get();
        }

        if (createUser) {
            String email = facebookData.getEmail();
            ShopUser shopUser = ShopUser.builder()
                    .username(email)
                    .email(email)
                    .facebookId(facebookId)
                    .password("dummy")
                    .roles(Set.of("customer"))
                    .build();
            return shopUserRepository.save(shopUser);
        }

        return null;
    }
}