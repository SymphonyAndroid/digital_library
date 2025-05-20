package com.symphony.digital_library.ui.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.symphony.digital_library.R;
import com.symphony.digital_library.databinding.DialogTakeBookBinding;
import com.symphony.digital_library.ui.base.dialog.BaseBottomSheetDialog;
import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.ViewUtil;
import com.symphony.digital_library.util.function.NotNullConsumer;

/**
 * Клас {@code TakeBookBottomDialog}, який реалізує діалог для введення імені користувача та його телефону.
 */
public class TakeBookBottomDialog extends BaseBottomSheetDialog<DialogTakeBookBinding> {

    /**
     * Логічний прапорець, що визначає, чи відображається екран введення імені користувача.
     */
    private boolean isNameStep = true;

    /**
     * Callback-функція для обробки змін імені.
     */
    private NotNullConsumer<String> onNameChanged = it -> {};

    /**
     * Callback-функція для обробки змін телефону.
     */
    private NotNullConsumer<String> onPhoneChanged = it -> {};

    /**
     * Конструктор для створення діалогу.
     *
     * @param context Контекст, у якому створюється діалог.
     */
    public TakeBookBottomDialog(@NonNull Context context) {
        super(context, 0);
    }

    /**
     * Повертає Inflater для створення об'єкта {@link DialogTakeBookBinding}.
     *
     * @return Інстанс {@link ViewBindingUtil.Inflater}.
     */
    @NonNull
    @Override
    protected ViewBindingUtil.Inflater<DialogTakeBookBinding> inflater() {
        return DialogTakeBookBinding::inflate;
    }

    /**
     * Повертає Bind-функцію для прив'язки {@link DialogTakeBookBinding} до існуючого View.
     *
     * @return Інстанс {@link ViewBindingUtil.Bind}.
     */
    @NonNull
    @Override
    protected ViewBindingUtil.Bind<DialogTakeBookBinding> bind() {
        return DialogTakeBookBinding::bind;
    }

    /**
     * Встановлює callback для обробки змін імені.
     *
     * @param onNameChanged Callback-функція, яка приймає змінений текст імені.
     * @return Поточний інстанс {@link TakeBookBottomDialog} (для ланцюжкового виклику методів).
     */
    public TakeBookBottomDialog onNameChanged(@NonNull NotNullConsumer<String> onNameChanged) {
        this.onNameChanged = onNameChanged;
        return this;
    }

    /**
     * Встановлює callback для обробки змін телефону.
     *
     * @param onPhoneChanged Callback-функція, яка приймає змінений текст телефону.
     * @return Поточний інстанс {@link TakeBookBottomDialog} (для ланцюжкового виклику методів).
     */
    public TakeBookBottomDialog onPhoneChanged(@NonNull NotNullConsumer<String> onPhoneChanged) {
        this.onPhoneChanged = onPhoneChanged;
        return this;
    }

    /**
     * Викликається під час відображення діалогу.
     * <p>
     * Налаштовує кнопки та обробники тексту для управління введенням імені та телефону.
     * </p>
     *
     * @param binding Ініціалізований об'єкт {@link DialogTakeBookBinding}.
     */
    @Override
    protected void onShow(@NonNull DialogTakeBookBinding binding) {
        super.onShow(binding);
        withBinding(vb -> {
            // Налаштування кнопки "ОК"
            vb.okBtn.setOnClickListener(v -> {
                String text = vb.editText.getText() == null ? "" : vb.editText.getText().toString();
                onOkClick(text);
            });

            // Динамічна активація кнопки на основі введеного тексту
            ViewUtil.onTextChanged(binding.editText, text -> {
                vb.okBtn.setEnabled(!text.isEmpty());
            });
        });
    }

    /**
     * Обробляє натискання кнопки "ОК" залежно від поточного етапу введення.
     * <p>
     * На етапі введення імені змінюється на етап введення телефону, або виконується callback на телефон.
     * </p>
     *
     * @param text Введене ім'я або телефон.
     */
    private void onOkClick(String text) {
        withBinding(vb -> {
            if (isNameStep) {
                // Обробка імені
                onNameChanged.accept(text);
                isNameStep = false;
                // Змінюється підказка на полі вводу
                vb.til.setHint(getContext().getString(R.string.phone_hint));
            } else {
                // Обробка телефону
                onPhoneChanged.accept(text);
            }
        });
    }

    /**
     * Встановлює текст телефону у відповідне текстове поле.
     *
     * @param phone Текст телефону, який потрібно встановити.
     */
    public void setPhone(String phone) {
        withBinding(vb -> vb.editText.setText(phone));
    }

    /**
     * Відображає або приховує ProgressBar у діалозі.
     *
     * @param isVisible {@code true}, якщо ProgressBar має бути видимим; {@code false} – якщо прихованим.
     */
    public void showProgressBar(boolean isVisible) {
        withBinding(vb -> {
            if (isVisible) {
                vb.progressBar.setVisibility(View.VISIBLE);
            } else {
                vb.progressBar.setVisibility(View.GONE);
            }
        });
    }
}