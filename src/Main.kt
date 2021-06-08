package cryptography

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.ceil
import kotlin.math.pow

fun main() {
    while (true) {
        println("Task (hide, show, exit):")

        when (val input = readLine()!!) {
            "exit" -> {
                println("Bye!")
                break
            }
            "hide" -> hide()
            "show" -> show()
            else -> println("Wrong task: $input")
        }
    }
}

fun hide() {
    println("Input image file:")

    val inputFile = File(readLine()!!)

    println("Output image file:")

    val outputFile = File(readLine()!!)

    println("Message to hide:")

    val message = readLine()!!

    println("Password:")

    val password = readLine()!!

    if (!inputFile.exists()) println("Can't read input file!") else {
        val inputImage = ImageIO.read(inputFile)

        if (inputImage.height * inputImage.width < message.length * 8 + 24) {
            println("The input image is not large enough to hold this message.")
        } else {
            var i = 0
            var encryptedMessage = ""
            val bitMessage = message.toByteArray().toBitString()
            val bitPassword = password.toBitPassword(message.length)

            bitMessage.forEachIndexed { ind, char ->
                encryptedMessage = encryptedMessage.plus(if (char == bitPassword[ind]) '0' else '1') }

            encryptedMessage = encryptedMessage.plus("000000000000000000000011")

            loop1@ for (y in 0 until inputImage.height) {
                for (x in 0 until inputImage.width) {
                    if (i <= encryptedMessage.lastIndex) {
                        inputImage.setRGB(x, y, inputImage.hideBit(x, y, encryptedMessage[i]))
                        i++
                    } else break@loop1
                }
            }

            ImageIO.write(inputImage, "png", outputFile)

            println("Message saved in ${outputFile.name} image.")
        }
    }
}

fun show() {
    println("Input image file:")

    val inputFile = File(readLine()!!)

    println("Password:")

    val password = readLine()!!

    if (!inputFile.exists()) println("Can't read input file!") else {
        val inputImage = ImageIO.read(inputFile)
        var encryptedMessage = ""

        loop2@ for (y in 0 until inputImage.height) {
            for (x in 0 until inputImage.width) {
                if (encryptedMessage.endsWith("000000000000000000000011")) {
                    encryptedMessage = encryptedMessage.dropLast(24)

                    break@loop2
                }

                encryptedMessage += Color(inputImage.getRGB(x, y)).blue.toString(2).last()
            }
        }

        var message = ""
        val bitPassword = password.toBitPassword(encryptedMessage.length / 8)

        encryptedMessage.forEachIndexed { ind, char ->
            message = message.plus(if (char == bitPassword[ind]) '0' else '1') }

        println("Message:\n${message.toStringFromBinary()}")
    }
}

fun BufferedImage.hideBit(x: Int, y: Int, bit: Char): Int {
    val newBlue = Color(this.getRGB(x, y)).blue.toBinaryWithLSB(bit).toDecimalFromBinary()

    return Color(Color(this.getRGB(x, y)).red, Color(this.getRGB(x, y)).green, newBlue).rgb
}

fun ByteArray.toBitString(): String {
    var string = ""

    this.forEach { string += "%08d".format(it.toString(2).toInt()) }

    return string
}

fun Int.toBinaryWithLSB(newBit: Char): String = this.toString(2).dropLast(1) + newBit

fun String.toDecimalFromBinary(): Int {
    var binaryNum = this.toInt()
    var decimalNum = 0
    var i = 0
    var remainder: Int

    while (binaryNum != 0) {
        remainder = binaryNum % 10
        binaryNum /= 10
        decimalNum += (remainder * 2.0.pow(i.toDouble())).toInt()
        ++i
    }

    return decimalNum
}

fun String.toStringFromBinary(): String {
    val byteList = mutableListOf<Byte>()

    for (i in 0..this.lastIndex step 8) {
        byteList.add(this.subSequence(i..i + 7).toString().toDecimalFromBinary().toByte())
    }

    return byteList.toByteArray().toString(Charsets.UTF_8)
}

fun String.toBitPassword(messageLength: Int): String {
    var bitPassword = if (messageLength > this.length) {
        this.repeat(ceil(messageLength / this.length.toDouble()).toInt())
    } else this

    bitPassword = bitPassword.dropLast(bitPassword.length - messageLength).toByteArray().toBitString()

    return bitPassword
}
