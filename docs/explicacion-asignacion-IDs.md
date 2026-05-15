# Explicación de la Asignación de IDs

## Patrón General

La asignación de IDs en este proyecto sigue un **patrón de responsabilidad delegada a los repositorios**. Los repositorios son los únicos responsables de generar y asignar IDs a las entidades cuando se guardan por primera vez.

## Estrategia de Asignación

### 1. Estado Inicial en el Dominio

Cuando se crea una nueva entidad del dominio sin ID especificado, se inicializa con el valor **`-1`** como marcador de "sin asignar":

```java
protected Persona(String nombre, String dni) {
    this.id = -1;  // Marcador de entidad nueva, sin ID aún
    this.nombre = nombre;
    this.dni = dni;
}
```

### 2. Responsabilidad del Repositorio

**Los repositorios son quienes asignan las IDs**. Cada repositorio mantiene un contador interno (`idCounter`) que se incrementa para cada nueva entidad guardada:

```java
@Repository
public class ClienteRepositoryMemoria implements ClienteRepositoryPort {
    private long idCounter = 1;
    
    @Override
    public void guardar(Cliente cliente) {
        if(cliente.getId() == -1) {  // Si la entidad no tiene ID
            cliente.setId(idCounter++);  // Asignar nuevo ID
        }
        clientes.add(cliente);
    }
}
```

Este mismo patrón se repite en:
- **`ClienteRepositoryMemoria`** - Genera IDs para clientes
- **`SolicitudRepositoryMemoria`** - Genera IDs para solicitudes  
- **`TecnicoRepositoryMemoria`** - Genera IDs para técnicos

Igualmente, en el caso de usar persistencia, se delegara en el repositorio correstpondiente.

### 3. Flujo Completo

```
1. Crear entidad en el dominio
   └─> id = -1 (sin asignar)
           ↓
2. Enviar al repositorio para guardar
           ↓
3. Repositorio detecta id == -1
   └─> Asigna nuevo ID con idCounter++
           ↓
4. Entidad guardada con ID permanente
   └─> id = 1, 2, 3, ...
```

## Ventajas de Este Patrón

✅ **Separación de responsabilidades**: El dominio no conoce de asignación de IDs, el repositorio (infraestructura) la maneja  
✅ **Flexibilidad**: Cada implementación del repositorio puede usar su propia estrategia (memoria, BD, etc.)  
✅ **Consistencia**: Todas las entidades se asignan secuencialmente sin duplicados  
✅ **Simplicidad en el dominio**: Las entidades del dominio no tienen lógica de generación de IDs  

## Implementaciones por Tipo de Almacenamiento

### Repositorios en Memoria
Usan un contador simple (`idCounter`) que se incrementa en cada guardado.

### Repositorios JPA (Base de Datos)
Utilizan la estrategia JPA `@GeneratedValue(strategy = GenerationType.SEQUENCE)`:

```java
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
private long id;
```

Esto delega la generación de IDs a la base de datos, pero sigue el mismo principio: **el repositorio es responsable**.

## Ejemplo de Uso

```java
// 1. Crear cliente nuevo
Cliente cliente = new Cliente("Juan", "juan@email.com", TipoCliente.STANDARD);
System.out.println(cliente.getId()); // Output: -1

// 2. Guardar a través del repositorio
clienteRepository.guardar(cliente);

// 3. El repositorio asigna el ID
System.out.println(cliente.getId()); // Output: 1

// 4. Los siguientes también son asignados automáticamente
clienteRepository.guardar(new Cliente("Ana", "ana@email.com", TipoCliente.PREMIUM));
// Ana obtiene ID = 2
```

## Cambios Realizados en Esta Sesión

Se ha consolidado y documentado este patrón de asignación de IDs para garantizar:

- ✅ Consistencia en toda la aplicación
- ✅ Claridad en la responsabilidad de cada capa (dominio vs. infraestructura)
- ✅ Facilidad para cambiar la estrategia de asignación sin afectar al dominio
- ✅ Pruebas unitarias verifican que los IDs se asignan correctamente
