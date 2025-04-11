package com.pixado.pixado.controller;

import com.pixado.pixado.dto.UsuarioRequestDTO;
import com.pixado.pixado.dto.UsuarioResponseDTO;
import com.pixado.pixado.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public UsuarioResponseDTO cadastrar(@RequestBody UsuarioRequestDTO dto) {
        return service.cadastrar(dto);
    }
}
