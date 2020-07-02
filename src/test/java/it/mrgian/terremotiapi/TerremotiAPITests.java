package it.mrgian.terremotiapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.mrgian.terremotiapi.model.Terremoto;
import it.mrgian.terremotiapi.utils.FileUtils;
import it.mrgian.terremotiapi.utils.TerremotiUtils;

class TerremotiAPITests {

	/**
	 * Effettua il test del parsing dei tweet. Effettua il parsing di una lista di
	 * tweet di esempio presente nel file twitterResponseExample.json e lo confronta
	 * con una lista di terremoti già parsati presente nel file
	 * terremotiExample.json
	 */
	@Test
	void testParsing() {
		try {
			String twitterResponse = FileUtils.readFile("/tests/twitterResponseExample.json", getClass());
			ArrayList<Terremoto> terremoti = TerremotiUtils.getTerremotiFromTwitterResponse(twitterResponse);
			String terremotiJson = new ObjectMapper().writeValueAsString(terremoti);
			String terremotiJsonExpected = FileUtils.readFile("/tests/terremotiExample.json", getClass());

			JsonNode node = new ObjectMapper().readTree(terremotiJson);
			JsonNode expectedNode = new ObjectMapper().readTree(terremotiJsonExpected);

			assertEquals(expectedNode, node);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Effettua il test delle statistiche. Calcola le statistiche di una lista di
	 * terremoti presente nel file terremotiExample.json e le confronta con le
	 * statistiche presenti nel file statsExample.json
	 */
	@Test
	void testStats() {
		try {
			String terremotiJson = FileUtils.readFile("/tests/terremotiExample.json", getClass());
			String statsJson = FileUtils.readFile("/tests/statsExample.json", getClass());

			Terremoto[] terremoti = new ObjectMapper().readValue(terremotiJson, Terremoto[].class);
			List<Terremoto> terremotiList = Arrays.asList(terremoti);

			JsonNode node = new ObjectMapper().readTree(statsJson);
			JsonNode expectedNode = new ObjectMapper().readTree(TerremotiUtils.getStatsTerremoti(terremotiList));

			assertEquals(expectedNode, node);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
