package com.hcl.got.utils

// Represents a resource that encapsulates different states of an operation
data class Resource<out T>(
    // The current status of the operation
    val status: Status,
    // The data associated with the operation (can be null)
    val data: T?,
    // Optional message providing additional information about the status (can be null)
    val message: String?
) {
    companion object {
        // Factory method to create a success resource with data
        fun <T> success(data: T): Resource<T> =
            Resource(Status.SUCCESS, data, null)

        // Factory method to create an error resource with optional data and message
        fun <T> error(data: T? = null, message: String): Resource<T> =
            Resource(Status.ERROR, data, message)

        // Factory method to create a loading resource with optional data
        fun <T> loading(data: T? = null): Resource<T> =
            Resource(Status.LOADING, data, null)
    }
}
