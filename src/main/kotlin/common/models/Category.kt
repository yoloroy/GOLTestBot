package common.models

import common.ToMessageStringConvertible
import lib.util.toMessageString

data class Category(
    val name: String,
    val roadmaps: List<Roadmap>
) : ToMessageStringConvertible {
    override fun toMessageString() = "Категория $name:\n" + roadmaps.toMessageString()
}

fun List<Category>.toMessageString() = map { it.name }.toMessageString()
