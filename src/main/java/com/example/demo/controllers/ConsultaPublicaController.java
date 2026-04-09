package com.example.demo.controllers;
import com.example.demo.models.OrdenMedica;
import com.example.demo.repositories.HistoriaClinicaRepository;
import com.example.demo.repositories.OrdenMedicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@Controller

public class ConsultaPublicaController {
    @Autowired
    private OrdenMedicaRepository ordenMedicaRepo;
    @Autowired
    private HistoriaClinicaRepository historiaRepo;
    @PostMapping("/consultar")
    public String consultarOrden(@RequestParam("numeroOrden") Integer numeroOrden, @RequestParam("cedula") String cedula, Model model) {
        Optional<OrdenMedica> ordenOpt = ordenMedicaRepo.findByIdOrden(numeroOrden);
        if (ordenOpt.isPresent()) {
            OrdenMedica orden = ordenOpt.get();
            //TOCA AGREGAR UNA VALIDACION DE QUE LA ENTRADA SEA SOLO NUMEROS CON TRY CARCH
            String cedulaPaciente = orden.getPaciente().getUsuario().getCedula();
            if (cedulaPaciente.equals(cedula)) {
                model.addAttribute("orden", orden);
            } else {
                model.addAttribute("error", "Datos incorrectos.");
            }
            return "resultado_consulta"; // pagina vista o html que mostrará los datos de la orden
        } else {
            model.addAttribute("error", "No se encontró ninguna orden con ese número.");
            return "index"; // vuelve al inicio mostrando un mensaje de error
        }
    }

}
