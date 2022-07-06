package com.ucmart.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ucmart.R
import com.ucmart.databinding.FragmentLoginDialogBinding
import com.ucmart.databinding.FragmentVerifyOtpBinding

class LoginDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentLoginDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.login.setOnClickListener {
            val action = LoginDialogFragmentDirections.actionNavLoginToNavVerifyOtp(
                binding.phoneNumber.text.toString()
            )
            findNavController().navigate(action)
        }
    }
}