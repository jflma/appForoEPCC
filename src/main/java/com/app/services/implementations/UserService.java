package com.app.services.implementations;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;

import com.app.domain.user.Person;
import com.app.domain.user.Role;
import com.app.exceptions.CreationException;
import com.app.controller.dto.LoginRequestDTO;
import com.app.controller.dto.SignupFieldsDTO;
import com.app.controller.dto.response.TokenResponse;
import com.app.domain.user.ForoUser;
import com.app.repositories.UserRepositoryImp;
import com.app.resources.JwtUtil;
import com.app.services.interfaces.IPersonService;
import com.app.services.interfaces.IUserService;


@Service
public class UserService implements IUserService{

  private IPersonService personService;
  private UserRepositoryImp userRepository;
  private PasswordEncoder passwordEncoder;
  private JwtUtil jwtUtil;

  public UserService (IPersonService personService, UserRepositoryImp userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
    this.personService=personService;
    this.userRepository=userRepository;
    this.passwordEncoder=passwordEncoder;
    this.jwtUtil=jwtUtil;
  }

  @Override
  @Transactional
  public ForoUser registerUser(SignupFieldsDTO fields){
    try {
      Person personCreated = personService.createPerson(fields.firstName(),fields.lastName(), fields.email(), fields.birthDay());
      Role userRole = Role.builder().name("USER").build();
      Role adminRole = Role.builder().name("ADMIN").build();

      ForoUser userCreated = ForoUser.builder()
                                     .username(fields.username())
                                     .password(fields.password())
                                     .person(personCreated) 
                                     .roles(Set.of(userRole,adminRole))
                                     .build();
      return userRepository.save(userCreated);
    } catch(Exception e){
      throw new CreationException("Error al registrar el nuevo usuario");
    }
  }

  @Override
  @Transactional(readOnly = true)
  public ForoUser getUserbyId(Long id){ 
    return userRepository.findById(id)
      .orElseThrow();
  }

  
  @Override
  @Transactional(readOnly = true)
  public ForoUser getUserByUsername (String userName) {
     return userRepository.findForoUserByUsername(userName)
          .orElseThrow(()-> new UsernameNotFoundException("No se encontro el usuario"));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    ForoUser userFound = userRepository.findForoUserByUsername(username)
          .orElseThrow(()-> new UsernameNotFoundException("El usuario "+username+" no fue encontrado"));

    List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

    userFound.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));

    return new User(userFound.getUsername(),
                    userFound.getPassword(),
                    userFound.isEnabled(),
                    userFound.isAccountNoExpired(),
                    userFound.isCredentialNoExpired(),
                    userFound.isAccountNoLocked(),
                    authorityList);
    
  }

  public Authentication authenticate (String username, String password) {
    UserDetails userFound = this.loadUserByUsername(username);

    if (userFound == null) {
      throw new BadCredentialsException("Invalid username or password!");
    }

    if (!passwordEncoder.matches(password, userFound.getPassword())){
      throw new BadCredentialsException("Invalid username or password!");
    }

    return new UsernamePasswordAuthenticationToken(username, userFound.getPassword(),userFound.getAuthorities());

  }

  public TokenResponse loginUser(LoginRequestDTO loginRequest) {
    String username = loginRequest.username();
    String password = loginRequest.password();

    Authentication auth = this.authenticate(username, password);
    SecurityContextHolder.getContext().setAuthentication(auth);

    return TokenResponse.builder().token( this.jwtUtil.generateToken(auth)).build();

  }
}
