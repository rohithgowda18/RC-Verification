package com.SmartVehicle.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.SmartVehicle.backend.config.AdminKeyValidator;
import com.SmartVehicle.backend.exception.UnauthorizedException;
import com.SmartVehicle.backend.model.Rc;
import com.SmartVehicle.backend.service.RcService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/rc")
@CrossOrigin("*")
public class RcController {

    private final RcService rcService;
    private final AdminKeyValidator adminKeyValidator;

    @Autowired
    public RcController(RcService rcService, AdminKeyValidator adminKeyValidator) {
        this.rcService = rcService;
        this.adminKeyValidator = adminKeyValidator;
    }

    @GetMapping
    public List<Rc> getAll() {
        return rcService.getAll();
    }

    @GetMapping("/{id}")
    public Rc getById(@PathVariable String id) {
        return rcService.getById(id);
    }

    @GetMapping("/search")
    public Rc searchByRcNumber(@RequestParam String rcNumber) {
        return rcService.searchByRcNumber(rcNumber);
    }

    @PostMapping
    public Rc create(@RequestBody Rc rc, HttpServletRequest request) {
        if (!adminKeyValidator.isAdminAuthorized(request)) throw new UnauthorizedException();
        return rcService.add(rc);
    }

    @PutMapping("/{id}")
    public Rc update(@PathVariable String id, @RequestBody Rc rc, HttpServletRequest request) {
        if (!adminKeyValidator.isAdminAuthorized(request)) throw new UnauthorizedException();
        return rcService.update(id, rc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id, HttpServletRequest request) {
        if (!adminKeyValidator.isAdminAuthorized(request)) throw new UnauthorizedException();
        rcService.delete(id);
    }
}
