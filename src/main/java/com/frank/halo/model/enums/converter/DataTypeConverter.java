package com.frank.halo.model.enums.converter;


import com.frank.halo.model.enums.DataType;

import javax.persistence.Converter;

/**
 * Data type converter.
 *
 * @author johnniang
 * @date 4/10/19
 */
@Converter(autoApply = true)
public class DataTypeConverter extends AbstractConverter<DataType, Integer> {

    public DataTypeConverter() {
        super(DataType.class);
    }
}
