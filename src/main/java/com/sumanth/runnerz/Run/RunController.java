package com.sumanth.runnerz.Run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/runs")
public class RunController {
    private final RunRepository runRepository;
    public RunController(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    @GetMapping("")
    List<Run> findAll() {
        return runRepository.findAll();
    }

    @GetMapping("/location/{location}")
    List<Run> findAllByLocation(@PathVariable String location) {
        return runRepository.findAllByLocation(location);
    }

    @GetMapping("/{id}")
    Run findById(@PathVariable Integer id) {

        Optional<Run> run = runRepository.findById(id);
        if (run.isEmpty()){
            throw new RunNotFoundException();
        }
        return run.get();
    }
    //Post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void create(@Valid @RequestBody Run run) {
        runRepository.save(run);

        }

    //Put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody Run run, @PathVariable Integer id){
        runRepository.save(run);
    }

    //Delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        runRepository.delete(runRepository.findById(id).get());
    }




}
