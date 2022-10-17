package com.vladbystrov.yandexmapkit.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vladbystrov.yandexmapkit.databinding.DialogListPlaceBinding
import com.vladbystrov.yandexmapkit.domain.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PlacesBottomSheetDialogFragment(
    private val places: Flow<List<Place>>,
    private val onItemClicked: (Place) -> Unit
    ): BottomSheetDialogFragment() {

    private var _binding: DialogListPlaceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogListPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewDialog.layoutManager = LinearLayoutManager(requireContext())
        val adapter = PlaceAdapter {
            onItemClicked(it)
            dismiss()
        }
        binding.recyclerViewDialog.adapter = adapter

        lifecycle.coroutineScope.launch {
            places.collect() {
                adapter.submitList(it)
            }
        }

    }
}