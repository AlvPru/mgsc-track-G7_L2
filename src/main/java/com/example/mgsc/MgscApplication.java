package com.example.mgsc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;
import com.example.mgsc.service.*;
import com.example.mgsc.infrastucture.*;
import com.example.mgsc.dominio.*;

@SpringBootApplication
public class MgscApplication {

	public static void main(String[] args) {
		// Instanciar repositorios
		ClienteRepositoryPort clienteRepo = ClienteRepositoryMemoria.getInstance();
		TecnicoRepositoryPort tecnicoRepo = new TecnicoRepositoryMemoria();
		SolicitudRepositoryPort solicitudRepo = SolicitudRepositoryMemoria.getInstance();

		// Instanciar servicios
		ClienteService clienteService = new ClienteService(clienteRepo);
		TecnicoService tecnicoService = new TecnicoService(tecnicoRepo);
		SolicitudService solicitudService = new SolicitudService(solicitudRepo);

		// Añadir datos de ejemplo
		clienteService.guardar(new Cliente(1, "Juan Perez", "juan@email.com", TipoCliente.STANDARD));
		clienteService.guardar(new Cliente(2, "Ana Gomez", "ana@email.com", TipoCliente.PREMIUM));
		tecnicoService.guardar(new Tecnico(1, "Carlos Ruiz", "Electricidad", true));
		tecnicoService.guardar(new Tecnico(2, "Maria Lopez", "Plomeria", true));
		solicitudService.guardar(new Solicitud("Reparar luz", clienteService.buscarPorId(1).get()));
		solicitudService.guardar(new Solicitud("Arreglar grifo", clienteService.buscarPorId(2).get()));

		// Scanner para entrada
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\nMenu:");
			System.out.println("1. Listar Clientes");
			System.out.println("2. Ver Detalle Cliente");
			System.out.println("3. Modificar Cliente");
			System.out.println("4. Añadir Cliente");
			System.out.println("5. Listar Tecnicos");
			System.out.println("6. Ver Detalle Tecnico");
			System.out.println("7. Modificar Tecnico");
			System.out.println("8. Añadir Tecnico");
			System.out.println("9. Listar Solicitudes");
			System.out.println("10. Ver Detalle Solicitud");
			System.out.println("11. Modificar Solicitud");
			System.out.println("12. Añadir Solicitud");
			System.out.println("0. Salir");
			System.out.print("Opcion: ");

			int opcion = scanner.nextInt();
			scanner.nextLine(); // Consumir newline

			switch (opcion) {
				case 1:
					System.out.println("Clientes:");
					clienteService.listar().forEach(c -> System.out.println("ID: " + c.getId() + ", Nombre: " + c.getNombre()));
					break;
				case 2:
					System.out.print("ID Cliente: ");
					int idC = scanner.nextInt();
					scanner.nextLine();
					clienteService.buscarPorId(idC).ifPresentOrElse(
						c -> System.out.println("ID: " + c.getId() + ", Nombre: " + c.getNombre() + ", Email: " + c.getEmail() + ", Tipo: " + c.getTipo()),
						() -> System.out.println("Cliente no encontrado")
					);
					break;
				case 3:
					System.out.print("ID Cliente: ");
					int idCM = scanner.nextInt();
					scanner.nextLine();
					clienteService.buscarPorId(idCM).ifPresent(c -> {
						System.out.print("Nuevo Email: ");
						String email = scanner.nextLine();
						c.setEmail(email);
						System.out.print("Nuevo Tipo (PREMIUM/STANDARD): ");
						String tipoStr = scanner.nextLine();
						try {
							TipoCliente tipo = TipoCliente.valueOf(tipoStr.toUpperCase());
							c.setTipo(tipo);
							System.out.println("Cliente modificado.");
						} catch (IllegalArgumentException e) {
							System.out.println("Tipo invalido.");
						}
					});
					break;
				case 4:
					System.out.print("ID Cliente: ");
					int idNuevoC = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Nombre: ");
					String nombreC = scanner.nextLine();
					System.out.print("Email: ");
					String emailC = scanner.nextLine();
					System.out.print("Tipo (PREMIUM/STANDARD): ");
					String tipoStrC = scanner.nextLine();
					try {
						TipoCliente tipoC = TipoCliente.valueOf(tipoStrC.toUpperCase());
						Cliente nuevoCliente = new Cliente(idNuevoC, nombreC, emailC, tipoC);
						clienteService.guardar(nuevoCliente);
						System.out.println("Cliente añadido.");
					} catch (IllegalArgumentException e) {
						System.out.println("Tipo invalido.");
					}
					break;
				case 5:
					System.out.println("Tecnicos:");
					tecnicoService.listar().forEach(t -> System.out.println("ID: " + t.getId() + ", Nombre: " + t.getNombre()));
					break;
				case 6:
					System.out.print("ID Tecnico: ");
					int idT = scanner.nextInt();
					scanner.nextLine();
					tecnicoService.listar().stream().filter(t -> t.getId() == idT).findFirst().ifPresentOrElse(
						t -> System.out.println("ID: " + t.getId() + ", Nombre: " + t.getNombre() + ", Especialidad: " + t.getEspecialidad() + ", Activo: " + t.isActivo()),
						() -> System.out.println("Tecnico no encontrado")
					);
					break;
				case 7:
					System.out.print("ID Tecnico: ");
					int idTM = scanner.nextInt();
					scanner.nextLine();
					tecnicoService.listar().stream().filter(t -> t.getId() == idTM).findFirst().ifPresent(t -> {
						System.out.print("Nueva Especialidad: ");
						String esp = scanner.nextLine();
						t.setEspecialidad(esp);
						System.out.print("Activo (true/false): ");
						boolean activo = scanner.nextBoolean();
						t.setActivo(activo);
						System.out.println("Tecnico modificado.");
					});
					break;
				case 8:
					System.out.print("ID Tecnico: ");
					int idNuevoT = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Nombre: ");
					String nombreT = scanner.nextLine();
					System.out.print("Especialidad: ");
					String espT = scanner.nextLine();
					System.out.print("Activo (true/false): ");
					boolean activoT = scanner.nextBoolean();
					scanner.nextLine(); // Consumir newline
					Tecnico nuevoTecnico = new Tecnico(idNuevoT, nombreT, espT, activoT);
					tecnicoService.guardar(nuevoTecnico);
					System.out.println("Tecnico añadido.");
					break;
				case 9:
					System.out.println("Solicitudes:");
					solicitudService.listar().forEach(s -> System.out.println("ID: " + s.getId() + ", Descripcion: " + s.getDescripcion()));
					break;
				case 10:
					System.out.print("ID Solicitud: ");
					int idS = scanner.nextInt();
					scanner.nextLine();
					solicitudService.buscarPorId(idS).ifPresentOrElse(
						s -> System.out.println("ID: " + s.getId() + ", Descripcion: " + s.getDescripcion() + ", Estado: " + s.getEstado() + ", Cliente: " + (s.getClienteAsignado() != null ? s.getClienteAsignado().getNombre() : "N/A") + ", Tecnico: " + (s.getTecnico() != null ? s.getTecnico().getNombre() : "N/A")),
						() -> System.out.println("Solicitud no encontrada")
					);
					break;
				case 11:
					System.out.print("ID Solicitud: ");
					int idSM = scanner.nextInt();
					scanner.nextLine();
					solicitudService.buscarPorId(idSM).ifPresent(s -> {
						System.out.print("Nueva Descripcion: ");
						String desc = scanner.nextLine();
						s.setDescripcion(desc);
						System.out.print("Nuevo Estado: ");
						String estado = scanner.nextLine();
						s.setEstado(estado);
						solicitudService.modificar(s);
						System.out.println("Solicitud modificada.");
					});
					break;
				case 12:
					System.out.print("Descripcion: ");
					String descNueva = scanner.nextLine();
					System.out.print("ID Cliente: ");
					int idClienteS = scanner.nextInt();
					scanner.nextLine();
					clienteService.buscarPorId(idClienteS).ifPresentOrElse(
						c -> {
							Solicitud nuevaSolicitud = new Solicitud(descNueva, c);
							solicitudService.guardar(nuevaSolicitud);
							System.out.println("Solicitud añadida.");
						},
						() -> System.out.println("Cliente no encontrado.")
					);
					break;
				case 0:
					System.out.println("Saliendo...");
					scanner.close();
					System.exit(0);
					break;
				default:
					System.out.println("Opcion invalida.");
			}
		}
	}

}
