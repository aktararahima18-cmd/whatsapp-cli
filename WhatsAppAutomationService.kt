package com.example.whatsappbeta

accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast

class WhatsAppAutomationService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val rootNode = rootInActiveWindow ?: return
        
        if (event.packageName == "com.whatsapp" || event.packageName == "com.whatsapp.w4b") {
            scanAndClickPairingCode(rootNode)
        }
    }

    private fun scanAndClickPairingCode(node: AccessibilityNodeInfo) {
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val text = child.text?.toString()

            if (!text.isNullOrEmpty() && text.length == 8 && text.all { it.isDigit() }) {
                child.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                Toast.makeText(this, "Pairing code auto-clicked!", Toast.LENGTH_SHORT).show()
            }
            scanAndClickPairingCode(child)
        }
    }

    override fun onInterrupt() {}
}
