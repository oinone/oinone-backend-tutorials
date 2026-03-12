package pro.shushi.oinone.tutorials.boot;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StopWatch;
import pro.shushi.pamirs.meta.annotation.fun.extern.Slf4j;

import java.util.Arrays;

/**
 * Oinone Tutorials Boot
 *
 * @author oinone on 2026-03-04 00:00:00
 */
@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, FreeMarkerAutoConfiguration.class})
@MapperScan(value = {"pro.shushi.pamirs", "pro.shushi.oinone"}, annotationClass = Mapper.class)
@ComponentScan(
        basePackages = {
                "pro.shushi.pamirs",
                "pro.shushi.oinone"
        },
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        value = {
                                RedisAutoConfiguration.class,
                                RedisRepositoriesAutoConfiguration.class,
                                RedisReactiveAutoConfiguration.class,
                        }
                ),
        }
)
@EnableTransactionManagement
@EnableAsync
@EnableDubbo
public class TutorialsApplication {

    public static void main(String[] args) {
        log.info(Arrays.toString(args));

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        log.info("Oinone Tutorials App Starting ...");

        System.setProperty("dubbo.application.logger", "slf4j");

        new SpringApplicationBuilder(TutorialsApplication.class)
                .web(WebApplicationType.SERVLET)
                .listeners(
                        new ApplicationPidFileWriter("oinone-tutorials-boot.pid")
                )
                .run(args);

        stopWatch.stop();

        double totalTime = stopWatch.getTotalTimeSeconds();
        log.info("*****************************************************************************");
        log.info("*                                                                           *");
        log.info("*                                                                           *");
        log.info("* 启动成功，耗时 {} ", String.format("%.3f", totalTime) + "s");
        log.info("*                                                                           *");
        log.info("*                                                                           *");
        log.info("*****************************************************************************");
    }

}