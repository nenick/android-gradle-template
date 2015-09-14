[Back to Index](../index.md)

# Mocking REST Service

http://wiremock.org/running-standalone.html

### NoSuchMethodError on Java 7

Latest Wiremock versions needs Java 8. For Java 7 we need older wiremock version. Or you will see errors like:

    ava.lang.NoSuchMethodError: java.util.concurrent.ConcurrentHashMap.keySet()Ljava/util/concurrent/ConcurrentHashMap$KeySetView;
        at com.github.tomakehurst.wiremock.global.ThreadSafeRequestDelayControl.cancelAllDelays(ThreadSafeRequestDelayControl.java:51)
        at com.github.tomakehurst.wiremock.global.ThreadSafeRequestDelayControl.clearDelay(ThreadSafeRequestDelayControl.java:38)
        at com.github.tomakehurst.wiremock.core.WireMockApp.resetMappings(WireMockApp.java:141)
        at com.github.tomakehurst.wiremock.core.WireMockApp.resetToDefaultMappings(WireMockApp.java:151)
        at com.github.tomakehurst.wiremock.admin.ResetToDefaultMappingsTask.execute(ResetToDefaultMappingsTask.java:26)
        at com.github.tomakehurst.wiremock.http.AdminRequestHandler.handleRequest(AdminRequestHandler.java:55)
        at com.github.tomakehurst.wiremock.http.AbstractRequestHandler.handle(AbstractRequestHandler.java:38)
        at com.github.tomakehurst.wiremock.jetty6.Jetty6HandlerDispatchingServlet.service(Jetty6HandlerDispatchingServlet.java:98)

---

[Back to Index](../index.md)