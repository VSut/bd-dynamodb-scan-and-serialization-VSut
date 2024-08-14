package com.amazon.ata.dynamodbscanandserialization.icecream.converter;

import com.amazon.ata.dynamodbscanandserialization.icecream.exception.SundaeSerializationException;
import com.amazon.ata.dynamodbscanandserialization.icecream.model.Sundae;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class SundaeConverter implements DynamoDBTypeConverter<String, List<Sundae>> {
    private ObjectMapper mapper;
    public SundaeConverter() {
        mapper = new ObjectMapper();
    }
    public String convert(List<Sundae> sundaes) {
        if (sundaes == null) {
            return "";
        }
        String jsonSundaes;
        try {
            jsonSundaes = mapper.writeValueAsString(sundaes);
        } catch (Exception e) {
            throw new SundaeSerializationException(e.getMessage(), e);
        }
        return jsonSundaes;
    }
    public List<Sundae> unconvert (String json) {
        List<Sundae> sundaes = new ArrayList<>();
        if (json.isBlank()) {
            return sundaes;
        }
        try {
            sundaes = mapper.readValue(json, new TypeReference<List<Sundae>>(){});
        } catch (Exception e) {
            throw new SundaeSerializationException(e.getMessage(), e);
        }
        return sundaes;
    }
}
