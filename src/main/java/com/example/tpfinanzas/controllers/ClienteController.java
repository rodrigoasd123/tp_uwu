package com.example.tpfinanzas.controllers;

import com.example.tpfinanzas.entities.Cliente;
import com.example.tpfinanzas.repositories.ClienteRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/clientes") @CrossOrigin
public class ClienteController {
    private final ClienteRepository repo;
    public ClienteController(ClienteRepository repo){ this.repo = repo; }

    @GetMapping public List<Cliente> listar(){ return repo.findAll(); }
    @PostMapping public Cliente crear(@RequestBody Cliente c){ return repo.save(c); }
    @PutMapping("/{id}") public Cliente actualizar(@PathVariable Long id, @RequestBody Cliente c){ c.setId(id); return repo.save(c); }
    @DeleteMapping("/{id}") public void eliminar(@PathVariable Long id){ repo.deleteById(id); }
}
