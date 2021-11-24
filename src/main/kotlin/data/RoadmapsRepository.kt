package data

import common.models.Category
import common.models.Roadmap
import common.models.Task

object RoadmapsRepository {
    var categories: List<Category> = listOf(
        Category(
            "Физическое развитие",
            listOf(
                Roadmap(
                    "Воркаут",
                    "(ДЛЯ МУЖЧИН!)Если ты недавно начал заниматься спортом, но лёгкие тренеровки уже не дают ощущения усталости, то эта ежедневная программа для дома специально для тебя. Она занимает не больше 30 минут в день, но даёт ощутимый результат в прибавке мышечной массы.",
                    listOf(
                        Task(
                            "Пресс",
                            "4 подхода по 10 раз каждый с перерывом в 30 секунд",
                            "https://www.youtube.com/watch?v=vmuVurQFBBE"
                        ),
                        Task(
                            "Отжимания",
                            "4 подхода по 10 раз каждый с перерывом в 30 секунд",
                            "https://www.youtube.com/watch?v=vO0ItS53Jno"
                        ),
                        Task(
                            "Приседания",
                            "4 подхода по 15 раз каждый с перерывом в 30 секунд",
                            "https://www.youtube.com/watch?v=51xwwaA5BnQ"
                        ),
                        Task(
                            "Подтягивания",
                            "4 подхода по 7 раз каждый с перерывом в 30 секунд",
                            "https://www.youtube.com/watch?v=K5UVjeNCKmY"
                        )
                    )
                )
            )
        ),
        Category(
            "Программирование",
            listOf(
                Roadmap(
                    "Фронтенд разработка",
                    "Это программа предназначена для начинающих программистов. Она позволит вам изучить такие базовые вещи как: css, html и js. Эта база поможет вам развиваться дальше и заложит фундамент вашей будующей карьеры.",
                    listOf(
                        Task(
                            "HTML",
                            "Несколько ссылок для самостоятельного изучения\n1 ссылка - видеоурок для познания основ\n2 ссылка - полный справочник по HTML5",
                            "https://www.youtube.com/watch?v=bWNmJqgri4Q, http://htmlbook.ru/html5"
                        ),
                        Task(
                            "CSS",
                            "Несколько ссылок для самостоятельного изучения\n1 ссылка - онлайн урок по базе css\n2 ссылка - справочник по свойствам",
                            "https://www.youtube.com/watch?v=iPV5GKeHyV4&t=934s, https://developer.mozilla.org/ru/docs/Web/CSS/Reference"
                        ),
                        Task(
                            "JS",
                            "Несколько ссылок для самостоятельного изучения\n1 ссылка - объёмный експресс-курс для старта программирования на js\n2 ссылка - немного про расширение js(библиотеки и фреймворки)\n3 ссылка - общепринятые нормы по написанию кода на js",
                            "https://www.youtube.com/watch?v=Bluxbh9CaQ0, https://code.tutsplus.com/ru/articles/essential-javascript-libraries-and-frameworks-you-should-know-about--cms-29540, https://learn.javascript.ru/coding-style"
                        ),
                        Task(
                            "Итоговая работа",
                            "Несколько ссылок для самостоятельного изучения\n1 ссылка - посмотреть видео и подчеркнуть для себя этапы разработки сайта\n2 ссылка - сверстать представленный макет самому",
                            "https://www.youtube.com/watch?v=Q2zxQNQGcU8&t=3057s, https://dribbble.com/shots/1358205-Fitnesstime-Landing-Page-FREE-PSD"
                        )
                    )
                )
            )
        )
    )
}
