# SimpleNotes

Мобильное Android-приложение для лабораторных работ №11–12 по дисциплине «Методы тестирования и отладки ПО».

## Описание

SimpleNotes — приложение для локального хранения заметок. Пользователь может создавать, редактировать, удалять и просматривать заметки. Архитектура проекта построена по MVVM и использует Room Database, LiveData и Kotlin Coroutines.

## Стек

- Kotlin 1.9
- Min SDK 26
- Target SDK 34
- Room Database (SQLite)
- MVVM
- LiveData
- Kotlin Coroutines + Flow
- XML Layouts
- RecyclerView
- Material Design 3
- JUnit 4 / AndroidX Test
- Espresso

## Реализованный функционал

- список заметок в `RecyclerView`
- сортировка по `updatedAt DESC`
- создание новой заметки
- редактирование существующей заметки
- удаление по swipe left через `ItemTouchHelper`
- сообщение пустого состояния
- превью содержимого (первые 50 символов)
- отображение даты обновления
- локальное хранение данных в Room

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
│   └── profiling-results.md
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Запуск

1. Открыть проект в Android Studio.
2. Дождаться `Gradle Sync`.
3. Запустить проект на эмуляторе или устройстве Android (API 26+).

> В репозитории добавлены `gradlew` и `gradle-wrapper.properties`

## Тесты

### DAO / Room

```bash
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.example.simplenotes.NoteDaoTest
```

### UI / Espresso

```bash
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.example.simplenotes.NoteUITest
```


