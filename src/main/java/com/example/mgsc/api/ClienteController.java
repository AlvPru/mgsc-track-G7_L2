package com.example.mgsc.api;

import com.example.mgsc.api.DTOs.ClienteRequestDTO;
import com.example.mgsc.api.DTOs.ClienteResponseDTO;
import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.service.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteResponseDTO> listarClientes() {
        return clienteService.listar().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO obtenerCliente(@PathVariable long id) {
        return toResponseDTO(clienteService.buscarPorIdOrThrow(id));
    }

    @PostMapping
    public ClienteResponseDTO crearCliente(@RequestBody ClienteRequestDTO request) {
        Cliente cliente = clienteService.crearCliente(
                request.getId(),
                request.getNombre(),
                request.getEmail(),
                request.getTipo());
        return toResponseDTO(cliente);
    }
 
    private ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getTipo());
    }
}