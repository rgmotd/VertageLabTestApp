package com.example.vertagelabtestapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.vertagelabtestapp.MainActivity
import com.example.vertagelabtestapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupOnClickListeners()
    }

    private fun setupToolbar() {
        val toolbar = (requireActivity() as MainActivity).toolbar
        toolbar.title = "Login screen"
    }

    private fun setupOnClickListeners() {
        binding.btnLogin.setOnClickListener {
            if (validate(binding.etEmail.text.toString(), binding.etPassword.text.toString())) {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToMapFragment(
                        binding.etEmail.text.toString()
                    )
                )
            } else {
                Toast.makeText(requireContext(), "Please fill up the form", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun validate(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}