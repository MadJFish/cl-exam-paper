package com.cupcake.learning.exampaper;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PatchModelMapper extends ModelMapper {

    public PatchModelMapper() {
        super();
        getConfiguration().setPropertyCondition(Conditions.isNotNull());
    }
}
