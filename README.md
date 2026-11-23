# Bad DOPO Cream — Edición POO

Juego de escritorio en Java 21 y Swing, con selección de personaje y cuatro niveles.

## Ejecutar

Desde la carpeta `badDOPOCream` (la que contiene `resources` y `src`):

```bash
mkdir -p build
javac -encoding UTF-8 -d build src/domain/*.java src/presentation/*.java
java -cp build presentation.BadDOPOCreamGUI
```

También puedes importar el proyecto en IntelliJ como proyecto Java y establecer `badDOPOCream` como directorio de trabajo.

## Novedades

- Nivel 4 **Laberinto congelado** con enemigos que persiguen al jugador, frutas, obstáculos, bloques de hielo, vidas, puntos, reinicio y condición de victoria/derrota.
- Selector de niveles rediseñado con acceso al nivel 4.
- Modelo `FrozenLabyrinth` separado de la vista/controlador `LevelFour`.
- Reparación de errores de compilación en `Fruit` y `BananaMovil`.
- Recursos visuales faltantes de respaldo para las pantallas y niveles originales.
- Prueba básica de las reglas del nuevo nivel.

## Controles del nivel 4

Flechas o WASD: mover. Espacio: crear/destruir hielo delante del personaje. R: reiniciar. Esc: volver al selector.

## Conceptos de POO

- **Encapsulamiento:** `FrozenLabyrinth` mantiene privado su estado y expone consultas controladas.
- **Abstracción:** las reglas del juego se ejecutan sin depender de Swing.
- **Herencia y polimorfismo:** `Fruit` y sus subclases implementan `getPoints()`; `BananaMovil` es una especialización.
- **Separación de responsabilidades:** `domain` contiene reglas y entidades; `presentation` contiene la interfaz Swing y entrada del usuario.
- **Colecciones:** `Set<Point>` evita posiciones duplicadas y `List<Point>` gestiona enemigos.

## Prueba rápida

```bash
javac -encoding UTF-8 -cp build -d build src/test/FrozenLabyrinthSmokeTest.java
java -cp build test.FrozenLabyrinthSmokeTest
```

## Limitaciones actuales

Los niveles 1–3 conservan buena parte de su implementación original. El nivel 3 sigue siendo principalmente visual; su integración completa con las mecánicas del nivel 4 sería una siguiente fase. Los recursos JPG añadidos son gráficos de respaldo, no sprites originales.
