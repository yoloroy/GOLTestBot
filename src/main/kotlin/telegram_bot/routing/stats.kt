package telegram_bot.routing

import common.models.UserData
import dev.inmo.tgbotapi.extensions.api.send.sendTextMessage
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContext
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import dev.inmo.tgbotapi.types.message.content.TextContent

suspend fun BehaviourContext.sendStats(message: CommonMessage<TextContent>, data: UserData) {
    sendTextMessage(
        message.chat.id,
        "Ваш прогресс:\n"
             + data.currentRoadmaps.joinToString("\n") {
                "${it.name}: ${(data.getFinishingValue(it) * 100).toInt()}%"
             }
    )
}
