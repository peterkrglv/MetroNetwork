package com.example.learncompose.data

import androidx.compose.ui.graphics.Color
import com.example.learncompose.domain.LinesRepository
import com.example.learncompose.domain.MetroLine

class LinesRepositoryImpl : LinesRepository {
    override suspend fun loadData(): List<MetroLine> {
        val metroLines = mutableListOf<MetroLine>()
//        metroLines.addAll(
//            listOf(
//                MetroLine(
//                    "Сокольническая", Color(0xFFCC0000), listOf(
//                        "Бульвар Рокоссовского",
//                        "Черкизовская",
//                        "Преображенская площадь",
//                        "Сокольники",
//                        "Красносельская",
//                        "Комсомольская",
//                        "Красные Ворота",
//                        "Чистые пруды",
//                        "Лубянка",
//                        "Охотный Ряд",
//                        "Библиотека имени Ленина",
//                        "Кропоткинская",
//                        "Парк культуры",
//                        "Фрунзенская",
//                        "Спортивная",
//                        "Воробьёвы горы",
//                        "Университет",
//                        "Проспект Вернадского",
//                        "Юго-Западная",
//                        "Тропарёво",
//                        "Румянцево",
//                        "Саларьево",
//                        "Филатов Луг",
//                        "Прокшино",
//                        "Ольховая",
//                        "Новомосковская",
//                        "Потапово"
//                    )
//                ),
//                MetroLine(
//                    "Замоскворецкая", Color(0xFF008000), listOf(
//                        "Ховрино",
//                        "Беломорская",
//                        "Речной вокзал",
//                        "Водный стадион",
//                        "Войковская",
//                        "Сокол",
//                        "Аэропорт",
//                        "Динамо",
//                        "Белорусская",
//                        "Маяковская",
//                        "Тверская",
//                        "Театральная",
//                        "Новокузнецкая",
//                        "Павелецкая",
//                        "Автозаводская",
//                        "Технопарк",
//                        "Коломенская",
//                        "Каширская",
//                        "Кантемировская",
//                        "Царицыно",
//                        "Орехово",
//                        "Домодедовская",
//                        "Красногвардейская",
//                        "Алма-Атинская",
//                    )
//                ), // Green
//                MetroLine("Арбатско-Покровская", Color(0xFF0096FF), emptyList()), // Dark blue
//                MetroLine("Филёвская", Color(0xFF89CFF0), emptyList()), // Light blue
//                MetroLine("Кольцевая", Color(0xFF8B4513), emptyList()), // Brown
//                MetroLine("Калужско-Рижская", Color(0xFFFFA500), emptyList()), // Orange
//                MetroLine("Таганско-Краснопресненская", Color(0xFFA000A0), emptyList()), // Violet
//                MetroLine("Калининская", Color(0xAAFFFF00), emptyList()), // Yellow
//                MetroLine("Серпуховско-Тимирязевская", Color(0xFF808080), emptyList()), // Grey
//                MetroLine("Люблинско-Дмитровская", Color(0xFF90EE90), emptyList()), // Salad
//                MetroLine("Каховская", Color(0xFF96DED1), emptyList()), // Blue
//                MetroLine("Бутовская", Color(0xFFB0E0E6), emptyList()) // Lighter blue
//            )
//        )


        return metroLines
    }
}