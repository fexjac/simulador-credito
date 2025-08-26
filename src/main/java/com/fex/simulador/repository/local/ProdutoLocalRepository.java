package com.fex.simulador.repository.local;

import com.fex.simulador.model.local.ProdutoLocal;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class ProdutoLocalRepository implements PanacheRepository<ProdutoLocal> {

    public Optional<ProdutoLocal> findByCodigo(int coProduto) {
        return find("coProduto", coProduto).firstResultOptional();
    }
}
