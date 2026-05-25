package com.example.shirokhorshid.v2ray

import com.google.gson.JsonObject
import com.google.gson.JsonArray

/**
 * V2Ray Configuration Model
 * Represents the complete V2Ray configuration structure
 */
data class V2RayConfig(
    val comment: Comment? = null,
    val log: LogConfig? = null,
    val inbounds: List<Inbound> = emptyList(),
    val outbounds: List<Outbound> = emptyList(),
    val dns: DnsConfig? = null,
    val routing: RoutingConfig? = null
) {
    fun toJson(): JsonObject {
        val json = JsonObject()
        
        comment?.let {
            val commentJson = JsonObject()
            commentJson.addProperty("remark", it.remark)
            json.add("_comment", commentJson)
        }
        
        log?.let { json.add("log", it.toJson()) }
        
        val inboundsArray = JsonArray()
        inbounds.forEach { inboundsArray.add(it.toJson()) }
        json.add("inbounds", inboundsArray)
        
        val outboundsArray = JsonArray()
        outbounds.forEach { outboundsArray.add(it.toJson()) }
        json.add("outbounds", outboundsArray)
        
        dns?.let { json.add("dns", it.toJson()) }
        routing?.let { json.add("routing", it.toJson()) }
        
        return json
    }
}

data class Comment(
    val remark: String = ""
)

data class LogConfig(
    val access: String = "",
    val error: String = "",
    val loglevel: String = "info",
    val dnsLog: Boolean = false
) {
    fun toJson(): JsonObject {
        val json = JsonObject()
        json.addProperty("access", access)
        json.addProperty("error", error)
        json.addProperty("loglevel", loglevel)
        json.addProperty("dnsLog", dnsLog)
        return json
    }
}

data class Inbound(
    val tag: String = "",
    val port: Int = 0,
    val protocol: String = "socks",
    val listen: String = "0.0.0.0",
    val settings: InboundSettings? = null,
    val sniffing: Sniffing? = null
) {
    fun toJson(): JsonObject {
        val json = JsonObject()
        json.addProperty("tag", tag)
        json.addProperty("port", port)
        json.addProperty("protocol", protocol)
        json.addProperty("listen", listen)
        settings?.let { json.add("settings", it.toJson()) }
        sniffing?.let { json.add("sniffing", it.toJson()) }
        return json
    }
}

data class InboundSettings(
    val auth: String = "noauth",
    val udp: Boolean = true,
    val userLevel: Int = 8
) {
    fun toJson(): JsonObject {
        val json = JsonObject()
        json.addProperty("auth", auth)
        json.addProperty("udp", udp)
        json.addProperty("userLevel", userLevel)
        return json
    }
}

data class Sniffing(
    val enabled: Boolean = false
) {
    fun toJson(): JsonObject {
        val json = JsonObject()
        json.addProperty("enabled", enabled)
        return json
    }
}

data class Outbound(
    val tag: String = "",
    val protocol: String = "freedom",
    val settings: OutboundSettings? = null,
    val streamSettings: StreamSettings? = null,
    val mux: MuxConfig? = null
) {
    fun toJson(): JsonObject {
        val json = JsonObject()
        json.addProperty("tag", tag)
        json.addProperty("protocol", protocol)
        settings?.let { json.add("settings", it.toJson()) }
        streamSettings?.let { json.add("streamSettings", it.toJson()) }
        mux?.let { json.add("mux", it.toJson()) }
        return json
    }
}

data class OutboundSettings(
    val servers: List<ServerConfig> = emptyList(),
    val domainStrategy: String = "AsIs",
    val fragment: FragmentConfig? = null
) {
    fun toJson(): JsonObject {
        val json = JsonObject()
        
        val serversArray = JsonArray()
        servers.forEach { serversArray.add(it.toJson()) }
        if (servers.isNotEmpty()) json.add("servers", serversArray)
        
        json.addProperty("domainStrategy", domainStrategy)
        fragment?.let { json.add("fragment", it.toJson()) }
        
        return json
    }
}

