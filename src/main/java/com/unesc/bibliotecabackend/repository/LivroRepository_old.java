package com.unesc.bibliotecabackend.repository;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.unesc.bibliotecabackend.model.Livro;
import com.unesc.bibliotecabackend.model.exception.ResourceNotFoundException;

@Repository
public class LivroRepository_old {
    

    private List<Livro> livros = new ArrayList<Livro>();
    private Integer ultimoLivro = 0;

    /**
     * Retorna todos os livros cadastrados
     * 
     * @return Lista de livros
     */
    public List<Livro> obterTodos() {
        return livros;
    }

    /**
     * Metodo para retornar um livro basedo no id
     * 
     * @param id do livro a ser pesquisado
     * @return Retorna um livro
     */
    public Optional<Livro> obterPorId(Integer id) {
        return livros
                .stream()
                .filter(livro -> livro.getId() == id)
                .findFirst();
    }

    /**
     * Adiciona um novo livro
     * 
     * @param livro a ser adicionado
     * @return Livro adicionado
     */
    public Livro adicionar(Livro livro) {
        ultimoLivro++;
        livro.setId(ultimoLivro);
        livros.add(livro);
        return livro;
    }


     /**
     * Adiciona um novo livro
     * 
     * @param livro a ser adicionado
     * @return Livro adicionado
     */
    public Livro add(Livro livro) {       
        livros.add(livro);
        return livro;
    }


    /**
     * Remove um livro baseado em seu id
     * 
     * @param id do livro a ser removido
     */
    public void remover(Integer id) {
       
        Optional<Livro> livroEncontrado = obterPorId(id);

        if (livroEncontrado.isEmpty()) {
            throw new ResourceNotFoundException("Livro não encontrado");
        }
        
        livros.removeIf(livro -> livro.getId() == id);
    }

    /**
     * Metodo para atualizar um livro
     * 
     * @param livro a ser atualizado
     * @return o livro atualizado
     */
    public Livro atualizar(Livro livro) {
        // Pesquisar o livro a ser atualizado
        Optional<Livro> livroEncontrado = obterPorId(livro.getId());

        if (livroEncontrado.isEmpty()) {
            throw new ResourceNotFoundException("Livro não encontrado");
        }
        // Remover o livro antigo
        remover(livro.getId());

        // Adicionar o novo livro
        add(livro);

        return livro;
    }

}
