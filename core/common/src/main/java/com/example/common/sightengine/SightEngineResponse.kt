package com.example.common.sightengine

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SightEngineResponse(
    @SerializedName("summary")
    val summary: Summary
): Serializable {
    override fun toString(): String {
        return "SightEngineResponse(summary=$summary)"
    }
}

data class Summary(
    @SerializedName("action")
    val action: String,
    @SerializedName("reject_prob")
    val rejectProb: Double
): Serializable {
    override fun toString(): String {
        return "Summary(action='$action', rejectProb=$rejectProb)"
    }
}
