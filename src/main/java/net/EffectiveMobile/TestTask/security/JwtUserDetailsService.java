package net.EffectiveMobile.TestTask.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.EffectiveMobile.TestTask.model.User;
import net.EffectiveMobile.TestTask.security.jwt.JwtUser;
import net.EffectiveMobile.TestTask.security.jwt.JwtUserFactory;
import net.EffectiveMobile.TestTask.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserEntityByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with username: %s not found", username));
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("loadUserByUsername - user with username: {} successfully loaded", username);
        return jwtUser;
    }
}
