package com.vladbystrov.yandexmapkit.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.vladbystrov.yandexmapkit.databinding.DialogFragmentPlaceInfoBinding
import com.vladbystrov.yandexmapkit.domain.Place

class PlaceInfoDialogFragment(
    private val place: Place,
    private val onDeleteClickListener: (Place) -> Unit
) : DialogFragment() {

    private var _binding: DialogFragmentPlaceInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogFragmentPlaceInfoBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        binding.tvHeader.text = place.name
        binding.tvDescription.text = place.description
        binding.buttonDeletePlace.setOnClickListener {
            onDeleteClickListener.invoke(place)
            dismiss()
        }
        binding.buttonCancelPlace.setOnClickListener {
            dismiss()
        }
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}