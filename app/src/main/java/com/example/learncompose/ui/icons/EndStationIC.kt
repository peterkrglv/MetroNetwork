package com.example.learncompose.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val EndStationIC: ImageVector
    get() {
        if (_EndStation != null) {
            return _EndStation!!
        }
        _EndStation = ImageVector.Builder(
            name = "EndStation",
            defaultWidth = 40.dp,
            defaultHeight = 40.dp,
            viewportWidth = 40f,
            viewportHeight = 40f
        ).apply {
            group(
                clipPathData = PathData {
                    moveTo(0f, 0f)
                    horizontalLineToRelative(40f)
                    verticalLineToRelative(40f)
                    horizontalLineToRelative(-40f)
                    close()
                }
            ) {
                path(
                    fill = SolidColor(Color(0xFF000000)),
                    stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1f
                ) {
                    moveTo(24f, 40.5f)
                    horizontalLineTo(24.5f)
                    verticalLineTo(40f)
                    verticalLineTo(29.489f)
                    curveTo(28.047f, 27.805f, 30.5f, 24.189f, 30.5f, 20f)
                    curveTo(30.5f, 14.201f, 25.799f, 9.5f, 20f, 9.5f)
                    curveTo(14.201f, 9.5f, 9.5f, 14.201f, 9.5f, 20f)
                    curveTo(9.5f, 24.189f, 11.953f, 27.805f, 15.5f, 29.489f)
                    verticalLineTo(40f)
                    verticalLineTo(40.5f)
                    horizontalLineTo(16f)
                    horizontalLineTo(24f)
                    close()
                    moveTo(25.5f, 20f)
                    curveTo(25.5f, 23.038f, 23.038f, 25.5f, 20f, 25.5f)
                    curveTo(16.962f, 25.5f, 14.5f, 23.038f, 14.5f, 20f)
                    curveTo(14.5f, 16.962f, 16.962f, 14.5f, 20f, 14.5f)
                    curveTo(23.038f, 14.5f, 25.5f, 16.962f, 25.5f, 20f)
                    close()
                }
            }
        }.build()

        return _EndStation!!
    }

@Suppress("ObjectPropertyName")
private var _EndStation: ImageVector? = null
