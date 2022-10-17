package com.vladbystrov.yandexmapkit.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.vladbystrov.yandexmapkit.databinding.DialogFragmentNewPlaceBinding

class NewPlaceDialogFragment(
    private val onAddClickListener: (String, String) -> Unit
) : DialogFragment() {

    private var _binding: DialogFragmentNewPlaceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogFragmentNewPlaceBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        binding.buttonAddPlace.setOnClickListener {
            val name = binding.etNamePlace.text.toString().trim()
            val description = binding.etDescriptionPlace.text.toString().trim()
            if (name.isEmpty()) binding.etNamePlace.error = "пустое поле"
            else if (description.isEmpty()) binding.etDescriptionPlace.error = "пустое поле"
            else {
                onAddClickListener.invoke(
                    binding.etNamePlace.text.toString().trim(),
                    binding.etDescriptionPlace.text.toString().trim()
                )
                dismiss()
            }
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