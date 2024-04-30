# Опис
Додаток "Електронна бібліотека".
Доступний функціонал:
- Перегляд доступних книжок
- Отримання книжок
- Здача книжок
- Перегляд книжок які отримав користувач

За замовчуванням бібліотека містить 100 книжок взятих з [Вікіпедії](https://uk.wikipedia.org/wiki/100_книг_століття_за_версією_«Ле-Монд»).

# Для тестування:
- доступна тут [app-debug.apk](https://drive.google.com/file/d/1bJ2Mek9fM86TgpK28FrSYChKpxh_rFNc/view?usp=sharing)

# Programming Principles
- ## DRY (Don`t repeat yourself)
  Повторення коду в програмі відсутнє
- ## KISS (Keep It Simple, Stupid)
  У програмі використано структури данних, зрозумілі та лаконічні назви класів, та їх структура має просту та зрозумілу реалізацію. Саме через це - код є простим та зрозумілим.
- ## YAGNI (You Ain't Gonna Need It)
  Програма не містить функціоналу, який не використовується
- ## SOLID
    - ### Single responsibility
      Класи дотримуються данного принципу. Наприклад, [`GetAllBooksUseCase`](app/src/main/java/com/symphony/digital_library/data/use_case/impl/GetAllBooksUseCase.java) має єдиний публічний метод [`invoke`](app/src/main/java/com/symphony/digital_library/data/use_case/impl/GetAllBooksUseCase.java#L16), успадкований від  [`UseCaseParams`](app/src/main/java/com/symphony/digital_library/data/use_case/UseCaseParams.java) та виконує лише одне завдання, а саме повертає перелік усіх книжок.
    - ### Open/closed
      Абстрактний клас [`BasePresenterImpl`](app/src/main/java/com/symphony/digital_library/mvp/base/BasePresenterImpl.java) дотримується цього принципу. [Дочірні класи](app/src/main/java/com/symphony/digital_library/mvp) розширюють його функціональність, не впливаючи на батьківські методи
    - ### Liskov substitution /Interface segregation
      Інтерфейс [`ComponentProvider`](app/src/main/java/com/symphony/digital_library/component_provider/ComponentProvider.java) та клас [`ComponentProviderImpl`](app/src/main/java/com/symphony/digital_library/component_provider/impl/ComponentProviderImpl.java) дотримуються цього принципу. Усі звернення до ComponentProvider можна замінити на зверненя до ComponentProviderImpl і навпаки. 
      Приклади звернення:
        - [`BasePresenterImpl`](app/src/main/java/com/symphony/digital_library/mvp/base/BasePresenterImpl.java#L44-L56)
        - [`FindBooksUseCase`](app/src/main/java/com/symphony/digital_library/data/use_case/impl/FindBooksUseCase.java#L18)
        - [`ReturnBookUseCase`](app/src/main/java/com/symphony/digital_library/data/use_case/impl/ReturnBookUseCase.java#L14)
    - ### Dependency inversion
      У програмі доступ до окремих компонентів реалізовано через інтерфейси. Завдяки чому, звертаючись до них, програма не прив’язується до конкретної реалізації, а лише до абстрактної сутності, яка описує їх функціональність.

      Приклади інтерфейсів:
        - [`ComponentProvider`](app/src/main/java/com/symphony/digital_library/component_provider/ComponentProvider.java)
        - [`AppSchedulers`](app/src/main/java/com/symphony/digital_library/component_provider/components/AppSchedulers.java)
        - [`UseCases`](app/src/main/java/com/symphony/digital_library/component_provider/components/UseCases.java)
        - [`CacheWithAction`](app/src/main/java/com/symphony/digital_library/component_provider/components/CacheWithAction.java)
        - [`BaseDao`](app/src/main/java/com/symphony/digital_library/data/database/dao/BaseDao.java)
        - [`UseCase`](app/src/main/java/com/symphony/digital_library/data/use_case/UseCase.java)
        - [`UseCaseParams`](app/src/main/java/com/symphony/digital_library/data/use_case/UseCaseParams.java)
        - [`BaseView`](app/src/main/java/com/symphony/digital_library/mvp/base/BaseMvp.java#L9) та всі його дочірні інтерфейси
        - [`BasePresenter`](app/src/main/java/com/symphony/digital_library/mvp/base/BaseMvp.java#L13) та всі його дочірні інтерфейси

      #### Приклад такої взаємодії
      ```
      @Override
      public UseCase<Single<List<Book>>> getAllBooks() {
      return new GetAllBooksUseCase();
      }
      ```
      ```
      private Disposable getAllBooks() {
      return getUseCases()
                .getAllBooks()
                .invoke()
                .observeOn(getSchedulers().ui())
                .subscribe(this::onResult, this::onError);
      }
      ```

      В методі [`getAllBooks()`](app/src/main/java/com/symphony/digital_library/mvp/splash/SplashPresenter.java#L20) описана логіка отримання всіх книжок.
      Для цьго викликається метод [`getUseCases().getAllBooks()`](app/src/main/java/com/symphony/digital_library/component_provider/impl/use_cases/UseCasesImpl.java#L35),
      який повертає інтерфейс `UseCase<Single<List<Book>>>`
      (цей інтерфейс описує, завдяки шаблонізації, метод `invoke()` має повернути об'єкт `Single<List<Book>>`).
      Під час виклику методу `getUseCases().getAllBooks()` створюється та повертається об’єкт [`GetAllBooksUseCase`](app/src/main/java/com/symphony/digital_library/component_provider/impl/use_cases/UseCasesImpl.java#L36),
      який реалізує цей інтерфейс. За допомогою цієї взаємодії, за необхідності можна замінити цей об’єкт(`GetAllBooksUseCase`) на будь-який інший, 
      але новий об’єкт має реалізувати інтерфейс `UseCase<Single<List<Book>>>` та повернути відповідне значення.
      Завдяки цьому це ніяким чином не вплине на метод `getAllBooks()` та він завжди буде повертати необхідне значення.


# Design Patterns
- ## MVP (Model-View-Presenter)
  Паттерн реалізовано в класах, розміщених у пакетах [`mvp`](app/src/main/java/com/symphony/digital_library/mvp), [`ui/fragment`](app/src/main/java/com/symphony/digital_library/ui/fragment) та [`ui/activity`](app/src/main/java/com/symphony/digital_library/ui/activity).
  Завдяки цьому паттерну ми можемо розділити бізнес-логіку (Presenter) додатку та користувацький інтерфейс (View), що робить наш проект більш гнучким та дозволяє з легкістю змінити або замінити частину коду не впливаючи один на одного.

- ## Use Case
  Реалізаця патерну знаходиться у пакеті [`data/use_case`](app/src/main/java/com/symphony/digital_library/data/use_case)
  ### Приклад:
  Клас [`FindBooksUseCase`](app/src/main/java/com/symphony/digital_library/data/use_case/impl/FindBooksUseCase.java)
  описує логіку отримання про книжок за пошуковим запитом користувача 

- ## Reactive Pattern та Observer Pattern
  Паттерни реалізовано за допомогою бібліотеки rxjava2, та використовується для асинхронності методів, та прослуховування результатів цих методів.
  Прикладом використання буде вже знайомий нам метод
     ```
    private Disposable getAllBooks() {
    return getUseCases()
                .getAllBooks()
                .invoke()
                .observeOn(getSchedulers().ui())
                .subscribe(this::onResult, this::onError);
      }
    ```
  У цьому прикладі ми підписуємось на подію результату запиту до бази данних, отриманий результат викликає метод `onResult` у якому ми вже реагуємо на подію. 
