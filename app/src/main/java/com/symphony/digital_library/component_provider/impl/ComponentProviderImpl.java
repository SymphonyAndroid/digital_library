package com.symphony.digital_library.component_provider.impl;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.AppSchedulers;
import com.symphony.digital_library.component_provider.components.CacheWithAction;
import com.symphony.digital_library.component_provider.components.UseCases;
import com.symphony.digital_library.component_provider.impl.database.AppDatabase;
import com.symphony.digital_library.component_provider.impl.database.CacheWithActionImpl;
import com.symphony.digital_library.component_provider.impl.scheduler.SchedulersImpl;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.component_provider.impl.use_cases.UseCasesImpl;
import com.symphony.digital_library.data.database.dao.BaseDao;

import java.util.function.Function;

/**
 * Реалізація інтерфейсу {@link ComponentProvider}, яка забезпечує надання основних компонентів додатку.
 * <p>
 * Цей клас відповідає за створення та управління:
 * <ul>
 *     <li>Планувальниками потоків {@link AppSchedulers}.</li>
 *     <li>Сценаріями використання {@link UseCases}.</li>
 *     <li>Роботою з кешем через {@link CacheWithAction}.</li>
 *     <li>Реалізацією бази даних {@link Database}.</li>
 * </ul>
 * </p>
 */
public class ComponentProviderImpl implements ComponentProvider {

    /**
     * Контекст додатку для створення компонентів.
     */
    private final Context context;

    /**
     * Інстанс планувальників.
     */
    private AppSchedulers schedulers;

    /**
     * Інстанс бази даних.
     */
    private Database database;

    /**
     * Інстанс сценаріїв використання (use cases).
     */
    private UseCases useCases;

    /**
     * Конструктор для створення компонента провайдера.
     *
     * @param context Контекст, потрібний для створення компонентів додатку.
     */
    public ComponentProviderImpl(Context context) {
        this.context = context;
    }

    /**
     * Повертає екземпляр {@link AppSchedulers} для керування потоками у додатку.
     * <p>
     * Якщо планувальники ще не були створені, створюється новий екземпляр {@link SchedulersImpl}.
     * </p>
     *
     * @return Інстанс {@link AppSchedulers}.
     */
    @NonNull
    @Override
    public AppSchedulers getSchedulers() {
        if (schedulers == null) schedulers = new SchedulersImpl();
        return schedulers;
    }

    /**
     * Повертає екземпляр {@link UseCases} для реалізації сценаріїв використання.
     * <p>
     * Якщо сценарії використання ще не були створені, створюється новий екземпляр {@link UseCasesImpl}.
     * </p>
     *
     * @return Інстанс {@link UseCases}.
     */
    @NonNull
    @Override
    public UseCases getUseCases() {
        if (useCases == null) useCases = new UseCasesImpl(context);
        return useCases;
    }

    /**
     * Повертає екземпляр {@link CacheWithAction}, який підтримує роботу з кешованими даними.
     * <p>
     * Метод приймає функцію, що забезпечує доступ до DAO (Data Access Object) бази даних.
     * На основі цієї функції та бази даних створюється кеш.
     * </p>
     *
     * @param function Функція, що приймає базу даних і повертає DAO.
     * @param <D>      Тип DAO (дочірній клас {@link BaseDao}).
     * @param <R>      Тип сутності, яка обробляється DAO.
     * @return Інстанс {@link CacheWithAction}.
     */
    @NonNull
    @Override
    public <D extends BaseDao<R>, R> CacheWithAction<D, R> getCacheWithAction(Function<Database, D> function) {
        return new CacheWithActionImpl<>(function.apply(getDatabase()));
    }

    /**
     * Повертає екземпляр бази даних {@link Database}.
     * <p>
     * Якщо база даних ще не була ініціалізована, створюється новий екземпляр з використанням
     * бібліотеки Room. У разі потреби проводиться руйнівна міграція.
     * </p>
     *
     * @return Інстанс бази даних {@link Database}.
     */
    @NonNull
    private Database getDatabase() {
        if (database == null) database = Room
                .databaseBuilder(context, AppDatabase.class, "library")
                .fallbackToDestructiveMigration()
                .build();
        return database;
    }
}