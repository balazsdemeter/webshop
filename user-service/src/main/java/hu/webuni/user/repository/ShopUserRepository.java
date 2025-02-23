package hu.webuni.user.repository;

import hu.webuni.user.model.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopUserRepository extends JpaRepository<ShopUser, Integer>  {
    Optional<ShopUser> findByUsername(String username);

    Optional<ShopUser> findByFacebookId(String fbId);
}