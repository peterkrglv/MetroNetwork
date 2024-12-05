package com.example.learncompose.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val StationIC: ImageVector
    get() {
        if (_Station != null) {
            return _Station!!
        }
        _Station = ImageVector.Builder(
            name = "Station",
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
                    moveTo(24.5f, 0f)
                    verticalLineTo(-0.5f)
                    horizontalLineTo(24f)
                    horizontalLineTo(16f)
                    horizontalLineTo(15.5f)
                    verticalLineTo(0f)
                    verticalLineTo(10.511f)
                    curveTo(11.953f, 12.195f, 9.5f, 15.811f, 9.5f, 20f)
                    curveTo(9.5f, 24.189f, 11.953f, 27.805f, 15.5f, 29.489f)
                    verticalLineTo(40f)
                    verticalLineTo(40.5f)
                    horizontalLineTo(16f)
                    horizontalLineTo(24f)
                    horizontalLineTo(24.5f)
                    verticalLineTo(40f)
                    verticalLineTo(29.489f)
                    curveTo(28.047f, 27.805f, 30.5f, 24.189f, 30.5f, 20f)
                    curveTo(30.5f, 15.811f, 28.047f, 12.195f, 24.5f, 10.511f)
                    verticalLineTo(0f)
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

        return _Station!!
    }

@Suppress("ObjectPropertyName")
private var _Station: ImageVector? = null
