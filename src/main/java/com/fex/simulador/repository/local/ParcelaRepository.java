package com.fex.simulador.repository.local;

import com.fex.simulador.model.local.ParcelaEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ParcelaRepository implements PanacheRepository<ParcelaEntity> { }
