package com.example.learncompose.data

import androidx.compose.ui.graphics.Color
import com.example.learncompose.domain.LinesRepository
import com.example.learncompose.domain.MetroLine

class LinesRepositoryImpl : LinesRepository {
    override suspend fun loadData(): List<MetroLine> {
        val metroLines = mutableListOf<MetroLine>()
        //fill with moscow metro lines
        metroLines.addAll(
            listOf(
                MetroLine(
                    "Сокольническая", Color.Red, listOf(
                        "Бульвар Рокоссовского",
                        "Черкизовская",
                        "Преображенская площадь",
                        "Сокольники",
                        "Красносельская",
                        "Комсомольская",
                        "Красные Ворота",
                        "Чистые пруды",
                        "Лубянка",
                        "Охотный Ряд",
                        "Библиотека имени Ленина",
                        "Кропоткинская",
                        "Парк культуры",
                        "Фрунзенская",
                        "Спортивная",
                        "Воробьёвы горы",
                        "Университет",
                        "Проспект Вернадского",
                        "Юго-Западная",
                        "Тропарёво",
                        "Румянцево",
                        "Саларьево",
                        "Филатов Луг",
                        "Прокшино",
                        "Ольховая",
                        "Новомосковская",
                        "Потапово"
                    )
                ),
                MetroLine(
                    "Замоскворецкая", Color(0xFF008000), listOf(
                        "Ховрино",
                        "Беломорская",
                        "Речной вокзал",
                        "Водный стадион",
                        "Войковская",
                        "Сокол",
                        "Аэропорт",
                        "Динамо",
                        "Белорусская",
                        "Маяковская",
                        "Тверская",
                        "Театральная",
                        "Новокузнецкая",
                        "Павелецкая",
                        "Автозаводская",
                        "Технопарк",
                        "Коломенская",
                        "Каширская",
                        "Кантемировская",
                        "Царицыно",
                        "Орехово",
                        "Домодедовская",
                        "Красногвардейская",
                        "Алма-Атинская",
                    )
                ), // Green
                MetroLine("Арбатско-Покровская", Color(0xFF000080), emptyList()), // Dark blue
                MetroLine("Филёвская", Color(0xFFADD8E6), emptyList()), // Light blue
                MetroLine("Кольцевая", Color(0xFFA52A2A), emptyList()), // Brown
                MetroLine("Калужско-Рижская", Color(0xFFFFA500), emptyList()), // Orange
                MetroLine("Таганско-Краснопресненская", Color(0xFF800080), emptyList()), // Violet
                MetroLine("Калининская", Color(0xFFFFFF00), emptyList()), // Yellow
                MetroLine("Серпуховско-Тимирязевская", Color(0xFF808080), emptyList()), // Grey
                MetroLine("Люблинско-Дмитровская", Color(0xFF90EE90), emptyList()), // Salad
                MetroLine("Каховская", Color(0xFF0000FF), emptyList()), // Blue
                MetroLine("Бутовская", Color(0xFFB0E0E6), emptyList()) // Lighter blue
            )
        )


        return metroLines
    }
}