package ru.testfield.tags.service.packer;

import java.util.List;
import java.util.Optional;

public interface Packer<T> {

    /**
     * Accept value for further packing.
     *
     * @return true if added successfully, false otherwise.
     */
    boolean addValue(T value);

    /**
     * Pack and return all available values.
     *
     * @return Optional, containing list with all available values, or empty optional if no values available.
     */
    Optional<List<T>> getPack();

    /**
     * Pack of size maxNumber, or less if maxNumber is not available.
     *
     * @param maxNumber - maximum number of elements to return.
     * @return Optional, containing list with packed values, or empty if maxNumber less then 1, or empty optional if
     * no values available.
     */
    Optional<List<T>> getPack(int maxNumber);
}
