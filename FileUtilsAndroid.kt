
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.*

object FileUtils {

    private val TAG = FileUtils::class.java.simpleName

    @Throws(IOException::class)
    //This function will copy the source file into the target file
    //The formats will be of File only
    fun copy(sourceLocation: File, targetLocation: File) {

        try {

            if (sourceLocation.isDirectory) {
                copyDirectory(sourceLocation, targetLocation)
            } else {
                copyFile(sourceLocation, targetLocation)
            }
        } catch (e: Exception) {
            Log.e(
                TAG,
                "Unable to copy file to the target directory cause : ${e.cause} with message ${e.message}"
            )
        }
    }

    @Throws(IOException::class)
    //If the target is a directory, then copy the whole directory
    private fun copyDirectory(source: File, target: File) {

        try {
            //If the target does not exists, mkdir
            if (!target.exists()) {
                target.mkdir()
            }

            //Copy all the files of the directory
            for (f in source.list()!!) {
                copy(File(source, f), File(target, f))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Unable to copy directory. ${e.message} ${e.cause}")
        }
    }

    /**
     * This will take the input stream and pour in output stream. And this is when the file is provided
     */
    @Throws(IOException::class, FileNotFoundException::class)
    private fun copyFile(source: File, target: File) {
        try {
            val inputStream = FileInputStream(source)
            val out = FileOutputStream(target)
            out.write(inputStream.readBytes())
            out.flush()
            out.close()
        } catch (e: IOException) {
            Log.e(TAG, "Unable to copy file with ${e.message} ${e.cause}")
            throw IOException()

        }
    }

    /**
     * Overloaded Copy function with given Input, OutputStream
     */
    @Throws(IOException::class)
    private fun copyFile(inputStream: InputStream, out: OutputStream) {
        out.write(inputStream.readBytes())
        out.flush()
        out.close()
    }

    /**
     * Takes in a file and saves it.
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)              //Requires API 19
    fun save(file: File, data: ByteArray, bmp: Bitmap?): File {

        try {
            FileOutputStream(file).use { fileOutputStream ->

                bmp?.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                    ?: fileOutputStream.write(data)
                fileOutputStream.flush()
            }
        } catch (e: IOException) {
            Log.e(TAG, "Unable to save file ${e.message} ${e.cause}")
            e.printStackTrace()
        }
        return file
    }

    /**
     *   To check if the file exists, and if not, return false if it can not be even made or the file has some problems with its parent directory
     */
    fun createDirIfNotExists(path: String): Boolean {

        var ret = true
        val file = File(path)
        if (!file.exists()) {
            if (!file.mkdirs()) {
                ret = false
            }
        }
        return ret
    }

    /**
     * Delete a file on a specified path
     */
    fun deleteFile(path: String): Boolean {
        val f = File(path)

        return if (f.exists()) {
            f.delete()
        } else false

    }

    /**
     * Delete a file with a given file
     */
    fun deleteFile(f: File): Boolean {

        return if (f.exists()) {
            f.delete()
        } else false

    }

    /**
     * Delete a directory
     * Recursive function
     */
    fun deleteDir(fileOrDirectory: File) {
        try {


            if (fileOrDirectory.isDirectory)
                for (child in fileOrDirectory.listFiles()!!)
                    deleteDir(child)
            fileOrDirectory.delete()

        } catch (e: java.lang.Exception) {
            Log.e(
                TAG,
                "In the recursive call for the delete directory function failing with exception ${e.message} ${e.cause}"
            )
        }
    }

}