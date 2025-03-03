package co.com.pragma.api;

import co.com.pragma.api.handlers.BranchHandler;
import co.com.pragma.api.handlers.FranchiseHandler;
import co.com.pragma.api.handlers.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFranchiseFunction(FranchiseHandler franchiseHandler) {
        return route(POST("/franchise"), franchiseHandler::save)

                ;
    }

    @Bean
    public RouterFunction<ServerResponse> routerBranchFunction(BranchHandler branchHandler) {
        return route(POST("/branch"), branchHandler::save)

                ;
    }

    @Bean
    public RouterFunction<ServerResponse> routerProductFunction(ProductHandler productHandler) {
        return route(POST("/product"), productHandler::save)
                .andRoute(GET("/product/{id}"), productHandler::delete)
                .andRoute(PATCH("/product-stock"), productHandler::updateStock)

                ;
    }

}
