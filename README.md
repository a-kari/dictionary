# О приложении

Приложение "Dictionary" - это толковый словарь английских слов.

---

# Возможности приложения

### 1. Глобальный словарь (Discover):

* Просмотр списка всех имеющихся на сервере слов (с пагинацией).
* Фильтрация слов по частоте употребления в речи и части речи.
* Просмотр слова и сохранение его в локальный словарь.

[![Discover](https://user-images.githubusercontent.com/31964210/85997851-e1085080-ba2b-11ea-8417-dc49ece27da0.png)](https://user-images.githubusercontent.com/31964210/85997851-e1085080-ba2b-11ea-8417-dc49ece27da0.png)
    
### 2. Локальный словарь (Home):

* Просмотр списка всех сохраненных локально слов.
* Удаление локальных слов в режиме редактирования.

[![Home](https://user-images.githubusercontent.com/31964210/85997863-e36aaa80-ba2b-11ea-85e6-7d051a1641f3.png)](https://user-images.githubusercontent.com/31964210/85997863-e36aaa80-ba2b-11ea-85e6-7d051a1641f3.png)

### 3. Просмотр слова (Word):

* Просмотр транскрипции слова, его значений, примеров использования, синонимов, антонимов, частоты употребления в речи.
* Возможность прослушать звучание слова в выбранном произношении и выбранным в настройках голосом.

[![Word](https://user-images.githubusercontent.com/31964210/85997859-e2d21400-ba2b-11ea-8273-897352d21d7b.png)](https://user-images.githubusercontent.com/31964210/85997859-e2d21400-ba2b-11ea-8273-897352d21d7b.png)

### 4. Поиск слова (Search):

* Поиск конкретного слова и добавление его в словарь.

[![Search](https://user-images.githubusercontent.com/31964210/85997854-e2397d80-ba2b-11ea-8416-c0fa09ebecbc.png)](https://user-images.githubusercontent.com/31964210/85997854-e2397d80-ba2b-11ea-8416-c0fa09ebecbc.png)
    
### 5. Настройки (Settings):

* Выбор произношения, с которым будут проговариваться слова (британское, американское, индийское, австралийское).
* Выбор тембра голоса, с которым будут проговариваться слова.

[![Settings](https://user-images.githubusercontent.com/31964210/85997845-df3e8d00-ba2b-11ea-95be-0b865b0aa7f3.png)](https://user-images.githubusercontent.com/31964210/85997845-df3e8d00-ba2b-11ea-95be-0b865b0aa7f3.png)

----

# Использованные технологии

* Kotlin + Coroutines
* Clean Architecture + MVVM
* Multimodule Gradle Project
* Dagger 2
* Retrofit
* Room Persistence Library
* Paging Library
* Unit testing: JUnit, Mockito, Robolectric

### Структура Gradle-модулей:

[![Gradle modules stucture](https://user-images.githubusercontent.com/31964210/86001084-39415180-ba30-11ea-8b0f-0ebfabc73735.png)](https://user-images.githubusercontent.com/31964210/86001084-39415180-ba30-11ea-8b0f-0ebfabc73735.png)

### Структура Dagger-компонентов:

[![Dagger components stucture](https://user-images.githubusercontent.com/31964210/85998274-668c0080-ba2c-11ea-9d26-65977a75e022.png)](https://user-images.githubusercontent.com/31964210/85998274-668c0080-ba2c-11ea-9d26-65977a75e022.png)
