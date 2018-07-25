package com.simplaex.clients.druid;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.druid.jackson.DefaultObjectMapper;
import lombok.*;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DruidClientConfig {

  @Getter
  private final String host;
  private final Integer port;
  private final ObjectMapper objectMapper;

  private final Supplier<ExecutorService> executorServiceFactory;
  private final ExecutorService executorService;

  private final DruidClient.EventEmitter eventEmitter;

  private final Integer numConnections;

  public ObjectMapper getObjectMapper() {
    if (objectMapper == null) {
      return new DefaultObjectMapper();
    } else {
      return objectMapper;
    }
  }

  @Nonnull
  public ExecutorService getExecutorService() {
    if (executorService == null) {
      if (executorServiceFactory == null) {
        return Executors.newWorkStealingPool();
      }
      return executorServiceFactory.get();
    }
    return executorService;
  }

  @Nonnull
  public DruidClient.EventEmitter getEventEmitter() {
    if (eventEmitter == null) {
      return __ -> {
      };
    }
    return eventEmitter;
  }

  @Nonnegative
  public int getPort() {
    return port != null && port > 0 ? port : 8080;
  }

  @Nonnegative
  public int getNumConnections() {
    return numConnections != null && numConnections > 0 ? numConnections : 1;
  }
}
