package pro.shushi.oinone.trutorials.boot;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 在springboot项目中部署vue打包的dist以及刷新遇到404的解决方法
 * https://www.cnblogs.com/woju/p/17646002.html
 */
@Component
public class ErrorConfig implements ErrorPageRegistrar {
 
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
        registry.addErrorPages(error404Page);
    }
}