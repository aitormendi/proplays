package com.tutorial.crud.jwt.controller;

import com.tutorial.crud.jwt.JwtProvider;
import com.tutorial.crud.jwt.domain.JWTDto;
import com.tutorial.crud.jwt.domain.LoginUsuario;
import com.tutorial.crud.jwt.domain.NuevoUsuario;
import com.tutorial.crud.producto.infraestructure.repository.dto.Mensaje;
import com.tutorial.crud.security.application.service.RolService;
import com.tutorial.crud.security.application.service.UsuarioService;
import com.tutorial.crud.security.domain.entity.Rol;
import com.tutorial.crud.security.domain.entity.Usuario;
import com.tutorial.crud.security.domain.enums.RolNombre;
import com.tutorial.crud.security.infraestructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired AuthenticationManager authenticationManager;

  @Autowired UsuarioService usuarioService;

  @Autowired RolService rolService;

  // Para la creación de tokens
  @Autowired JwtProvider jwtProvider;

  @Autowired
  UsuarioRepository usuarioRepository;

  // Enviamos Json convertimos en clase java nuevo usuario y para validarlo BindingResult
  /**
   * Genera nuevo usuario a partir de lo pasado en el json Comprobamos por una parte que se hayan
   * introducido bien los datos y por otra parte que el nombre no exista lo mismo con el email ya
   * que son únicos finalmente creamos el usuario
   */
  @PostMapping("/nuevo")
  public ResponseEntity<?> nuevo(
       @RequestBody NuevoUsuario nuevoUsuario) {
   /* if (bindingResult.hasErrors()) {
      return new ResponseEntity(
          new Mensaje("Campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
    }*/
    if (usuarioService.existsByNombreUsuario(nuevoUsuario.getUsername())) {
      return new ResponseEntity(new Mensaje("El nombre ya existe"), HttpStatus.BAD_REQUEST);
    }
    if (usuarioService.existsByEmail(nuevoUsuario.getEmail())) {
      return new ResponseEntity(new Mensaje("El email ya existe"), HttpStatus.BAD_REQUEST);
    }

    //El passwordEncoder solamente está realiizado para hacer encoder no decoder si eso habría que personalizarlo
    Usuario usuario =
        new Usuario(
            nuevoUsuario.getNombre(),
            nuevoUsuario.getUsername(),
            nuevoUsuario.getEmail(),
            passwordEncoder.encode(nuevoUsuario.getPassword()));
    // TRAS LA CREACIÓN DEL USUARIO ASIGNAMOS EL ROL
    Set<Rol> roles = new HashSet<>();
    if (nuevoUsuario.getRoles().contains("alumno")) {
      roles.add(rolService.getByRolNombre(RolNombre.ROLE_ALUMNO).get());
    }
    if (nuevoUsuario.getRoles().contains("admin")) {
      roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
    }
    if (nuevoUsuario.getRoles().contains("profe")) {
      roles.add(rolService.getByRolNombre(RolNombre.ROLE_PROFE).get());
    }
    roles.add(rolService.getByRolNombre(RolNombre.ROLE_ALUMNO).get());


    usuario.setRoles(roles);
    usuario.setActivo(false);
    usuarioService.save(usuario);
    return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<JWTDto> login(
      @Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return new ResponseEntity(new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);
      }
    Usuario u= usuarioRepository.findByNombreUsuario(loginUsuario.getUsername()).orElse(null);
    if (!u.getActivo()){
      return new ResponseEntity(new Mensaje("No puede loguearse hasta que active un administrador su usuario"), HttpStatus.FORBIDDEN);
    }
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getUsername(),loginUsuario.getPassword()));
       //PARA AUTENTICAR EL USUARIO EN EL LOGIN Y tras autenticación creamos el token
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=jwtProvider.generateToken(authentication);
       //PARA LA AUTORITIES DEL USUARIO esto define el usuario que se encuentra logueado en el token generado a partir de la autenticación
      // con el nombre de usuario y la contraseña
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        //fINALMENTE CREAMOS EL JWTDTO con las authorities es decir los permisos del usuario
        JWTDto jwtDto=new JWTDto(jwt,userDetails.getUsername(),userDetails.getAuthorities());
        return new ResponseEntity(jwtDto,HttpStatus.OK);
    }
  }

