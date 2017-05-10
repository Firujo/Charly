# Charly
## ISCTE-IUL Computer Engineering Masters
### Technologies for Operational Information Systems

Made by Filipe Gonçalves & João Andrade

Under supervision of Prof. Carlos Costa, PhD.

## Android OkHttp Debugger 
This http debugger consists in an Interceptor implementation from the OkHttp lib.

code example:
```
OkHttpClient defaultHttpClient =
            new OkHttpClient.Builder()
              .addInterceptor(new CharlyInterceptor(context))
              .build();
```
            
