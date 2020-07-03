package it.mrgian.terremotiapi.controller;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.mrgian.terremotiapi.model.Terremoto;
import it.mrgian.terremotiapi.utils.TerremotiUtils;
import it.mrgian.terremotiapi.webclient.TwitterWebClient;
import it.mrgian.terremotiapi.webclient.config.TwitterWebClientConfig;

/**
 * Controller che gestisce le richieste alla API.
 * 
 * @author Gianmatteo Palmier
 */
@RestController
public class TerremotiController {
    @Autowired
    TwitterWebClient twitterWebClient;

    /**
     * Inizializza il web client usato per ricevere i dati.
     */
    @PostConstruct
    public void init() {
        TwitterWebClientConfig config = new TwitterWebClientConfig();
        twitterWebClient = new TwitterWebClient(config);
    }

    /**
     * Gestisce le richieste GET alla rotta "/terremoti". Restituisce in formato
     * JSON le informazioni sui terremoti degli ultimi 7 giorni.
     * 
     * @return Informazioni sui terremoti in formato JSON
     */
    @RequestMapping(value = "/terremoti", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getTerremoti() {
        return new ResponseEntity<>(twitterWebClient.getLatestTerremoti(), HttpStatus.OK);
    }

    @RequestMapping(value = "/terremoti", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> getFilteredTerremoti(@RequestBody(required = false) String filter) {
        return new ResponseEntity<>(twitterWebClient.getLatestFilteredTerremoti(filter), HttpStatus.OK);
    }

    /**
     * Gestisce le richieste GET alla rotta "/terremoti/stats". Restituisce in
     * formato JSON le statistiche sui terremoti degli ultimi 7 giorni.
     * 
     * @return Statistiche sui terremoti in formato JSON
     */
    @RequestMapping(value = "/terremoti/stats", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getStatsTerremoti() {
        return new ResponseEntity<>(twitterWebClient.getStatsLatestTerremoti(), HttpStatus.OK);
    }

    /**
     * Gestisce le richieste GET alla rotta "/terremoti/metadata"
     * 
     * @return metadata dell'oggetto {@link it.mrgian.terremotiapi.model.Terremoto}
     *         in formato JSON
     */
    @RequestMapping(value = "/terremoti/metadata", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getMetadata() {
        return new ResponseEntity<Object>(TerremotiUtils.getMetadata(), HttpStatus.OK);
    }

}