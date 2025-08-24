package dev.vinicius.EncurtaURL.application.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ObjectMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static <O, D> D parseObject (O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> D parseListObject(List<O> originList, Class<D> destination) {
        return mapper.map(originList, destination);
    }

}
