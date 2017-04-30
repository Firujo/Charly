package com.android.charly.data;

import com.android.charly.utils.JsonConvertor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Headers;

/**
 * Created by Jdandrade on 4/30/2017.
 * This class represents an Http request made to the network (request+response)
 */

public class HttpRequest {

    private Long id;
    private Date date;
    private String url;
    private String method;

    //request
    private String requestHeaders;
    private String requestBody;

    //response
    private Integer responseCode;
    private String responseHeaders;
    private String responseBody;


    public HttpRequest() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Headers headers) {
        setRequestHeaders(toHttpHeaderList(headers));
    }

    public void setRequestHeaders(List<HttpHeader> headers) {
        requestHeaders = JsonConvertor.getInstance().toJson(headers);
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(String responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    private List<HttpHeader> toHttpHeaderList(Headers headers) {
        List<HttpHeader> httpHeaders = new ArrayList<>();
        for (int i = 0, count = headers.size(); i < count; i++) {
            httpHeaders.add(new HttpHeader(headers.name(i), headers.value(i)));
        }
        return httpHeaders;
    }
}
