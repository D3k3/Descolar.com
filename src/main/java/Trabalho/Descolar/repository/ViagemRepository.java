/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Trabalho.Descolar.repository;


import Trabalho.Descolar.model.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ViagemRepository extends JpaRepository<Viagem, Long> {
    List<Viagem> findByDestinoContainingIgnoreCase(String destino);
}
