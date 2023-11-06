class NotesApp {
    private val archives = mutableListOf<Archive>()

    fun run() {
        navigationAndInput(
            items = archives,
            showMenu = {
                showArchiveMenu()
            },
            showCreation = {
                showArchiveCreation()
            },
            open = { itemIndex ->
                openArchive(itemIndex)
            }
        )
    }

    private fun navigationAndInput(
        items: List<*>,
        showMenu: () -> Unit,
        showCreation: () -> Unit,
        open: (index: Int) -> Unit,
    ) {
        while (true) {
            showMenu()
            val input = readLine()?.toIntOrNull()
            when {
                input == getCreationMenuNumber(items) -> {
                    showCreation()
                }

                input == getExitMenuNumber(items) -> return
                input in getAllMenuNumbers(items) && input != null -> {
                    open(input)
                }

                else -> {
                    handleInvalidInput(input)
                }
            }
        }
    }

    private fun showArchiveMenu() {
        showMenu(
            title = "Список архивов:",
            menuItem = archives,
            creationButtonName = "Создать архив",
            exitButtonName = "Выход"
        )
    }

    private fun showNotesMenu(notes: List<Note>, archiveName: String) {
        println("Архив: $archiveName")
        showMenu(
            title = "Заметки:",
            menuItem = notes,
            creationButtonName = "Создать заметку",
            exitButtonName = "Назад"
        )
    }

    private fun showMenu(title: String, menuItem: List<MenuItem>, creationButtonName: String, exitButtonName: String) {
        println(title)
        menuItem.forEachIndexed { index, item ->
            println("$index. ${item.name}")
        }
        println("${menuItem.size}. $creationButtonName")
        println("${menuItem.size + 1}. $exitButtonName")
    }

    private fun getCreationMenuNumber(items: List<*>): Int = items.size

    private fun showArchiveCreation() {
        println("Введите название нового архива:")
        val archiveName = readLine()
        if (archiveName.isNullOrBlank()) {
            println("Название архива не может быть пустым.")
        } else {
            archives.add(Archive(archiveName))
            println("Архив создан.")
        }
    }

    private fun showNoteCreation(archive: Archive) {
        println("Введите название новой заметки:")
        val noteTitle = readLine()
        if (noteTitle.isNullOrBlank()) {
            println("Название заметки не может быть пустым.")
        } else {
            println("Введите содержание новой заметки:")
            val noteContent = readLine()
            archive.notes.add(Note(noteTitle, noteContent ?: ""))
            println("Заметка создана.")
        }
    }

    private fun getExitMenuNumber(items: List<*>): Int {
        return (items.size + 1)
    }

    private fun getAllMenuNumbers(items: List<*>): IntRange = 0..items.lastIndex

    private fun openArchive(archiveIndex: Int) {
        val currentArchive = archives[archiveIndex]
        navigationAndInput(
            items = currentArchive.notes,
            showMenu = {
                showNotesMenu(currentArchive.notes, currentArchive.name)
            },
            showCreation = {
                showNoteCreation(currentArchive)
            },
            open = { itemIndex ->
                openNote(itemIndex, currentArchive)
            }
        )
    }

    private fun openNote(noteIndex: Int, archive: Archive) {
        val selectedNote = archive.notes[noteIndex]
        println("Заметка: ${selectedNote.name}\n${selectedNote.content}")
    }

    private fun handleInvalidInput(input: Int?) {
        if (input == null) {
            println("Введите цифру")
        } else {
            println("Введите корректный индекс")
        }
    }
}
