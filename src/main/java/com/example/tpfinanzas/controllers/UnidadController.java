package com.example.tpfinanzas.controllers;

import com.example.tpfinanzas.entities.Unidad;
import com.example.tpfinanzas.repositories.UnidadRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/unidades") @CrossOrigin
public class UnidadController {
    private final UnidadRepository repo;
    public UnidadController(UnidadRepository repo){ this.repo = repo; }

    @GetMapping public List<Unidad> listar(){ return repo.findAll(); }
    @PostMapping public Unidad crear(@RequestBody Unidad u){ return repo.save(u); }
    @PutMapping("/{id}") public Unidad actualizar(@PathVariable Long id, @RequestBody Unidad u){ u.setId(id); return repo.save(u); }
    @DeleteMapping("/{id}") public void eliminar(@PathVariable Long id){ repo.deleteById(id); }
}
