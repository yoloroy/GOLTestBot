package telegram_bot.routing

import dev.inmo.tgbotapi.extensions.api.send.sendTextMessage
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContext
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import dev.inmo.tgbotapi.types.message.content.TextContent
import dev.inmo.tgbotapi.utils.PreviewFeature

@PreviewFeature
suspend fun BehaviourContext.info(message: CommonMessage<TextContent>) {
    sendTextMessage(message.chat.id, "В главном меню тебе будут доступны две команды: категории и текущие задания.\n" +
            "\n" +
            "КАТЕГОРИИ — содержат в себе различные области, в которых ты можешь развиваться. Выбрав одну из областей, ты получишь список доступных дорожных карт, которые содержат в себе задания, необходимые для выполнения. После выбора одной из дорожных карт, ты получишь первое задание для выполнения. По завершению одной задачи, ты автоматически получишь следующую. Постепенно выполняя поставленные задачи, ты будешь всё дальше продвигаться в освоении интересующей тебя области.\n" +
            "\n" +
            "ТЕКУЩИЕ ЗАДАНИЯ — содержат в себе список активных заданий из всех выбранных тобою дорожных карт, которые тебе необходимо выполнить. О данных заданиях можно узнать более подробную информацию или отметить завершенными.")
    start(message)
}
