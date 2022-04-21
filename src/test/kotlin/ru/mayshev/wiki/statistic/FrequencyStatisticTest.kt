package ru.mayshev.wiki.statistic

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class FrequencyStatisticTest {

    @Test
    fun simple() {
        testFrequency(listOf(
            "1" to "заголовок",
            "1" to "простой"
        ), "Простой заголовок with some English words")

        testFrequency(listOf(
            "1" to "как",
            "1" to "простой",
            "1" to "текст"
        ), "[[Простой как-бы текст]] ну и English words!")
    }

    @Test
    fun second() {
        testFrequency(listOf(
            "1" to "базовая",
            "1" to "заголовок",
            "1" to "простой",
            "1" to "статья",
            "1" to "участник"
        ),
            "Простой заголовок with some English words",
            "Базовая статья",
            "Участник:Hijiri"
        )

        testFrequency(
            listOf(
                "2" to "википедии",
                "2" to "йцукенгшщзхъ",
                "2" to "первый",
                "2" to "русской",
                "2" to "участник",
                "2" to "фывапролджэ",
                "2" to "ячсмитьбю",
                "1" to "аригато",
                "1" to "википедия",
                "1" to "высшее",
                "1" to "его",
                "1" to "заглавная",
                "1" to "изменить",
                "1" to "история",
                "1" to "ите",
                "1" to "как",
                "1" to "который",
                "1" to "куретеите",
                "1" to "лучшим",
                "1" to "мир",
                "1" to "мичурин",
                "1" to "ноябрь",
                "1" to "ноября",
                "1" to "обоете",
                "1" to "обс",
                "1" to "объяснить",
                "1" to "привет",
                "1" to "призвание",
                "1" to "простой",
                "1" to "работает",
                "1" to "раздела",
                "1" to "русскоязычного",
                "1" to "сделать",
                "1" to "состоит",
                "1" to "страница",
                "1" to "текст",
                "1" to "токио",
                "1" to "только",
                "1" to "том",
                "1" to "человека",
                "1" to "чтобы",
                "1" to "юрисконсульт",
                "1" to "японский"
            ),
            "[[Простой как-бы текст]] ну и English words!",
            "#REDIRECT [[Заглавная страница]]",
            "{{userbox|gold|white|id=&lt;span style=&quot;color: black; font-size: 18pt;&quot;&gt;#1&lt;/span&gt;|info=Первый участник Русской Википедии!}}\n",
            "Высшее призвание человека состоит в том, чтобы не только объяснить, но и изменить мир, сделать его лучшим.  — И. В. Мичурин\n",
            "\n",
            "----\n",
            "\n",
            "'''Hijiri''' 14:52 Nov 13, 2002 (UTC)\n",
            "\n",
            "Привет! Я японский юрисконсульт, который работает в Токио.\n",
            "\n",
            "My homeground is http://ja.wikipedia.org/wiki/User:Hijiri\n",
            "\n",
            "Первый участник Русской Википедии с 7 Ноябрь 2002г.\n",
            "\n",
            "----\n",
            "\n",
            "Обоете ите куретеите аригато!\n",
            "\n",
            "[[Википедия:История_русскоязычного_раздела]]\n",
            "\n",
            "[[У:Hijiri|Hijiri]] ([[ОУ:Hijiri|обс.]]) 13:25, 2 ноября 2018 (UTC)\n",
            "\n",
            "----\n",
            "\n",
            "===reminder for me===\n",
            "Russian keyboard assignment\n",
            "\n",
            "ё1234567890-= &lt;BR&gt;\n",
            " йцукенгшщзхъ &lt;BR&gt;\n",
            "  фывапролджэ\\ &lt;BR&gt;\n",
            "   ячсмитьбю. &lt;BR&gt;\n",
            "&lt;BR&gt;\n",
            "Ё!&quot;№;%:?*()_+ &lt;BR&gt;\n",
            " ЙЦУКЕНГШЩЗХЪ &lt;BR&gt;\n",
            "  ФЫВАПРОЛДЖЭ/ &lt;BR&gt;\n",
            "   ЯЧСМИТЬБЮ, &lt;BR&gt;\n",
            "\n",
            "[[ja:利用者:Hijiri]]\n",
            "[[en:User:Hijiri]]\n",
            "[[fr:Utilisateur:Hijiri]]"
        )
    }

    private fun testFrequency(expectedStatistics: List<Pair<String, String>>, vararg strings: String) {
        val frequencyStatistic = FrequencyStatistic().apply {
            for (data in strings) {
                update(data)
            }
        }

        Assertions.assertEquals(expectedStatistics, frequencyStatistic.collect())
    }
}
