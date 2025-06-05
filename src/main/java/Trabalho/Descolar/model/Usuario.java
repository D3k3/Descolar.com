/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Trabalho.Descolar.model;

/**
 *
 * @author mathe
 */
public class Usuario {
    private String nome;
    private String tipo; // "NORMAL" ou "ADMIN"
    
    public Usuario() {}
    
    public Usuario(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }
    
    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}