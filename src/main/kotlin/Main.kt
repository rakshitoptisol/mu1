package com.example.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils

fun main() {
    // Create an HttpClient instance
    val httpClient: CloseableHttpClient = HttpClients.createDefault()

    try {
        // Create a GET request
        val request = HttpGet("https://jsonplaceholder.typicode.com/posts/1")

        // Execute the request
        val response: CloseableHttpResponse = httpClient.execute(request)

        // Use 'use' to automatically close resources
        response.use {
            // Check response status
            val statusCode = it.statusLine.statusCode
            if (statusCode == 200) {
                // Get response entity
                val entity = it.entity
                if (entity != null) {
                    // Convert response entity to String
                    val result = EntityUtils.toString(entity)
                    println(result)

                    // If response is JSON, map it to a Kotlin object
                    val mapper = jacksonObjectMapper()
                    val post: Post = mapper.readValue(result)
                    println(post)
                }
            } else {
                println("Error: $statusCode")
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        // Close the HttpClient
        httpClient.close()
    }
}

// Data class to map the JSON response
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
