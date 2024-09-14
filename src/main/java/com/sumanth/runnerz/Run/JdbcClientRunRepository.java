package com.sumanth.runnerz.Run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientRunRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcClientRunRepository.class);
    private final JdbcClient jdbcClient;

    public JdbcClientRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;

    }

    public List<Run> findAll() {
        return jdbcClient.sql("select * from run")
                .query(Run.class)
                .list();


    }
    public Optional<Run> findById(Integer id){
        return jdbcClient.sql("select id, title, started_on, completed_on, miles, location FROM Run WHERE id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();

    }
    public void create(Run run){
        var updated = jdbcClient.sql("INSERT INTO RUN(id, title, started_on, completed_on, miles, location) values(?,?,?,?,?,?)")
                .params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString()))
                .update();
        Assert.state(updated == 1, "Run not created" + run.title());;

    }
    public void update(Run run, Integer id){
        var updated = jdbcClient.sql("UPDATE RUN SET title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? WHERE id = ?")
                .params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(), id))
                .update();
        Assert.state(updated == 1, "Run not updated" + run.title());;

    }
    public void delete(Integer id){
        var updated = jdbcClient.sql("DELETE FROM RUN WHERE id = ?")
                .params(List.of(id))
                .update();
        Assert.state(updated == 1, "Run not deleted" );;

    }

    public void saveAll(List<Run> runs) {
        runs.stream().forEach(this::create);
    }
    public int count(){ return jdbcClient.sql("select * from run").query().listOfRows().size();}
}












//    private List<Run> runs = new ArrayList<>();
//
//    List<Run> findAll(){
//        return runs;
//    }
//    Optional<Run> findById(Integer id){
//        return runs.stream()
//                .filter(run -> run.id().equals(id))
//                .findFirst();
//    }
//    void create( Run run){
//        runs.add(run);
//    }
//    void update(Run run, Integer id){
//        Optional<Run> existingRun = findById(id);
//        if(existingRun.isPresent()){
//            runs.set(runs.indexOf(existingRun.get()), run);
//        }
//    }
//
//    void delete(Integer id){
//        runs.removeIf(run -> run.id().equals(id));
//    }
//
//    @PostConstruct
//    private void init(){
//        runs.add(new Run(1, "First Run", LocalDateTime.now(), LocalDateTime.now().plus(30, ChronoUnit.HOURS), 3, Location.OUTDOOR));
//        runs.add(new Run(2, "Second Run", LocalDateTime.now(), LocalDateTime.now().plus(60, ChronoUnit.HOURS), 5, Location.INDOOR));
//    }




