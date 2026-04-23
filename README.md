# SimpleNotes

Мобильное Android-приложение для локального хранения заметок. 

**Репозиторий:** `https://github.com/4MaticAirlines/simple-notes`

## Соответствие техническому заданию

В приложении реализован минимальный функционал:
- создание заметки: **заголовок + текст**;
- редактирование заметки;
- удаление заметки;
- просмотр списка заметок;
- локальное хранение данных.

Также в рамках задания предусмотрены:
- модульное тестирование основного функционала;
- UI-тестирование интерфейса;
- фиксация потребления ресурсов через Android Profiler;
- документирование результатов в виде скриншотов.

## Используемые технологии

- **Kotlin**
- **Android SDK 34**
- **Min SDK 26**
- **Room Database (SQLite)**
- **MVVM**
- **LiveData / Coroutines / Flow**
- **RecyclerView**
- **Material Design 3**
- **JUnit 4**
- **AndroidX Test**
- **Espresso**

## Основной функционал

### 1. Просмотр списка заметок
На главном экране отображается список всех сохранённых заметок. Если заметок нет, показывается пустое состояние.

<img width="1080" height="1920" alt="screen_01_notes_list" src="https://github.com/user-attachments/assets/06011343-2da7-420c-b07e-a7ec039f8be3" />


### 2. Создание и редактирование заметки
Пользователь может создать новую заметку или изменить существующую. Для каждой заметки предусмотрены два поля:
- заголовок;
- основной текст.

<img width="1080" height="1920" alt="screen_02_note_editor" src="https://github.com/user-attachments/assets/7da1ba35-dd68-47bc-8e25-7aa5726d847a" />


### 3. Удаление заметки
Удаление реализовано из списка заметок. После удаления запись исчезает из локального хранилища.

### 4. Локальное хранение данных
Все заметки сохраняются локально в базе данных **Room (SQLite)**. После повторного запуска приложения ранее сохранённые заметки остаются доступны.

## Структура проекта

```text
simple-notes/
├── app/
│   ├── src/main/java/com/example/simplenotes/
│   │   ├── data/
│   │   │   ├── Note.kt
│   │   │   ├── NoteDao.kt
│   │   │   ├── AppDatabase.kt
│   │   │   └── NoteRepository.kt
│   │   └── ui/
│   │       ├── NoteViewModel.kt
│   │       ├── NoteListActivity.kt
│   │       ├── NoteEditActivity.kt
│   │       ├── NoteAdapter.kt
│   │       └── DateFormatter.kt
│   ├── src/androidTest/java/com/example/simplenotes/
│   │   ├── NoteDaoTest.kt
│   │   └── NoteUITest.kt
│   └── src/main/res/
├── docs/
│   ├── profiling-results.md
│   └── screenshots.md
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Запуск проекта

### Требования
- Android Studio;
- Android SDK Platform 34;
- Android-эмулятор или физическое устройство с Android 8.0+;
- JDK 17.

### Шаги запуска
1. Клонировать репозиторий:
   ```bash
   git clone https://github.com/4MaticAirlines/simple-notes.git
   cd simple-notes
   ```
2. Открыть проект в Android Studio.
3. Дождаться завершения `Gradle Sync`.
4. Запустить приложение на эмуляторе или подключённом устройстве.

## Имитация запуска приложения

Ниже приведён пример сценария запуска проекта:

```bash
$ git clone https://github.com/4MaticAirlines/simple-notes.git
Cloning into 'simple-notes'...
Receiving objects: 100% (100/100), done.
Resolving deltas: 100% (25/25), done.

$ cd simple-notes
$ ./gradlew assembleDebug
> Task :app:preBuild UP-TO-DATE
> Task :app:compileDebugKotlin
> Task :app:mergeDebugResources
> Task :app:packageDebug
> Task :app:assembleDebug

BUILD SUCCESSFUL in 18s
29 actionable tasks: 29 executed
```

После запуска пользователь попадает на экран списка заметок. Через кнопку добавления можно создать новую запись, затем открыть её для редактирования или удалить.

## Модульное тестирование

Для проверки логики локального хранения данных используются тесты DAO / Room. Проверяются:
- создание заметки;
- загрузка заметок;
- редактирование заметки;
- удаление заметки;
- сортировка и подсчёт записей.

### Команда запуска тестов

```bash
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.example.simplenotes.NoteDaoTest
```

### Имитация результата запуска тестов

```bash
> Task :app:connectedDebugAndroidTest
com.example.simplenotes.NoteDaoTest > insertNote_returnsId PASSED
com.example.simplenotes.NoteDaoTest > insertAndRetrieveNote PASSED
com.example.simplenotes.NoteDaoTest > getAllNotes_returnsInsertedNotes PASSED
com.example.simplenotes.NoteDaoTest > updateNote_changesContent PASSED
com.example.simplenotes.NoteDaoTest > deleteNote_removesFromDatabase PASSED
com.example.simplenotes.NoteDaoTest > deleteNote_decreasesCount PASSED
com.example.simplenotes.NoteDaoTest > getNotesCount_emptyDatabase PASSED
com.example.simplenotes.NoteDaoTest > getAllNotes_orderedByUpdatedDesc PASSED

BUILD SUCCESSFUL
```

<img width="1600" height="900" alt="screen_03_tests" src="https://github.com/user-attachments/assets/d872bcc1-3256-4e1c-9188-bd240d1ed3d1" />


## UI-тестирование

Для тестирования пользовательского интерфейса применялся фреймворк **Espresso**. Проверялись:
- отображение главного экрана;
- открытие формы создания заметки;
- создание заметки через UI;
- валидация пустого заголовка;
- открытие и редактирование существующей заметки;
- отображение пустого состояния.

### Команда запуска UI-тестов

```bash
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.example.simplenotes.NoteUITest
```

### Результаты запуска UI-тестов

```bash
> Task :app:connectedDebugAndroidTest
com.example.simplenotes.NoteUITest > mainScreen_displaysCorrectly PASSED
com.example.simplenotes.NoteUITest > fabClick_opensEditActivity PASSED
com.example.simplenotes.NoteUITest > createNote_fillFieldsAndSave PASSED
com.example.simplenotes.NoteUITest > createNote_emptyTitle_showsError PASSED
com.example.simplenotes.NoteUITest > clickNote_opensEditWithData PASSED
com.example.simplenotes.NoteUITest > editNote_updateTitle PASSED
com.example.simplenotes.NoteUITest > emptyState_showsMessage PASSED

BUILD SUCCESSFUL
```

## Профилирование в Android Profiler

В процессе разработки выполнялась фиксация потребления ресурсов устройства через **Android Profiler**.

По итогам наблюдений:
- приложение стабильно работает при базовых сценариях использования;
- потребление памяти остаётся в умеренных пределах;
- пиковая нагрузка возникает при запуске и открытии экранов редактирования;
- после завершения действий показатели возвращаются к стабильному уровню.

<img width="1550" height="804" alt="Снимок экрана 2026-04-23 в 21 57 44" src="https://github.com/user-attachments/assets/a301da70-9e13-468b-9ea0-2b19dbc2adb8" />





## Вывод

В результате разработки создано мобильное приложение **SimpleNotes** для хранения заметок с локальным сохранением данных. Реализованы все обязательные функции из технического задания: создание, редактирование, удаление и просмотр заметок, а также локальное хранение.


