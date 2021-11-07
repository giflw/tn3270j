package com.itquasar.multiverse.tn3270j.status;

import java.util.Arrays;

public interface StatusCode {

    String code();

    static <S extends StatusCode> S fromCode(S[] values, String code) {
        return Arrays.stream(values).filter(it -> it.code().equals(code)).findFirst().get();
    }
}
