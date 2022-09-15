# UN Icons

Manejo de los valores mostrados por equipo:

Los cambios sobre el name tag del usuario se realizan sobre:

    "plugins/NametagEdit/groups.yml"

Ahi se debe agregar un permiso especial definido como "nte.NOMBRE_GRUPO_LP" o el permiso indicado en el archivo "config.yml", ahi ponemos el prefijo y sufijo a mostrar, y la prioridad a la hora de organizarlo, por ejemplo:

    un:
        # El permiso debe coincidir con el señalado en el archivo de configuración
        Permission: nte.un
        Prefix: "&a[UN_N]"
        Suffix: "&f"
        SortPriority: 5

Para manejar el nombre mostrado en el tablist es necesario ir a:

    "plugins/TabList/groups.yml"

Ahi la sección inicia con el nombre del grupo en LP y lleva el formato de prefix y apariencia del nombre del jugador usando el placeholder "%player%", ejemplo:

      un:
        prefix: "&a[UN_T]"
        tabname: "&7%player%"

Los atributos no comentados en el archivo de configuración, mantienen su utilidad.

**NOTA: Esto es temporal mientras se mejora el proceso de automatizacion del plugin, por ende el archivo de configuracion puede ser modificado a futuro.**
