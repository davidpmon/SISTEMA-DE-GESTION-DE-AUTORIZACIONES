package com.example.demo.services;

import com.example.demo.models.Medicamento;
import com.example.demo.repositories.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
    public class OrdenesService {
        @Autowired
        private MedicamentoRepository medicamentoRepo;

        /*@Autowired
        private TerapiaRepository terapiarepo;*/ //ACA PUEDE IR EL DE TERAPIAS

        public List<Medicamento> listarMedicamentos() {
            return medicamentoRepo.findAll();
        }

    }

