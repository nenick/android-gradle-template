package com.example.project.network.contact;

import android.content.Context;

import com.example.project.R;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URI;

@EBean
public class RootUrlInterceptor implements ClientHttpRequestInterceptor {

    @RootContext
    Context context;

    @Override
    public ClientHttpResponse intercept(final HttpRequest originalRequest, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        final URI uriWithRootUrl = prependRootUrl(originalRequest.getURI());
        return executeWithInterceptedUri(originalRequest, body, execution, uriWithRootUrl);
    }

    private ClientHttpResponse executeWithInterceptedUri(final HttpRequest originalRequest, byte[] body, ClientHttpRequestExecution execution, final URI uriWithRootUrl) throws IOException {
        HttpRequest interceptedRequest = new HttpRequest() {
            @Override
            public HttpHeaders getHeaders() {
                return originalRequest.getHeaders();
            }

            @Override
            public HttpMethod getMethod() {
                return originalRequest.getMethod();
            }

            @Override
            public URI getURI() {
                return uriWithRootUrl;
            }
        };
        return execution.execute(interceptedRequest, body);
    }

    private URI prependRootUrl(URI originalUri) {
        return URI.create("http://" + context.getResources().getString(R.string.const_wiremock_ip) + ":1337" + originalUri.toString());
    }
}
