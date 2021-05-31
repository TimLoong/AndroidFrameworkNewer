package com.otmiao.android.framework.handler

import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.otmiao.android.framework.handler.databinding.FragmentFirstBinding

/**
 * onViewCreated subThreadChangeUi can change ui and show toast ,why?
 * button0 click event error,why?
 * button1 click event error ,why?
 * button2 click event error ,why?
 * button3 click event ,show toast, why?
 * button4 click event error , why?
 */
class HandlerFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        subThreadChangeUi("before:sub thread change text","before:sub thread show toast")

        binding.button0.setOnClickListener {
            Looper.prepare()
            Toast.makeText(context, "show toast in sub thread with looper", Toast.LENGTH_SHORT)
                .show()
            Looper.loop()
//            2021-06-01 00:25:13.149 14555-14555/com.otmiao.android.framework.handler D/AndroidRuntime: Shutting down VM
//            2021-06-01 00:25:13.150 14555-14555/com.otmiao.android.framework.handler E/AndroidRuntime: FATAL EXCEPTION: main
//            Process: com.otmiao.android.framework.handler, PID: 14555
//            java.lang.RuntimeException: Only one Looper may be created per thread
//            at android.os.Looper.prepare(Looper.java:108)
//            at android.os.Looper.prepare(Looper.java:103)
//            at com.otmiao.android.framework.handler.HandlerFragment$onViewCreated$2.onClick(HandlerFragment.kt:48)
//            at android.view.View.performClick(View.java:7140)
//            at com.google.android.material.button.MaterialButton.performClick(MaterialButton.java:992)
//            at android.view.View.performClickInternal(View.java:7117)
//            at android.view.View.access$3500(View.java:801)
//            at android.view.View$PerformClick.run(View.java:27351)
//            at android.os.Handler.handleCallback(Handler.java:883)
//            at android.os.Handler.dispatchMessage(Handler.java:100)
//            at android.os.Looper.loop(Looper.java:214)
//            at android.app.ActivityThread.main(ActivityThread.java:7356)
//            at java.lang.reflect.Method.invoke(Native Method)
//            at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:492)
//            at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:930)
//            2021-06-01 00:25:13.165 14555-14555/? I/Process: Sending signal. PID: 14555 SIG: 9
        }


        binding.button1.setOnClickListener {
            Thread {
                Toast.makeText(context, "sub thread use toast", Toast.LENGTH_SHORT).show()
                //2021-06-01 00:17:30.547 13751-13799/com.otmiao.android.framework.handler E/AndroidRuntime: FATAL EXCEPTION: Thread-3
                //    Process: com.otmiao.android.framework.handler, PID: 13751
                //    java.lang.RuntimeException: Can't toast on a thread that has not called Looper.prepare()
                //        at android.widget.Toast$TN.<init>(Toast.java:407)
                //        at android.widget.Toast.<init>(Toast.java:121)
                //        at android.widget.Toast.makeText(Toast.java:286)
                //        at android.widget.Toast.makeText(Toast.java:276)
                //        at com.otmiao.android.framework.handler.FirstFragment$onViewCreated$2$1.run(FirstFragment.kt:49)
                //        at java.lang.Thread.run(Thread.java:919)
                //2021-06-01 00:17:30.565 13751-13799/com.otmiao.android.framework.handler I/Process: Sending signal. PID: 13751 SIG: 9
            }.start()
        }

        binding.button2.setOnClickListener {
            Thread {
                binding.buttonNext.text = "NEXT--> sub thread change text";
                //2021-06-01 00:18:00.446 13837-13883/com.otmiao.android.framework.handler E/AndroidRuntime: FATAL EXCEPTION: Thread-3
                //    Process: com.otmiao.android.framework.handler, PID: 13837
                //    android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
                //        at android.view.ViewRootImpl.checkThread(ViewRootImpl.java:8191)
                //        at android.view.ViewRootImpl.requestLayout(ViewRootImpl.java:1420)
                //        at android.view.View.requestLayout(View.java:24469)
                //        at android.view.View.requestLayout(View.java:24469)
                //        at android.view.View.requestLayout(View.java:24469)
                //        at android.view.View.requestLayout(View.java:24469)
                //        at android.view.View.requestLayout(View.java:24469)
                //        at android.view.View.requestLayout(View.java:24469)
                //        at android.view.View.requestLayout(View.java:24469)
                //        at androidx.constraintlayout.widget.ConstraintLayout.requestLayout(ConstraintLayout.java:3146)
                //        at android.view.View.requestLayout(View.java:24469)
                //        at android.view.View.requestLayout(View.java:24469)
                //        at androidx.constraintlayout.widget.ConstraintLayout.requestLayout(ConstraintLayout.java:3146)
                //        at android.view.View.requestLayout(View.java:24469)
                //        at android.widget.TextView.checkForRelayout(TextView.java:9681)
                //        at android.widget.TextView.setText(TextView.java:6269)
                //        at android.widget.TextView.setText(TextView.java:6097)
                //        at android.widget.TextView.setText(TextView.java:6049)
                //        at com.otmiao.android.framework.handler.FirstFragment$onViewCreated$3$1.run(FirstFragment.kt:63)
                //        at java.lang.Thread.run(Thread.java:919)
                //2021-06-01 00:18:00.537 13837-13871/com.otmiao.android.framework.handler D/OpenGLRenderer: endAllActiveAnimators on 0x6faa5e0700 (RippleDrawable) with handle 0x6faa018de0
            }.start()
        }

        binding.button3.setOnClickListener {
            Thread {
                Looper.prepare()
                Toast.makeText(context, "show toast in sub thread with looper", Toast.LENGTH_SHORT)
                    .show()
                Looper.loop()
            }.start()
        }

        binding.button4.setOnClickListener {
            subThreadChangeUi("sub thread change text","sub thread show toast")
            //2021-06-01 00:08:15.874 12798-12851/com.otmiao.android.framework.handler E/AndroidRuntime: FATAL EXCEPTION: Thread-3
            //    Process: com.otmiao.android.framework.handler, PID: 12798
            //    android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
            //        at android.view.ViewRootImpl.checkThread(ViewRootImpl.java:8191)
            //        at android.view.ViewRootImpl.requestLayout(ViewRootImpl.java:1420)
            //        at android.view.View.requestLayout(View.java:24469)
            //        at android.view.View.requestLayout(View.java:24469)
            //        at android.view.View.requestLayout(View.java:24469)
            //        at android.view.View.requestLayout(View.java:24469)
            //        at android.view.View.requestLayout(View.java:24469)
            //        at android.view.View.requestLayout(View.java:24469)
            //        at android.view.View.requestLayout(View.java:24469)
            //        at androidx.constraintlayout.widget.ConstraintLayout.requestLayout(ConstraintLayout.java:3146)
            //        at android.view.View.requestLayout(View.java:24469)
            //        at android.view.View.requestLayout(View.java:24469)
            //        at androidx.constraintlayout.widget.ConstraintLayout.requestLayout(ConstraintLayout.java:3146)
            //        at android.view.View.requestLayout(View.java:24469)
            //        at android.widget.TextView.checkForRelayout(TextView.java:9681)
            //        at android.widget.TextView.setText(TextView.java:6269)
            //        at android.widget.TextView.setText(TextView.java:6097)
            //        at android.widget.TextView.setText(TextView.java:6049)
            //        at com.otmiao.android.framework.handler.FirstFragment$subThreadChangeUi$1.run(FirstFragment.kt:94)
            //        at java.lang.Thread.run(Thread.java:919)
            //2021-06-01 00:08:15.892 12798-12851/? I/Process: Sending signal. PID: 12798 SIG: 9
        }


    }

    private fun subThreadChangeUi( buttonText:String,toastString:String) {
        Thread {
            Looper.prepare()
            binding.buttonNext.text = "NEXT--> $buttonText";
            Toast.makeText(context, toastString, Toast.LENGTH_SHORT).show()
            Looper.loop()
        }.start()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}