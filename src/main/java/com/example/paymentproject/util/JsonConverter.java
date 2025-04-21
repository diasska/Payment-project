package com.example.paymentproject.util;

import com.example.paymentproject.model.dto.CreatePaymentTransactionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JsonConverter {

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T toObject(String json ,Class<T> clazz)  {
        try{
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("JSON deserializing exception: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String toJson(Object obj)  {
        try{
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("JSON serializing exception: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