data class ServerConfig(
    val address: String = "",
    val port: Int = 443,
    val password: String = "",
    val method: String = "",
    val ota: Boolean = false,
    val level: Int = 8,
    val flow: String = ""
) {
    fun toJson(): JsonObject {
        val json = JsonObject()
        json.addProperty("address", address)
        json.addProperty("port", port)
        json.addProperty("password", password)
        if (method.isNotEmpty()) json.addProperty("method", method)
        json.addProperty("ota", ota)
        json.addProperty("level", level)
        if (flow.isNotEmpty()) json.addProperty("flow", flow)
        return json
    }
}

data class StreamSettings(
    val network: String = "tcp",
    val security: String = "",
    val wsSettings: WebSocketSettings? = null,
    val tlsSettings: TlsSettings? = null,
    val sockopt: SockOptConfig? = null
) {
    fun toJson(): JsonObject {
        val json = JsonObject()
        json.addProperty("network", network)
        if (security.isNotEmpty()) json.addProperty("security", security)
        wsSettings?.let { json.add("wsSettings", it.toJson()) }
        tlsSettings?.let { json.add("tlsSettings", it.toJson()) }
        sockopt?.let { json.add("sockopt", it.toJson()) }
        return json
    }
}

data class WebSocketSettings(
    val path: String = "",
    val headers: Map<String, String> = emptyMap()
) {
    fun toJson(): JsonObject {
        val json = JsonObject()
        json.addProperty("path", path)
        
        val headersJson = JsonObject()
        headers.forEach { (key, value) -> headersJson.addProperty(key, value) }
        json.add("headers", headersJson)
        
        return json
    }
}

data class TlsSettings(
    val allowInsecure: Boolean = false,
    val serverName: String = "",
    val alpn: List<String> = emptyList(),
    val fingerprint: String = "",
    val show: Boolean = false
) {
    fun toJson(): JsonObject {
        val json = JsonObject()
        json.addProperty("allowInsecure", allowInsecure)
        json.addProperty("serverName", serverName)
        
        val alpnArray = JsonArray()
        alpn.forEach { alpnArray.add(it) }
        if (alpn.isNotEmpty()) json.add("alpn", alpnArray)
        
        json.addProperty("fingerprint", fingerprint)
        json.addProperty("show", show)
        
        return json
    }
}

data class SockOptConfig(
    val dialerProxy: String = "",
    val tcpKeepAliveIdle: Int = 100,
    val tcpNoDelay: Boolean = true
) {
    fun toJson(): JsonObject {
        val json = JsonObject()
        if (dialerProxy.isNotEmpty()) json.addProperty("dialerProxy", dialerProxy)
        json.addProperty("tcpKeepAliveIdle", tcpKeepAliveIdle)
        json.addProperty("tcpNoDelay", tcpNoDelay)
        return json
    }
}

data class MuxConfig(
    val enabled: Boolean = false,
    val concurrency: Int = 8
) {
    fun toJson(): JsonObject {
        val json = JsonObject()
        json.addProperty("enabled", enabled)
        json.addProperty("concurrency", concurrency)
        return json
    }
}

data class DnsConfig(
    val servers: List<String> = emptyList()
) {
    fun toJson(): JsonObject {
        val json = JsonObject()
        val serversArray = JsonArray()
        servers.forEach { serversArray.add(it) }
        json.add("servers", serversArray)
        return json
    }
}

data class RoutingConfig(
    val domainStrategy: String = "UseIp",
    val rules: List<JsonObject> = emptyList(),
    val balancers: List<JsonObject> = emptyList()
) {
    fun toJson(): JsonObject {
        val json = JsonObject()
        json.addProperty("domainStrategy", domainStrategy)
        
        val rulesArray = JsonArray()
        rules.forEach { rulesArray.add(it) }
        json.add("rules", rulesArray)
        
        val balancersArray = JsonArray()
        balancers.forEach { balancersArray.add(it) }
        json.add("balancers", balancersArray)
        
        return json
    }
}

data class FragmentConfig(
    val packets: String = "tlshello",
    val length: String = "100-200",
    val interval: String = "10-20"
) {
    fun toJson(): JsonObject {
        val json = JsonObject()
        json.addProperty("packets", packets)
        json.addProperty("length", length)
        json.addProperty("interval", interval)
        return json
    }
}
