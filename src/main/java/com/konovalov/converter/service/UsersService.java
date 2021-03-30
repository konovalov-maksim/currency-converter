package com.konovalov.converter.service;

import com.konovalov.converter.model.entity.User;
import com.konovalov.converter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(s);
        if (user == null) {
            log.error("Пользователь " + s + " не найден");
            throw new UsernameNotFoundException("Пользователь " + s + " не найден");
        }
        return user;
    }
}
