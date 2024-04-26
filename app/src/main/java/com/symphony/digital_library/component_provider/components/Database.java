package com.symphony.digital_library.component_provider.components;

import com.symphony.digital_library.data.database.dao.BookDao;
import com.symphony.digital_library.data.database.dao.UserBookCrossRefDao;
import com.symphony.digital_library.data.database.dao.UserDao;

public interface Database {

    BookDao getBookDao();
    UserDao getUserDao();
    UserBookCrossRefDao getUserBookDao();

}
