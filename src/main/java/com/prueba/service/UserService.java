package com.prueba.service;

import com.prueba.model.User;
import com.prueba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findOneByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El email "+email+" no existe."));

        return new UserDetailsImpl(user);
    }

    public User save(User users) {
        return userRepository.save(users);
    }
}
