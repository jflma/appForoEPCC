
# Proyecto de Foro

Este proyecto es una implementación de un foro donde los usuarios pueden comentar en las entradas. La clase `Post` es  parte fundamental de esta implementación.

## Convenciones de Codificación

se a seguido las siguientes convenciones en la implementacion:

1. **Nombres Claros y Descriptivos:**
    - Nombres de clases, métodos y variables son claros y reflejan su propósito.
    - Ejemplo: La clase `Post` representa una publicación en el foro.

2. **Funciones con Responsabilidades Claras:**
    - Cada método y función tiene una única responsabilidad, lo que facilita su lectura y mantenimiento.
    - Ejemplo: Los métodos de acceso (`getters` y `setters`) siguen esta práctica.

## Prácticas de Codificación Legible

Este proyecto sigue principios de Clean Code para asegurar la legibilidad y mantenibilidad del código. A continuación se describen dos prácticas de codificación legible aplicadas en los archivos del proyecto.

### Práctica 1: Nombres Descriptivos
Usar nombres descriptivos para variables, funciones y clases hace que el código sea más fácil de entender. Los nombres deben comunicar la intención de su uso, eliminando la necesidad de comentarios adicionales para explicar su propósito.

**Fragmento de Código**  
Archivo: Answer.java
```
@Entity
@Table(name = "answer")
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "id_entry", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_entry_answer"), nullable = false)
  private Entry entry;
  
  @ManyToOne
  @JoinColumn(name = "id_post", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_post_answer"), nullable = false)
  private Post post;

  public Long getId() {
    return id;
  }

  public Entry getEntry() {
    return entry;
  }

  public Post getPost() {
    return post;
  }

  public void setEntry(Entry entry) {
    this.entry = entry;
  }

  public void setPost(Post post) {
    this.post = post;
  }

}
```
**Análisis:**  
- El nombre de la clase Answer es claro y describe que representa una respuesta.
- Los nombres de los métodos (getId, getEntry, getPost, setEntry, setPost) indican claramente su propósito.

### Práctica 2: Manejo de Excepciones con Mensajes Claros
Cuando se manejan excepciones, es importante proporcionar mensajes de error claros y significativos. Esto ayuda a identificar rápidamente el problema cuando ocurre una excepción.

**Fragmento de Código**  
Archivo: AnswerService.java
```
@Override
@Transactional
public Answer createAnswer(Long postIdToReply, Long userId, String content){
  try {
    ForoUser user = userService.getUserById(userId);
    Entry entryCreated = entryService.createEntry(user, content);
    Post postToReply = postService.getPostById(postIdToReply);

    Answer answerCreated = new Answer();
    answerCreated.setEntry(entryCreated);
    answerCreated.setPost(postToReply);
    
    return answerRepository.save(answerCreated);
  } catch (Exception e) {
    throw new RuntimeException("No se pudo crear la respuesta");
  }
}
```

**Análisis:**  
El mensaje de excepción "No se pudo crear la respuesta" proporciona una indicación clara de lo que salió mal, facilitando la depuración.

### Práctica 3: Funciones con Responsabilidades Claras
Cada método y función tiene una única responsabilidad, lo que facilita su lectura y mantenimiento.

**Fragmento de Código**   
Archivo: Answer.java
```
@Entity
@Table(name = "answer")
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "id_entry", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_entry_answer"), nullable = false)
  private Entry entry;
  
  @ManyToOne
  @JoinColumn(name = "id_post", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_post_answer"), nullable = false)
  private Post post;

  public Long getId() {
    return id;
  }

  public Entry getEntry() {
    return entry;
  }

  public Post getPost() {
    return post;
  }

  public void setEntry(Entry entry) {
    this.entry = entry;
  }

  public void setPost(Post post) {
    this.post = post;
  }

}
```
**Análisis:**  
- Los métodos (getId, getEntry, getPost, setEntry, setPost) tienen responsabilidad única.

