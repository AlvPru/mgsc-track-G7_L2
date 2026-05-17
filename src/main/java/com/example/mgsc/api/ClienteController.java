package com.example.mgsc.api;

import com.example.mgsc.api.DTOs.ClienteRequestDTO;
import com.example.mgsc.api.DTOs.ClienteResponseDTO;
import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> crearCliente(@RequestBody ClienteRequestDTO request) {
        try {
            Cliente cliente = clienteService.crearCliente(
                    request.getNombre(),
                    request.getDni(),
                    request.getEmail(),
                    request.getTipo());
            return ResponseEntity.ok(toResponseDTO(cliente));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<ClienteResponseDTO> buscarPorDni(@PathVariable String dni) {
        return clienteService.buscarPorDni(dni)
                .map(c -> ResponseEntity.ok(toResponseDTO(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    private ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getDni(),
                cliente.getEmail(),
                cliente.getTipo());
    }
}