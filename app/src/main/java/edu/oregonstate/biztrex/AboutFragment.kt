package edu.oregonstate.biztrex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.oregonstate.biztrex.databinding.FragmentAboutBinding

/**
 * Screen for displaying app info
 */
class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    companion object {
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(layoutInflater)
        return binding.root
    }
}