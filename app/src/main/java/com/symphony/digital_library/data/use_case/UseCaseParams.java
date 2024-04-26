package com.symphony.digital_library.data.use_case;

public interface UseCaseParams<T, R> {
    R invoke(T item);
}
