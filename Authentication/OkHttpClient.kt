private fun okhttpClient(context: Context): OkHttpClient {

    //HTTP Logging
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY


    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(
            AuthInterceptor(
                context
            )
        )
        .followRedirects(false)
        .writeTimeout(20, TimeUnit.SECONDS)
        .authenticator(
            TokenAuthenticator(
                context
            )
        )
        .build()

}

private fun authClient(): OkHttpClient {

    //The context is required if such interceptors/ authenticators are added
    return OkHttpClient.Builder()
        .followRedirects(false)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()
}
