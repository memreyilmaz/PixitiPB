package com.example.pixiti.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.pixiti.R
import com.example.pixiti.databinding.FragmentImageDetailInfoBinding
import com.example.pixiti.model.Image
import com.example.pixiti.utils.showIf
import com.example.pixiti.utils.toIntOrZero

class ImageDetailInfoFragment : DialogFragment() {

    private var selectedImage: Image? = null
    private var _binding: FragmentImageDetailInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDetailInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setImageTags()
        view.apply {
            isFocusableInTouchMode = true
            requestFocus()
            setOnKeyListener(View.OnKeyListener { _, _, _ ->
                dismiss()
                return@OnKeyListener false
            })
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments?.let {
            selectedImage = (arguments?.getParcelable(SELECTED_IMAGE) as? Image)
        }
        return super.onCreateDialog(savedInstanceState)
    }

    private fun initView() {
        binding.apply {
            textViewResolution.text = getString(
                    R.string.desc_detail_info_resolution,
                    selectedImage?.imageWidth.toIntOrZero(),
                    selectedImage?.imageHeight.toIntOrZero()
            )
            textViewViewCount.text = selectedImage?.views.toIntOrZero().toString()
            textViewDownloadCount.text = selectedImage?.downloads.toIntOrZero().toString()
        }
    }

    private fun setImageTags() {
        if (selectedImage?.tags.isNullOrEmpty()) return
        val tagsList: MutableList<String> = mutableListOf()

        val result: List<String>? = selectedImage?.tags?.split(",")?.map { it.trim() }

        result?.forEach {
            tagsList.add(it)
        }

        binding.apply {
            tagsList.forEach { tag ->
                val textViewTag =
                        LayoutInflater.from(context).inflate(
                                R.layout.item_info_tag,
                                flexboxLayoutTags,
                                false
                        ) as TextView
                textViewTag.text = tag
                flexboxLayoutTags.addView(textViewTag)
            }
            separatorDetailInfo.showIf(tagsList.isNotEmpty())
            flexboxLayoutTags.showIf(tagsList.isNotEmpty())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ImageDetailInfoFragment"
        private const val SELECTED_IMAGE = "selected_image"

        fun newInstance(image: Image) = ImageDetailInfoFragment().apply {
            val args = Bundle()
            args.putParcelable(SELECTED_IMAGE, image)
            arguments = args
        }
    }
}