package telegram_bot

import dev.inmo.tgbotapi.bot.Ktor.telegramBot
import dev.inmo.tgbotapi.extensions.api.bot.getMe
import dev.inmo.tgbotapi.extensions.behaviour_builder.buildBehaviourWithLongPolling
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onCommand
import dev.inmo.tgbotapi.utils.PreviewFeature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import telegram_bot.routing.start

/**
 * This method by default expects one argument in [args] field: telegram bot token
 */
@PreviewFeature
suspend fun main(args: Array<String>) {
    val bot = telegramBot(args.first())

    val scope = CoroutineScope(Dispatchers.Default)

    bot.buildBehaviourWithLongPolling(scope) {
        onCommand("start", requireOnlyCommandInMessage = true) {
            start(it)
        }

        println(bot.getMe())
    }.join()
}
