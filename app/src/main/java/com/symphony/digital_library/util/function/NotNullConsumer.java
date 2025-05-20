package com.symphony.digital_library.util.function;

import androidx.annotation.NonNull;

/**
 * Загальний функціональний інтерфейс {@code NotNullConsumer}, який представляє дію,
 * що приймає один вхідний параметр, обов'язково ненульовий, і не повертає результату.
 *
 * <p>Цей інтерфейс є спеціалізованою версією {@link java.util.function.Consumer},
 * яка додатково гарантує, що переданий параметр ніколи не буде {@code null}, що
 * перевіряється на рівні анотацій.</p>
 *
 * @param <T> Тип вхідного параметра
 */
public interface NotNullConsumer<T> {

    /**
     * Виконує дію з вхідним параметром {@code it}.
     *
     * @param it Вхідний параметр, обов'язково ненульовий
     * @throws NullPointerException Якщо переданий параметр {@code null}
     */
    void accept(@NonNull T it);

}