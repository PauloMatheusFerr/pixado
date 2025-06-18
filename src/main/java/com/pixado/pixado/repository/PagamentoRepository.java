package com.pixado.pixado.repository;
import com.pixado.pixado.model.Pagamento;
import com.pixado.pixado.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    Optional<Pagamento> findByIdTransacao(String idTransacao);
    List<Pagamento> findAllByUsuario(Usuario usuario);

}
