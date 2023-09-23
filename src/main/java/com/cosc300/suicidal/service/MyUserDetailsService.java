package com.cosc300.suicidal.service;

import com.cosc300.suicidal.model.MyUserDetails;
import com.cosc300.suicidal.model.User;
import com.cosc300.suicidal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with the provided email was not found");
        }

        return new MyUserDetails(user);
    }
}
