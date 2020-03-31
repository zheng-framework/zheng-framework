package com.dadazhishi.zheng.metrics.example;

import com.dadazhishi.zheng.metrics.MetricsModule;
import com.dadazhishi.zheng.rest.resteasy.RestModule;
import com.dadazhishi.zheng.service.Run;
import com.dadazhishi.zheng.web.WebConfig;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import javax.inject.Inject;

public class MetricsModuleExample {

  public static void main(String[] args) throws Exception {
    final Injector injector = Guice.createInjector(
        new MetricsModule()
        , new RestModule()
        , new AbstractModule() {
          @Override
          protected void configure() {
//            bind(TestResource.class);
            bind(TestService.class).toInstance(new TestService());
            bind(WebConfig.class).toInstance(new WebConfig());
          }

          @Inject
          @Provides
          public TestResource testResource(TestService testService) {
            return new TestResource(testService);
          }
        }
    );

    // start services
    injector.getInstance(Run.class).start();
  }

}
