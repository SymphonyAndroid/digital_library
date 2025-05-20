package com.symphony.digital_library.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.symphony.digital_library.R;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.databinding.FragmentSearchBinding;
import com.symphony.digital_library.mvp.search_user.SearchUserMvp;
import com.symphony.digital_library.mvp.search_user.SearchUserPresenter;
import com.symphony.digital_library.ui.adapter.UserAdapter;
import com.symphony.digital_library.ui.base.fragment.BaseFragmentMvp;
import com.symphony.digital_library.ui.dialog.BooksByUserDialog;
import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас {@code SearchUserFragment}, який представляє екран пошуку користувачів.
 * <p>
 * Реалізує MVP View через {@link SearchUserMvp.View}, щоб працювати з {@link SearchUserMvp.Presenter}.
 * Відображає список користувачів, показує книги, взяті користувачем, та обробляє повернення книг.
 * </p>
 */
public class SearchUserFragment
        extends BaseFragmentMvp<FragmentSearchBinding, SearchUserMvp.Presenter, SearchUserMvp.View>
        implements SearchUserMvp.View {

    /**
     * Адаптер, який використовується для відображення списку користувачів у {@link androidx.recyclerview.widget.RecyclerView}.
     */
    private UserAdapter adapter;

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
     * Повертає новий екземпляр {@link SearchUserPresenter}, який реалізує {@link SearchUserMvp.Presenter}.
     * </p>
     *
     * @return Інстанс Presenter для цього фрагменту.
     */
    @NonNull
    @Override
    protected SearchUserMvp.Presenter presenterInject() {
        return new SearchUserPresenter();
    }

    /**
     * Викликається після створення View для цього фрагменту.
     * <p>
     * Ініціалізує список користувачів (адаптер) та обробляє введення пошукового запиту.
     * </p>
     *
     * @param view Створене View для фрагменту.
     * @param savedInstanceState Збережений стан, якщо фрагмент відновлюється (може бути {@code null}).
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new UserAdapter(new ArrayList<>(), this::onUserClick);
        withBinding(binding -> {
            // Встановлення підказки для пошуку
            binding.searchTil.setHint(getString(R.string.search_user_hint));
            // Налаштування адаптера
            binding.recycler.setAdapter(adapter);
            // Обробка змін у текстовому полі пошуку
            ViewUtil.onTextChanged(binding.searchEt, query -> getPresenter().onQueryChanged(query));
        });
    }

    /**
     * Показує список користувачів на основі результатів пошуку.
     *
     * @param users Список користувачів, отриманих у результаті пошуку.
     */
    @Override
    public void showUsers(List<User> users) {
        adapter.updateItems(users);
    }

    /**
     * Відображає список книг, взятих обраним користувачем.
     *
     * @param books Список книг, взятих користувачем.
     */
    @Override
    public void updateBooks(List<Book> books) {
        BooksByUserDialog dialog = new BooksByUserDialog(requireContext())
                .setBooks(books)
                .onBookClick(this::onBookClick);
        dialog.show();
    }

    /**
     * Показує повідомлення про те, що у обраного користувача відсутні взяті книги.
     *
     * @param name Ім'я користувача.
     */
    @Override
    public void onEmptyBooks(String name) {
        String text = getString(R.string.book_taken_empty, name);
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show();
    }

    /**
     * Показує повідомлення про успішне повернення книги.
     *
     * @param name Ім'я користувача, який повернув книгу.
     * @param title Назва повернутої книги.
     */
    @Override
    public void onBookReturned(String name, String title) {
        String text = getString(R.string.book_returned, name, title);
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show();
    }

    /**
     * Обробляє подію кліку на книгу.
     * <p>
     * Викликає Presenter для виконання логіки повернення книги {@link Book}.
     * </p>
     *
     * @param book Вибрана книга.
     */
    private void onBookClick(Book book) {
        getPresenter().onReturnBook(book);
    }

    /**
     * Обробляє подію кліку на користувача.
     * <p>
     * Викликає Presenter для отримання списку книг, взятих користувачем.
     * </p>
     *
     * @param user Вибраний користувач.
     */
    private void onUserClick(User user) {
        getPresenter().onUserClick(user);
    }
}