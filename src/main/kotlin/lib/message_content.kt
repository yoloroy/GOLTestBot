package lib

import dev.inmo.tgbotapi.types.message.content.TextContent

val TextContent.commandArg get() = text.split(" ").drop(1).joinToString(" ")
