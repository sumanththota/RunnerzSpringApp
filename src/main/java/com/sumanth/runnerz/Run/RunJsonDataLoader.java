package com.sumanth.runnerz.Run;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
@Component
public class RunJsonDataLoader implements CommandLineRunner {
    private final static Logger log = LoggerFactory.getLogger(RunJsonDataLoader.class);

    private final JdbcClientRunRepository runRepository;
    private final ObjectMapper objectMapper;

    public RunJsonDataLoader(JdbcClientRunRepository runRepository, ObjectMapper objectMapper) {
        this.runRepository = runRepository;
        this.objectMapper = objectMapper;
    }


    @Override
    public void run(String... args) throws Exception {
        if(runRepository.count() == 0){
            try(InputStream inputstream = TypeReference.class.getResourceAsStream("/data/runs.json")){
                Runs allRuns = objectMapper.readValue(inputstream, Runs.class);
                runRepository.saveAll(allRuns.runs());
                log.info("Loaded {} runs from JSON file", allRuns.runs().size());
        }   catch (IOException e){
                log.error("Failed to load JSON file", e);
            }
        }


    }
}
