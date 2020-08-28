package me.fabiooliveira.getnotes.presentation.dialogfragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.popup_dialog_dialog_fragment.*
import ui.popupdialog.R

class PopupDialog : DialogFragment() {

    private val resTitle: Int by lazy {
        arguments?.getInt(
                TITLE
        ) ?: 0
    }

    private val resSubtitle: Int by lazy {
        arguments?.getInt(
                SUBTITLE
        ) ?: 0
    }

    private val strSubtitle: String? by lazy {
        arguments?.getString(
                SUBTITLE
        )
    }

    private val resConfirmText: Int by lazy {
        arguments?.getInt(
                CONFIRM_TEXT
        ) ?: 0
    }

    private val resCancelText: Int by lazy {
        arguments?.getInt(
                CANCEL_TEXT
        ) ?: 0
    }

    private val confirmListener: PopupDialogConfirmListener? by lazy {
        arguments?.getParcelable(
                CONFIRM_LISTENER
        ) as PopupDialogConfirmListener?
    }

    private val cancelListener: PopupDialogCancelListener? by lazy {
        arguments?.getParcelable(
                CANCEL_LISTENER
        ) as PopupDialogCancelListener?
    }

    private val resColor: Int by lazy {
        arguments?.getInt(
                CONFIRM_BUTTON_COLOR
        ) ?: android.R.color.black
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
                R.layout.popup_dialog_dialog_fragment,
                container,
                false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.also { dismiss() }
        init()
    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            isCancelable = false
            setStyle(STYLE_NORMAL, R.style.popup_dialog_FullScreenDialogTheme)
            window?.also {
                it.setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
                it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    private fun init() {
        setupTitle()
        setupSubtitle()
        setupConfirmButton()
        setupCancelButton()
    }

    private fun getBackgroundDrawable(
            pressedColor: Int,
            backgroundDrawable: Drawable
    ): RippleDrawable {
        return RippleDrawable(getPressedState(pressedColor), backgroundDrawable, null)
    }

    private fun getPressedState(pressedColor: Int): ColorStateList {
        return ColorStateList(arrayOf(intArrayOf()), intArrayOf(pressedColor))
    }

    private fun setupTitle() {
        check(resTitle != 0) { "Must have title set." }
        txtTitle.text = context?.resources?.getString(resTitle)
    }

    private fun setupSubtitle() {
        check((resSubtitle != 0) || (strSubtitle != null)) { "Must have subtitle set." }

        strSubtitle?.also {
            txtSubtitle.text = it
        } ?: run {
            txtSubtitle.text = context?.resources?.getString(resSubtitle)
        }
    }

    private fun setupConfirmButton() {
        confirmListener?.also { confirm ->
            btnConfirm.text = context?.resources?.getString(resConfirmText)
            btnConfirm.visibility = View.VISIBLE
            btnConfirm.setOnClickListener {
                confirm.onClickConfirmButton()
                dismiss()
            }
        }
        setupConfirmButtonColor()
    }

    private fun setupCancelButton() {
        cancelListener?.also { cancel ->
            btnCancel.text = context?.resources?.getString(resCancelText)
            btnCancel.visibility = View.VISIBLE
            btnCancel.setOnClickListener {
                cancel.onClickCancelButton()
                dismiss()
            }
        }
        setupCancelButtonColor()
    }

    private fun setupConfirmButtonColor() {
        context?.also {
            with(btnConfirm) {
                when (background) {
                    is ShapeDrawable -> (background as ShapeDrawable).paint.color =
                            ContextCompat.getColor(
                                    it,
                                    if (resColor == 0) android.R.color.black else resColor
                            )
                    is GradientDrawable -> (background as GradientDrawable).setColor(
                            ContextCompat.getColor(
                                    it,
                                    if (resColor == 0) android.R.color.black else resColor
                            )
                    )
                    is ColorDrawable -> (background as ColorDrawable).color =
                            ContextCompat.getColor(
                                    it,
                                    if (resColor == 0) android.R.color.black else resColor
                            )
                }
                background = getBackgroundDrawable(
                        ContextCompat.getColor(it, android.R.color.white),
                        background
                )
            }
        }
    }

    private fun setupCancelButtonColor() {
        context?.also {
            btnCancel.setTextColor(ContextCompat.getColor(it, if (resColor == 0) android.R.color.black else resColor))
        }
    }

    class Builder {
        private val args: Bundle = Bundle()

        fun setImage(@DrawableRes resTitle: Int) = apply {
            args.putInt(IMAGE, resTitle)
        }

        fun setTitle(@StringRes resTitle: Int) = apply {
            args.putInt(TITLE, resTitle)
        }

        fun setSubtitle(@StringRes resSubtitle: Int) = apply {
            args.putInt(SUBTITLE, resSubtitle)
        }

        fun setupConfirmButton(
                @StringRes resConfirmText: Int,
                listener: PopupDialogConfirmListener
        ) =
                apply {
                    args.putInt(CONFIRM_TEXT, resConfirmText)
                    args.putParcelable(CONFIRM_LISTENER, listener)
                }

        fun setupConfirmButtonColor(
                @ColorRes resColor: Int
        ) = apply {
            args.putInt(CONFIRM_BUTTON_COLOR, resColor)
        }

        fun setupCancelButton(
                @StringRes resCancelText: Int,
                listener: PopupDialogCancelListener
        ) =
                apply {
                    args.putInt(CANCEL_TEXT, resCancelText)
                    args.putParcelable(CANCEL_LISTENER, listener)
                }

        fun build() = PopupDialog().apply {
            this.arguments = args
        }
    }

    interface PopupDialogConfirmListener : Parcelable {
        fun onClickConfirmButton()
        override fun describeContents(): Int = 0
        override fun writeToParcel(dest: Parcel, flags: Int) {
        }
    }

    interface PopupDialogCancelListener : Parcelable {
        fun onClickCancelButton()
        override fun describeContents(): Int = 0
        override fun writeToParcel(dest: Parcel, flags: Int) {
        }
    }

    companion object {
        const val TAG = "PopupDialog"
        const val IMAGE = "IMAGE"
        const val TITLE = "TITLE"
        const val SUBTITLE = "SUBTITLE"
        const val CONFIRM_BUTTON_COLOR = "CONFIRM_BUTTON_COLOR"
        const val CONFIRM_TEXT = "CONFIRM_TEXT"
        const val CONFIRM_LISTENER = "CONFIRM_LISTENER"
        const val CANCEL_LISTENER = "CANCEL_LISTENER"
        const val CANCEL_TEXT = "CANCEL_TEXT"
    }
}