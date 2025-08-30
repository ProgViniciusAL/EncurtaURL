package dev.vinicius.EncurtaURL.adapter.out.security.auth;

import dev.vinicius.EncurtaURL.domain.model.User.User;
import dev.vinicius.EncurtaURL.adapter.out.repository.SpringDataUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SpringDataUserRepository springDataUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = springDataUserRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserDetailsImpl(user);

    }

}
