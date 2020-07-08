package io.mega.nrobinson.jackal.api.model

sealed class Progress(open val name: String)
data class TorrentProgress(
    override val name: String, val current: Int, val total: Int): Progress(name)
data class Pending(override val name: String): Progress(name)
data class Calculating(override val name: String): Progress(name)
data class FTPProgress(override val name: String, val current: Int, val total: Int): Progress(name)
data class Done(override val name: String): Progress(name)
data class JackalProgress(
    val torrents: List<TorrentProgress>,
    val pending: List<Pending>,
    val calculating: List<Calculating>,
    val ftps: List<FTPProgress>
)