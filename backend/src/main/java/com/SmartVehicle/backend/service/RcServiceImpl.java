package com.SmartVehicle.backend.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartVehicle.backend.model.Rc;
import com.SmartVehicle.backend.repository.RcRepository;

@Service
public class RcServiceImpl implements RcService {

    private final RcRepository repo;

    @Autowired
    public RcServiceImpl(RcRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Rc> getAll() {
        return repo.findAll();
    }

    @Override
    public Rc getById(String id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Rc searchByRcNumber(String rcNumber) {
        return repo.findByRcNumber(rcNumber);
    }

    @Override
    public Rc add(Rc rc) {
        rc.setCreatedAt(Instant.now());
        rc.setUpdatedAt(Instant.now());
        return repo.save(rc);
    }

    @Override
    public Rc update(String id, Rc rc) {
        rc.setId(id);
        rc.setUpdatedAt(Instant.now());
        return repo.save(rc);
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
}
