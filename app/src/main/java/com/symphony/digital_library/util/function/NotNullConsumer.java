package com.symphony.digital_library.util.function;

import androidx.annotation.NonNull;

public interface NotNullConsumer<T> {
    void accept(@NonNull T it);

}
