
    import android.R.attr.label
    import android.content.ClipData
    import android.content.ClipboardManager


    private fun copyToClipboard(link: String) {
        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label.toString(), link)
        clipboard.setPrimaryClip(clip)
    }