## Principios SOLID
- S – Single Responsibility Principle (SRP)
- O – Open/Closed Principle (OCP)
- L – Liskov Substitution Principle (LSP)
- I – Interface Segregation Principle (ISP)
- D – Dependency Inversion Principle (DIP)

### Principio de Responsabilidad Única (SRP)
Una clase debe tener una, y solo una, razón para cambiar. Esto significa que una clase debe tener una sola responsabilidad o propósito.

**Fragmento de Código**  
Archivo: AnswerService.java

```
@Service
public class AnswerService implements IAnswerService {

  @Autowired
  private UserService userService;

  @Autowired
  private PostService postService;

  @Autowired
  private EntryService entryService;

  @Autowired
  private AnswerRepositoryImp answerRepository;

  @Override
  @Transactional
  public Answer createAnswer(Long postIdToReply, Long userId, String content){
    try {
      ForoUser user = userService.getUserById(userId);
      Entry entryCreated = entryService.createEntry(user, content);
      Post postToReply = postService.getPostById(postIdToReply);

      Answer answerCreated = new Answer();
      answerCreated.setEntry(entryCreated);
      answerCreated.setPost(postToReply);
      
      return answerRepository.save(answerCreated);
    } catch (Exception e) {
      throw new RuntimeException("No se pudo crear la respuesta");
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Answer getAnswerById(Long answerId){
    return answerRepository.findById(answerId)
      .orElseThrow(() -> new RuntimeException("Respuesta no encontrada"));
  }
}
```
**Análisis:**  
La clase AnswerService tiene la única responsabilidad de manejar la lógica relacionada con las respuestas (Answer). Otras responsabilidades como la gestión de usuarios, entradas y publicaciones se delegan a los respectivos servicios (UserService, EntryService, PostService).

### Principio de Inversión de Dependencias (DIP)
Los módulos de alto nivel no deben depender de módulos de bajo nivel. Ambos deben depender de abstracciones. Las abstracciones no deben depender de detalles. Los detalles deben depender de abstracciones.

**Fragmento de Código**  
Archivo: AnswerService.java

```
@Service
public class AnswerService implements IAnswerService {

  @Autowired
  private UserService userService;

  @Autowired
  private PostService postService;

  @Autowired
  private EntryService entryService;

  @Autowired
  private AnswerRepositoryImp answerRepository;

  @Override
  @Transactional
  public Answer createAnswer(Long postIdToReply, Long userId, String content){
    // Lógica de creación de respuesta
  }

  @Override
  @Transactional(readOnly = true)
  public Answer getAnswerById(Long answerId){
    // Lógica de obtención de respuesta por ID
  }
}
```

**Análisis:**  
La clase AnswerService depende de las interfaces de servicio (IAnswerService) y repositorios (AnswerRepositoryImp). Esto permite que AnswerService dependa de abstracciones en lugar de implementaciones concretas, facilitando el cambio de las implementaciones sin afectar el código de alto nivel.

### Principio de Segregación de Interfaces (ISP)
Los clientes no deben estar forzados a depender de interfaces que no utilizan. En lugar de una interfaz grande, se deben crear interfaces más específicas y pequeñas.

**Fragmento de Código**  
Archivo: IAnswerService.java

```
public interface IAnswerService {
  Answer createAnswer(Long postIdToReply, Long userId, String content);
  Answer getAnswerById(Long answerId);
}
```

**Análisis:**  
La interfaz IAnswerService está segregada en dos métodos específicos (createAnswer y getAnswerById). Esto asegura que las implementaciones de IAnswerService solo necesiten implementar los métodos que realmente usan, evitando interfaces grandes y monolíticas.

## Estilos de Programación Aplicados

### Error/Exception Handling
El manejo de errores y excepciones es un enfoque que se utiliza para gestionar situaciones inesperadas y errores en el flujo de ejecución del programa. Este estilo ayuda a mantener el código limpio y a proporcionar mensajes de error claros.

