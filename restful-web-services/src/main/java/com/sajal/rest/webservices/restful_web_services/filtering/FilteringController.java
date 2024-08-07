package com.sajal.rest.webservices.restful_web_services.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public MappingJacksonValue filtering(){
        SomeBean someBean = new SomeBean("value1", "value2", "value3");

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);

        SimpleBeanPropertyFilter filters = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field3");

        FilterProvider filter = new SimpleFilterProvider().addFilter("SomeBeanFilter",filters);

        mappingJacksonValue.setFilters(filter);

        return mappingJacksonValue;
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue filteringList(){

        List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3"),new SomeBean("value4", "value5", "value6"));

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);

        SimpleBeanPropertyFilter filters = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");

        FilterProvider filter = new SimpleFilterProvider().addFilter("SomeBeanFilter",filters);

        mappingJacksonValue.setFilters(filter);

        return mappingJacksonValue;
    }

}
