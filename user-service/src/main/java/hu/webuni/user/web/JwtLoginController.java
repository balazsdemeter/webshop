package hu.webuni.user.web;

import hu.webuni.tokenlib.tokenlib.JwtService;
import hu.webuni.user.dto.LoginDto;
import hu.webuni.user.dto.RegisterDto;
import hu.webuni.user.sercurity.ShopUserService;
import hu.webuni.user.service.FacebookLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class JwtLoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final FacebookLoginService facebookLoginService;
    private final ShopUserService shopUserService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterDto registerDto) {
        String facebookToken = registerDto.getFacebookToken();
        if (facebookToken != null) {
            facebookLoginService.getUserDetailsForToken(facebookToken,false);
        } else {
            shopUserService.registerUser(registerDto);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        UserDetails userDetails;
        String facebookToken = loginDto.getFacebookToken();
        if (ObjectUtils.isEmpty(facebookToken)) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            userDetails = (UserDetails) authentication.getPrincipal();
        } else {
            userDetails = facebookLoginService.getUserDetailsForToken(facebookToken,false);
        }
        return "\"" + jwtService.creatJwtToken(userDetails) + "\"";
    }
}