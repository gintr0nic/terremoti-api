package it.mrgian.terremotiapi.exception;

import java.util.HashMap;

import it.mrgian.terremotiapi.utils.JsonUtils;

/**
 * Eccezione generata quando il filtro passato non è valido
 */
public class InvalidFilterException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidFilterException(String message) {
        super(message);
    }

    public String getJsonMessage() {
        HashMap<String, Object> errorMap = new HashMap<String, Object>();
        errorMap.put("errore", getMessage());
        return JsonUtils.mapToJson(errorMap);
    }
}