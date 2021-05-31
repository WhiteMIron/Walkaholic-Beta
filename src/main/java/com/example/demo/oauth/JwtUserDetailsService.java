//package com.example.demo.oauth;
//
//
//import com.example.demo.user.model.entity.User;
//import com.example.demo.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import javax.management.relation.Role;
//import java.util.HashSet;
//
//@Service
//@RequiredArgsConstructor
//public class JwtUserDetailsService implements UserDetailsService {
//
//    private PasswordEncoder passwordEncoder;
//
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(Long id) throws UsernameNotFoundException {
//        User member = userRepository.findByUserId(id)
//                .orElseThrow(() -> new UsernameNotFoundException(Long.toString(id)));
////        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
////        grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
////        if (email.equals("sup2is@gmail.com")) {
////            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
////        }
//
//        return new User(member.getEmail(), member.getPassword());
//    }
//
//
//}
