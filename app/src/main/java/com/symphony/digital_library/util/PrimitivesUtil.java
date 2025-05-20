package com.symphony.digital_library.util;

import java.util.function.Predicate;

/**
 * Утилітний клас {@code PrimitivesUtil}, який містить корисні методи для роботи з базовими
 * примітивними типами або їх обгортками, як-от {@link CharSequence}.
 */
public class PrimitivesUtil {

    /**
     * Перевіряє, чи даний рядок {@link CharSequence} є пустим або таким, що складається лише з пробільних символів.
     *
     * @param value Рядок {@link CharSequence}, який потрібно перевірити
     * @return {@code true}, якщо рядок пустий або складається лише з пробілів;
     *         {@code false} в іншому випадку.
     * @throws NullPointerException Якщо {@code value} дорівнює {@code null}.
     */
    public static boolean isBlank(CharSequence value) {
        return value.length() == 0 || all(value, Character::isWhitespace);
    }

    /**
     * Перевіряє, чи всі символи в даному рядку {@link CharSequence} задовольняють вказаному предикату.
     *
     * @param value Рядок {@link CharSequence}, який потрібно перевірити
     * @param predicate Умова {@link Predicate}, яка буде перевіряти кожен символ
     * @return {@code true}, якщо всі символи задовольняють предикату,
     *         або рядок є пустим; {@code false} в іншому випадку.
     * @throws NullPointerException Якщо {@code value} або {@code predicate} дорівнює {@code null}.
     */
    public static boolean all(CharSequence value, Predicate<Character> predicate) {
        if (value.length() == 0) return true;
        for (int i = 0; i < value.length(); i++) {
            if (!predicate.test(value.charAt(i))) return false;
        }
        return true;
    }
}