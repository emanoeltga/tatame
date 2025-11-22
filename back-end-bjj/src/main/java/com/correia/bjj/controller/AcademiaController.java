package com.correia.bjj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.correia.bjj.controller.dto.AcademiaDTO;
import com.correia.bjj.model.Academia;
import com.correia.bjj.servico.AcademiaService;

@RestController
@RequestMapping("api/academias")
public class AcademiaController {

	@Autowired
    private AcademiaService academiaService;

    @GetMapping
    public List<Academia> listarAcademias() {
        return academiaService.listarAcademias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Academia> consultarAcademia(@PathVariable Long id) {
        Academia academia = academiaService.consultarAcademia(id);
        return ResponseEntity.ok(academia);
    }

    @PostMapping
    public Academia criarAcademia(@RequestBody AcademiaDTO academiaDTO) {
        return academiaService.criarAcademia(academiaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Academia> editarAcademia(@PathVariable Long id, @RequestBody AcademiaDTO academiaDTO) {
        Academia academia = academiaService.editarAcademia(id, academiaDTO);
        return ResponseEntity.ok(academia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Academia > removerAcademia(@PathVariable Long id) {
       // academiaService.removerAcademia(id);
        return ResponseEntity.ok(academiaService.removerAcademia(id));
    }
}
