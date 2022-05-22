package com.tutorial.crud.producto.infraestructure.controller;

import com.tutorial.crud.producto.infraestructure.repository.dto.Mensaje;
import com.tutorial.crud.producto.infraestructure.repository.dto.ProductoDto;
import com.tutorial.crud.producto.domain.entity.Producto;
import com.tutorial.crud.producto.application.service.ProductoService;
import com.tutorial.crud.security.domain.entity.UsuarioPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/lista")
    public ResponseEntity<List<Producto>> list(){
      //  String nombre=principal.getName();
      //  UsuarioPrincipal usuarioPrincipal= (UsuarioPrincipal) authentication.getPrincipal();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String algo=authentication.getName();
        List<Producto> list = productoService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") int id){
        if(!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Producto producto = productoService.getOne(id).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Producto> getByNombre(@PathVariable("nombre") String nombre){
        if(!productoService.existsByNombre(nombre))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Producto producto = productoService.getByNombre(nombre).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductoDto productoDto){

        //En cada petición a partir del token que se le pase sacar el rol o poner la anotación de quienes tienen permitido realizar la petición
        // en función del rol

        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDto.getPrecio()==null || productoDto.getPrecio()<0 )
            return new ResponseEntity(new Mensaje("el precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);
        if(productoService.existsByNombre(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        Producto producto = new Producto(productoDto.getNombre(), productoDto.getPrecio());

       /* if (HttpStatus.UNAUTHORIZED.isError()){
            return new ResponseEntity(new Mensaje("El usuario no tiene permisos"), HttpStatus.UNAUTHORIZED);
        }*/

        productoService.save(producto);
        return new ResponseEntity(new Mensaje("producto creado"), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody ProductoDto productoDto){
        if(!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if(productoService.existsByNombre(productoDto.getNombre()) && productoService.getByNombre(productoDto.getNombre()).get().getId() != id)
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDto.getPrecio()==null || productoDto.getPrecio()<0 )
            return new ResponseEntity(new Mensaje("el precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);

        Producto producto = productoService.getOne(id).get();
        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());
        productoService.save(producto);
        return new ResponseEntity(new Mensaje("producto actualizado"), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        productoService.delete(id);
        return new ResponseEntity(new Mensaje("producto eliminado"), HttpStatus.OK);
    }


}
