package com.unesc.bibliotecabackend.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unesc.bibliotecabackend.model.Livro;
import com.unesc.bibliotecabackend.services.LivroService;
import com.unesc.bibliotecabackend.shared.LivroDTO;
import com.unesc.bibliotecabackend.view.model.LivroRequest;
import com.unesc.bibliotecabackend.view.model.LivroResponse;

@RestController
@RequestMapping("/api/livros")

public class LivroController {
    @Autowired
    private LivroService livroService;

    @GetMapping
    public ResponseEntity<List<LivroResponse>> obterTodos(){
        List<LivroDTO> livros = livroService.obterTodos();
        
        ModelMapper mapper = new ModelMapper();
        
        List<LivroResponse> resposta = livros.stream()
        .map(livroDto -> mapper.map(livroDto, LivroResponse.class))
        .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<LivroResponse> adicionar(@RequestBody LivroRequest livro){
        ModelMapper mapper = new ModelMapper();
        LivroDTO livroDto = mapper.map(livro, LivroDTO.class);
        livroDto = livroService.adicionarLivro(livroDto);
        return new ResponseEntity<>(mapper.map(livroDto, LivroResponse.class),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<LivroResponse>> obterPorId(@PathVariable Integer id){
        
        Optional<LivroDTO> livroDto = livroService.obterPorId(id);
        
        LivroResponse livro = new ModelMapper().map(livroDto.get(), LivroResponse.class);
        
        return new ResponseEntity<>(Optional.of(livro), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Integer id){
        livroService.remover(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponse> atualizar(@RequestBody LivroRequest livroReq,@PathVariable Integer id){
        ModelMapper mapper = new ModelMapper();
        LivroDTO livroDto = mapper.map(livroReq, LivroDTO.class);
        livroDto = livroService.atualizar(id, livroDto);

        return new ResponseEntity<>(
            mapper.map(livroDto, LivroResponse.class),
            HttpStatus.OK
        );
    }

}
