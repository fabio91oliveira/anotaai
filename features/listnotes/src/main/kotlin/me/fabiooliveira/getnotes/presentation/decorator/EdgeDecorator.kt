import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


//package me.fabiooliveira.getnotes.presentation.decorator
//
//import android.graphics.Rect
//import android.view.View
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.RecyclerView.ItemDecoration
//
//
///**
// * Created by anthonykiniyalocts on 12/8/16.
// *
// * Quick way to add padding to first and last item in recyclerview via decorators
// */
//class EdgeDecorator
///**
// * EdgeDecorator
// * @param edgePadding padding set on the left side of the first item and the right side of the last item
// */
//    : ItemDecoration() {
//    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//        super.getItemOffsets(outRect, view, parent, state)
//        val itemCount = state.itemCount
//        val itemPosition = parent.getChildAdapterPosition(view)
//
//
//        var maxBottom = 0
//        val childRect = Rect()
//        for (i in 0 until parent.childCount) {
//            val child = parent.getChildAt(i)
//            parent.getDecoratedBoundsWithMargins(child, childRect)
//            maxBottom = Math.max(maxBottom, childRect.bottom)
//        }
//
//        val rectF = Rect(childRect)
//
//        // first item
//        if (itemPosition == 0) {
//
//            // last item
//        } else if (itemCount > 0 && itemPosition == itemCount - 1) {
//
//            // others
//        } else {
//            outRect.set(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom())
//        }
//    }
//
////    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
////        super.getItemOffsets(outRect, view, parent, state)
////        val itemCount = state.itemCount
////        val itemPosition = parent.getChildAdapterPosition(view)
////
////        // no position, leave it alone
////        if (itemPosition == RecyclerView.NO_POSITION) {
////            return
////        }
////
////        // first item
////        if (itemPosition == 0) {
////
////
////            outRect.set(edgePadding, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom())
////        } else if (itemCount > 0 && itemPosition == itemCount - 1) {
////            outRect.set(view.getPaddingLeft(), view.getPaddingTop(), edgePadding, view.getPaddingBottom())
////        } else {
////            outRect.set(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom())
////        }
////    }
//
//}


class RoundCornersDecoration(private val radius: Float) : ItemDecoration() {
    private val defaultRectToClip: RectF = RectF(Float.MAX_VALUE, Float.MAX_VALUE, 0f, 0f)

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val rectToClip = getRectToClip(parent)

        // has no items with ViewType == `R.layout.item_image`
        if (rectToClip == defaultRectToClip) {
            return
        }
        val path = Path()
        path.addRoundRect(rectToClip, radius, radius, Path.Direction.CW)
        canvas.clipPath(path)
    }

    private fun getRectToClip(parent: RecyclerView): RectF {
        val rectToClip = RectF(defaultRectToClip)
        val childRect = Rect()
        for (i in 0 until parent.childCount) {
            val child: View = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, childRect)
            rectToClip.left = rectToClip.left.coerceAtMost(childRect.left.toFloat())
            rectToClip.top = rectToClip.top.coerceAtMost(childRect.top.toFloat())
            rectToClip.right = rectToClip.right.coerceAtLeast(childRect.right.toFloat())
            rectToClip.bottom = rectToClip.bottom.coerceAtLeast(childRect.bottom.toFloat())
        }
        return rectToClip
    }

}