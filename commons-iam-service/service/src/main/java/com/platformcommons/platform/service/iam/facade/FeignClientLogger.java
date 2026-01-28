package com.platformcommons.platform.service.iam.facade;

import feign.Logger;
import org.slf4j.LoggerFactory;
import feign.Request;
import feign.Response;

import java.io.IOException;

public class FeignClientLogger extends Logger {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FeignClientLogger.class);

    @Override
    protected void log(String configKey, String format, Object... args) {
        logger.info(String.format(methodTag(configKey) + format, args));
    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        if (logLevel.ordinal() >= Logger.Level.HEADERS.ordinal()) {
            logger.info(getCurlCommand(request));
        }
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        if (logLevel.ordinal() >= Logger.Level.HEADERS.ordinal()) {
            logger.info("Response HTTP/{} {}", response.status(), response.reason());
            response.headers().forEach((name, values) -> values.forEach(value -> logger.info("{}: {}", name, value)));
            logger.info(getCurlCommand(response.request()));
        }
        return super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);
    }

    private String getCurlCommand(Request request) {
        StringBuilder curlCommand = new StringBuilder("curl");
        curlCommand.append(" -X ").append(request.httpMethod().name());
        request.headers().forEach((name, values) -> values.forEach(value -> curlCommand.append(" -H '").append(name).append(": ").append(value).append("'")));
        curlCommand.append(" '").append(request.url()).append("'");
        if(request.body() != null)
            curlCommand.append(" -d '").append(new String(request.body())).append("'");
        return curlCommand.toString();
    }
}
