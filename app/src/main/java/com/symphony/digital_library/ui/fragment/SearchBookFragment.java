package com.symphony.digital_library.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.symphony.digital_library.R;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.databinding.FragmentSearchBinding;
import com.symphony.digital_library.mvp.search_book.SearchBookMvp;
import com.symphony.digital_library.mvp.search_book.SearchBookPresenter;
import com.symphony.digital_library.ui.adapter.BookAdapter;
import com.symphony.digital_library.ui.base.fragment.BaseFragmentMvp;
import com.symphony.digital_library.ui.dialog.TakeBookBottomDialog;
import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас {@code SearchBookFragment}, який представляє екран пошуку книг.
 * <p>
 * Реалізує MVP View через {@link SearchBookMvp.View}, щоб працювати з {@link SearchBookMvp.Presenter}.
 * Відображає список книг, дозволяє вибрати книгу та обробляє події, пов'язані з книгою.
 * </p>
 */
public class SearchBookFragment
        extends BaseFragmentMvp<FragmentSearchBinding, SearchBookMvp.Presenter, SearchBookMvp.View>
        implements SearchBookMvp.View {

    /**
     * Адаптер, який використовується для відображення списку книг у {@link androidx.recyclerview.widget.RecyclerView}.
     */
    private BookAdapter adapter;

    /**
     * Діалог для введення імені користувача та телефону під час вибору книги.
     */
    private TakeBookBottomDialog dialog;

    /**
     * Надає функцію для створення об'єкта {@link FragmentSearchBinding} через метод інфлювання.
     *
     * @return Інстанс {@link ViewBindingUtil.InflaterParent}, який інфлятує {@link FragmentSearchBinding}.
     */
    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentSearchBinding> inflater() {
        return FragmentSearchBinding::inflate;
    }

    /**
     * Ін'єктує Presenter для цього фрагменту.
     * <p>
     * Повертає новий екземпляр {@link SearchBookPresenter}, який реалізує {@link SearchBookMvp.Presenter}.
     * </p>
     *
     * @return Інстанс Presenter для цього фрагменту.
     */
    @NonNull
    @Override
    protected SearchBookMvp.Presenter presenterInject() {
        return new SearchBookPresenter();
    }

    /**
     * Викликається після створення View для цього фрагменту.
     * <p>
     * Налаштовує адаптер для RecyclerView та обробляє події введення пошукового запиту.
     * </p>
     *
     * @param view Створене View для фрагменту.
     * @param savedInstanceState Збережений стан, якщо фрагмент відновлюється (може бути {@code null}).
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new BookAdapter(new ArrayList<>(), this::onBookClick, true);
        withBinding(binding -> {
            // Встановлення підказки для пошуку
            binding.searchTil.setHint(getString(R.string.search_book_hint));
            // Налаштування адаптера
            binding.recycler.setAdapter(adapter);
            // Обробка змін у текстовому полі пошуку
            ViewUtil.onTextChanged(binding.searchEt, query -> getPresenter().onQueryChanged(query));
        });
    }

    /**
     * Показує результати пошуку книг у списку.
     *
     * @param result Список книг, отриманих у результаті пошуку.
     */
    @Override
    public void showResult(List<Book> result) {
        adapter.updateItems(result);
    }

    /**
     * Обробляє подію кліку на книгу.
     * <p>
     * Відкриває {@link TakeBookBottomDialog}, який дозволяє ввести ім'я користувача та телефон при виборі книги.
     * </p>
     *
     * @param book Вибрана книга.
     */
    private void onBookClick(Book book) {
        dialog = new TakeBookBottomDialog(requireContext());
        dialog
                .onNameChanged(name -> getPresenter().onNameChanged(name)) // Обробка введення імені
                .onPhoneChanged(phone -> getPresenter().onPhoneChanged(phone, book)) // Обробка введення телефону
                .show();
    }

    /**
     * Встановлює телефон користувача в діалог {@link TakeBookBottomDialog}.
     *
     * @param phone Номер телефону, який потрібно відобразити.
     */
    @Override
    public void showUserPhone(String phone) {
        dialog.setPhone(phone);
    }

    /**
     * Відображає повідомлення про успішне взяття книги.
     * <p>
     * Закриває діалог і показує Toast із повідомленням.
     * </p>
     *
     * @param userName Ім'я користувача, який взяв книгу.
     * @param bookName Назва взятої книги.
     */
    @Override
    public void onTakeSuccess(String userName, String bookName) {
        dialog.dismiss();
        String text = getString(R.string.book_taken, userName, bookName);
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show();
    }

    /**
     * Відображає або приховує ProgressBar у діалозі {@link TakeBookBottomDialog}.
     *
     * @param isVisible {@code true}, якщо ProgressBar має бути видимим; {@code false}, якщо прихованим.
     */
    @Override
    public void showProgressBar(boolean isVisible) {
        dialog.showProgressBar(isVisible);
    }

}