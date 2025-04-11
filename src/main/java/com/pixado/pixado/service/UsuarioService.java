package com.pixado.pixado.service;

import com.pixado.pixado.dto.UsuarioRequestDTO;
import com.pixado.pixado.dto.UsuarioResponseDTO;
import com.pixado.pixado.model.Usuario;
import com.pixado.pixado.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome);
        usuario.setBanco(dto.banco);
        usuario.setChavePix(dto.chavePix);
        usuario.setClientId(dto.clientId);
        usuario.setClientSecret(dto.clientSecret);
        usuario.setCaminhoCertificado(dto.caminhoCertificado);
        repository.save(usuario);

        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.id = usuario.getId();
        response.nome = usuario.getNome();
        response.banco = usuario.getBanco();
        response.chavePix = usuario.getChavePix();
        return response;
    }
}
