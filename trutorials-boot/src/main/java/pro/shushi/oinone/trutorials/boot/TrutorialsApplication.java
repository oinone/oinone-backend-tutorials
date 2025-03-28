package pro.shushi.oinone.trutorials.boot;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StopWatch;
import pro.shushi.pamirs.framework.connectors.data.kv.RedisClusterConfig;
import pro.shushi.pamirs.meta.annotation.fun.extern.Slf4j;
import pro.shushi.pamirs.meta.api.ModelsHelper;
import pro.shushi.pamirs.meta.base.manager.data.CodeDataManager;
import pro.shushi.pamirs.meta.base.manager.data.FieldDataManager;
import pro.shushi.pamirs.meta.base.manager.data.IdDataManager;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@ComponentScan(
        basePackages = {
                "pro.shushi.pamirs",
                "pro.shushi.oinone"
        },
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        value = {RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class, RedisClusterConfig.class}
                )
        })
@Slf4j
@EnableTransactionManagement
@EnableAsync
@EnableDubbo
@MapperScan(value = "pro.shushi", annotationClass = Mapper.class)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, FreeMarkerAutoConfiguration.class})
public class TrutorialsApplication {

    public static void main(String[] args) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        new ApplicationPid().write(new File("oinone-trutorials.pid"));
        ConfigurableApplicationContext application = new SpringApplicationBuilder(TrutorialsApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
        stopWatch.stop();

        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        double totalTime = stopWatch.getTotalTimeSeconds();
        log.info("*****************************************************************************");
        log.info("*                                                                           *");
        log.info("*                                                                           *");
        log.info("* 启动成功，耗时 {} ", String.format("%.3f", totalTime) + "s,  Access URLs:");
        log.info("* Local:   http://localhost:" + port);
        log.info("* Network: http://" + ip + ":" + port);
        log.info("*                                                                           *");
        log.info("*                                                                           *");
        log.info("*****************************************************************************");
    }

}