# Gestión de Contactos (Java Swing) — Documentación del Proyecto

**Fecha:** 2026-04-20  
**IDE:** Eclipse + WindowBuilder  
**Lenguaje:** Java  
**Tipo de aplicación:** Escritorio (GUI)  
**Patrón arquitectónico:** MVC (Model–View–Controller)

---

## 1. Descripción general

Este proyecto implementa una aplicación de escritorio para la **gestión de contactos**, basada en una interfaz tipo formulario como la mostrada en la imagen de referencia, incorporando mejoras de usabilidad y escalabilidad:

- Interfaz organizada por pestañas con `JTabbedPane`.
- Gestión de contactos mediante una `JTable` con **ordenamiento** y **filtrado**.
- Eventos adicionales de teclado y mouse (atajos + menú contextual).
- Exportación de contactos a archivo **CSV**.
- Barra de progreso `JProgressBar` para simular procesos como carga de datos.
- Organización del código aplicando el patrón **MVC**.

---

## 2. Objetivos del sistema

### Objetivo general
Desarrollar una aplicación Java Swing que permita registrar, visualizar, modificar, eliminar y exportar contactos, manteniendo un código mantenible mediante MVC.

### Objetivos específicos
1. Separar responsabilidades con MVC.
2. Mejorar la experiencia de usuario con atajos y acciones rápidas.
3. Permitir búsqueda y organización de contactos con tabla ordenable y filtrable.
4. Exportar la información a un formato estándar (CSV).
5. Mostrar progreso al simular cargas de información.

---

## 3. Funcionalidades implementadas

### 3.1. Componentes gráficos (GUI)
- `JTabbedPane` con:
  - **Contactos**: formulario + tabla + filtro + barra de progreso.
  - **Estadísticas**: total de contactos, favoritos y conteo por categoría.
- Formulario:
  - Nombre, Teléfono, Email, Favorito, Categoría.
- Botones:
  - **AGREGAR**, **MODIFICAR**, **ELIMINAR**
  - **Exportar CSV**
  - **Cargar (demo)**

### 3.2. Eventos adicionales
**Atajos de teclado:**
- `Ctrl + N`: limpiar formulario (nuevo)
- `Ctrl + S`: agregar contacto
- `Delete`: eliminar contacto seleccionado

**Eventos de mouse:**
- Doble clic sobre una fila: cargar contacto en el formulario para editar.
- Clic derecho sobre la tabla: menú contextual con:
  - Editar
  - Eliminar
  - Marcar/Desmarcar favorito

### 3.3. Tabla con ordenamiento y filtrado
- `JTable` usando `ContactTableModel` (modelo de tabla).
- Ordenamiento por columnas mediante `TableRowSorter`.
- Filtrado por nombre con un campo “BUSCAR POR NOMBRE”.

### 3.4. Exportación a CSV
- Exporta todos los contactos a un archivo `.csv` usando `JFileChooser`.
- Formato del CSV:
  - `id,nombre,telefono,email,favorito,categoria`

### 3.5. Barra de progreso (JProgressBar)
- Proceso “Cargar (demo)” simula una carga incremental de 0% a 100%.
- Implementado con `SwingWorker` para no congelar la interfaz.
- Al finalizar, se cargan contactos de ejemplo (datos demo).

### 3.6. Estadísticas
En la pestaña **Estadísticas** se muestran:
- Total de contactos.
- Número de favoritos.
- Conteo agrupado por categoría.

---

## 4. Arquitectura MVC

### 4.1. Model (modelo de datos)
Responsable de representar la información y reglas de negocio.

Clases:
- `model.Contact`: Entidad contacto.
- `model.ContactRepository`: Almacena contactos en memoria (lista) y provee CRUD.
- `model.ContactTableModel`: Adaptador para mostrar contactos en `JTable`.
- `model.ContactStats`: Cálculos de estadísticas.

### 4.2. View (interfaz gráfica)
Responsable de mostrar la interfaz y exponer componentes mediante getters.

Clases:
- `view.MainFrame`: Ventana principal, contiene `JTabbedPane`.
- `view.ContactsPanel`: Vista de contactos (formulario, tabla, filtro, progreso).
- `view.StatsPanel`: Vista de estadísticas.

> Nota: La vista no implementa la lógica de negocio; solamente define UI.

### 4.3. Controller (controlador)
Coordina eventos, validaciones básicas, operaciones del repositorio y actualización de la vista.

Clases:
- `controller.MainController`: 
  - Conecta listeners a botones, teclado y mouse.
  - Aplica filtrado/ordenamiento de tabla.
  - Gestiona agregar, modificar, eliminar.
  - Ejecuta carga demo con progreso.
  - Actualiza estadísticas.
- `controller.CsvExporter`: Exporta lista de contactos a CSV.

---

## 5. Estructura del proyecto

```
src/
  app/
    Main.java
  model/
    Contact.java
    ContactRepository.java
    ContactTableModel.java
    ContactStats.java
  view/
    MainFrame.java
    ContactsPanel.java
    StatsPanel.java
  controller/
    MainController.java
    CsvExporter.java
```

---

## 6. Requisitos del sistema

- Java JDK 8 o superior (recomendado 11 o 17)
- Eclipse IDE
- Plugin WindowBuilder (para edición visual)

---

## 7. Instrucciones de instalación y ejecución (Eclipse)

1. Abrir Eclipse.
2. `File > New > Java Project`
   - Nombre: `GestionContactosMVC`
3. Crear paquetes: `app`, `model`, `view`, `controller`.
4. Crear las clases con los nombres indicados y pegar el código fuente.
5. Ejecutar:
   - Clic derecho en `app/Main.java`
   - `Run As > Java Application`

---

## 8. Manual de usuario (uso)

### Agregar contacto
1. Completar los campos del formulario.
2. Presionar **AGREGAR** o usar `Ctrl+S`.
3. El contacto aparecerá en la tabla.

### Modificar contacto
1. Seleccionar un contacto en la tabla.
2. Doble clic o clic derecho > **Editar** para cargar datos al formulario.
3. Cambiar los datos y presionar **MODIFICAR**.

### Eliminar contacto
1. Seleccionar un contacto en la tabla.
2. Presionar **ELIMINAR**, o tecla `Delete`, o clic derecho > **Eliminar**.

### Buscar contacto por nombre
1. Escribir en “BUSCAR POR NOMBRE”.
2. La tabla filtrará resultados automáticamente.

### Exportar contactos a CSV
1. Clic en **Exportar CSV**.
2. Elegir ubicación y nombre del archivo.
3. Guardar.

### Cargar demo con progreso
1. Clic en **Cargar (demo)**.
2. Se mostrará progreso en la barra.
3. Al finalizar, se cargarán contactos de ejemplo.

---

## 9. Consideraciones y limitaciones

- Los datos se almacenan en memoria: al cerrar la app, se pierden.
- La carga es una simulación (demo) para ilustrar el uso de `JProgressBar` y `SwingWorker`.
- La exportación CSV exporta la lista total (no solo lo filtrado).

---

## 10. Posibles mejoras futuras

- Persistencia real: guardar y cargar contactos desde archivo o base de datos.
- Importación desde CSV.
- Exportar solo lo visible (filtrado) en la tabla.
- Validaciones avanzadas (formato de email, teléfono, campos obligatorios).
- Edición en un diálogo modal con validación.

---

## 11. Créditos / Autor

- Autor: Wilmer Andres Trujillo Vera
