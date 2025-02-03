package com.ps.back.service.util;


import com.ps.back.model.exception.JsonNullException;
import com.ps.back.model.pojos.consume.ConsumeJsonGeneric;

public class ValidUtils {

    public static void validateConsume(Object consume) {
        if (consume == null) {
            throw new JsonNullException("Json null or empty");
        }

        if (consume instanceof ConsumeJsonGeneric consumeJsonGeneric) {
            if (consumeJsonGeneric.getDatos() == null) {
                throw new JsonNullException("missing data in json");
            }
        }

    }
}
