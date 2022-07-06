package com.ucmart.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.navArgs
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ucmart.R
import com.ucmart.databinding.FragmentVerifyOtpBinding
import java.util.concurrent.TimeUnit

class VerifyOtpFragment : Fragment() {

    private lateinit var binding: FragmentVerifyOtpBinding

    private val args by navArgs<VerifyOtpFragmentArgs>()

    private lateinit var mAuth: FirebaseAuth

    private var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifyOtpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.subtitle.text = HtmlCompat.fromHtml(
            getString(R.string.label_otp_sent, args.phoneNumber),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        mAuth = FirebaseAuth.getInstance()

        mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                println("credential ${credential.smsCode}")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                println("exception ${exception.message}")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

            }
        }

        if (mAuth.currentUser != null) {
            println("CurrentUser ${mAuth.currentUser}")
            mAuth.signOut()
        } else {
            startPhoneNumberVerification()
        }
    }

    private fun startPhoneNumberVerification() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91${args.phoneNumber}",   // PhoneNumber with Country Code (+91 India)
            120,
            TimeUnit.SECONDS,   // Unit of timeout
            requireActivity(),  // Activity for callback binding
            mCallBacks!!    // OnVerificationStateChangedCallbacks
        )
    }

    private fun verifyPhoneNumberWithCode(
        verificationId: String,
        code: String
    ) {
        val credentials = PhoneAuthProvider.getCredential(verificationId, code)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = task.result?.user?.uid
                println("uid $uid")
            } else {
                println("SignIn Issue ${task.exception?.message}")
            }
        }
    }

}