package com.edw.service;

import com.edw.dto.TransferDto;
import com.edw.model.Transfer;
import com.edw.repository.TransferRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TransferService {

    @Inject
    TransferRepository repository;

    @Transactional
    public Uni<List<Transfer>> findAll() {
        return repository.find_All();
    }


    @Transactional
    public Uni<Void> createAsync(TransferDto transferDto) {
        return repository.save(transferDto);
    }
}
