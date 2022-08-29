package com.unesc.bibliotecabackend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unesc.bibliotecabackend.model.Livro;
import com.unesc.bibliotecabackend.model.exception.ResourceNotFoundException;
import com.unesc.bibliotecabackend.repository.LivroRepository;
import com.unesc.bibliotecabackend.shared.LivroDTO;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    /**
     * Retorna todos os livros cadastrados
     * @return Lista de livros
     */
    public List<LivroDTO> obterTodos(){
        //return livroRepository.obterTodos();
        List<Livro> livros = livroRepository.findAll();
        return livros.stream()
        .map(livro -> new ModelMapper().map(livro,LivroDTO.class))
        .collect(Collectors.toList());
    }

     /**
     * Metodo para retornar um livro basedo no id
     * 
     * @param id do livro a ser pesquisado
     * @return Retorna um livro
     */
    public Optional<LivroDTO> obterPorId(Integer id){
        //return livroRepository.obterPorId(id);
        Optional<Livro> livro = livroRepository.findById(id);

        if(livro.isEmpty()){
            throw new ResourceNotFoundException("Livro inexistente");
        }   
        
        LivroDTO livroDto = new ModelMapper().map(livro.get(),  LivroDTO.class);
        return Optional.of(livroDto);
    }

    /**
     * Adiciona um novo livro
     * 
     * @param livro a ser adicionado
     * @return Livro adicionado
     */
    public LivroDTO adicionarLivro(LivroDTO livroDto){

        livroDto.setId(null);
        ModelMapper mapper = new ModelMapper();
        Livro livro = mapper.map(livroDto, Livro.class);
        livro = livroRepository.save(livro);
        livroDto.setId(livro.getId());
        return livroDto;

       // return livroRepository.adicionar(livro);
    }

    /**
     * Remove um livro baseado em seu id
     * 
     * @param id do livro a ser removido
     */
    public void remover(Integer id){
        Optional<Livro> livro = livroRepository.findById(id);
        if  (livro.isEmpty()){
            throw new ResourceNotFoundException("Livro inexistente");
        }
        livroRepository.deleteById(id);
    }

    /**
     * Metodo para atualizar um livro
     * 
     * @param id do livro que será atualizado
     * @param livro a ser atualizado
     * @return o livro atualizado     
     */
    public LivroDTO atualizar(Integer id, LivroDTO livroDto){
        //regra de negocio
        livroDto.setId(id);

        ModelMapper mapper = new ModelMapper();

        Livro livro =  mapper.map(livroDto, Livro.class);

        livroRepository.save(livro);

        return livroDto;

        //return livroRepository.atualizar(livro); 
    }


}
