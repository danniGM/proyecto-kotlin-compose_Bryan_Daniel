package joto.demo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform