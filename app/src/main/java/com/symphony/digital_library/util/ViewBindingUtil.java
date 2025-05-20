package com.symphony.digital_library.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.symphony.digital_library.R;

/**
 * Утилітний клас {@code ViewBindingUtil}, який надає корисні функціональні можливості для роботи з ViewBinding.
 * <p>
 * Цей клас включає методи для ідентифікації, збереження та пошуку ViewBinding у певному кореневому вигляді,
 * а також декілька інтерфейсів для інфлювання і прив’язки ViewBinding.
 * </p>
 */
public class ViewBindingUtil {

    /**
     * Створює унікальний ідентифікатор для ViewBinding на основі його класу і hash-коду.
     *
     * @param binding Інстанс {@link ViewBinding}, для якого створюється ідентифікатор
     * @param <VB> Тип, що розширює {@link ViewBinding}
     * @return Унікальний ідентифікатор {@link String}, створений для даного ViewBinding
     * @throws NullPointerException Якщо {@code binding} дорівнює {@code null}
     */
    public static <VB extends ViewBinding> String getId(@NonNull VB binding) {
        return binding.getClass().getName() +
                '@' +
                Integer.toHexString(System.identityHashCode(binding));
    }

    /**
     * Зберігає унікальний ідентифікатор ViewBinding як тег для кореневого елемента {@code View}.
     *
     * @param binding Інстанс {@link ViewBinding}, для якого створюється і зберігається ідентифікатор
     * @param <VB> Тип, що розширює {@link ViewBinding}
     * @return Унікальний ідентифікатор {@link String}, збережений у кореневому {@code View}
     * @throws NullPointerException Якщо {@code binding} дорівнює {@code null}
     */
    public static <VB extends ViewBinding> String saveViewBindingId(@NonNull VB binding) {
        String id = getId(binding);
        if (!PrimitivesUtil.isBlank(id)) binding.getRoot().setTag(R.id.tag_view_binding_root, id);
        return id;
    }

    /**
     * Шукає кореневий {@link View}, пов’язаний із заданим ідентифікатором ViewBinding.
     *
     * @param view Початковий {@link View}, з якого починається пошук
     * @param id Унікальний ідентифікатор ViewBinding
     * @return Знайдений {@link View}, пов’язаний із вказаним ідентифікатором, або {@code null}, якщо нічого не знайдено
     */
    @Nullable
    public static View findViewBindingRoot(@Nullable View view, @NonNull String id) {
        if (view == null || PrimitivesUtil.isBlank(id)) return null;

        for (View v : ViewUtil.allViews(view)) {
            Object tag = v.getTag(R.id.tag_view_binding_root);
            if (tag instanceof String && tag.equals(id)) return v;
        }

        return null;
    }

    /**
     * Інтерфейс для інфлювання {@link ViewBinding} без батьківського контейнера.
     *
     * @param <VB> Тип, що реалізує {@link ViewBinding}
     */
    public interface Inflater<VB extends ViewBinding> {

        /**
         * Інфлює {@link ViewBinding} без прив'язки до батьківського контейнера.
         *
         * @param inflater Інстанс {@link LayoutInflater}, необхідний для інфлювання
         * @return Інстанс {@link ViewBinding}, створений у результаті інфлювання
         */
        VB inflate(LayoutInflater inflater);
    }

    /**
     * Інтерфейс для інфлювання {@link ViewBinding} із прив'язкою до батьківського контейнера.
     *
     * @param <VB> Тип, що реалізує {@link ViewBinding}
     */
    public interface InflaterParent<VB extends ViewBinding> {

        /**
         * Інфлює {@link ViewBinding} із прив'язкою до батьківського контейнера.
         *
         * @param inflater Інстанс {@link LayoutInflater}, необхідний для інфлювання
         * @param parent Батьківський контейнер {@link ViewGroup}, до якого прив'язується ViewBinding
         * @param attachToParent {@code true}, якщо потрібно прив’язати до батьківського контейнера
         * @return Інстанс {@link ViewBinding}, створений у результаті інфлювання
         */
        VB inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent);
    }

    /**
     * Інтерфейс для прив'язки вже існуючого {@link View} до {@link ViewBinding}.
     *
     * @param <VB> Тип, що реалізує {@link ViewBinding}
     */
    public interface Bind<VB extends ViewBinding> {

        /**
         * Прив'язує вже існуючий {@link View} до {@link ViewBinding}.
         *
         * @param v Існуючий {@link View}, який необхідно прив'язати
         * @return Інстанс {@link ViewBinding}, прив'язаний до вказаного {@link View}
         */
        VB bind(View v);
    }
}