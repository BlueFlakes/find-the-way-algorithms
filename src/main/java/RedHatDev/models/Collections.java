package RedHatDev.models;

import java.util.List;
import java.util.Optional;

public class Collections {
    public static <T> Optional<T> getLast(List<T> list) {
        int lastIndex = list.size() - 1;

        return list.size() == 0 ? Optional.empty()
                                : Optional.of(list.get(lastIndex));
    }
}
