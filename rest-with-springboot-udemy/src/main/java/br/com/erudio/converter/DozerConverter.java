package br.com.erudio.converter;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class DozerConverter {

	public static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}
	
	public static <O, D> List<D> parseListObjects(List<O> listOrigin, Class<D> destination) {
		List<D> listDestination = new ArrayList<>();
		listOrigin.forEach(l -> listDestination.add(mapper.map(l, destination)));
		return listDestination;
	}
}