**Fragmento de Código**  
Archivo: AnswerService.java

```
@Override
@Transactional
public Answer createAnswer(Long postIdToReply, String content){
  try {
    String userByUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    ForoUser user = userService.getUserByUsername(userByUsername);
    Entry entryCreated = entryService.createEntry(user, content);
    Post postToReply = postService.getPostById(postIdToReply);

    Answer answerCreated = new Answer();
    answerCreated.setEntry(entryCreated);
    answerCreated.setPost(postToReply);
    return answerRepository.save(answerCreated);
  } catch (Exception e) {
    throw new CreationException("No se pudo crear la respuesta");
  }
}
```

**Análisis:**  
- El bloque `try-catch` maneja cualquier excepción que pueda ocurrir durante el proceso de creación de una respuesta.
- La excepción se encapsula en una `CreationException` con un mensaje claro, lo que facilita la depuración y el manejo de errores en niveles superiores del código.

### Pipeline
El estilo de programación Pipeline consiste en encadenar una serie de operaciones donde la salida de una operación se convierte en la entrada de la siguiente. Este estilo promueve un flujo de datos claro y una transformación paso a paso.

**Fragmento de Código**  
Archivo: AnswerService.java

```
@Override
@Transactional
public Answer createAnswer(Long postIdToReply, String content){
  String userByUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
  ForoUser user = userService.getUserByUsername(userByUsername);
  Entry entryCreated = entryService.createEntry(user, content);
  Post postToReply = postService.getPostById(postIdToReply);

  Answer answerCreated = new Answer();
  answerCreated.setEntry(entryCreated);
  answerCreated.setPost(postToReply);
  return answerRepository.save(answerCreated);
}
```

**Análisis:**  
- El método `createAnswer` sigue un flujo de operaciones secuenciales:
  -  Obtener el usuario autenticado.
  - Crear una entrada (Entry) para el usuario.
  - Obtener la publicación (Post) a la que se responde.
  - Crear una respuesta (Answer) con la entrada y la publicación.
  - Guardar la respuesta en el repositorio.
- Cada paso depende de la salida del paso anterior, formando un pipeline claro y estructurado.

### Things

El estilo "Things" se refiere a la creación y manipulación de objetos o entidades del dominio. Este estilo se centra en la gestión y las interacciones de las entidades que representan conceptos del dominio del problema.

**Fragmento de Código**  
Archivo: AnswerService.java

```
@Service
public class AnswerService implements IAnswerService {

  private UserService userService;
  private PostService postService;
  private EntryService entryService;
  private AnswerRepositoryImp answerRepository;

  public AnswerService(UserService userService, PostService postService, EntryService entryService, AnswerRepositoryImp answerRepository) {
    this.userService = userService;
    this.postService = postService;
    this.entryService = entryService;
    this.answerRepository = answerRepository;
  }

  @Override
  @Transactional
  public Answer createAnswer(Long postIdToReply, String content){
    String userByUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    ForoUser user = userService.getUserByUsername(userByUsername);
    Entry entryCreated = entryService.createEntry(user, content);
    Post postToReply = postService.getPostById(postIdToReply);

    Answer answerCreated = new Answer();
    answerCreated.setEntry(entryCreated);
    answerCreated.setPost(postToReply);
    return answerRepository.save(answerCreated);
  }

  @Override
  @Transactional(readOnly = true)
  public Answer getAnswerById(Long idAnswer){
    return answerRepository.findById(idAnswer)
      .orElseThrow();
  }
}
```

**Análisis:**  
- La clase AnswerService maneja las entidades del dominio ForoUser, Entry, Post, y Answer.
- Los métodos createAnswer y getAnswerById se centran en la creación y recuperación de instancias de Answer, interactuando con otras entidades del dominio.
- Este enfoque facilita la manipulación de objetos del dominio y sus interacciones, encapsulando la lógica de negocio relacionada con estas entidades.