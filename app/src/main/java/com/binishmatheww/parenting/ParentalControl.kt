package com.binishmatheww.parenting

import android.app.Application
import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*

class ParentalControl : Application() {

    companion object{

        private lateinit var appInstance : ParentalControl

        fun instance() : ParentalControl {

            return if (this::appInstance.isInitialized){
                appInstance
            }
            else{
                appInstance = ParentalControl()
                appInstance
            }

        }

    }

    private val LOCK = Any()

    //Http client
    private var client : HttpClient? = null


    fun getHttpClient() = client ?: synchronized(LOCK){
        client ?: HttpClient(CIO){
            engine {

                maxConnectionsCount = 1000

                endpoint {
                    //Maximum number of requests for a specific endpoint route.
                    maxConnectionsPerRoute = 100
                    // Max size of scheduled requests per connection(pipeline queue size).
                    pipelineMaxSize = 20
                    //Max number of milliseconds to keep idle connection alive.
                    keepAliveTime = 5000
                    //Number of milliseconds to wait trying to connect to the server.
                    connectTimeout = 500
                    //Maximum number of attempts for retrying a connection.
                    connectAttempts = 10
                }
            }
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }.also {
            client = it
            Log.wtf("ParentControl","new application instance initialized...")
        }
    }

}