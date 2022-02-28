package m.derakhshan.done.core.utils


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection


operator fun PaddingValues.plus(
    paddingValues: PaddingValues,
): PaddingValues {
    val direction = LayoutDirection.Ltr
    return PaddingValues(
        start = this.calculateStartPadding(direction) + paddingValues.calculateEndPadding(direction),
        end = this.calculateEndPadding(direction) + paddingValues.calculateEndPadding(direction),
        top = this.calculateTopPadding() + paddingValues.calculateTopPadding(),
        bottom = this.calculateBottomPadding() + paddingValues.calculateBottomPadding()
    )
}

operator fun PaddingValues.plus(
    padding: Dp,
): PaddingValues {
    val direction: LayoutDirection = LayoutDirection.Ltr
    return PaddingValues(
        start = this.calculateStartPadding(direction) + padding,
        end = this.calculateEndPadding(direction) + padding,
        top = this.calculateTopPadding() + padding,
        bottom = this.calculateBottomPadding() + padding
    )

}