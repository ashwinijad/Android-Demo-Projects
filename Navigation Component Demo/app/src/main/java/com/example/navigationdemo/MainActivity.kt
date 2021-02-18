package com.example.navigationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    companion object {
        private val Fragment.navController get() = Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment)

    }
}

/*
class BlankFragment : Fragment(R.layout.fragment_blank) {
    private var counter: Int = 0
    var gotoFragment2: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gotoFragment2 = view.findViewById(R.id.button_frag1);
        gotoFragment2?.setOnClickListener {
            navController.navigate(R.id.action_blankFragment_to_blankFragment2)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("counter", counter)
    }
}

class BlankFragment2 : Fragment(R.layout.fragment_blank2) {
var btn:Button?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn=view.findViewById(R.id.button_frag2)

        btn?.setOnClickListener { navController.navigate(R.id.action_blankFragment2_to_blankFragment) }
    }

}

*/
/*
class Fragment3 : Fragment(R.layout.fragment_3) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnProceed.setOnClickListener { navController.navigate(R.id.action_fragment_3_to_fragment_1) }
    }
*//*




private val Fragment.navController get() = Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment)*/
