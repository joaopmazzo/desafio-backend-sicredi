 package br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.scheduler;

 import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.job.FinishSessoesJob;
 import jakarta.annotation.PostConstruct;
 import org.quartz.*;
 import org.springframework.context.annotation.Configuration;

@Configuration
public class FinishSessaoScheduler {

    private final Scheduler scheduler;

    public FinishSessaoScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void scheduleJob() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(FinishSessoesJob.class)
                .withIdentity("finishSessaoJob")
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("finishSessaoTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(1)
                        .repeatForever())
                .build();

        if (!scheduler.checkExists(jobDetail.getKey())) {
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

}
