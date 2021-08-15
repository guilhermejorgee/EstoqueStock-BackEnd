package br.org.guilherme.gerenciadordeestoque.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.org.guilherme.gerenciadordeestoque.model.Usuario;
import br.org.guilherme.gerenciadordeestoque.repository.UsuarioRepository;
import br.org.guilherme.gerenciadordeestoque.secutiry.UserDetailsImplements;

@Service
public class UserDetailssImplementsService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Usuario> user = userRepository.findByUsuario(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException(username + "Not Found"));
		
		return user.map(UserDetailsImplements::new).get();
	}
	
	

}
