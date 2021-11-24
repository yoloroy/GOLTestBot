package common.models

import common.ToMessageStringConvertible
import lib.util.toMessageString

data class Roadmap(
    val name: String,
    val description: String,
    //val img:
    val tasks: List<Task>
) : ToMessageStringConvertible {
    override fun toMessageString(): String = "$name\n$description"
}

fun List<Roadmap>.toMessageString() = map { it.name }.toMessageString()
