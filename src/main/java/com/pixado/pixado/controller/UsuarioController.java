package com.pixado.pixado.controller;

import com.pixado.pixado.dto.UsuarioRequestDTO;
import com.pixado.pixado.dto.UsuarioResponseDTO;
import com.pixado.pixado.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin
@Tag(name = "Usuários", description = "Endpoints para gerenciar usuários e suas credenciais PIX")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    @Operation(
        summary = "Cadastrar novo usuário", 
        description = "Registra um novo usuário no sistema com suas credenciais PIX e informações bancárias"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Usuário cadastrado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Dados inválidos fornecidos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Erro interno do servidor",
            content = @Content
        )
    })
    public UsuarioResponseDTO cadastrar(@RequestBody UsuarioRequestDTO dto) {
        return service.cadastrar(dto);
    }
}
